package com.example.demo_cloud.util;


import com.example.demo_cloud.dto.vo.TableInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FreeMarkerUtil {
	Configuration configuration=new Configuration(Configuration.getVersion());

	//初始化方法
	public void init(){
		try {
			//解析路径是中文乱码时找不到路径的错误
			String path= URLDecoder.decode(this.getClass().getClassLoader().getResource("").getPath(),"UTF-8") +"templates";
			configuration.setDirectoryForTemplateLoading(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//把获得的信息写入到模板中
	public void createFile(String template,String dir, String filename, TableInfo ll){

		try {
			Template template1=configuration.getTemplate(template);
			Map map=new HashMap();
			//传入内容到模板
			map.put("tableInfo",ll);

			File dirFile = new File(dir);
			if(!dirFile.exists()){
				dirFile.mkdirs();
			}
			String pathname = dir + Tool.FileSplit + filename;
			@Cleanup FileOutputStream fs=new FileOutputStream(pathname);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fs);
			template1.process(map,outputStreamWriter);
		} catch (IOException e) {
			log.error("io异常",e);
		} catch (TemplateException e) {
			log.error("找不到模板",e);

		}
	}
}
