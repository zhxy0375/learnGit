package ${tableInfo.entityLocation};
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
* 类描述：${tableInfo.tableComment} entity
* 创建时间：${.now?string["yyyyMMdd"]}
* 作者：${tableInfo.author}
* Entity json: ${tableInfo.entityJson}
*/
@Data
public class ${tableInfo.className} implements Serializable{
<#list tableInfo.columnList as item>
    /**
    * ${item.comment}
    */
    <#if item.javaType == "Date">
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    </#if>
    private ${item.javaType} ${item.convertName};
</#list>

<#--<#list tableInfo.columnlist as item>-->
<#--    public void set${item.columnName}(${item.javaType} ${item.updateColumnName}){-->
<#--    this.${item.updateColumnName} = ${item.updateColumnName};-->
<#--    }-->
<#--    public ${item.javaType} get${item.columnName}(){-->
<#--    return ${item.updateColumnName};-->
<#--    }-->
<#--</#list>-->
}