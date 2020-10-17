package com.ecy.show.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jack魏
 * @since 2020-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Works implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 1清纯JK 2漫展场照 3动漫人物 4 游戏角色 5Lolita 6古装
     */
    private Integer type;

    /**
     * 标题
     */
    private String title;

    private String details;

    /**
     * 图片url逗号分隔
     */
    private String imgsUrl;

    /**
     * 1coser 2摄影师
     */
    private Integer roleType;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
