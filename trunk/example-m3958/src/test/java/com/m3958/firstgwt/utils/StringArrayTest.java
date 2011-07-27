package com.m3958.firstgwt.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.m3958.firstgwt.client.layout.AppToolStrip.Btname;

public class StringArrayTest {

	
	public Btname[] getBtstatusWithout(Btname[] status,Btname...without){
		List<Btname> bts = new ArrayList<Btname>();
		for(Btname bt :status){
			boolean find = false;
			for(Btname btn: without){
				if(btn == bt){
					find = true;
					break;
				}
			}
			if(!find)bts.add(bt);
		}
		Btname[] bbttss = new Btname[bts.size()];
		for(int i=0;i<bbttss.length;i++){
			bbttss[i] = bts.get(i);
		}
		return bbttss;
	}
	
	@Test
	public void tt(){
		Btname[] bts = new Btname[]{Btname.ADD,Btname.EDIT,Btname.REFRESH};
		Btname[] bb = getBtstatusWithout(bts, Btname.REFRESH,Btname.REFRESH);
		Assert.assertEquals(2, bb.length);
	}
	
}
