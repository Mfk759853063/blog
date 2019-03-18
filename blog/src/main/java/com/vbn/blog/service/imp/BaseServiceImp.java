package com.vbn.blog.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vbn.blog.service.IBaseService;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.orderbyhelper.OrderByHelper;

public class BaseServiceImp<T> implements IBaseService<T> {

	@Autowired
    private Mapper<T> mapper;

	@Override
	public T queryById(Object id) throws Exception {
		// TODO Auto-generated method stub
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<T> queryAll() throws Exception {
		// TODO Auto-generated method stub
		return this.mapper.select(null);
	}

	@Override
	public List<T> queryListByWhere(T param) throws Exception {
		// TODO Auto-generated method stub
		return this.mapper.select(param);
	}

	@Override
	public Integer queryCount(T param) {
		// TODO Auto-generated method stub
		return this.mapper.selectCount(param);
	}

	@Override
	public List<T> queryListByWhere(T param, String orderBy) throws Exception{
		// TODO Auto-generated method stub
		OrderByHelper.orderBy(orderBy);
    	return this.mapper.select(param);
	}

	@Override
	public PageInfo<T> queryPageListByWhere(T param, Integer page, Integer rows) throws Exception{
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
        List<T> list = this.queryListByWhere(param);
        return new PageInfo<T>(list);
	}

	@Override
	public PageInfo<T> queryPageListByWhere(T param, String orderBy, Integer page, Integer rows) throws Exception{
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows, orderBy);
    	List<T> list = this.queryListByWhere(param,orderBy);
        return new PageInfo<T>(list);

	}

	@Override
	public T queryOne(T param) throws Exception {
		// TODO Auto-generated method stub
		return this.mapper.selectOne(param);
	}

	@Override
	@Transactional
	public Integer save(T param) throws Exception {
		// TODO Auto-generated method stub
		return this.mapper.insert(param);
	}

	@Override
	@Transactional
	public Integer saveSelect(T param) throws Exception {
		// TODO Auto-generated method stub
		return this.mapper.insertSelective(param);
	}

	@Override
	@Transactional
	public Integer update(T param) throws Exception {
		// TODO Auto-generated method stub
		return this.mapper.updateByPrimaryKey(param);
	}

	@Override
	@Transactional
	public Integer updateSelective(T param) throws Exception {
		// TODO Auto-generated method stub
		return this.mapper.updateByPrimaryKeySelective(param);
	}

	@Override
	@Transactional
	public Integer deleteById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional
	public Integer deleteByIds(Class<T> clazz, List<T> values) throws Exception {
		// TODO Auto-generated method stub
		Example example = new Example(clazz);
        example.createCriteria().andIn("id", values);
        return this.mapper.deleteByExample(example);
	}
	
	
}
