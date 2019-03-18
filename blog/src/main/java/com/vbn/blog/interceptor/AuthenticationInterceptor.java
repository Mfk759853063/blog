package com.vbn.blog.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.vbn.blog.annotation.PassToken;
import com.vbn.blog.annotation.UserLoginToken;
import com.vbn.blog.entity.User;
import com.vbn.blog.service.IUserService;

public class AuthenticationInterceptor implements HandlerInterceptor {
	
	@Autowired
	IUserService userservice;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                	JSONObject respJsonObject = new JSONObject();
                	respJsonObject.put("status", "-1");
                	respJsonObject.put("message", "无token，请重新登录");
                	respJsonObject.put("data", "");
                    throw new RuntimeException(respJsonObject.toJSONString());
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                	JSONObject respJsonObject = new JSONObject();
                	respJsonObject.put("status", "-1");
                	respJsonObject.put("message", "无token，请重新登录");
                	respJsonObject.put("data", "");
                    throw new RuntimeException(respJsonObject.toJSONString());
                }
                User user = userservice.queryById(userId);
                if (user == null) {
                	JSONObject respJsonObject = new JSONObject();
                	respJsonObject.put("status", "-1");
                	respJsonObject.put("message", "无token，请重新登录");
                	respJsonObject.put("data", "");
                    throw new RuntimeException(respJsonObject.toJSONString());
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassWord())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                	JSONObject respJsonObject = new JSONObject();
                	respJsonObject.put("status", "-1");
                	respJsonObject.put("message", "无token，请重新登录");
                	respJsonObject.put("data", "");
                    throw new RuntimeException(respJsonObject.toJSONString());
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, 
                                  HttpServletResponse httpServletResponse, 
                            Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, 
                                          HttpServletResponse httpServletResponse, 
                                          Object o, Exception e) throws Exception {
    }
}
