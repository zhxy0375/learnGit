package com.example.demo_cloud.util;


import com.example.demo_cloud.dto.vo.TableInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerUtil {
	Configuration configuration=new Configuration(Configuration.getVersion());
	//初始化方法
	public void init(){
		try {
			//解析路径是中文乱码时找不到路径的错误
			String path= URLDecoder.decode(this.getClass().getClassLoader().getResource("").getPath(),"UTF-8");
			configuration.setDirectoryForTemplateLoading(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//把获得的信息写入到模板中
	public void createFile(String template, String pathname, TableInfo ll){
		try {
			Template template1=configuration.getTemplate(template);
			Map map=new HashMap();
			//传入内容到模板
			map.put("tablelist",ll);
			FileOutputStream fs=new FileOutputStream(pathname);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fs);
			template1.process(map,outputStreamWriter);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
