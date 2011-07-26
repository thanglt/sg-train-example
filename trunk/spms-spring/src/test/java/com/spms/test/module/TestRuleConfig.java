package com.spms.test.module;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skynet.common.gwt.GwtActionHelper;
import com.skynet.common.prop.PropManager;
import com.skynet.spms.datasource.entity.EntityMetaInfo;
import com.skynet.spms.modules.ModuleElementStore;
import com.skynet.spms.modules.common.ModuleTree;
import com.skynet.spms.modules.common.RootModuleInfo;
import com.skynet.spms.modules.entity.ModuleList;
import com.skynet.spms.modules.entity.ModulesElement;
import com.skynet.spms.modules.entity.PreDefineElement;
import com.skynet.spms.modules.entity.RuleGlobeConfig;
import com.skynet.spms.modules.imp.ModuleElementStoreImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath:base_Context.xml",
		"classpath:service_Context.xml",
		"classpath:util_Context.xml"})
public class TestRuleConfig {

	@Autowired
	private ModuleElementStore store;	
	
	@Test
	public void ruleList(){
		
		
		List<String> ruleList=store.getFullRoleList();
		assertEquals(ruleList.size(),2);
	}
	
	
}
