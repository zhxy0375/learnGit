package com.example.demo_cloud.dao.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据库表：cdp_insight_topical_chart_type
 * 表注释：
 * @author ZXY09
 * @create 2020-07-02
 */
@Data
public class CdpInsightTopicalChartType implements Serializable {
    /**
     * 洞察类型id
     */
    private String insightId;

    /**
     * 洞察类型名称
     */
    private String insightTitle;

    /**
     * 标签数量
     */
    private String tagdetailCnt;

    /**
     * 洞察图表类型
     */
    private String chartType;

    /**
     * 模块类型
     */
    private String moduleType;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 排序
     */
    private Integer posIndx;


}