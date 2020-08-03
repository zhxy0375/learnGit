package ${tableInfo.basePackage}.mapper;


import ${tableInfo.basePackage}.entity.${tableInfo.className};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* 类描述：${tableInfo.tableComment} Mapper
* 创建时间：${.now?string["yyyyMMdd"]}
* 作者：${tableInfo.author}
*/
public interface ${tableInfo.className}Mapper {

    int insert(${tableInfo.className} ${tableInfo.convertName});

    List<${tableInfo.className}> selectList(${tableInfo.className} ${tableInfo.convertName});
<#--
<#if tableInfo.primaryColumn??>
    <#assign primary = tableInfo.primaryColumn >
    ${tableInfo.className} selectByPrimaryKey(@Param("${primary.convertName}") ${primary.javaType} ${primary.convertName});

    int updateByPrimaryKey(@Param("${primary.convertName}") ${primary.javaType} ${primary.convertName});

    int deleteByPrimaryKey(@Param("${primary.convertName}") ${primary.javaType} ${primary.convertName});
<#else>
    int update(${tableInfo.className} ${tableInfo.convertName});

    int delete(${tableInfo.className} ${tableInfo.convertName});
</#if>-->
<#if tableInfo.primaryColumns??&&(tableInfo.primaryColumns?size > 0)>
    <#assign prKeys = tableInfo.primaryColumns>
    <#assign primaryParamDef><#list prKeys as item><#if item_has_next>@Param("${item.convertName}") ${item.javaType} ${item.convertName},<#else >@Param("${item.convertName}") ${item.javaType} ${item.convertName}</#if></#list></#assign>

    ${tableInfo.className} selectByPrimaryKey(${primaryParamDef});

    int updateByPrimaryKey(${tableInfo.className} ${tableInfo.convertName});

    int deleteByPrimaryKey(${primaryParamDef});
<#else>
    int update(${tableInfo.className} ${tableInfo.convertName});

    int delete(${tableInfo.className} ${tableInfo.convertName});
</#if>

<#if tableInfo.uniqueColumns??&&(tableInfo.uniqueColumns?size > 0)>
<#assign unKeys = tableInfo.uniqueColumns>
<#assign ukParamDef><#list unKeys as item><#if item_has_next>@Param("${item.convertName}") ${item.javaType} ${item.convertName},<#else >@Param("${item.convertName}") ${item.javaType} ${item.convertName}</#if></#list></#assign>
    ${tableInfo.className} selectByUniqueKey(${ukParamDef});

    int updateByUniqueKey(${tableInfo.className} ${tableInfo.convertName});

    int deleteByUniqueKey(${ukParamDef});
</#if>
}