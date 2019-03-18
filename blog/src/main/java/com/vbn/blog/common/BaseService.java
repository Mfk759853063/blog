package com.vbn.blog.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.orderbyhelper.OrderByHelper;


/**
 * 基本Service
 *
 * @auther ny by:dbw
 * @create 2018-06-09
 */

public abstract class BaseService<T>  {

    @Autowired
    private Mapper<T> mapper;
       
    //根據id查詢實體
    public T queryById(Object id){
        return this.mapper.selectByPrimaryKey(id);
    }
    
    //查詢所有
    public List<T> queryAll(){
        return this.mapper.select(null);
    }
    
    //條件查詢
    public List<T> queryListByWhere(T param){
        return this.mapper.select(param);
    }
    
    //查詢記錄數
    public Integer queryCount(T param){
        return this.mapper.selectCount(param);
    }
    
    // 排序 eg: "id desc"
    public List<T>queryListByWhere(T param, String orderBy) {
    	OrderByHelper.orderBy(orderBy);
    	return this.mapper.select(param);
    }
    
    //分頁
    public PageInfo<T> queryPageListByWhere(T param,Integer page,Integer rows){
        PageHelper.startPage(page, rows);
        List<T> list = this.queryListByWhere(param);
        return new PageInfo<T>(list);

    }
    
    // 分頁排序 eg: "id desc"
    public PageInfo<T>queryPageListByWhere(T param, String orderBy,Integer page,Integer rows) {
    	
    	PageHelper.startPage(page, rows, orderBy);
    	List<T> list = this.queryListByWhere(param,orderBy);
        return new PageInfo<T>(list);
    }
    
    //查詢一條記錄
    public T queryOne(T param){
        return this.mapper.selectOne(param);
    }
    
    //插入
    public Integer save(T param){
        return this.mapper.insert(param);
    }
    
    //新增非空欄位
    public Integer saveSelect(T param){
        return this.mapper.insertSelective(param);
    }
    
    //根據主鍵更新
    public Integer update(T param){
        return this.mapper.updateByPrimaryKey(param);
    }
    
    //根據主鍵更新非空欄位
    public Integer updateSelective(T param){
        return this.mapper.updateByPrimaryKeySelective(param);
    }
       
    //根據主鍵刪除
    public Integer deleteById(Long id){
        return this.mapper.deleteByPrimaryKey(id);
    }     
    
    //批量刪除
    public Integer deleteByIds(Class<T> clazz,List<Object> values){
        Example example = new Example(clazz);
        example.createCriteria().andIn("id", values);
        return this.mapper.deleteByExample(example);
    }  
    
}
