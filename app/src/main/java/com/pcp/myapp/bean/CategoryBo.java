package com.pcp.myapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : huazi
 * time   : 2021/4/27
 * company：inkr
 * desc   : 分类
 */
public class CategoryBo implements Serializable {

    private Integer code;
    private String msg;
    private List<String> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
