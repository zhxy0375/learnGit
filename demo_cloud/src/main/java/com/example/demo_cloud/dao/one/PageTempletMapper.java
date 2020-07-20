package com.example.demo_cloud.dao.one;


import com.example.demo_cloud.dao.entity.PageTemplet;

import java.math.BigDecimal;

public interface PageTempletMapper {
    int deleteByPrimaryKey(BigDecimal sid);

    int insert(PageTemplet record);

    PageTemplet selectByPrimaryKey(BigDecimal sid);

    int updateByPrimaryKey(PageTemplet record);
}