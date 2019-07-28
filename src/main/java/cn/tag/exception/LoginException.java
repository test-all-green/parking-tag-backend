package cn.tag.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginException extends RuntimeException {
    private String errMessage;


    public enum LoginMsg {
        NOT_TOKEN,
        INVALID_PWD,
        no_exist_user
        ;
    }
}

