package ${tableInfo.controllerLocation};

import ${tableInfo.entityLocation}.${tableInfo.className};
import ${tableInfo.serviceLocation}.${tableInfo.className}Service;
import ${tableInfo.responseClassPath};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 类描述：${tableInfo.tableComment} controller
* 创建时间：${.now?string["yyyyMMdd"]}
* 作者：${tableInfo.author}
*/
@RestController
@RequestMapping("${tableInfo.mappingPath}")
public class ${tableInfo.className}Controller {

    @Autowired
    private ${tableInfo.className}Service ${tableInfo.convertName}Service;


    @RequestMapping(value="/add",method = RequestMethod.POST)
    public ${tableInfo.responseClass} add(@RequestBody ${tableInfo.className} ${tableInfo.convertName}){
        int result = ${tableInfo.convertName}Service.insert(${tableInfo.convertName});
        return new ${tableInfo.responseClass}(result);
    }

    @RequestMapping(value="/list",method = RequestMethod.POST)
    public ${tableInfo.responseClass} selectList(@RequestBody ${tableInfo.className} ${tableInfo.convertName}){
        List<${tableInfo.className}> result = ${tableInfo.convertName}Service.selectList(${tableInfo.convertName});
        return new ${tableInfo.responseClass}(result);
    }

    @RequestMapping(value="/page",method = RequestMethod.POST)
    public ${tableInfo.responseClass} selectPage(@RequestBody ${tableInfo.className} ${tableInfo.convertName}){
        Object result = ${tableInfo.convertName}Service.selectPage(${tableInfo.convertName});
        return new ${tableInfo.responseClass}(result);
    }

<#if tableInfo.primaryColumns??&&(tableInfo.primaryColumns?size > 0)>
    <#assign prKeys = tableInfo.primaryColumns>
    <#assign primaryParamDef><#list prKeys as item><#if item_has_next>${item.javaType} ${item.convertName},<#else >${item.javaType} ${item.convertName}</#if></#list></#assign>
    <#assign primaryParam><#list prKeys as item><#if item_has_next>${item.convertName},<#else >${item.convertName}</#if></#list></#assign>
    <#assign primaryGetParam><#list prKeys as item><#if item_has_next>req.${item.getterName}(),<#else >req.${item.getterName}()</#if></#list></#assign>
    @RequestMapping(value="/id",method = RequestMethod.GET)
    public ${tableInfo.responseClass}<${tableInfo.className}> findById(${primaryParamDef}){
        ${tableInfo.className} result = ${tableInfo.convertName}Service.selectByPrimaryKey(${primaryParam});
        return new ${tableInfo.responseClass}(result);
    }

<#--    更新和 删除-->
    @RequestMapping(value="/del/id",method = RequestMethod.POST)
    public ${tableInfo.responseClass} deleteById(@RequestBody ${tableInfo.className} req){
        int result = ${tableInfo.convertName}Service.deleteByPrimaryKey(${primaryGetParam});
        return new ${tableInfo.responseClass}(result);
    }

    @RequestMapping(value="/update/id",method = RequestMethod.POST)
    public ${tableInfo.responseClass} updateById(@RequestBody ${tableInfo.className} req){
        int result = ${tableInfo.convertName}Service.updateByPrimaryKey(req);
        return new ${tableInfo.responseClass}(result);
    }

<#else>
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public ${tableInfo.responseClass} update(@RequestBody ${tableInfo.className} req){
        int result = ${tableInfo.convertName}Service.update(req);
        return new ${tableInfo.responseClass}(result);
    }

    @RequestMapping(value="/delete",method = RequestMethod.POST)
    public ${tableInfo.responseClass} delete(@RequestBody ${tableInfo.className} req){
        int result = ${tableInfo.convertName}Service.delete(req);
        return new ${tableInfo.responseClass}(result);
    }
</#if>

<#if tableInfo.uniqueColumns??&&(tableInfo.uniqueColumns?size > 0)>
<#assign unKeys = tableInfo.uniqueColumns>
<#assign ukParamDef><#list unKeys as item><#if item_has_next>${item.javaType} ${item.convertName},<#else >${item.javaType} ${item.convertName}</#if></#list></#assign>
<#assign ukParam><#list unKeys as item><#if item_has_next>${item.convertName},<#else >${item.convertName}</#if></#list></#assign>
    <#assign ukGetParam><#list unKeys as item><#if item_has_next>req.${item.getterName}(),<#else >req.${item.getterName}()</#if></#list></#assign>
    @RequestMapping(value="/uk",method = RequestMethod.GET)
    public ${tableInfo.responseClass}<${tableInfo.className}> findByUk(${ukParamDef}){
        ${tableInfo.className} result = ${tableInfo.convertName}Service.selectByUniqueKey(${ukParam});
        return new ${tableInfo.responseClass}(result);
    }

    <#--    更新和 删除-->
    @RequestMapping(value="/del/uk",method = RequestMethod.POST)
    public ${tableInfo.responseClass} deleteByUk(@RequestBody ${tableInfo.className} req){
        int result = ${tableInfo.convertName}Service.deleteByUniqueKey(${ukGetParam});
        return new ${tableInfo.responseClass}(result);
    }

    @RequestMapping(value="/update/uk",method = RequestMethod.POST)
    public ${tableInfo.responseClass} updateByUk(@RequestBody ${tableInfo.className} req){
        int result = ${tableInfo.convertName}Service.updateByUniqueKey(req);
        return new ${tableInfo.responseClass}(result);
    }
</#if>
}