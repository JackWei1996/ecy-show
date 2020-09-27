package com.ecy.show.global;

import com.ecy.show.annotation.DisableAuditing;
import com.ecy.show.entity.sys.SysLog;
import com.ecy.show.service.sys.SysLogService;
import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 天宇
 * 请求参数、响应体统一日志打印
 */
@Aspect
@Component
public class RestControllerAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysLogService sysLogService;
    /**
     * 环绕通知
     *
     * @param joinPoint 连接点
     * @return 切入点返回值
     * @throws Throwable 异常信息
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController)" +
            " || @annotation(org.springframework.web.bind.annotation.RestController)"+
        "|| execution(* com.ecy.show.shiro.JwtFilter.*(..))")
    public Object apiLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        logger.info("********* api log *********");
        logger.info("url: " + request.getMethod() + " " + request.getRequestURI());
        logger.info("method: " + joinPoint.getSignature().toString());
        // 去除敏感字段后的parameter map
        Gson gson = new Gson();

        logger.info("parameter map: " + gson.toJson(deleteSensitiveContent(request.getParameterMap())));
        logger.info("user-agent: " + request.getHeader("user-agent"));
        logger.info("remote ip: " + request.getRemoteAddr() + ", port: " + request.getRemotePort());
        logger.info("request time: " + new Date());


        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DisableAuditing disableAuditing=method.getAnnotation(DisableAuditing.class);
        String jsonArgs=null;

        if(disableAuditing==null) {
            Object[] args = joinPoint.getArgs();
            Object[] arguments = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                    continue;
                }
                arguments[i] = args[i];
            }
            jsonArgs = gson.toJson(arguments);
            logger.info("args: " + jsonArgs);
        }

        //执行方法
        Object result = joinPoint.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        if(disableAuditing==null){
            sysLogService.saveLog(request,jsonArgs,joinPoint,time, SysLog.Operation);
        }
        return result;
    }

    /**
     * 删除参数中的敏感内容
     * @return 去除敏感内容后的参数对象
     */
    private Map<String, String[]> deleteSensitiveContent(Map<String, String[]> parameterMap) {
        HashMap<String, String[]> resMap = new HashMap<>(16);
        resMap.putAll(parameterMap);
        getSensitiveFieldList().forEach(s -> {
            if (resMap.containsKey(s)) {
                resMap.put(s, new String[]{"******"});
            }
        });
        return resMap;
    }

    /**
     * 敏感字段列表（当然这里你可以更改为可配置的）
     */
    private List<String> getSensitiveFieldList() {
        List<String> sensitiveFieldList = new ArrayList<>();
        sensitiveFieldList.add("pwd");
        sensitiveFieldList.add("password");
        sensitiveFieldList.add("oldPassword");
        sensitiveFieldList.add("newPassword");
        sensitiveFieldList.add("oldPwd");
        sensitiveFieldList.add("newPwd");
        return sensitiveFieldList;
    }
}
