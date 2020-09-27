package com.ecy.show.entity.keytop;

import lombok.Data;

@Data
public class CarEntity {
    /**
     * 停车场编号
     */
    private String parkCode;
    /**
     * 车牌号
     */
    private String plateNo;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 楼层
     */
    private Integer floorId;
    /**
     * 区域
     */
    private Integer areaId;
    /**
     * 入场时间
     */
    private String entryTime;
    /**
     * 入场时间
     */
    private String enterTime;
    /**
     * 出场时间
     */
    private String leaveTime;

    private String spaceNo;

    private String inTime;
    /**
     * 入口
     */
    private String entryPlace;
    /**
     * 分页页数（1开始）
     */
    private Integer pageIndex;
    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 每页数量
     */
    private Integer pageCount;
    /**
     * 车场id
     */
    private  Integer parkId;
    /**
     * 车场名称
     */
    private String parkName;
    /**
     * 区域
     */
    private String area;
    /**
     * 楼层名称
     */
    private String floorName;
    /**
     * 已停放时长(分钟)
     */
    private String parkTime;
    /**
     * 车位状态（0: 未占用；1：占用;2不存在,3故障）
     */
    private Integer status;
    /**
     * 类型： 0：车位 1：区域
     */
    private Integer type;

    /**
     * 选定的车位/区域ID 必填
     */
    private String addrId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 总数
     */
    private Integer totalNum;
    /**
     * 车位/区域名称
     */
    private String addrName;
    /**
     * 预约开始时间
     */
    private String startTime;
    /**
     * 预约结束时间
     */
    private String endTime;

    private String deviceCode;
}
