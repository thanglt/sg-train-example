package com.m3958.firstgwt.jpa;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import jxl.Workbook;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.m3958.firstgwt.client.types.AddressField;
import com.m3958.firstgwt.client.types.CareerField;
import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.model.Address;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.Career;
import com.m3958.firstgwt.model.Family;
import com.m3958.firstgwt.model.House;
import com.m3958.firstgwt.model.Lgb;
import com.m3958.firstgwt.model.Reward;
import com.m3958.firstgwt.model.Shequ;
import com.m3958.firstgwt.model.StepCareer;





public class JxlTest {
	
	public static class LgbExcelExport{
		private WritableWorkbook workbook;
		private PropertyUtilsBean pub = new PropertyUtilsBean();
		private Map<String, Integer> startCols = new HashMap<String, Integer>();
		private  Map<String, Integer> currentRows = new HashMap<String, Integer>();
		private Set<String> ignoreFields = new HashSet<String>();
		private Map<String, Boolean> titleExist = new HashMap<String, Boolean>();
			
		private String[] modelNames = new String[]{
			Lgb.class.getName(),
			Address.class.getName(),
			Family.class.getName(),
			House.class.getName(),
			Career.class.getName(),
			StepCareer.class.getName(),
			Reward.class.getName()
		};
		
		private void init(){
			ignoreFields.add("creator");
			ignoreFields.add("creatorIds");
			ignoreFields.add("version");
			ignoreFields.add("createdAt");
			ignoreFields.add("class");
			ignoreFields.add("creatorIds");
			ignoreFields.add("departmentIds");

			startCols.put(Lgb.class.getName(), 0);
			startCols.put(Address.class.getName(), 40);
			startCols.put(Family.class.getName(), 50);
			startCols.put(House.class.getName(), 60);
			startCols.put(Career.class.getName(), 70);
			startCols.put(StepCareer.class.getName(), 80);
			startCols.put(Reward.class.getName(), 90);
			
			for(String s:modelNames){
				currentRows.put(s,1);
			}
			
			for(String s:modelNames){
				titleExist.put(s, false);
			}			
		}
		
		private String filename;
		
		public LgbExcelExport(String fn){
			this.filename = fn;
			init();
		}
		
		public void createExcelToFile(EntityManager em){
			try {
				workbook = Workbook.createWorkbook(new File(filename));
				WritableSheet  sheet = workbook.createSheet("First Sheet", 0);
				startTest(em,sheet);			
				workbook.write(); 
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		
		//(column, row)
		@SuppressWarnings("unchecked")
		private void startTest(EntityManager em,WritableSheet sheet) {
			String qs = "SELECT l FROM Lgb AS l";
			Query q = em.createQuery(qs);
			List<Lgb> lgbs = q.getResultList();
			PropertyDescriptor[] pds = pub.getPropertyDescriptors(new Lgb());
			for(Lgb lgb:lgbs){
				int col = startCols.get(Lgb.class.getName());
				int row = currentRows.get(Lgb.class.getName());
				for(PropertyDescriptor pd: pds){
					try {
						if(pd.getName().equals("attachments")){
						}else if(pd.getName().equals("shequ")){
							Shequ shequ = (Shequ) pub.getProperty(lgb,"shequ"); 
							String shequName = "";
							if(shequ != null){
								shequName = shequ.getName();
							}
							Label label = new Label(col,row,shequName);
							if(!titleExist.get(Lgb.class.getName())){
								Label tlabel = new Label(col,0,"社区");
								sheet.addCell(tlabel);
							}
							sheet.addCell(label);
							col++;
						}else if(pd.getName().equals("families")){
							processRelation(Family.class.getName(),lgb,em,sheet);
						}else if(ignoreFields.contains(pd.getName())){
							;
						}else if(pd.getName().equals("careers")){
							processRelation(Career.class.getName(),lgb,em,sheet);
						}else if(pd.getName().equals("stepCareers")){
							processRelation(StepCareer.class.getName(),lgb,em,sheet);
						}else if(pd.getName().equals("rewards")){
							processRelation(Reward.class.getName(),lgb,em,sheet);
						}else if(pd.getName().equals("houses")){
							processRelation(House.class.getName(),lgb,em,sheet);
						}else if(pd.getName().equals("addresses")){
							processRelation(Address.class.getName(),lgb,em,sheet);
						}else if(pd.getName().equals("zp")){
							WritableImage wi = new WritableImage(col,row,1,1,new byte[]{});
							sheet.addImage(wi);
							if(!titleExist.get(Lgb.class.getName())){
								Label tlabel = new Label(col,0,"照片");
								sheet.addCell(tlabel);
							}
							col++;
							
						}else if(pd.getName().equals("department")){
							;
						}else if(pd.getName().equals("mainAddress")){
							;
						}else{
							Object rawObject = pub.getProperty(lgb,pd.getName());
							addCell(sheet, col, row, rawObject, pd, Lgb.class.getName());
							col++;
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (RowsExceededException e) {
						e.printStackTrace();
					} catch (WriteException e) {
						e.printStackTrace();
					}
				}
				titleExist.put(Lgb.class.getName(), true);
				currentRows.put(Lgb.class.getName(), currentRows.get(Lgb.class.getName()) + 1);
			}
		}
		
		private void addCell(WritableSheet sheet,int col,int row,Object rawObject,PropertyDescriptor pd,String modelName) throws RowsExceededException, WriteException{
			if(rawObject == null){
				addLabelCell(sheet, col, row, "");
			}else if(pd.getPropertyType() == Date.class){
				addDateCell(sheet, col, row, (Date) rawObject);
			}else if(pd.getPropertyType() == int.class || pd.getPropertyType() == Integer.class){
				addIntegerCell(sheet,col,row,(Integer) rawObject);
			}else if(pd.getPropertyType() == float.class || pd.getPropertyType() == Float.class){
				addFloatCell(sheet,col,row,(Float) rawObject);
			}else if(pd.getPropertyType() == Boolean.class || pd.getPropertyType() == boolean.class){
				addBooleanCell(sheet,col,row,(Boolean) rawObject);
			}else if(pd.getPropertyType() == String.class){
				addLabelCell(sheet, col, row, (String) rawObject);
			}else{
				addLabelCell(sheet, col, row, rawObject.toString());
			}
			if(!titleExist.get(modelName)){
				Label tlabel;
				if(Lgb.class.getName().equals(modelName)){
					 tlabel = new Label(col,0,LgbField.getFieldEnumByEname(pd.getName()).getCname());
				}else if(Address.class.getName().equals(modelName)){
					tlabel = new Label(col,0,AddressField.getFieldEnumByEname(pd.getName()).getCname());
				}else if(Career.class.getName().equals(modelName)){
					tlabel = new Label(col,0,CareerField.getFieldEnumByEname(pd.getName()).getCname());
				}else if(StepCareer.class.getName().equals(modelName)){
					tlabel = new Label(col,0,LgbField.getFieldEnumByEname(pd.getName()).getCname());
				}else if(Lgb.class.getName().equals(modelName)){
					tlabel = new Label(col,0,LgbField.getFieldEnumByEname(pd.getName()).getCname());
				}else if(Lgb.class.getName().equals(modelName)){
					tlabel = new Label(col,0,LgbField.getFieldEnumByEname(pd.getName()).getCname());
				}else if(Lgb.class.getName().equals(modelName)){
					tlabel = new Label(col,0,LgbField.getFieldEnumByEname(pd.getName()).getCname());
				}else{
					tlabel = new Label(col,0,pd.getName());
				}
				sheet.addCell(tlabel);
			}
		}
		
		private void addBooleanCell(WritableSheet sheet, int col, int row,
				Boolean bvalue) throws RowsExceededException, WriteException {
			jxl.write.Boolean b = new jxl.write.Boolean(col,row,bvalue);
			sheet.addCell(b);
		}

		private void addFloatCell(WritableSheet sheet, int col, int row,
				Float f) throws RowsExceededException, WriteException {
			WritableCellFormat integerFormat = new WritableCellFormat (NumberFormats.FLOAT);
			jxl.write.Number number = new jxl.write.Number(col, row, f, integerFormat);
			sheet.addCell(number);			
		}

		private void addIntegerCell(WritableSheet sheet, int col, int row,	int i) throws RowsExceededException, WriteException {
			WritableCellFormat integerFormat = new WritableCellFormat (NumberFormats.INTEGER);
			jxl.write.Number number = new jxl.write.Number(col, row, i, integerFormat);
			sheet.addCell(number);
		}

		private void addDateCell(WritableSheet sheet,int col,int row,Date d) throws RowsExceededException, WriteException{
			DateFormat df = new DateFormat("yyyy-MM-dd");
			WritableCellFormat wcf = new WritableCellFormat(df);
			DateTime dateCell = new DateTime(col,currentRows.get(Lgb.class.getName()),d,wcf);
			sheet.addCell(dateCell);			
		}
		
		private void addLabelCell(WritableSheet sheet,int col,int row,String s) throws RowsExceededException, WriteException{
			Label label = new Label(col,currentRows.get(Lgb.class.getName()),s);
			sheet.addCell(label);
		}
		
		@SuppressWarnings("unchecked")
		private void processRelation(String os,Lgb lgb,EntityManager em,WritableSheet sheet) {
			String[] ss = os.split("\\.");
			String sos = ss[ss.length -1 ];
			String qs = "SELECT o FROM " + sos + " AS o WHERE o.lgb.id = :lgbId";
			Query q = em.createQuery(qs);
			q.setParameter("lgbId", lgb.getId());
			List<BaseModel> results = q.getResultList();
			Class c;
			PropertyDescriptor[] pds = null;
			try {
				c = Class.forName(os);
				pds = pub.getPropertyDescriptors(c.newInstance());
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			 
			for(BaseModel bm:results){
				int col = startCols.get(os);
				int row = currentRows.get(os);
				if(pds == null)return;
				for(PropertyDescriptor pd: pds){
					try {
						if(pd.getName().equals("lgb")){
							Lgb l = (Lgb) pub.getProperty(bm,"lgb"); 
							Label label = new Label(col,row,l.getId()+"");
							sheet.addCell(label);
							if(!titleExist.get(os)){
								Label tlabel = new Label(col,0,"老干部ID");
								sheet.addCell(tlabel);
							}
							col++;
						}else if(ignoreFields.contains(pd.getName())){
							;
						}else{
							Object rawObject = pub.getProperty(bm,pd.getName());
							addCell(sheet, col, row, rawObject, pd, os);
							col++;
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (RowsExceededException e) {
						e.printStackTrace();
					} catch (WriteException e) {
						e.printStackTrace();
					}
				}
				titleExist.put(os, true);
				currentRows.put(os, currentRows.get(os) + 1);
			}
		}
		
	}
	
	private static final String PERSISTENCE_UNIT_NAME = "p-unit";
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	
	

	
	@Before
	public void create(){
	}
	

	@Test
	public void testMe(){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		LgbExcelExport lee = new LgbExcelExport("e:/myfile.xls");
		lee.createExcelToFile(em);
		em.getTransaction().commit();
		em.close();
	}

	@After
	public void destroy(){

	}
}
