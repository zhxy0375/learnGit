package ${tableInfo.entityLocation};

<#if tableInfo.columnImports??&&(tableInfo.columnImports?size > 0)>
    <#list tableInfo.columnImports as item>
import ${item};
<#if item == "java.util.Date">
import com.fasterxml.jackson.annotation.JsonFormat;
</#if>
    </#list>
</#if>

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 数据库表：${tableInfo.tableName!}
* 类描述：${tableInfo.tableComment!} entity
* 创建时间：${.now?string["yyyy-MM-dd"]}
* 作者：${tableInfo.author}
* Entity json: ${tableInfo.entityJson}
*/
@Data
@NoArgsConstructor
public class ${tableInfo.className} implements Serializable{
<#if tableInfo.columnList??&&(tableInfo.columnList?size > 0)>
    <#list tableInfo.columnList as item>
        /**
        * ${item.comment!}
        */
        <#if item.javaType == "Date">
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
        </#if>
        private ${item.javaType} ${item.convertName};
    </#list>
</#if>
<#--<#list tableInfo.columnlist as item>-->
<#--    public void set${item.columnName}(${item.javaType} ${item.updateColumnName}){-->
<#--    this.${item.updateColumnName} = ${item.updateColumnName};-->
<#--    }-->
<#--    public ${item.javaType} get${item.columnName}(){-->
<#--    return ${item.updateColumnName};-->
<#--    }-->
<#--</#list>-->
}