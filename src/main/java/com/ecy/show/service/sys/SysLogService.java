package com.ecy.show.service.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecy.show.dto.sys.CurrentUser;
import com.ecy.show.dto.sys.SysLogCondition;
import com.ecy.show.entity.sys.SysLog;
import com.ecy.show.mapper.sys.SysLogMapper;
import com.ecy.show.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.time.LocalDateTime;

/**
 *
 * @author AntZero
 * @since 2019-09-23
 */
@Service
public class SysLogService extends ServiceImpl<SysLogMapper, SysLog> {

    public IPage listSystemLogByAdmin(Page page, SysLogCondition condition) {
        if (null != condition.getEndDate()){
            condition.setEndDate(condition.getEndDate().plusDays(1));
        }

        return page(page,new QueryWrapper<SysLog>()
                .eq(null != condition.getIp(),"ip", condition.getIp())
                .like(!StringUtils.isEmpty(condition.getNickname()), "nickname", condition.getNickname())
                .like(!StringUtils.isEmpty(condition.getUrl()), "url", condition.getUrl())
                .eq(null != condition.getLogType(), "log_type", condition.getLogType())
                .ge(null != condition.getBeginDate(), "gmt_create", condition.getBeginDate())
                .le(null != condition.getEndDate(), "gmt_create", condition.getEndDate())
                .ge(null != condition.getMinSpendTime(), "spend_time", condition.getMinSpendTime())
                .le(null != condition.getMaxSpendTime(), "spend_time", condition.getMaxSpendTime()));
    }

    public void saveLog(HttpServletRequest request, String args, ProceedingJoinPoint joinPoint, Long time, Integer logType){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ApiOperation apiOperation=method.getAnnotation(ApiOperation.class);

        String content=joinPoint.getSignature().toString()+(apiOperation!=null?(":"+apiOperation.value()):"");

        SysLog sysLog = new SysLog()
                .setIp(getIpAddr(request))
                .setGmtCreate(LocalDateTime.now())
                .setMethod(request.getMethod())
                .setUrl(request.getRequestURI())
                .setContent(content)
                .setParameter(args)
                .setLogType(logType)
                .setUserId(JwtUtil.getUserId(request))
                .setSpendTime(time);

        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getPrincipal();
        if (currentUser != null){
            sysLog.setNickname(currentUser.getName());
        }

        save(sysLog);
    }


    /**
     * 获取ip地址
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
