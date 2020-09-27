package com.ecy.show.util;


import com.google.gson.Gson;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class InterceptorUtil {
    /**
     * 为response设置header，实现跨域
     */
    public static void allowCORS(HttpServletRequest request, HttpServletResponse response){
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.setStatus(org.springframework.http.HttpStatus.OK.value());
    }

    public static  void AccessFail(ServletRequest request, ServletResponse response, TResultCode tResultCode) throws IOException {
        InterceptorUtil.allowCORS((HttpServletRequest) request,(HttpServletResponse) response);
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(TResult.failure(tResultCode)));
        out.flush();
        out.close();
    }

    public static  void AccessFail(ServletRequest request, ServletResponse response, Exception ex) throws IOException {
        InterceptorUtil.allowCORS((HttpServletRequest) request,(HttpServletResponse) response);
        PrintWriter out = response.getWriter();
        ((HttpServletResponse) response).setStatus(401);
        out.println(new Gson().toJson(TResult.failure(ex.getMessage())));
        out.flush();
        out.close();
    }
}
