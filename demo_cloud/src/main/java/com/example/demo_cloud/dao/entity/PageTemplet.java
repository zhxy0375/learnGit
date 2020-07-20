package com.example.demo_cloud.dao.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PageTemplet implements Serializable {
    private BigDecimal sid;

    private Object name;

    private String channel;

    private BigDecimal status;

    private String cBy;

    private String mBy;

    private Date cTime;

    private Date mTime;

    private BigDecimal isDel;

    private BigDecimal tempType;

}