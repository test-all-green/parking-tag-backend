package cn.tag.service;

import cn.tag.entity.Employee;
import cn.tag.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

	public String getToken(Employee user) {
		Date start = new Date();
		//设置有效时间（一小时有效时间）
		long currentTime = System.currentTimeMillis() + 24 *60 * 60 * 1000;
		Date end = new Date(currentTime);
		String token = "";
		
		token = JWT.create().withAudience(user.getId()+"",user.getRoleId()+"").withIssuedAt(start).withExpiresAt(end)
				.sign(Algorithm.HMAC256(user.getEmployeePassword()));
		return token;
	}
	public String getToken(User user) {
		Date start = new Date();
		//设置有效时间（一小时有效时间）
		long currentTime = System.currentTimeMillis() + 24 *60 * 60 * 1000;
		Date end = new Date(currentTime);
		String token = "";

		token = JWT.create().withAudience(user.getId()+"").withIssuedAt(start).withExpiresAt(end)
				.sign(Algorithm.HMAC256(user.getUserPassword()));
		return token;
	}
}
