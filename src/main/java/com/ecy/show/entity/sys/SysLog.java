package com.ecy.show.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author AntZero
 * @since 2019-09-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`sys_log`")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;
    //操作日志
    public static final int Operation = 0;
    //异常警告
    public static final int Warning = 1;
    //错误
    public static final int Error = 2;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String content;

    private String url;

    private String method;

    private String parameter;

    private String ip;

    private Long userId;

    private String nickname;

    private Integer logType;

    private Long spendTime;

    private LocalDateTime gmtCreate;


}
