package com.ecy.show.entity.keytop;

import lombok.Data;

import java.util.List;

@Data
public class ResultEntity {
    public static final int ERROR = 1;
    public static final int OK = 0;

    /**
     * 响应代码（0：正常,1:错误）
     */
    private Integer resCode;
    /**
     * 响应消息(错误消息)
     */
    private String resMsg;
    /**
     * 总数
     */
    private Integer totalNum;
    private List<CarEntity> data;
}
