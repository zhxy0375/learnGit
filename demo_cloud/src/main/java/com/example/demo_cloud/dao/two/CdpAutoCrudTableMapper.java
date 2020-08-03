package com.example.demo_cloud.dao.two;

import com.example.demo_cloud.dao.entity.CdpAutoCrudTable;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* 类描述：自动crud测试表 Mapper
* 创建时间：20200803
* 作者：zxy09
*/
public interface CdpAutoCrudTableMapper {

    int insert(CdpAutoCrudTable cdpAutoCrudTable);

    List<CdpAutoCrudTable> selectList(CdpAutoCrudTable cdpAutoCrudTable);

    CdpAutoCrudTable selectByPrimaryKey(@Param("code") Long code,@Param("name") String name);

    int updateByPrimaryKey(CdpAutoCrudTable cdpAutoCrudTable);

    int deleteByPrimaryKey(@Param("code") Long code,@Param("name") String name);

    CdpAutoCrudTable selectByUniqueKey(@Param("unKey1") String unKey1,@Param("unKey2") String unKey2);

    int updateByUniqueKey(CdpAutoCrudTable cdpAutoCrudTable);

    int deleteByUniqueKey(@Param("unKey1") String unKey1,@Param("unKey2") String unKey2);
}