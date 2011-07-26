package com.spms.test.manager;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.skynet.spms.manager.organization.EnterpriseManager;
import com.skynet.spms.persistence.entity.csdd.c.CAGECode;
import com.skynet.spms.persistence.entity.csdd.c.CountryCode;
import com.skynet.spms.persistence.entity.csdd.c.CustomerIdentificationCode;
import com.skynet.spms.persistence.entity.organization.enterpriseInformation.baseEnterpriseInformation.BaseEnterpriseInformation;
import com.skynet.spms.persistence.entity.organization.enterpriseInformation.carriers.Carrier;
import com.skynet.spms.persistence.entity.organization.enterpriseInformation.clearanceAgencys.ClearanceAgency;
import com.skynet.spms.persistence.entity.organization.enterpriseInformation.customers.Customer;
import com.skynet.spms.persistence.entity.organization.enterpriseInformation.manufacturers.Manufacturer;
import com.skynet.spms.persistence.entity.organization.enterpriseInformation.repairAgencys.RepairAgency;
import com.skynet.spms.persistence.entity.organization.enterpriseInformation.suppliers.Supplier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:base_Context.xml",
		"classpath:service_Context.xml",
		"classpath:util_Context.xml"})
@TransactionConfiguration(transactionManager="transManager", defaultRollback=false)
public class TestEnterpriseManager {

	@Autowired
	private EnterpriseManager enterpriseManager ;
	
	
	private Logger log=LoggerFactory.getLogger(TestEnterpriseManager.class);
	//@Test
	public void delete(){
		
		enterpriseManager.deleteEntity("488080382eeac392012eeac3c5600000", BaseEnterpriseInformation.class);
	}
	
	//@Test
	public void instert(){
		/*COMACSC entity = new COMACSC();
		//BaseEntity Properties
		entity.setCreateBy("shanyf");
		entity.setCreateDate(new Date());
		entity.setKeyword("COMACSC");
		entity.setVersion(1);
		//BaseEnterpriseInformation Properties
		entity.setAbbreviation("skynet");
		entity.setAddress_en("shanghai");
		entity.setAddress_zh("天络");
		entity.setEmail("sky@skynetsoft.org");
		entity.setEnterpriseHomepageURL("www.skynetsoft.org");
		entity.setEnterpriseName_en("sky net software");
		entity.setEnterpriseName_zh("天络科技");
		entity.setFax("64542121");
		entity.setLinkman("huang");
		entity.setLogoURL("skynet.logo");
		CAGECode cagecode = new CAGECode();
		cagecode.setCode("abc123");
		entity.setM_CAGECode(cagecode);
		entity.setM_CountryCode(CountryCode.CN);
		entity.setMainEnterprise(true);
		entity.setParentCompany(null);
		entity.setParentId(null);
		entity.setPostCode("200200");
		entity.setRunning(true);
		entity.setTelephone("65453456");
		enterpriseManager.insertEntity(entity);*/
		
		/*Carrier entity = new Carrier();
		//BaseEntity Properties
		entity.setCreateBy("shanyf");
		entity.setCreateDate(new Date());
		entity.setKeyword("carrier");
		entity.setVersion(1);
		//BaseEnterpriseInformation Properties
		entity.setAbbreviation("scott");
		entity.setAddress_en("shanghai");
		entity.setAddress_zh("斯科特");
		entity.setEmail("scott@scott.org");
		entity.setEnterpriseHomepageURL("www.scott.com");
		entity.setEnterpriseName_en("scott carrier");
		entity.setEnterpriseName_zh("斯科特运输代理");
		entity.setFax("64542333");
		entity.setLinkman("hejie");
		entity.setLogoURL("scott.logo");
		CAGECode cagecode = new CAGECode();
		cagecode.setCode("abc456");
		entity.setM_CAGECode(cagecode);
		entity.setM_CountryCode(CountryCode.CN);
		entity.setMainEnterprise(false);
		entity.setParentCompany(null);
		entity.setParentId(null);
		entity.setPostCode("200203");
		entity.setRunning(true);
		entity.setTelephone("65451234");
		CarrierName cname = new CarrierName();
		cname.setCode("carr1001");
		entity.setM_CarrierName(cname);
		enterpriseManager.insertEntity(entity);*/
		
		/*Carrier entity = new Carrier();
		//BaseEntity Properties
		entity.setCreateBy("huang");
		entity.setCreateDate(new Date());
		entity.setKeyword("subcarrier");
		entity.setVersion(1);
		//BaseEnterpriseInformation Properties
		entity.setAbbreviation("tiger");
		entity.setAddress_en("shanghai");
		entity.setAddress_zh("泰格");
		entity.setEmail("tiger@tiger.org");
		entity.setEnterpriseHomepageURL("www.tiger.com");
		entity.setEnterpriseName_en("tiger sub carrier");
		entity.setEnterpriseName_zh("泰格运输代理");
		entity.setFax("64542666");
		entity.setLinkman("zhang");
		entity.setLogoURL("tiger.logo");
		CAGECode cagecode = new CAGECode();
		cagecode.setCode("abc456001");
		entity.setM_CAGECode(cagecode);
		entity.setM_CountryCode(CountryCode.CN);
		entity.setMainEnterprise(false);
		entity.setParentCompany("斯科特运输代理");
		entity.setParentId("488080382efa10fd012efa1140cd0000");
		entity.setPostCode("200203");
		entity.setRunning(true);
		entity.setTelephone("65459876");
		CarrierName cname = new CarrierName();
		cname.setCode("carr1002");
		entity.setM_CarrierName(cname);
		enterpriseManager.insertEntity(entity);*/
		
		/*Customer entity = new Customer();
		//BaseEntity Properties
		entity.setCreateBy("wu");
		entity.setCreateDate(new Date());
		entity.setKeyword("customer");
		entity.setVersion(1);
		//BaseEnterpriseInformation Properties
		entity.setAbbreviation("eastean");
		entity.setAddress_en("shanghai");
		entity.setAddress_zh("东方");
		entity.setEmail("eastean@eastean.org");
		entity.setEnterpriseHomepageURL("www.eastean.com");
		entity.setEnterpriseName_en("eastean air");
		entity.setEnterpriseName_zh("东方航空");
		entity.setFax("64542444");
		entity.setLinkman("peng");
		entity.setLogoURL("eastean.logo");
		CAGECode cagecode = new CAGECode();
		cagecode.setCode("abc789");
		entity.setM_CAGECode(cagecode);
		entity.setM_CountryCode(CountryCode.CN);
		entity.setMainEnterprise(false);
		entity.setParentCompany(null);
		entity.setParentId(null);
		entity.setPostCode("200211");
		entity.setRunning(true);
		entity.setTelephone("65455678");
		CustomerIdentificationCode cicode = new CustomerIdentificationCode();
		cicode.setCode("cust1002");
		entity.setM_CustomerIdentificationCode(cicode);
		enterpriseManager.insertEntity(entity);*/
	}
	@Test
	public void testList() {
		List<BaseEnterpriseInformation> list = enterpriseManager.list(0, -1, BaseEnterpriseInformation.class);
		for(BaseEnterpriseInformation ei : list){
			log.info("**************************************");
			log.info("id: " + ei.getId());
			log.info("Abbreviation: " + ei.getAbbreviation());
			log.info("Address_en: " + ei.getAddress_en());
			log.info("Address_en: " + ei.getAddress_en());
			log.info("CreateBy: " + ei.getCreateBy());
			log.info("Email: " + ei.getEmail());
			log.info("EnterpriseHomepageURL: " + ei.getEnterpriseHomepageURL());
			log.info("EnterpriseName_en: " + ei.getEnterpriseName_en());
			log.info("EnterpriseName_zh: " + ei.getEnterpriseName_zh());
			log.info("Fax: " + ei.getFax());
			log.info("Keyword: " + ei.getKeyword());
			log.info("Linkman: " + ei.getLinkman());
			log.info("LogoURL: " + ei.getLogoURL());
			log.info("ParentCompany: " + ei.getParentCompany());
			log.info("PostCode: " + ei.getPostCode());
			log.info("Telephone: " + ei.getTelephone());
			log.info("Version: " + ei.getVersion());
			log.info("CreateDate: " + ei.getCreateDate());
			log.info("M_CAGECode: " + ei.getM_CAGECode().getCode());
			log.info("M_CountryCode: " + ei.getM_CountryCode());
			if(ei instanceof Carrier){
				log.info("M_CarrierName.id: " + ((Carrier)ei).getM_CarrierName().getId());
				log.info("M_CarrierName.code: " + ((Carrier)ei).getM_CarrierName().getCode());
			}
			if(ei instanceof ClearanceAgency){
				log.info("M_ClearanceAgent.id: " + ((ClearanceAgency)ei).getM_ClearanceAgent().getId());
				log.info("M_ClearanceAgent.code: " + ((ClearanceAgency)ei).getM_ClearanceAgent().getCode());
			}
			if(ei instanceof Customer){
				log.info("M_CustomerIdentificationCode.id: " + ((Customer)ei).getM_CustomerIdentificationCode().getId());
				log.info("M_CustomerIdentificationCode.code: " + ((Customer)ei).getM_CustomerIdentificationCode().getCode());
			}
			if(ei instanceof Manufacturer){
				log.info("M_ManufacturerCode.id: " + ((Manufacturer)ei).getM_ManufacturerCode().getId());
				log.info("M_ManufacturerCode.code: " + ((Manufacturer)ei).getM_ManufacturerCode().getCode());
			}
			if(ei instanceof RepairAgency){
				log.info("M_RepairShopCode.id: " + ((RepairAgency)ei).getM_RepairShopCode().getId());
				log.info("M_RepairShopCode.code: " + ((RepairAgency)ei).getM_RepairShopCode().getCode());
			}
			if(ei instanceof Supplier){
				log.info("M_SupplierCode.id: " + ((Supplier)ei).getM_SupplierCode().getId());
				log.info("M_SupplierCode.code: " + ((Supplier)ei).getM_SupplierCode().getId());
			}
			
			
			log.info("M_VAT: " + ei.getM_VAT());
			log.info("IsMainEnterprise: " + ei.isMainEnterprise());
			log.info("IsRunning: " + ei.isRunning());
			log.info("isDeleted: " + ei.isDeleted());
			log.info("parentId:" + ei.getParentId());

			log.info("**************************************");
			
		}
	}
}
