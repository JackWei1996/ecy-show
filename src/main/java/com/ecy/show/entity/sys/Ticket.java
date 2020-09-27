package com.ecy.show.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 南京白墨科技有限公司
 * @since 2019-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    /*
    1.有效
    0. 失效
    * */
    public static final int VALID = 1;
    public static final int INVALID = 0;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String jwt;

    private LocalDateTime gmtCreate;
    /*
    * 退出时间
    * */
    private LocalDateTime exitTime;

    private Integer isValid;

    private String nickname;

}
