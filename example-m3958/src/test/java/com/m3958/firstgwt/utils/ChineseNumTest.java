package com.m3958.firstgwt.utils;

import org.junit.Test;

public class ChineseNumTest {

	public static class ChineseNumber{
		private static final String[] TOBIG = new String[] { "零", "壹", "贰", "叁",
		    "肆", "伍", "陆", "柒", "捌", "玖" };
		private static final String POS[] = new String[] { "", "拾", "佰", "仟", "万",
		    "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿" };
		
		public String change(String str)
		{
		   str = delZero(str);
		   if(str.isEmpty())return "零";
		   String newStr ="";
		   for (int i = 0, j = str.length(); i < j; i++)
		   {
		    String s = str.substring(j - i - 1, j - i);
		    newStr = TOBIG[Integer.parseInt(s)].concat(POS[i])+newStr;
		   }
		   newStr = newStr.replace("零仟", "零");
		   newStr = newStr.replace("零佰", "零");
		   newStr = newStr.replace("零拾", "零");
		   newStr = newStr.replace("零万", "万");
		   for(int i= 0;i<8;i++)
		   newStr = newStr.replace("零零", "零");
		   newStr = newStr.replace("零仟", "仟");
		   newStr = newStr.replace("零佰", "佰");
		   newStr = newStr.replace("零拾", "拾");
		   newStr = newStr.replace("零万", "万");
		   newStr = newStr.replace("零亿", "亿");
		   if(newStr.endsWith("零"))
		    newStr = newStr.substring(0,newStr.length()-1);
		   return newStr;
		}
		// 去除用0开头的数
		private String delZero(String str)
		{
		   if (str.startsWith("0"))
		   {
		    str = str.substring(str.indexOf("0") + 1);
		    return delZero(str);
		   }
		   return str;
		}
	}
	
	@Test
	public void tt(){
		ChineseNumber cn = new ChineseNumber();
		System.out.println(cn.change("10"));
		System.out.println(cn.change("11"));
		System.out.println(cn.change("12"));
		System.out.println(cn.change("0"));
		System.out.println(cn.change("9"));
	}
	
}
