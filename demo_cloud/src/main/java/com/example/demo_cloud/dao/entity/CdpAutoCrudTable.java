package com.example.demo_cloud.dao.entity;
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
* 类描述：自动crud测试表 entity
* 创建时间：20200803
* 作者：zxy09
* Entity json: {"code":0,"name":"str","unKey1":"str","unKey2":"str","cDate":"str"}
*/
@Data
public class CdpAutoCrudTable implements Serializable{
    /**
    * 编码
    */
    private Long code;
    /**
    * 名称
    */
    private String name;
    /**
    * 唯一键1
    */
    private String unKey1;
    /**
    * 唯一键2
    */
    private String unKey2;
    /**
    * 创建时间
    */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cDate;

}