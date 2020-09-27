package com.ecy.show.shiro;

import com.ecy.show.dto.sys.CurrentUser;
import com.ecy.show.entity.sys.Ticket;
import com.ecy.show.entity.sys.User;
import com.ecy.show.service.sys.TicketService;
import com.ecy.show.service.sys.UserService;
import com.ecy.show.util.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ShiroRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LogManager.getLogger(ShiroRealm.class);

    private UserService userService;

    private TicketService ticketService;

    @Autowired
    public void setUserService(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        CurrentUser currentUser = (CurrentUser)principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        Set<String>groupSet=userService.getUserGroupSet(currentUser.getId());
        simpleAuthorizationInfo.setRoles(groupSet);

        Set<String> permission = userService.getUserPermissionsSet(currentUser.getId());
        simpleAuthorizationInfo.addStringPermissions(permission);

        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token =(String)auth.getCredentials();
        if (token == null) {
            throw new AuthenticationException("请先登录!");
        }

        Ticket ticket = ticketService.getTicketByToken(token);

        if (ticket == null || ticket.getIsValid() == Ticket.INVALID) {
            throw new AuthenticationException("该Token已过期,请重新获取!");
        }

        Long userId = JwtUtil.getUserId(token);
        if (userId == null) {
            throw new AuthenticationException("身份认证已过期,请重新登录!");
        }

        User user = userService.getById(userId);

        if (user == null) {
            throw new AuthenticationException("用户不存在!");
        }

        CurrentUser currentUser = new CurrentUser();
        BeanUtils.copyProperties(user, currentUser);

        return new SimpleAuthenticationInfo(currentUser, token, "current_realm");
    }
}
