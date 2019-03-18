package com.vbn.blog.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.vbn.blog.common.MyMapper;
import com.vbn.blog.entity.User;

@Mapper
public interface IUserMapper extends MyMapper<User> {

}
