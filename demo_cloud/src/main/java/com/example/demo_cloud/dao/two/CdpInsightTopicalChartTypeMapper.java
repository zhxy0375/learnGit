package com.example.demo_cloud.dao.two;


import com.example.demo_cloud.dao.entity.CdpInsightTopicalChartType;

public interface CdpInsightTopicalChartTypeMapper {
    int deleteByPrimaryKey(String insightId);

    int insert(CdpInsightTopicalChartType record);

    CdpInsightTopicalChartType selectByPrimaryKey(String insightId);

    int updateByPrimaryKey(CdpInsightTopicalChartType record);
}