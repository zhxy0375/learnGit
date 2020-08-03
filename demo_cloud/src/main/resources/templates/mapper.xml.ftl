<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${tableInfo.mapperLocation}.${tableInfo.className}Mapper">
<#if tableInfo.columnList??&&(tableInfo.columnList?size > 0)>

    <#assign colms = tableInfo.columnList>
    <resultMap type="${tableInfo.entityLocation}.${tableInfo.className}" id="${tableInfo.className}Result">
    <#list colms as item>
        <result property="${item.convertName}" column="${item.name}"/>
    </#list>
    </resultMap>

    <#assign basicFields><#list colms as item><#if item_has_next>${item.name},<#else >${item.name}</#if></#list></#assign>
    <!--查询基本字段-->
    <sql id="basicFieldVo">
        select ${basicFields} from ${tableInfo.tableName}
    </sql>

    <!--查询 列表-->
    <select id="selectList" parameterType="${tableInfo.entityLocation}.${tableInfo.className}" resultMap="${tableInfo.className}Result">
        <include refid="basicFieldVo"/>
        <where>
        <#list colms as item>
        <#if item.javaType=="String">
            <if test="${item.convertName} != null  and ${item.convertName} != ''"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        <#else>
            <if test="${item.convertName} != null"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        </#if>
        </#list>
        </where>
    </select>
    <!--插入 -->
    <insert id="insert" parameterType="${tableInfo.entityLocation}.${tableInfo.className}">
        insert into ${tableInfo.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list colms as item>
        <#if item.javaType=="String">
            <if test="${item.convertName} != null  and ${item.convertName} != ''">${item.name},</if>
        <#else>
            <if test="${item.convertName} != null">${item.name},</if>
        </#if>
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#list colms as item>
        <#if item.javaType=="String">
            <if test="${item.convertName} != null  and ${item.convertName} != ''"><#noparse>#{</#noparse>${item.convertName}},</if>
        <#else>
            <if test="${item.convertName} != null"><#noparse>#{</#noparse>${item.convertName}},</if>
        </#if>
        </#list>
        </trim>
    </insert>

    <#if tableInfo.primaryColumns??&&(tableInfo.primaryColumns?size > 0)>
        <#assign prKeys = tableInfo.primaryColumns>
    <select id="selectByPrimaryKey"  resultMap="${tableInfo.className}Result">
        <include refid="basicFieldVo"/>
        <where>
        <#list prKeys as item>
        <#if item.javaType=="String">
            <if test="${item.convertName} != null  and ${item.convertName} != ''"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        <#else>
            <if test="${item.convertName} != null"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        </#if>
        </#list>
        </where>
    </select>
    <!--更新 -->
    <update id="updateByPrimaryKey" parameterType="${tableInfo.entityLocation}.${tableInfo.className}">
        update ${tableInfo.tableName}
        <trim prefix="SET" suffixOverrides=",">
        <#list colms as item>
        <#if item.javaType=="String">
            <if test="${item.convertName} != null  and ${item.convertName} != ''">  ${item.name} = <#noparse>#{</#noparse>${item.convertName}},</if>
        <#else>
            <if test="${item.convertName} != null"> ${item.name} = <#noparse>#{</#noparse>${item.convertName}},</if>
        </#if>
        </#list>
        </trim>
        <where>
        <#list prKeys as item>
        <#if item.javaType=="String">
            <if test="${item.convertName} != null  and ${item.convertName} != ''"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        <#else>
            <if test="${item.convertName} != null"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        </#if>
        </#list>
        </where>
    </update>
    <!--删除 -->
    <delete id="deleteByPrimaryKey">
        delete from ${tableInfo.tableName}
        <where>
        <#list prKeys as item>
        <#if item.javaType=="String">
            <if test="${item.convertName} != null  and ${item.convertName} != ''"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        <#else>
            <if test="${item.convertName} != null"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        </#if>
        </#list>
        </where>
    </delete>
    <#else>
    <!--更新 -->
    <update id="update" parameterType="${tableInfo.entityLocation}.${tableInfo.className}">
        update ${tableInfo.tableName}
        <trim prefix="SET" suffixOverrides=",">
        <#list colms as item>
        <#if item.javaType=="String">
            <if test="${item.convertName} != null  and ${item.convertName} != ''">${item.name} = <#noparse>#{</#noparse>${item.convertName}},</if>
        <#else>
            <if test="${item.convertName} != null">${item.name} = <#noparse>#{</#noparse>${item.convertName}},</if>
        </#if>
        </#list>
        </trim>
        <where>
        <#list colms as item>
        <#if item.javaType=="String">
            <if test="${item.convertName} != null and ${item.convertName} != ''"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        <#else>
            <if test="${item.convertName} != null"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        </#if>
        </#list>
        </where>
    </update>
    <!--删除 -->
    <delete id="delete">
        delete from ${tableInfo.tableName}
        <where>
        <#list colms as item>
        <#if item.javaType=="String">
            <if test="${item.convertName} != null and ${item.convertName} != ''"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        <#else>
            <if test="${item.convertName} != null"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        </#if>
        </#list>
        </where>
    </delete>
    </#if>

    <#if tableInfo.uniqueColumns??&&(tableInfo.uniqueColumns?size > 0)>
        <#assign unKeys = tableInfo.uniqueColumns>
    <select id="selectByUniqueKey"  resultMap="${tableInfo.className}Result">
        <include refid="basicFieldVo"/>
        <where>
        <#list unKeys as item>
        <#if item.javaType=="String">
            <if test="${item.convertName} != null and ${item.convertName} != ''"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        <#else>
            <if test="${item.convertName} != null"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        </#if>
        </#list>
        </where>
    </select>
    <!--更新-->
    <update id="updateByUniqueKey" parameterType="${tableInfo.entityLocation}.${tableInfo.className}">
        update ${tableInfo.tableName}
        <trim prefix="SET" suffixOverrides=",">
        <#list colms as item>
        <#if item.javaType=="String">
            <if test="${item.convertName} != null and ${item.convertName} != ''">${item.name} = <#noparse>#{</#noparse>${item.convertName}},</if>
        <#else>
            <if test="${item.convertName} != null">${item.name} = <#noparse>#{</#noparse>${item.convertName}},</if>
        </#if>
        </#list>
        </trim>
        <where>
        <#list unKeys as item>
        <#if item.javaType=="String">
            <if test="${item.convertName} != null and ${item.convertName} != ''"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        <#else>
            <if test="${item.convertName} != null"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        </#if>
        </#list>
        </where>
    </update>
    <!--删除 -->
    <delete id="deleteByUniqueKey">
        delete from ${tableInfo.tableName}
        <where>
        <#list unKeys as item>
        <#if item.javaType=="String">
            <if test="${item.convertName} != null and ${item.convertName} != ''"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        <#else>
            <if test="${item.convertName} != null"> and ${item.name} = <#noparse>#{</#noparse>${item.convertName}}</if>
        </#if>
        </#list>
        </where>
    </delete>
    </#if>
</#if>
</mapper>