package com.m3958.firstgwt.utils;

import java.util.UUID;

import org.junit.Test;

public class AnonymousTest {
	
	/**
	 * 
	 */
	@Test
	public void tt(){
		Object o = new Anony(){

			public void hello() {
				System.out.print("hello");
			}};
		System.out.print(o.getClass().getName());
		
		String uuid = UUID.randomUUID().toString();
		System.out.println();
		System.out.print(uuid);
	}

}
