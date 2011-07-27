package com.m3958.firstgwt.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.m3958.firstgwt.client.types.SmartConstants;

/**
 *@author <weijosn@gmail.com>
 * 
 *@version 1.0
 */
public class StringUtils {
	protected final static String REX_NUMBER = "[\\d]{1,32}";
	protected final static String NULL_ = "";
	protected final static String NULL_NULL = "NULL";
	protected final static String NULL_null = "null";
	protected final static String STR_SPLIT = ",";

	/**
	 * 
	 * 
	 * @param arg
	 * @return
	 */
	public final static boolean isNull(String arg) {
		if (arg != null)
			arg = arg.trim();
		if (arg == null || NULL_.equals(arg) || NULL_NULL.equals(arg)
				|| NULL_null.equals(arg)) {
			return true;
		}
		return false;
	}
	
	public final static String urlFromStringPairs(String... pairs ){
		if(pairs.length % 2 != 0){
			return "";
		}
		String url = "";
		for(int i =0;i<pairs.length;){
			url += "&" + pairs[i++] + "=" + pairs[i++];  
		}
		return url.substring(1);
	}
	

	/**
	 * 
	 * 
	 * @param arg
	 * @return
	 */
	public final static boolean isNumber(String arg) {
		if (isNull(arg) && Pattern.matches(REX_NUMBER, arg)) {
			return true;
		}
		return false;
	}

	public final static String[] toArray(String s) {
		return StringUtils.toArray(s, STR_SPLIT);
	}

	public final static String toString(String[] array) {
		return StringUtils.toString(array, STR_SPLIT);
	}

	public final static String toString(String[] array, String splitchar) {
		String r = null;
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (r == null)
					r = array[i];
				else
					r += splitchar + array[i];
			}
		}
		return r;
	}

	public final static String[] toArray(String s, String splitchar) {
		if (s != null) {
			String[] r = s.split(splitchar);
			return r;
		}
		return null;
	}
	
	
	public static boolean isDateString(String ds){
		return ds.matches("^[0-9]{4}-[01]?[0-9]{1}-[0-3]?[0-9]{1}$");
	}
	
	public static boolean isTimeStampString(String ts){
		return ts.matches("^[0-9]{4}-[01]?[0-9]{1}-[0-3]?[0-9]{1}T[0-2]?[0-9]{1}:[0-6]?[0-9]{1}:[0-6]?[0-9]{1}$");
	}

	public final static Date StringToDate(String source) {
		if (!StringUtils.isNull(source)) {
			try {
				return new SimpleDateFormat("yyyy-MM-dd").parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String[] marginArray(String[] id, String... strings) {
		String[] r = new String[id.length + strings.length];
		System.arraycopy(id, 0, r, 0, id.length);
		System.arraycopy(strings, 0, r, id.length, strings.length);
		return r;
	}

	public static List<String> matchText(String content, String rex) {
		List<String> ls = new ArrayList<String>();
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(content);
		while (m.find()) {
			ls.add(m.group(0));
		}
		return ls;
	}

	/**
	 *
	 * 
	 * @param src
	 * @return
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 *
	 * 
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
	public final static String[] addTwoArray(String[] s1,String[] s2){
		int t = s1.length + s2.length;
		String[] s = new String[t];
		for (int i = 0;i < s1.length; i++) {
			s[i] = s1[i];
		}
		
		for(int j = 0; j < s2.length ;j++){
			s[s1.length + j] = s2[j];
		}
		return s;
	}
	
	public static String sanitizeFileName(String filename){
		String[] fns = null;
		if(Osdetecter.isWindows()){
			fns = filename.split("\\\\");
		}else{
			fns = filename.split("/");
		}
		if(fns != null){
			return fns[fns.length - 1];
		}else{
			return "nofilename";
		}
	}


	public static Long getFileSizeLongValue(String fileSizeStr) {
		String s = fileSizeStr.toUpperCase().trim();
		if(s.endsWith("K")){
			return Long.parseLong(s.substring(0, s.length()-1)) * 1024;
		}else if(s.endsWith("M")){
			return Long.parseLong(s.substring(0, s.length()-1)) * 1024 * 1024;
		}else if(s.endsWith("G")){
			return Long.parseLong(s.substring(0, s.length()-1)) * 1024 * 1024 * 1024;
		}
		if(s.matches("\\d+")){
			return Long.parseLong(s);
		}
		return 0L;
	}
	
	
	public static int getSmartInterValue(String str){
		int id = SmartConstants.NONE_EXIST_MODEL_ID;
		try {
			id = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public static final int[] getIdArray(String idsStr,String seperator){
		String[] idsStrArray = idsStr.trim().split(seperator);
		int[] idsArray = new int[idsStrArray.length];
		for(int i=0;i<idsStrArray.length;i++){
			idsArray[i] = Integer.parseInt(idsStrArray[i]);
		}
		return idsArray;
	}
	
	public static final String getSQLIntInClause(int[] intArray){
		StringBuffer sb = new StringBuffer("(");
		for(int i:intArray){
			sb.append(i);
			sb.append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(")");
		return sb.toString();
	}
	
	public static String getSimpleClassName(String fullClassName){
		String[] ss = fullClassName.split("\\.");
		return ss[ss.length - 1];
	}
	
	public static String basenameWithoutDot(String filename) {
		int i = filename.lastIndexOf('.');
		String bn = filename.substring(0, i);
		return bn;
	}
	
	public static String getFileExtension(File f){
		String fn = f.getName();
		int p = fn.lastIndexOf('.'); 
		if(p != -1){
			return fn.substring(p + 1);
		}else{
			return "";
		}
	}
	
	public static String getFileExtensionWithDot(File f){
		String fn = f.getName();
		int p = fn.lastIndexOf('.'); 
		if(p != -1){
			return fn.substring(p);
		}else{
			return "";
		}
	}

	
	public static String getFileNameWithoutExt(File f) {
		String fn = f.getName();
		int i = fn.lastIndexOf('.');
		if(i != -1){
			return fn.substring(0, i);
		}else{
			return fn;
		}
	}

	

	public static String htmlToStr(String htmlStr) {
		String result = "";
		boolean flag = true;
		if (htmlStr == null) {
			return null;
		}
		char[] a = htmlStr.toCharArray();
		int length = a.length;
		for (int i = 0; i < length; i++) {
			if (a[i] == '<') {
				flag = false;
				continue;
			}
			if (a[i] == '>') {
				flag = true;
				continue;
			}
			if (flag == true) {
				result += a[i];
			}
		}
		return result.toString();
	}
	
	public static final String[] arraysub(String[] sa,String[] sb){
		List<String> al = new ArrayList<String>();
		for(String s : sb){
			boolean find = false;
			for(String s1 :sa){
				if(s.equals(s1)){
					find = true;
					break;
				}
			}
			if(!find){
				al.add(s);
			}
		}
		return (String[]) al.toArray();
	}

}
