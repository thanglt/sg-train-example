package com.m3958.firstgwt.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.m3958.firstgwt.client.types.FileSaveTo;
import com.m3958.firstgwt.model.Address;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.House;
import com.m3958.firstgwt.model.Lgb;
import com.m3958.firstgwt.model.Reward;

public class IreportBeanSet {
	
	static List<Lgb>  al = new ArrayList<Lgb>();
	
	public static List<Lgb> getLgbs(){
		for(int i=0;i<6;i++){
			al.add(creaetLgb());
		}
		return al;
	}

	
	public static List<Address>  getAddress(){
		return al.get(0).getAddresses();
	}
	
	
	private static  Integer xx =0;
	
	private static Lgb creaetLgb() {
		Lgb l = new Lgb();
		l.setXm("江立波" + xx++);
		l.setSr(new Date());
		l.setRdsj(new Date());
		
		for(int i=0;i<10;i++){
			l.addAddress(createAddress());
		}
		
		for(int i=0;i<10;i++){
			l.addHouse(createHouse());
		}
		
		Asset a = new Asset();
		a.setSaveTo(FileSaveTo.FILE_SYSTEM);
		a.setFilePath("2010/10/28/1404e9b7-652b-4f3e-8287-72a81dd22bdb.jpg");
		l.setZp(a);
		for(int i=0;i<10;i++){
			Reward r = new Reward();
			r.setJlqk("Ivar Jacobson：我们为什么需要软件工程理论");
			l.addReward(r);
		}
		return l;
	}

	private static House createHouse() {
		House h = new House();
		h.setZfxz("公房");
		h.setStructure("砖混结构");
		h.setArea(55.8f);
		return h;
	}

	private static Address createAddress() {
		Address a = new Address();
		a.setDizhi("地址" + new Random());
		a.setDianhua("0574-88588885");
		return a;
	}
	
	public static InputStream getStream(String nn){
		SubReportFeed sf =new SubReportFeed();
//		sf.init();
		return sf.getStream(nn);
	}

}
