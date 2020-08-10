package com.example.demo_cloud.controller;

import com.example.demo_cloud.dao.entity.AutoCrudPageTemplet;
import com.example.demo_cloud.service.AutoCrudPageTempletService;
import com.example.demo_cloud.dto.res.JsonResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
* 类描述： controller
* 创建时间：20200805
* 作者：zxy09
*/
@RestController
@RequestMapping("/crud/page/templet")
public class AutoCrudPageTempletController {

    @Autowired
    private AutoCrudPageTempletService autoCrudPageTempletService;


    @RequestMapping(value="/add",method = RequestMethod.POST)
    public JsonResult add(@RequestBody AutoCrudPageTemplet autoCrudPageTemplet){
        int result = autoCrudPageTempletService.insert(autoCrudPageTemplet);
        return new JsonResult(result);
    }

    @RequestMapping(value="/list",method = RequestMethod.POST)
    public JsonResult selectList(@RequestBody AutoCrudPageTemplet autoCrudPageTemplet){
        List<AutoCrudPageTemplet> result = autoCrudPageTempletService.selectList(autoCrudPageTemplet);
        return new JsonResult(result);
    }

    @RequestMapping(value="/page/{pageNum}/{pageSize}",method = RequestMethod.POST)
    public JsonResult selectPage(@RequestBody AutoCrudPageTemplet autoCrudPageTemplet,@PathVariable int pageNum, @PathVariable int pageSize){
        PageInfo<AutoCrudPageTemplet> result = autoCrudPageTempletService.selectPage(autoCrudPageTemplet,pageNum, pageSize);
        return new JsonResult(result);
    }

    @RequestMapping(value="/id",method = RequestMethod.GET)
    public JsonResult<AutoCrudPageTemplet> findById(BigDecimal sid){
        AutoCrudPageTemplet result = autoCrudPageTempletService.selectByPrimaryKey(sid);
        return new JsonResult(result);
    }

    @RequestMapping(value="/del/id",method = RequestMethod.POST)
    public JsonResult deleteById(@RequestBody AutoCrudPageTemplet req){
        int result = autoCrudPageTempletService.deleteByPrimaryKey(req.getSid());
        return new JsonResult(result);
    }

    @RequestMapping(value="/update/id",method = RequestMethod.POST)
    public JsonResult updateById(@RequestBody AutoCrudPageTemplet req){
        int result = autoCrudPageTempletService.updateByPrimaryKey(req);
        return new JsonResult(result);
    }


    @RequestMapping(value="/uk",method = RequestMethod.GET)
    public JsonResult<AutoCrudPageTemplet> findByUk(BigDecimal sid,String name){
        AutoCrudPageTemplet result = autoCrudPageTempletService.selectByUniqueKey(sid,name);
        return new JsonResult(result);
    }

    @RequestMapping(value="/del/uk",method = RequestMethod.POST)
    public JsonResult deleteByUk(@RequestBody AutoCrudPageTemplet req){
        int result = autoCrudPageTempletService.deleteByUniqueKey(req.getSid(),req.getName());
        return new JsonResult(result);
    }

    @RequestMapping(value="/update/uk",method = RequestMethod.POST)
    public JsonResult updateByUk(@RequestBody AutoCrudPageTemplet req){
        int result = autoCrudPageTempletService.updateByUniqueKey(req);
        return new JsonResult(result);
    }
}