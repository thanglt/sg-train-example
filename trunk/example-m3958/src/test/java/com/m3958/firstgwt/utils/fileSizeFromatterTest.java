package com.m3958.firstgwt.utils;

import org.junit.Test;

public class fileSizeFromatterTest {
	
	public static class Formator{
		private int gb = 1073741824;
		private int mb = 1048576;
		private int kb = 1024;
		
		public String format(int i){
			String s = "";
			double d;
			boolean done = false;
			if(i/gb > 0){
				d = ((int)(i/(double)gb * 100))/100d;
				if(d > 1.1){
					s = d + "G";
					done = true;
				}
			}
			
			if(!done && i/mb>0){
				d = ((int)(i/(double)mb * 100))/100d;
				if(d > 1.1){
					s = d + "M";
					done = true;
				}
			}
			
			if(!done && i/kb>0){
				d = ((int)(i/(double)kb * 100))/100d;
				if(d>1.1){
					s = d + "K";
					done = true;
				}
			}
			
			if(!done){
				s = String.valueOf(i) + "B";
			}
			return s;
		}
	}
	
	@Test
	public void t(){
		Formator f = new Formator();
		String s = f.format(1024*1056);
		System.out.print(s);
	}

}
