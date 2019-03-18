package com.vbn.blog.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 该接口不能被扫描到，否则会出错
 * @author Administrator
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
