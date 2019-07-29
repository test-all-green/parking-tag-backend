package cn.tag.Interceptor;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeToken {
    boolean required() default true;
}
