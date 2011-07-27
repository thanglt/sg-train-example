package com.m3958.firstgwt.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.m3958.firstgwt.client.types.JasperRequestParaNames;
import com.m3958.firstgwt.dao.JrxmlFileDao;
import com.m3958.firstgwt.dao.LgbDao;
import com.m3958.firstgwt.model.JrxmlFile;
import com.m3958.firstgwt.model.Lgb;
import com.m3958.firstgwt.utils.MyBeanDataSource;

public class LgbJasperExport{
	
	@SuppressWarnings("unused")
	@Inject
	private com.google.inject.Provider<EntityManager> emp;
	
	@Inject
	private Injector injector;
	
	@Inject
	private SiteConfigService scs;
	
	public void execute(ReqParaService reqPs,HttpServletResponse res) {
		LgbDao dao = injector.getInstance(LgbDao.class);
		List<Lgb> lgbs = dao.smartFetch();
		MyBeanDataSource beans = new MyBeanDataSource(lgbs);
//		MyBeanDataSource beans = injector.getInstance(MyBeanDataSource.class);
//		beans.setBeans(lgbs);
		JasperPrint jasperPrint;
		
		try {
			jasperPrint = JasperFillManager.fillReport(
				getTemplateStream(reqPs.getIntegerValue(JasperRequestParaNames.JRXML_ID)), 
				getJParameters(), 
				beans
				);
			if("pdf".equals(reqPs.getStringValue(JasperRequestParaNames.FORMAT))){
				exportPdf(jasperPrint,res);
			}else if("html".equals(reqPs.getStringValue(JasperRequestParaNames.FORMAT))){
				exportHtml(jasperPrint,res);
			}else{
				exportHtml(jasperPrint,res);
			}

		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void exportHtml(JasperPrint jasperPrint,HttpServletResponse res) {
//		htmlString = JasperExportManager.exportReportTo
	}



	private void exportPdf(JasperPrint jasperPrint,HttpServletResponse res) {
		res.setContentType("application/pdf");
//		res.setHeader("Content-Disposition", "attachment; filename=lgb.pdf");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd.HHmmss");
		res.setHeader("Content-Disposition", "inline; filename=lgb" + sdf.format(new Date()) + ".pdf");
		try {
			JasperExportManager.exportReportToPdfStream(jasperPrint, res.getOutputStream());
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	private InputStream getTemplateStream(int jid){
		JrxmlFileDao dao = injector.getInstance(JrxmlFileDao.class);
		JrxmlFile jf = dao.find(jid);
		InputStream is = new ByteArrayInputStream(jf.getReport());
		return is;
	}
	
	private Map<String,Object> getJParameters(){
		Map<String,Object> parameters = new HashMap<String, Object>();
		parameters.put("JASPER_IMG_PATH", scs.getJasperTemplateDir());
		parameters.put("SUBREPORT_DIR","");
		parameters.put("SUBREPORT_EXT", "");
		parameters.put("ASSET_STORE_DIR", scs.getAssetSavePath());
//		parameters.put("SUB_STREAMS",new SubReportStreams(jf));
		return parameters;
	}
}
