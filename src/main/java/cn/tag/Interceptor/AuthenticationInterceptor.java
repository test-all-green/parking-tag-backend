package cn.tag.Interceptor;

import cn.tag.entity.Employee;
import cn.tag.entity.User;
import cn.tag.exception.CustomException;
import cn.tag.service.EmployeeService;
import cn.tag.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {
    public static final String NULL_TOKEN = "无token，请重新登录";
    public static final String CODE_100 = "100";
    public static final String USER_NOT_EXIT = "用户不存在，请重新登录";
    public static final String CODE_101 = "101";
    public static final String MESSAGE_NOT_PROMISION = "没有权限";
    public static final String TOKEN_BROKEN = "token过期";
    public static final String CODE_401 = "401";
    @Autowired
    EmployeeService userService;

    @Autowired
    UserService userService1;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        try {
            if(method.isAnnotationPresent(EmployeeToken.class)){
                EmployeeToken employeeToken = method.getAnnotation(EmployeeToken.class);
                if (employeeToken.required()) {
                    // 执行认证
                    if (token == null) {
                        throw new CustomException(CODE_100, NULL_TOKEN);
                    }
                    // 获取 token 中的 user id
                    int userId;
                    userId = Integer.parseInt(JWT.decode(token).getAudience().get(0));
                    User user = userService1.findUserById(userId);
                    if (user == null) {
                        throw new CustomException(CODE_100, USER_NOT_EXIT);
                    }
                    // 验证 token
                    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUserPassword())).build();
                    jwtVerifier.verify(token);
                    return true;
                }
            }
            else if (method.isAnnotationPresent(UserLoginToken.class)) {
                UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
                if (userLoginToken.required()) {
                    // 执行认证
                    if (token == null) {
                        throw new CustomException(CODE_100, NULL_TOKEN);
                    }
                    // 获取 token 中的 user id
                    int userId;
                    userId = Integer.parseInt(JWT.decode(token).getAudience().get(0));
                    Employee user = userService.findUserById(userId);
                    if (user == null) {
                        throw new CustomException(CODE_100, USER_NOT_EXIT);
                    }
                    // 验证 token
                    String role = JWT.decode(token).getAudience().get(1);
                    AuthToken authToken = method.getAnnotation(AuthToken.class);
                    System.out.println("role:" + role + ",role_name:" + authToken.role_name());
                    //if()
                    if (!role.equals(authToken.role_name())) {
                        throw new CustomException(CODE_101, MESSAGE_NOT_PROMISION);
                    }
                    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getEmployeePassword())).build();
                    jwtVerifier.verify(token);
                    return true;
                }
            }
        } catch (JWTVerificationException e) {
            throw new CustomException(CODE_401, TOKEN_BROKEN);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
