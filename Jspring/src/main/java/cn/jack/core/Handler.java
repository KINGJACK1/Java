package cn.jack.core;

import cn.jack.annotation.Jcontroller;

import java.lang.reflect.Method;

/**
 * @Auther: ZhangXing
 * @Date: 2019/1/6 * @Description: cn.jack.core * @version: 1.0
 */
public class Handler {
    private Object con;
    private Method method;

    public Object getCon() {
        return con;
    }

    public void setCon(Jcontroller con) {
        this.con = con;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Handler(Object con, Method method) {
        this.con = con;
        this.method = method;
    }

}
