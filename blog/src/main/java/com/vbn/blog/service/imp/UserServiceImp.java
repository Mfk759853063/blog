package com.vbn.blog.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vbn.blog.mapper.IUserMapper;
import com.vbn.blog.entity.User;
import com.vbn.blog.service.IUserService;


@Service
public class UserServiceImp extends BaseServiceImp<User> implements IUserService {

	
	@Autowired
	IUserMapper userMapper;
}
