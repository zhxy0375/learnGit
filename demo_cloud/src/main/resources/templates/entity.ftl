package com.bdqn.pojo;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;

@Data
public class ${tableInfo.className} implements Serializable{
<#list tableInfo.columnList as item>
    /**
    * ${item.comment}
    */
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