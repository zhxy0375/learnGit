package ${tableInfo.serviceLocation};

import ${tableInfo.entityLocation}.${tableInfo.className};
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

<#if tableInfo.columnImports??&&(tableInfo.columnImports?size > 0)>
    <#list tableInfo.columnImports as item>
import ${item};
    </#list>
</#if>
import java.util.List;

/**
* 类描述：${tableInfo.tableComment!} interface
* 创建时间：${.now?string["yyyy-MM-dd"]}
* 作者：${tableInfo.author}
*/
public interface I${tableInfo.className}Service {


    int insert(${tableInfo.className} ${tableInfo.convertName});

    List<${tableInfo.className}> selectList(${tableInfo.className} ${tableInfo.convertName});

    PageInfo<${tableInfo.className}> selectPage(${tableInfo.className} ${tableInfo.convertName},int pageNum, int pageSize);

<#if tableInfo.primaryColumns??&&(tableInfo.primaryColumns?size > 0)>
    <#assign prKeys = tableInfo.primaryColumns>
    <#assign primaryParamDef><#list prKeys as item><#if item_has_next>${item.javaType} ${item.convertName},<#else >${item.javaType} ${item.convertName}</#if></#list></#assign>

    ${tableInfo.className} selectByPrimaryKey(${primaryParamDef});

    int updateByPrimaryKey(${tableInfo.className} ${tableInfo.convertName});

    int deleteByPrimaryKey(${primaryParamDef});
<#else>
    int update(${tableInfo.className} ${tableInfo.convertName});

    int delete(${tableInfo.className} ${tableInfo.convertName});
</#if>

<#if tableInfo.uniqueColumns??&&(tableInfo.uniqueColumns?size > 0)>
<#assign unKeys = tableInfo.uniqueColumns>
<#assign ukParamDef><#list unKeys as item><#if item_has_next>${item.javaType} ${item.convertName},<#else >${item.javaType} ${item.convertName}</#if></#list></#assign>
    ${tableInfo.className} selectByUniqueKey(${ukParamDef});

    int updateByUniqueKey(${tableInfo.className} ${tableInfo.convertName});

    int deleteByUniqueKey(${ukParamDef});
</#if>

}