package com.vbn.blog.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.vbn.blog.common.JSONResult;
import com.vbn.blog.entity.User;
import com.vbn.blog.service.IUserService;
import com.vbn.blog.util.DateUtils;

@RestController

@RequestMapping("/admin")
public class UserController {
	
	@Autowired
	IUserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	private JSONObject login(@RequestBody User user) throws Exception {
		try {
			user.setStatus(1);
			User have = userService.queryOne(user);
			if (have != null) {
				have.setAccessToken(User.getToken(have));
				have.setLastLoginTime(DateUtils.date2TimeStamp(new Date()));
				return JSONResult.fillResultString(0, "登录成功", have);
			} else {
				return JSONResult.fillResultString(1, "失败", null); 
			}
			 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return JSONResult.fillResultString(1, "失败", null); 
		}
	}
	

}
