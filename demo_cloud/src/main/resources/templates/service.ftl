package ${tableInfo.serviceLocation};

import ${tableInfo.mapperLocation}.${tableInfo.className}Mapper;
import ${tableInfo.entityLocation}.${tableInfo.className};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<#if tableInfo.columnImports??&&(tableInfo.columnImports?size > 0)>
    <#list tableInfo.columnImports as item>
import ${item};
    </#list>
</#if>
import java.util.List;

/**
* 类描述：${tableInfo.tableComment!} service
* 创建时间：${.now?string["yyyy-MM-dd"]}
* 作者：${tableInfo.author}
*/
@Service
public class ${tableInfo.className}Service {
    @Autowired
    private ${tableInfo.className}Mapper ${tableInfo.convertName}Mapper;

    public int insert(${tableInfo.className} ${tableInfo.convertName}){
        return ${tableInfo.convertName}Mapper.insert(${tableInfo.convertName});
    }

    public List<${tableInfo.className}> selectList(${tableInfo.className} ${tableInfo.convertName}){
        return ${tableInfo.convertName}Mapper.selectList(${tableInfo.convertName});
    }
    /**
    * 分页查询
    */
    public List<${tableInfo.className}> selectPage(${tableInfo.className} ${tableInfo.convertName}){
        return ${tableInfo.convertName}Mapper.selectList(${tableInfo.convertName});
    }

<#--
<#if tableInfo.primaryColumn??>
    <#assign primary = tableInfo.primaryColumn >
    public ${tableInfo.className} selectByPrimaryKey(${primary.javaType} ${primary.convertName}){
        return ${tableInfo.convertName}Mapper.selectByPrimaryKey(${primary.convertName});
    }

    public int updateByPrimaryKey(${primary.javaType} ${primary.convertName}){
        return ${tableInfo.convertName}Mapper.updateByPrimaryKey(${primary.convertName});
    }

    public int deleteByPrimaryKey(${primary.javaType} ${primary.convertName}){
        return ${tableInfo.convertName}Mapper.deleteByPrimaryKey(${primary.convertName});
    }
<#else>
    public int update(${tableInfo.className} ${tableInfo.convertName}){
        return ${tableInfo.convertName}Mapper.insert(${tableInfo.convertName});
    }

    public int delete(${tableInfo.className} ${tableInfo.convertName}){
        return ${tableInfo.convertName}Mapper.insert(${tableInfo.convertName});
    }
</#if>-->
<#if tableInfo.primaryColumns??&&(tableInfo.primaryColumns?size > 0)>
    <#assign prKeys = tableInfo.primaryColumns>
    <#assign primaryParamDef><#list prKeys as item><#if item_has_next>${item.javaType} ${item.convertName},<#else >${item.javaType} ${item.convertName}</#if></#list></#assign>
    <#assign primaryParam><#list prKeys as item><#if item_has_next>${item.convertName},<#else >${item.convertName}</#if></#list></#assign>

    public ${tableInfo.className} selectByPrimaryKey(${primaryParamDef}){
        return ${tableInfo.convertName}Mapper.selectByPrimaryKey(${primaryParam});
    }

    public int updateByPrimaryKey(${tableInfo.className} ${tableInfo.convertName}){
        return ${tableInfo.convertName}Mapper.updateByPrimaryKey(${tableInfo.convertName});
    }

    public int deleteByPrimaryKey(${primaryParamDef}){
    return ${tableInfo.convertName}Mapper.deleteByPrimaryKey(${primaryParam});
    }
<#else>
    public int update(${tableInfo.className} ${tableInfo.convertName}){
        return ${tableInfo.convertName}Mapper.update(${tableInfo.convertName});
    }

    public int delete(${tableInfo.className} ${tableInfo.convertName}){
        return ${tableInfo.convertName}Mapper.delete(${tableInfo.convertName});
    }
</#if>

<#if tableInfo.uniqueColumns??&&(tableInfo.uniqueColumns?size > 0)>
<#assign unKeys = tableInfo.uniqueColumns>
<#assign ukParamDef><#list unKeys as item><#if item_has_next>${item.javaType} ${item.convertName},<#else >${item.javaType} ${item.convertName}</#if></#list></#assign>
<#assign ukParam><#list unKeys as item><#if item_has_next>${item.convertName},<#else >${item.convertName}</#if></#list></#assign>
    public ${tableInfo.className} selectByUniqueKey(${ukParamDef}){
        return ${tableInfo.convertName}Mapper.selectByUniqueKey(${ukParam});
    }

    public int updateByUniqueKey(${tableInfo.className} ${tableInfo.convertName}){
        return ${tableInfo.convertName}Mapper.updateByUniqueKey(${tableInfo.convertName});
    }

    public int deleteByUniqueKey(${ukParamDef}){
        return ${tableInfo.convertName}Mapper.deleteByUniqueKey(${ukParam});
    }
</#if>

}