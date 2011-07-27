package com.m3958.firstgwt.utils;

import org.junit.Test;

public class Grid960 {
	private static int totalWidth = 600;
	private static int leftMargin = 5;
	private static int rightMargin = 5;
	private static int cols = 24;
	
	@Test
	public void TestCols(){
		int totalDivWidth = totalWidth / cols;
		int divNetWidth = totalDivWidth - leftMargin - rightMargin;
		for(int i = 1;i<cols + 1;i++){
			//margin左右成对，所以加起来。
			System.out.println("col" + i + "=" + (i * divNetWidth + (i - 1)*(leftMargin + rightMargin)));
		}
	}
	
	@Test
	public void TestPrefix(){
		int totalDivWidth = totalWidth / cols;
		int divNetWidth = totalDivWidth - leftMargin - rightMargin;
		//prefix padding的时候，要考虑到自身的marginleft，所以它的偏移也是左右一对。自己左边要考虑进去。
		for(int i = 1;i<cols;i++){
			//margin左右成对，所以加起来。
			System.out.println("prefix" + i + "=" + (i * divNetWidth + (i)*(leftMargin + rightMargin)));
		}
	}
	
	@Test
	public void TestSuffix(){
		int totalDivWidth = totalWidth / cols;
		int divNetWidth = totalDivWidth - leftMargin - rightMargin;
		//prefix padding的时候，要考虑到自身的marginleft，所以它的偏移也是左右一对。自己左边要考虑进去。
		for(int i = 1;i<cols;i++){
			//margin左右成对，所以加起来。
			System.out.println("suffix" + i + "=" + (i * divNetWidth + (i)*(leftMargin + rightMargin)));
		}
	}
	
	

}
