package com.example.demo_cloud.dto.req;


import java.io.Serializable;
import lombok.Data;

/**
* 数据库表：CRUD_DB_CONFIG
* 类描述：增删改查数据库配置 entity
* 创建时间：2020-09-23
* 作者：zxy09
* Entity json: {"sid":0,"dbName":"str","driverClass":"str","url":"str","userName":"str","password":"str","properties":"str"}
*/
@Data
public class DbConfig implements Serializable{

        /**
        * 驱动类
        */
        private String driverClass;
        /**
        * db url
        */
        private String url;
        /**
        * db 用户名
        */
        private String userName;
        /**
        * db 密码
        */
        private String password;
        /**
        * connect propertyies
        */
        private String properties;
}