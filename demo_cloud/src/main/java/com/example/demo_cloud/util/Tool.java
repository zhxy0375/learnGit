package com.example.demo_cloud.util;

import cn.hutool.core.util.StrUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tool {
	private static Pattern linePattern = Pattern.compile("_(\\w)");

	private static String MappingSplit = "/";
	private static String LineSplit = "_";

	/** 下划线转驼峰 */
	public static String lineToHump(String str) {
		str = str.toLowerCase();
		Matcher matcher = linePattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/** 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)}) */
	public static String humpToLine(String str) {
		return str.replaceAll("[A-Z]", "_$0").toLowerCase();
	}

	private static Pattern humpPattern = Pattern.compile("[A-Z]");

	/** 驼峰转下划线,效率比上面高 */
	public static String humpToLine2(String str) {
		Matcher matcher = humpPattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, LineSplit + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 生成 mapping path
	 * @param str
	 * @return
	 */
	public static String mappingPath(String str) {
		if(StrUtil.isBlank(str) ){
			return MappingSplit;
		}

		String mappingPath = "";
		String[] mapSegAr = str.split(LineSplit);
		if(mapSegAr.length >3){
			mappingPath = MappingSplit + mapSegAr[1] + MappingSplit +mapSegAr[2]+ MappingSplit +mapSegAr[3];
		}else if(mapSegAr.length == 3){
			mappingPath =  MappingSplit + mapSegAr[0] + MappingSplit +mapSegAr[1]+ MappingSplit +mapSegAr[2];
		}else if(mapSegAr.length == 2){
			mappingPath =  MappingSplit + mapSegAr[0] + MappingSplit +mapSegAr[1];
		}else {
			mappingPath =  MappingSplit + mapSegAr[0];
		}
		return mappingPath.toLowerCase();
	}

	/**
	 * 首字母大写
	 * @param str
	 * @return
	 */
	public static String upperFirstChar (String str) {
		if(StrUtil.isBlank(str) ){
			return str;
		}

		String firstC = str.substring(0,1).toUpperCase();
		if(str.length() == 1){
			return firstC;
		}
		return firstC + str.substring(1);
	}

	public static void main(String[] args) {
		String lineToHump = lineToHump("f_parent_no_leader");
		System.out.println(lineToHump);// fParentNoLeader
		System.out.println(humpToLine(lineToHump));// f_parent_no_leader
		System.out.println(humpToLine2(lineToHump));// f_parent_no_leader
	}
}