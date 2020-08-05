package com.example.demo_cloud.dao.entity;

import java.math.BigDecimal;
import java.sql.Date;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
* 数据库表：AUTO_CRUD_PAGE_TEMPLET
* 类描述： entity
* 创建时间：2020-08-05
* 作者：zxy09
* Entity json: {"sid":0,"name":"str","channel":"str","status":0,"cBy":"str","mBy":"str","cTime":"str","mTime":"str","isDel":0,"tempType":0,"numb1":false,"numb3":0,"numb5":0,"numb10":0,"numb19":0,"numb38":0,"time2":"str"}
*/
@Data
public class AutoCrudPageTemplet implements Serializable{
        /**
        * 主键
        */
        private BigDecimal sid;
        /**
        * 名称
        */
        private String name;
        /**
        * 渠道
        */
        private String channel;
        /**
        * 状态, 0:草稿,1:上架,2:下架
        */
        private BigDecimal status;
        /**
        * 创建人
        */
        private String cBy;
        /**
        * 修改人
        */
        private String mBy;
        /**
        * 创建时间
        */
            @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        private Date cTime;
        /**
        * 更新时间
        */
            @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        private Date mTime;
        /**
        * 是否删除,0未删除,1已删除
        */
        private BigDecimal isDel;
        /**
        * 模板类型.1:普通,2:备用(兼容APP老版本)
        */
        private BigDecimal tempType;
        /**
        * 
        */
        private Boolean numb1;
        /**
        * 
        */
        private Short numb3;
        /**
        * 
        */
        private Integer numb5;
        /**
        * 
        */
        private Long numb10;
        /**
        * 
        */
        private BigDecimal numb19;
        /**
        * 
        */
        private BigDecimal numb38;
        /**
        * 
        */
            @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        private Date time2;
}