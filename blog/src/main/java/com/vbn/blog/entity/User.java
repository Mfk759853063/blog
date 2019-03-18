package com.vbn.blog.entity;

import javax.persistence.Table;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.Data;

@Data

@Table(name="t_user")

public class User {

	String Id;
	
    String userName;
    
    String passWord;
    
    String accessToken;
    
    Long createTime;
    
    Integer status;
    
    Long lastLoginTime;
    
    public static String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getPassWord()));
        return token;
    }
    
}
