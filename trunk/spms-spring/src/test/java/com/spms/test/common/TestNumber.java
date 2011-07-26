package com.spms.test.common;

import java.math.BigInteger;

import org.junit.Test;

public class TestNumber {
	
	@Test
	public void test2(){

		BigInteger intVal=new BigInteger("13512181811");
		
		BigInteger nextInt=intVal.nextProbablePrime();
		
		System.out.println("next:"+nextInt);
	}
	@Test
	public void test(){
		
		
		long val=13512181811l;
				
		long subVal=(long) Math.sqrt(val);
		boolean sign=true;
		for(int i=7;i<subVal;i+=2){
			
			if(val%i==0){
				
				System.out.println("no prime:"+i);
				sign=false;
				break;
			}
		}
		
		if(sign){
			System.out.println("ok,this is prime");
		}
		
	}

}
