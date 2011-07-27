package com.m3958.firstgwt.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class FieldShadowTest {
	public static class A{
		private String pirvatef = "private A";

		public void setPirvatef(String pirvatef) {
			this.pirvatef = pirvatef;
		}

		public String getPirvatef() {
			return pirvatef;
		}
	}
	
	public static class B extends A{
		private String pirvatef = "private B";

		public String getPirvatef() {
			return pirvatef;
		}

		public void setPirvatef(String pirvatef) {
			this.pirvatef = pirvatef;
		}
	}
	
	private A a;
	
	private B b;
	
	@Before
	public void setup(){
		a = new A();
		b = new B();
	}
	
	
	@Test
	public void t1(){
		System.out.print((a).getPirvatef());
		System.out.print(((A)b).getPirvatef());
		
	}
	
	
	@After
	public void cleanup(){
		a = null;
		b = null;
	}

}
