package com.vbn.blog.common;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

/**
 * Created by vbn on 2018/12/18.
 */
public class JSONResult {
    public static JSONObject fillResultString(Integer status, String message, Object result) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("msg", message);
        jsonObject.put("data", result);
        return jsonObject;
    }
    
    public static JSONObject fillResultStringWithPageInfo(Integer status, String message, PageInfo list) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("msg", message);
        JSONObject dataObject = new JSONObject();
        dataObject.put("total", list.getTotal());
        dataObject.put("list", list.getList());
        dataObject.put("total", list.getTotal());
        dataObject.put("isFirstPage", list.isIsFirstPage());
        dataObject.put("isLastPage", list.isIsLastPage());
        jsonObject.put("data", dataObject);
        return jsonObject;
    }
}
