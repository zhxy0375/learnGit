package com.example.demo_cloud.service;

import com.example.demo_cloud.dao.one.AutoCrudPageTempletMapper;
import com.example.demo_cloud.dao.entity.AutoCrudPageTemplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
* 类描述： service
* 创建时间：20200805
* 作者：zxy09
*/
@Service
public class AutoCrudPageTempletService {
    @Autowired
    private AutoCrudPageTempletMapper autoCrudPageTempletMapper;

    public int insert(AutoCrudPageTemplet autoCrudPageTemplet){
        return autoCrudPageTempletMapper.insert(autoCrudPageTemplet);
    }

    public List<AutoCrudPageTemplet> selectList(AutoCrudPageTemplet autoCrudPageTemplet){
        return autoCrudPageTempletMapper.selectList(autoCrudPageTemplet);
    }
    /**
    * 分页查询
    */
    public List<AutoCrudPageTemplet> selectPage(AutoCrudPageTemplet autoCrudPageTemplet){
        return autoCrudPageTempletMapper.selectList(autoCrudPageTemplet);
    }


    public AutoCrudPageTemplet selectByPrimaryKey(BigDecimal sid){
        return autoCrudPageTempletMapper.selectByPrimaryKey(sid);
    }

    public int updateByPrimaryKey(AutoCrudPageTemplet autoCrudPageTemplet){
        return autoCrudPageTempletMapper.updateByPrimaryKey(autoCrudPageTemplet);
    }

    public int deleteByPrimaryKey(BigDecimal sid){
    return autoCrudPageTempletMapper.deleteByPrimaryKey(sid);
    }

    public AutoCrudPageTemplet selectByUniqueKey(BigDecimal sid,String name){
        return autoCrudPageTempletMapper.selectByUniqueKey(sid,name);
    }

    public int updateByUniqueKey(AutoCrudPageTemplet autoCrudPageTemplet){
        return autoCrudPageTempletMapper.updateByUniqueKey(autoCrudPageTemplet);
    }

    public int deleteByUniqueKey(BigDecimal sid,String name){
        return autoCrudPageTempletMapper.deleteByUniqueKey(sid,name);
    }

}