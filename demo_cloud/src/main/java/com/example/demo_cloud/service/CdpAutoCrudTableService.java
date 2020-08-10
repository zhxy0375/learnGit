package com.example.demo_cloud.service;

import com.example.demo_cloud.dao.two.CdpAutoCrudTableMapper;
import com.example.demo_cloud.dao.entity.CdpAutoCrudTable;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* 类描述：自动crud测试表 service
* 创建时间：20200803
* 作者：zxy09
*/
@Service
public class CdpAutoCrudTableService {
    @Autowired
    private CdpAutoCrudTableMapper cdpAutoCrudTableMapper;

    public int insert(CdpAutoCrudTable cdpAutoCrudTable){
        return cdpAutoCrudTableMapper.insert(cdpAutoCrudTable);
    }

    public List<CdpAutoCrudTable> selectList(CdpAutoCrudTable cdpAutoCrudTable){
        return cdpAutoCrudTableMapper.selectList(cdpAutoCrudTable);
    }
    /**
    * 分页查询
    */
    public PageInfo<CdpAutoCrudTable> selectPage(CdpAutoCrudTable cdpAutoCrudTable,int pageNum, int pageSize){

        //对应的lambda用法
        PageInfo<CdpAutoCrudTable> page  = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> cdpAutoCrudTableMapper.selectList(cdpAutoCrudTable));
//        long total = PageHelper.count(() -> cdpAutoCrudTableMapper.selectList(cdpAutoCrudTable));
        
        return page;
    }


    public CdpAutoCrudTable selectByPrimaryKey(Long code,String name){
        return cdpAutoCrudTableMapper.selectByPrimaryKey(code,name);
    }

    public int updateByPrimaryKey(CdpAutoCrudTable cdpAutoCrudTable){
        return cdpAutoCrudTableMapper.updateByPrimaryKey(cdpAutoCrudTable);
    }

    public int deleteByPrimaryKey(Long code,String name){
    return cdpAutoCrudTableMapper.deleteByPrimaryKey(code,name);
    }

    public CdpAutoCrudTable selectByUniqueKey(String unKey1,String unKey2){
        return cdpAutoCrudTableMapper.selectByUniqueKey(unKey1,unKey2);
    }

    public int updateByUniqueKey(CdpAutoCrudTable cdpAutoCrudTable){
        return cdpAutoCrudTableMapper.updateByUniqueKey(cdpAutoCrudTable);
    }

    public int deleteByUniqueKey(String unKey1,String unKey2){
        return cdpAutoCrudTableMapper.deleteByUniqueKey(unKey1,unKey2);
    }

}