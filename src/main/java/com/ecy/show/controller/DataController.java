package com.ecy.show.controller;

import com.ecy.show.exception.BusinessException;
import com.ecy.show.util.MyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Jack魏
 * @since 2019-12-13 3:36 PM
 */
@RestController
@RequestMapping("/verifyDate")
@Api(tags = "验证时间")
public class DataController {
    private Logger logger = LoggerFactory.getLogger(DataController.class);
    private static String msg = null;
    /**
     * 两个小时
     */
    private final static Long DEADLINE = (3 * 60 * 60 * 1000L);
    private static Long lastTime = 0L;

    @GetMapping
    @ApiOperation("根据当前日期判断是否可以预约")
    public void verifyData() throws BusinessException {
        //获取当前时间戳（毫秒）
        Long nowTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        if(null == msg || nowTime > lastTime){
            String httpUrl = "http://tool.bitefu.net/jiari";
            BufferedReader reader = null;
            String result = null;
            StringBuffer sbf = new StringBuffer();
            httpUrl += "?d=" + MyUtils.getNowDateyyyyMMdd();

            lastTime = nowTime + DEADLINE;
            try {
                URL url = new URL(httpUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream is = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String strRead = null;
                while ((strRead = reader.readLine()) != null) {
                    sbf.append(strRead);
                    sbf.append("\r\n");
                }
                reader.close();
                result = sbf.toString();
            } catch(Exception e){
                logger.error("请求日期假期错误！", e);
                return;
            }
            // 0 上班 1周末 2节假日
            if (!result.startsWith("0")){
                msg = "ERR";
                throw new BusinessException("当前日期不在预约时间内!");
            }
            msg = "OK";
        }else if ("ERR".equals(msg)){
            throw new BusinessException("当前日期不在预约时间内!");
        }
    }
}
