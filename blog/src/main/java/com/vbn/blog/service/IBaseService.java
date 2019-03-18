package com.vbn.blog.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

public interface IBaseService <T> {
    //根據id查詢實體
    public T queryById(Object id) throws Exception;
    
    //查詢所有
    public List<T> queryAll() throws Exception;
    
    //條件查詢
    public List<T> queryListByWhere(T param) throws Exception;
    
    //查詢記錄數
    public Integer queryCount(T param) throws Exception ;
    
    // 排序 eg: "id desc"
    public List<T>queryListByWhere(T param, String orderBy) throws Exception;
    
    //分頁
    public PageInfo<T> queryPageListByWhere(T param,Integer page,Integer rows) throws Exception;
    
    // 分頁排序 eg: "id desc"
    public PageInfo<T>queryPageListByWhere(T param, String orderBy,Integer page,Integer rows) throws Exception;
    
    //查詢一條記錄
    public T queryOne(T param) throws Exception;
    
    //插入
    public Integer save(T param) throws Exception;
    
    //新增非空欄位
    public Integer saveSelect(T param) throws Exception;
    
    //根據主鍵更新
    public Integer update(T param) throws Exception;
    
    //根據主鍵更新非空欄位
    public Integer updateSelective(T param) throws Exception;
       
    //根據主鍵刪除
    public Integer deleteById(Long id) throws Exception;
    
    //批量刪除
    public Integer deleteByIds(Class<T> clazz,List<T> values) throws Exception;
}
