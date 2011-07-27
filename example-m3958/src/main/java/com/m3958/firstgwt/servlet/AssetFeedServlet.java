package com.m3958.firstgwt.servlet;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.accesschecker.AssetChecker;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.SmartOperationName;
import com.m3958.firstgwt.client.types.SmartSubOperationName;
import com.m3958.firstgwt.client.utils.StringUtils;
import com.m3958.firstgwt.dao.AssetDao;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.service.AppUtilService;
import com.m3958.firstgwt.service.FilePathService;
import com.m3958.firstgwt.service.ReqParaService;
import com.m3958.firstgwt.service.SiteConfigService;
import com.m3958.firstgwt.utils.ExecPerl;


@Singleton
public class AssetFeedServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	Injector injector;
	
	@Inject
	protected FilePathService fps;
	
	@Inject
	private AppUtilService aus;
	
	@Inject
	private SiteConfigService scs;
	
	private static Pattern p = Pattern.compile("^/assetfeed/(\\d+)\\.(\\d+x\\d+)?\\.?.*");
	//qw/800x600>m 128x128>s 48x48>t/;
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		String assetIdStr = req.getParameter("assetId");
		String size = req.getParameter("size");
		
		if(assetIdStr == null || assetIdStr.isEmpty()){
			String uri = req.getRequestURI();
			Matcher m = p.matcher(uri);
			boolean b = m.matches();
			if(b){
				assetIdStr = m.group(1);
				size = m.group(2);
			}else{
				return;
			}
		}
		String ssize; //空白是800x600
		if(size == null || size.isEmpty()){
			ssize = "800x600";
		}else if("o".equals(size)){
			ssize = "";
		}else if("m".equals(size)){
			ssize = "800x600";
		}else if("s".equals(size)){
			ssize = "128x128";
		}else if("t".equals(size)){
			ssize = "48x48";
		}else{
			ssize = "";
		}
		
		int assetId = Integer.parseInt(assetIdStr);
		AssetDao dao = injector.getInstance(AssetDao.class);
		Asset a = dao.find(assetId);
		if(a == null)return;
		
		File bf = new File(scs.getAssetSavePath());
		File f;
		if(size == null || size.isEmpty()){
			f = new File(bf,a.getFilePath());
		}else{
			f = new File(bf,StringUtils.getFileNameAppend(a.getFilePath(), ssize));
			if(!f.exists()){
//				createSizeImg(a);
//				if(!f.exists()){
					f = new File(bf,a.getFilePath());
//				}
			}
		}
		aus.sendSiteFile(req, res, f, a.getMimeType());
//		InputStream is = new FileInputStream(f);
//		res.setContentLength(new Long(f.length()).intValue());
//		res.setContentType(a.getMimeType());
//		
//		
//		byte[] buffer = new byte[4096];
//		int i = 0;
//		while((i = is.read(buffer)) > 0){
//			out.write(buffer, 0, i);
//		}
//		is.close();
//		out.close();
		
	}
	
//	private void createSizeImg(Asset a){
//    	if(StringUtils.isImageExt(a.getOriginName())){
//			ExecPerl ep = injector.getInstance(ExecPerl.class);//new ExecPerl("resize.pl",scs.getAssetSavePath(),a.getFilePath());
//	    	try {
//				ep.exec(new File(fps.getWebinfDir(),"resize.pl").getAbsolutePath(),scs.getAssetSavePath(),a.getFilePath());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//    	}
//	}
//
//	private boolean doCheck(HttpServletRequest req,String assetIdStr) {
//		AssetChecker ac = injector.getInstance(AssetChecker.class);
//		ReqParaService reqps = injector.getInstance(ReqParaService.class);
//		int aid = SmartConstants.NONE_EXIST_MODEL_ID;
//		aid = Integer.parseInt(assetIdStr);
//		reqps.setModelId(aid);
//		reqps.setModelName(Asset.class.getName());
//		reqps.setOpType(SmartOperationName.FETCH.getValue());
//		reqps.setSubOptype(SmartSubOperationName.FETCH_ONE.toString());
//		return ac.doCheck();
//	}
}
