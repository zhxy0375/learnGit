package com.bdqn.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bdqn.pojo.${tableInfo.className};
import com.bdqn.service.${tableInfo.className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${tableInfo.className}")
public class ${tableInfo.className}Controller {

@Autowired(required = false)
private ${tableInfo.className}Service ${tableInfo.convertName}Service;

public ${tableInfo.className}Service get${tableInfo.className}Service() {
return ${tableInfo.convertName}Service;
}

public void set${tableInfo.className}Service(${tableInfo.className}Service ${tableInfo.convertName}Service) {
this.${tableInfo.convertName}Service = ${tableInfo.convertName}Service;
}


@RequestMapping(value="/findAll${tableInfo.className}",method = RequestMethod.GET)
public String findAll${tableInfo.className}(){
return JSONArray.toJSONString(${tableInfo.convertName}Service.findAll${tableInfo.className}());
}

@RequestMapping(value="/find${tableInfo.className}ById",method = RequestMethod.GET)
public String find${tableInfo.className}ById(@RequestParam("id") int id){
return JSON.toJSONString(${tableInfo.convertName}Service.del${tableInfo.className}ById(id));
}

@RequestMapping(value="/add${tableInfo.className}",method = RequestMethod.POST)
public int add${tableInfo.className}(@RequestBody ${tableInfo.className} ${tableInfo.convertName}){return ${tableInfo.convertName}Service.add${tableInfo.className}(${tableInfo.convertName});}

@RequestMapping(value="/del${tableInfo.className}ById",method = RequestMethod.GET)
public int del${tableInfo.className}ById(@RequestParam("id") int id){return ${tableInfo.convertName}Service.del${tableInfo.className}ById(id);}

@RequestMapping(value="/update${tableInfo.className}",method = RequestMethod.POST)
public int update${tableInfo.className}(@RequestBody ${tableInfo.className} ${tableInfo.convertName}){return ${tableInfo.convertName}Service.update${tableInfo.className}(${tableInfo.convertName});}

}