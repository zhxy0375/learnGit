package com.bdqn.service;

import com.bdqn.dao.${tableInfo.tableClassName}Mapper;
import com.bdqn.pojo.${tableInfo.tableClassName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ${tableInfo.tableClassName}Service {
@Autowired(required = false)
private ${tableInfo.tableClassName}Mapper ${tableInfo.tableName}Mapper;

public ${tableInfo.tableClassName}Mapper get${tableInfo.tableClassName}Mapper() {
return ${tableInfo.tableName}Mapper;
}

public void set${tableInfo.tableClassName}Mapper(${tableInfo.tableClassName}Mapper ${tableInfo.tableClassName}Mapper) {
this.${tableInfo.tableName}Mapper = ${tableInfo.tableName}Mapper;
}

public List<${tableInfo.tableClassName}> findAll${tableInfo.tableClassName}(){return ${tableInfo.tableName}Mapper.findAll${tableInfo.tableClassName}();}

public List<${tableInfo.tableClassName}> find${tableInfo.tableClassName}ById(int id){return ${tableInfo.tableName}Mapper.find${tableInfo.tableClassName}ById(id);}

public int add${tableInfo.tableClassName}(${tableInfo.tableClassName} ${tableInfo.tableName}){return ${tableInfo.tableName}Mapper.add${tableInfo.tableClassName}(${tableInfo.tableName});}

public int del${tableInfo.tableClassName}ById(int id){return ${tableInfo.tableName}Mapper.del${tableInfo.tableClassName}ById(id);}

public int update${tableInfo.tableClassName}(${tableInfo.tableClassName} ${tableInfo.tableName}){return ${tableInfo.tableName}Mapper.update${tableInfo.tableClassName}(${tableInfo.tableName});}
}