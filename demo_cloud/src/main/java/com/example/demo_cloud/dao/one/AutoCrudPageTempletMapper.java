package com.example.demo_cloud.dao.one;

import com.example.demo_cloud.dao.entity.AutoCrudPageTemplet;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
* 类描述： Mapper
* 创建时间：20200805
* 作者：zxy09
*/
public interface AutoCrudPageTempletMapper {

    int insert(AutoCrudPageTemplet autoCrudPageTemplet);

    List<AutoCrudPageTemplet> selectList(AutoCrudPageTemplet autoCrudPageTemplet);

    AutoCrudPageTemplet selectByPrimaryKey(@Param("sid") BigDecimal sid);

    int updateByPrimaryKey(AutoCrudPageTemplet autoCrudPageTemplet);

    int deleteByPrimaryKey(@Param("sid") BigDecimal sid);

    AutoCrudPageTemplet selectByUniqueKey(@Param("sid") BigDecimal sid,@Param("name") String name);

    int updateByUniqueKey(AutoCrudPageTemplet autoCrudPageTemplet);

    int deleteByUniqueKey(@Param("sid") BigDecimal sid,@Param("name") String name);
}