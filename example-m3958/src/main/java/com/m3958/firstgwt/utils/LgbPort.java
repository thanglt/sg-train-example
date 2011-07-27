package com.m3958.firstgwt.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.inject.Inject;
import com.m3958.firstgwt.client.errorhandler.LgbRpcException;
import com.m3958.firstgwt.dao.AssetDao;
import com.m3958.firstgwt.dao.DepartmentDao;
import com.m3958.firstgwt.dao.LgbDao;
import com.m3958.firstgwt.model.Address;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Department;
import com.m3958.firstgwt.model.Lgb;


public class LgbPort {
//	private File srcFile = new File("E:/gwt/firstgwt/war/staticdss/fh.txt");
	
	@Inject
	private LgbDao dao;
	
	@Inject
	private AssetDao adao;
	
	@Inject
	private DepartmentDao ddao;
	
	private String toAppend = "";
	
	public void startPort(InputStreamReader isr) throws LgbRpcException{
		try {
//			FileInputStream fis = new FileInputStream(srcFile);
//			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
			LineNumberReader lr = new LineNumberReader(isr);
			String currentLine;
			Department d = ddao.findDepartmentByCname("奉化");
			while((currentLine = lr.readLine()) != null){
				BaseModel[] ms = match(currentLine);
//				if(ms != null)save((Lgb)ms[0],(Address)ms[1],d);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	line = " 张兴隆  山东  男  1929.03   194505  1945.06   1990.07   奉化市政协 主席                14   副地级   东门路147号301室    0574-88975668   857431986";
	
//	private void save(Lgb lgb,Address a,Department d) throws LgbRpcException {
//		lgb = dao.addLgb(lgb, d, null);
//		
//		if(a.getDizhi() != null){
//		}
//	}

	@SuppressWarnings("deprecation")
	public BaseModel[] match(String line){
		if(line.indexOf("花名册") != -1)return null;
		if(line.indexOf("入党时间") != -1)return null;
		if(line.indexOf(" 级别") != -1)return null;

		line = line.trim();
		if(line.isEmpty())return null;
		Pattern p = Pattern.compile("(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\D+)\\s+(\\d+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(.*)");
		Matcher m = p.matcher(line);
		
		boolean b = m.matches();
		if(b){
			try {
				Lgb lgb = new Lgb();
				Address a = new Address();
//				lgb.addAddress(a);
				for(int i=1;i<14;i++){
					String value  = "";
					if(i == 13 && toAppend.length()>0){
						value = toAppend;
						toAppend = "";
					}
					value += m.group(i);
					if(i == 1){
						lgb.setXm(value);
					}else if(i == 2){
						lgb.setJg(value);
					}else if(i == 3){
						lgb.setXb(value);
					}
					else if(i == 4){
						String[] ym = value.split("\\.");
						int year = Integer.parseInt(ym[0]) - 1900;
						int month = Integer.parseInt(ym[1]);
						Date d = new Date(year,month,1);
						lgb.setSr(d);
					}
					else if(i == 5){
						int year = Integer.parseInt(value.substring(0, 4)) - 1900;
						int month = Integer.parseInt(value.substring(4));
						Date d = new Date(year,month,1);
						lgb.setRwsj(d);
					}
					else if(i == 6){
						String[] ym = value.split("\\.");
						int year = Integer.parseInt(ym[0]) - 1900;
						int month = Integer.parseInt(ym[1]);
						Date d = new Date(year,month,1);
						lgb.setRdsj(d);
						
					}
					else if(i == 7){
						String[] ym = value.split("\\.");
						int year = Integer.parseInt(ym[0]) - 1900;
						int month = Integer.parseInt(ym[1]);
						Date d = new Date(year,month,1);
						lgb.setLtxsj(d);
						
					}
					else if(i == 8){
						String[] s = value.trim().split("\\s+");
						if(s.length > 1){
							lgb.setYgzdw(s[0]);
							lgb.setYzw(s[1]);
						}else{
							lgb.setYgzdw(s[0]);
						}
					}
					else if(i == 9){
						lgb.setXzjb(value);
					}
					else if(i == 10){
						
						lgb.setXsdy(value);
					}
					else if(i == 11){
						
						a.setDizhi(value);
					}
					else if(i == 12){

						a.setDianhua(value);						
					}
					else if(i == 13){

						lgb.setBz(value);
					}
				}
				return new BaseModel[]{lgb,a};
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return null;
			}
		}else{
//			toAppend = line;
		}
		return null;
	}
}
