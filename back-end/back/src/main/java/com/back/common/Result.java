package com.back.common;

import lombok.Data;
@Data
public class Result {

    private int code; // 编码 200成功 400失败
    private String msg; // 成功or失败
    private Long total; // 总记录数
    private Object data; // 数据

    public static Result fail () {
        Result res = new Result();
        res.setCode(400);
        res.setMsg("失败");
        res.setTotal(0L);
        res.setData(null);
        return res;
    }

    public static Result succ() {
        Result res = new Result();
        res.setCode(200);
        res.setMsg("成功");
        res.setTotal(0L);
        res.setData(null);
        return res;
    }

    public static Result succ(Object data) {
        Result res = new Result();
        res.setCode(200);
        res.setMsg("成功");
        res.setTotal(0L);
        res.setData(data);
        return res;
    }

    public static Result succ(Object data, Long total) {
        Result res = new Result();
        res.setCode(200);
        res.setMsg("成功");
        res.setTotal(total);
        res.setData(data);
        return res;
    }

    public static Result result(int code, String msg, Long total, Object data){
        Result res = new Result();
        res.setCode(code);
        res.setMsg(msg);
        res.setTotal(total);
        res.setData(data);
        return res;
    }
}