package com.ecy.show.service.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecy.show.dto.sys.TicketSearchDto;
import com.ecy.show.entity.sys.Ticket;
import com.ecy.show.entity.sys.User;
import com.ecy.show.global.Constant;
import com.ecy.show.mapper.sys.TicketMapper;
import com.ecy.show.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author AntZero
 * @since 2019-09-23
 */
@Service
public class TicketService extends ServiceImpl<TicketMapper, Ticket> {
    public String generatorToken(Long userId) throws UnsupportedEncodingException {
        String str = String.valueOf(userId) +
                System.currentTimeMillis() +
                UUID.randomUUID();
        return DigestUtils.md5DigestAsHex(str.getBytes("UTF-8"));
    }

    public Ticket getTicketByToken(String token) {
        Ticket ticket = getOne(new QueryWrapper<Ticket>().eq("jwt", token));
        if (ticket != null && Duration.between(LocalDateTime.now(), ticket.getGmtCreate()).toMillis() <= Constant.TOKEN_MAX_VALID_TIME && ticket.getIsValid() == Ticket.VALID) {
            return ticket;
        }
        return null;
    }

    public String saveTicket(User user) {
        String secret = user.getIdCard();
        if (StringUtils.isEmpty(secret)) {
            secret = "default-secret";
        }
        String jwtStr = JwtUtil.sign(user.getId().toString(), secret);
        Ticket ticket = new Ticket();
        ticket.setGmtCreate(LocalDateTime.now());
        ticket.setIsValid(Ticket.VALID);
        ticket.setJwt(jwtStr);
        ticket.setNickname(user.getName());
        save(ticket);
        return jwtStr;
    }

    public void logout(String jwt) {
        if (jwt != null) {
            update(new Ticket(), new UpdateWrapper<Ticket>()
                    .set("is_valid", Ticket.INVALID)
                    .set("exit_time", LocalDateTime.now())
                    .eq("jwt", jwt));
        }
    }

    public IPage listTicket(TicketSearchDto ticketSearchDto) {

        return page(ticketSearchDto, new QueryWrapper<Ticket>()
                .eq(null != ticketSearchDto.getIsValid(), "is_valid", ticketSearchDto.getIsValid())
                .like(null != ticketSearchDto.getNickname(), "nickname", ticketSearchDto.getNickname())
                .ge(null != ticketSearchDto.getBeginDate(), "gmt_create", ticketSearchDto.getBeginDate())
                .le(null != ticketSearchDto.getEndDate(), "gmt_create", ticketSearchDto.getEndDate())
        );
    }
}
