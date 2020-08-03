package com.example.demo_cloud.controller;

import com.example.demo_cloud.dao.entity.CdpAutoCrudTable;
import com.example.demo_cloud.service.CdpAutoCrudTableService;
import com.example.demo_cloud.dto.res.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 类描述：自动crud测试表 controller
* 创建时间：20200803
* 作者：zxy09
*/
@RestController
@RequestMapping("/auto/crud/table")
public class CdpAutoCrudTableController {

    @Autowired
    private CdpAutoCrudTableService cdpAutoCrudTableService;


    @RequestMapping(value="/add",method = RequestMethod.POST)
    public JsonResult add(@RequestBody CdpAutoCrudTable cdpAutoCrudTable){
        int result = cdpAutoCrudTableService.insert(cdpAutoCrudTable);
        return new JsonResult(result);
    }

    @RequestMapping(value="/list",method = RequestMethod.POST)
    public JsonResult selectList(@RequestBody CdpAutoCrudTable cdpAutoCrudTable){
        List<CdpAutoCrudTable> result = cdpAutoCrudTableService.selectList(cdpAutoCrudTable);
        return new JsonResult(result);
    }

    @RequestMapping(value="/page",method = RequestMethod.POST)
    public JsonResult selectPage(@RequestBody CdpAutoCrudTable cdpAutoCrudTable){
        Object result = cdpAutoCrudTableService.selectPage(cdpAutoCrudTable);
        return new JsonResult(result);
    }


    @RequestMapping(value="/id",method = RequestMethod.GET)
    public JsonResult<CdpAutoCrudTable> findById(Long code,String name){
        CdpAutoCrudTable result = cdpAutoCrudTableService.selectByPrimaryKey(code,name);
        return new JsonResult(result);
    }

    @RequestMapping(value="/del/id",method = RequestMethod.POST)
    public JsonResult deleteById(@RequestBody CdpAutoCrudTable req){
        int result = cdpAutoCrudTableService.deleteByPrimaryKey(req.getCode(),req.getName());
        return new JsonResult(result);
    }

    @RequestMapping(value="/update/id",method = RequestMethod.POST)
    public JsonResult updateById(@RequestBody CdpAutoCrudTable req){
        int result = cdpAutoCrudTableService.updateByPrimaryKey(req);
        return new JsonResult(result);
    }



    @RequestMapping(value="/uk",method = RequestMethod.GET)
    public JsonResult<CdpAutoCrudTable> findByUk(String unKey1,String unKey2){
        CdpAutoCrudTable result = cdpAutoCrudTableService.selectByUniqueKey(unKey1,unKey2);
        return new JsonResult(result);
    }

    @RequestMapping(value="/del/uk",method = RequestMethod.POST)
    public JsonResult deleteByUk(@RequestBody CdpAutoCrudTable req){
        int result = cdpAutoCrudTableService.deleteByUniqueKey(req.getUnKey1(),req.getUnKey2());
        return new JsonResult(result);
    }

    @RequestMapping(value="/update/uk",method = RequestMethod.POST)
    public JsonResult updateByUk(@RequestBody CdpAutoCrudTable req){
        int result = cdpAutoCrudTableService.updateByUniqueKey(req);
        return new JsonResult(result);
    }
}