package com.m3958.firstgwt.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.SortedMap;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.m3958.firstgwt.annotation.HowManyMilliSec;
import com.m3958.firstgwt.client.types.CommonField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.client.types.UploadFor;
import com.m3958.firstgwt.dao.AssetFolderDao;
import com.m3958.firstgwt.dao.BaseDao;
import com.m3958.firstgwt.dao.WebHostDao;
import com.m3958.firstgwt.exception.SmartJpaException;
import com.m3958.firstgwt.model.Asset;
import com.m3958.firstgwt.model.AssetFolder;
import com.m3958.firstgwt.model.BaseModel;
import com.m3958.firstgwt.model.WebHost;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.server.response.ErrorMessageResponse;
import com.m3958.firstgwt.server.types.AppConstants;
import com.m3958.firstgwt.server.types.HasToJson;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.utils.BaseModelUtilsBean;
import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

@Singleton
public class AppUtilService {
	
	@Inject
	private Logger logger;
	
	@Inject
	private Injector injector;
	
	@Inject
	protected ModelAndDao modelConfig;
	
	public String getStaticFileEtag(File f){
		return f.lastModified() + "" + f.length();
	}
	
	public String createJsonStr(Object...os){
		if(os.length % 2 != 0){
			return "{}";
		}else{
			JSONObject jo = new JSONObject();
			for(int i=0;i<os.length;i++){
				jo.put(os[i++], os[i]);
			}
			return jo.toString();
		}
	}
	
	public String getStaticFileEtag(String fn){
		File f = new File(fn);
		return f.lastModified() + "" + f.length();
	}
	
	public String read2String(InputStream is,String encode) throws IOException{
		Writer sw = new StringWriter();
		char[] buffer = new char[1024];
        try {
        	Reader r = new BufferedReader(new InputStreamReader(is,encode));
            int n;
            while ((n = r.read(buffer)) != -1) {
                sw.write(buffer, 0, n);
            }
        } finally {
            ;
        }
        return sw.toString();
	}
	
	public void sendSiteFile(HttpServletRequest req, HttpServletResponse res ,File f, String mimeType)
			throws FileNotFoundException, IOException {
	    String etag = getStaticFileEtag(f);
	    String previousTtag = req.getHeader("If-None-Match");
	    if(etag.equals(previousTtag)){
	    	res.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
	    	return;
	    }
	    res.setContentType(mimeType);
	    res.setContentLength(((Long)f.length()).intValue());
	    if(mimeType != null && mimeType.contains("image")){
	    	res.setHeader("Cache-Control","max-age=5184000" );//60天
	    }else if(f.getName().contains(".cache.")){
	    	res.setHeader("Cache-Control","max-age=51840000" );//600天
	    }else if(f.getName().contains(".nocache.")){
	    	res.setHeader("Cache-Control","no-cache " );//不缓存
	    }else{
	    	res.setHeader("Cache-Control","max-age=300");//5分鐘
	    }
	    res.setHeader("Etag",etag);
	    res.addDateHeader("Last-Modified",f.lastModified());
	    logger.info(f + " not cached!");
	    sendDiskFile(f, res);
		// A FileInputStream is for bytes
	}
	
	public void forceDownload(HttpServletRequest req, HttpServletResponse res ,File f, String mimeType,String originName) throws IOException{
		res.setContentType( (mimeType != null) ? mimeType : "application/octet-stream" );
        res.setContentLength( (int)f.length() );
        res.setHeader( "Content-Disposition", "attachment; filename=\"" + originName + "\"" );
        sendDiskFile(f, res);
	}
	
	
	private void sendDiskFile(File f,HttpServletResponse res) throws IOException{
	    ServletOutputStream out = res.getOutputStream();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
			byte[] buf = new byte[4 * 1024];  // 4K buffer
			int bytesRead;
			while ((bytesRead = fis.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}
		}finally {
			if (fis != null) fis.close();
		}
		out.flush();
		out.close();
	}
	
	
	public String getObjectValueResponse(Object svalue){
		JSONObject responseField = new JSONObject().element("status", 0);
		responseField.element("data", svalue);
		JSONObject root = new JSONObject();
		root.element("response", responseField);
		return root.toString();		
	}
	
	public String getObjectValueResponse(JSONObject jsonObject){
		JSONObject responseField = new JSONObject().element("status", 0);
		responseField.element("data", jsonObject);
		JSONObject root = new JSONObject();
		root.element("response", responseField);
		return root.toString();		
	}
	
	public String getObjectValueResponse(Object svalue,int status){
		JSONObject responseField = new JSONObject().element("status", status);
		responseField.element("data", svalue);
		JSONObject root = new JSONObject();
		root.element("response", responseField);
		return root.toString();		
	}
	
	public String getRawValueResponse(Object svalue){
		return svalue.toString();		
	}

	@HowManyMilliSec
	public String getListResponse(List<? extends BaseModel> results,Integer startRow,Integer totalRows){
		JSONArray dataField = new JSONArray();
		JSONObject responseField = new JSONObject().element("status", 0);
		if(results != null){
			responseField.element("startRow", startRow);
			responseField.element("endRow",startRow + results.size());
			responseField.element("totalRows", totalRows);
			for(BaseModel bm : results){
				dataField.add(bm.toJson());
			}
		}else{
			responseField.element("startRow", 0);
			responseField.element("endRow",0);
			responseField.element("totalRows", 0);
		}
		
		responseField.element("data", dataField);
		
		JSONObject root = new JSONObject();
		root.element("response", responseField);
		
		return root.toString();
	}
	
	public String getJsonResponse(List<HasToJson> results,Integer startRow,Integer totalRows){
		JSONArray dataField = new JSONArray();
		JSONObject responseField = new JSONObject().element("status", 0);
		if(results != null){
			responseField.element("startRow", startRow);
			responseField.element("endRow",startRow + results.size());
			responseField.element("totalRows", totalRows);
			for(HasToJson bm : results){
				dataField.add(bm.toJson());
			}
		}else{
			responseField.element("startRow", 0);
			responseField.element("endRow",0);
			responseField.element("totalRows", 0);
		}
		
		responseField.element("data", dataField);
		
		JSONObject root = new JSONObject();
		root.element("response", responseField);
		
		return root.toString();
	}
	
	
	public String getListResponse(BaseModel  model){
		JSONObject responseField = new JSONObject().element("status", 0);
		JSONArray dataField = new JSONArray();
		
		if(model != null){
			responseField.element("startRow", 0);
			responseField.element("endRow",1);
			responseField.element("totalRows", 1);
			dataField.add(model.toJsonOne());
		}else{
			responseField.element("startRow", 0);
			responseField.element("endRow",0);
			responseField.element("totalRows", 0);
		}
		responseField.element("data", dataField);
		JSONObject root = new JSONObject();
		root.element("response", responseField);
		
		return root.toString();
	}
	
	public String getEmptyListResponse(){
		JSONObject responseField = new JSONObject().element("status", 0);
		JSONArray dataField = new JSONArray();
		responseField.element("startRow", 0);
		responseField.element("endRow",0);
		responseField.element("totalRows", 0);

		responseField.element("data", dataField);
		JSONObject root = new JSONObject();
		root.element("response", responseField);
		
		return root.toString();
	}
	
	public String getListResponse(JSONObject jo){
		JSONObject responseField = new JSONObject().element("status", 0);
		JSONArray dataField = new JSONArray();
		
		if(jo != null){
			responseField.element("startRow", 0);
			responseField.element("endRow",1);
			responseField.element("totalRows", 1);
			dataField.add(jo);
		}else{
			responseField.element("startRow", 0);
			responseField.element("endRow",0);
			responseField.element("totalRows", 0);
		}
		responseField.element("data", dataField);
		JSONObject root = new JSONObject();
		root.element("response", responseField);
		
		return root.toString();
	}
	
	public void writeHtmlResponse(HttpServletResponse res,String content) throws IOException{
		res.setContentType("text/html;charset=utf-8");   
		res.setCharacterEncoding("UTF-8");
		Writer writer = res.getWriter();
		writer.write(content);
		writer.close();		
	}
	
	public void writeHtmlResponseWithTemplate(HttpServletResponse res,String content) throws IOException{
		res.setContentType("text/html;charset=utf-8");   
		res.setCharacterEncoding("UTF-8");
		Writer writer = res.getWriter();
		String s = "<!DOCTYPE html>" +
					"<html>" +
					"<head>" +
					"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
					"<title></title>" + 
					"</head>" + 
					"<body>" +
					"tosendcontent" +
					"</body>" + 
					"</html>";
		s = s.replace("tosendcontent", content);
		writer.write(s);
		writer.close();		
	}
	
	public void writeJsonResponse(HttpServletResponse res,String content) throws IOException{
		res.setContentType("application/json;charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		Writer writer = res.getWriter();
		writer.write(content);
		writer.close();
	}
	
	public boolean hasErrors(){
		ErrorMessages errors = injector.getInstance(ErrorMessages.class);
		return !errors.isEmpty();
	}
	
	public void writeJsonErrorResponse(HttpServletResponse res) throws IOException{
		res.setContentType("application/json;charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		Writer writer = res.getWriter();
		writer.write(injector.getInstance(ErrorMessageResponse.class).toString());
		writer.close();
	}
	
	public void writeJsonpErrorResponse(HttpServletResponse res,String callback) throws IOException{
		res.setContentType("application/json;charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		Writer writer = res.getWriter();
		StringBuffer sb = new StringBuffer(callback).append("(").append(injector.getInstance(ErrorMessageResponse.class).toString()).append(");");
		writer.write(sb.toString());
		writer.close();
	}
	
	public void writeJsonpResponse(HttpServletResponse res,String content,String callback) throws IOException{
		res.setContentType("application/json;charset=UTF-8");   
		res.setCharacterEncoding("UTF-8");
		Writer writer = res.getWriter();
		StringBuffer sb = new StringBuffer(callback).append("(").append(content).append(");");
		writer.write(sb.toString());
		writer.flush();
		writer.close();
	}
	
	public BaseModel getBaseModelFromParams() throws SmartJpaException{
		ReqParaService reqPs = injector.getInstance(ReqParaService.class);
		BaseDao dao = modelConfig.getDaoInstance(reqPs.getModelName());
		Class c = null;
		try {
			c = Class.forName(reqPs.getModelName());
		} catch (ClassNotFoundException e1) {}
		if(c == null)return null;
		BaseModel model = injector.getInstance(c);
		BaseModelUtilsBean bmub = injector.getInstance(BaseModelUtilsBean.class);
		try {
			bmub.copyProperties(model, reqPs.getAllParas());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	public BaseDao getDao(){
		ReqParaService reqPs = injector.getInstance(ReqParaService.class);
		return modelConfig.getDaoInstance(reqPs.getModelName());
	}
	
	
	public String getFnFromUrl(String url){
		String uri = url;
		if(url.contains("?")){
			String[] ss = url.split("?");
			uri = ss[0];
		}
		String[] ss = uri.split("/");
		if(ss.length > 0)
			return ss[ss.length - 1];
		else
			return "";
	}
	
	public Asset saveRemoteAsset(ReqParaService reqPs) throws ClientProtocolException, IOException{
		AssetRepository dar = injector.getInstance(DiskAssetRepository.class);
		String siteId = reqPs.getStringValue(CommonField.SITE_ID.getEname(),"");
		String url = reqPs.getStringValue("url","");
		if(!url.startsWith("http://")){
			url = "http://" + url;
		}
		if(url.isEmpty())return null;
		Asset a = null;
		String fname = getFnFromUrl(url);
		
		
	   	 HttpClient httpclient = new DefaultHttpClient();
	
		 // Prepare a request object
		 HttpGet httpget = new HttpGet(url);
	
		 // Execute the request
		 HttpResponse response = httpclient.execute(httpget);
	
		 // Examine the response status
		 System.out.println(response.getStatusLine());
	
		 // Get hold of the response entity
		 HttpEntity entity = response.getEntity();
		 
//		 SortedMap sm = Charset.availableCharsets();
//		 
//		 String destFile = "e:/hclient.out";
//		 FileOutputStream fos = new FileOutputStream(destFile);
	
		 // If the response does not enclose an entity, there is no need
		 // to worry about connection release
		 if (entity != null) {
//			 Header h = entity.getContentEncoding();
			 Header mime = entity.getContentType();
			 String ct = null;
			 if(mime!=null){
				 ct = mime.getValue();
			 }
			 if(ct == null)ct = "application/octet-stream";
		     InputStream instream = entity.getContent();
		     try {
		    	 a = dar.save(siteId,(UploadFor) UploadFor.UPLOAD_TO_FOLDER,fname,ct,instream);
		    	 
		    	 int rid = reqPs.getRelationModelId();
		    	 if(rid != SmartConstants.NONE_EXIST_MODEL_ID){
		 			try {
						AssetFolderDao dao = injector.getInstance(AssetFolderDao.class);
						AssetFolder fo = dao.find(rid);
						fo.getAssets().add(a);
						dao.smartUpdateBaseModel(fo);
					} catch (Exception e) {
						e.printStackTrace();
					}
		    	 }
//					byte[] b = new byte[1024];
//					while (true) {
//						int i = instream.read(b);
//						if (i == -1)
//							break;
//						fos.write(b, 0, i);
//					}
//					instream.close();
//					fos.close();
	
	//	         BufferedReader reader = new BufferedReader(
	//	                 new InputStreamReader(instream,Charset.forName("GB2312")));
	//	         // do something useful with the response
	//	         String line;
	//	         while((line = reader.readLine()) != null){
	//	        	 System.out.println(line); 
	//	         }
		         
	
		     } catch (IOException ex) {
	
		         // In case of an IOException the connection will be released
		         // back to the connection manager automatically
		         throw ex;
	
		     } catch (RuntimeException ex) {
	
		         // In case of an unexpected exception you may want to abort
		         // the HTTP request in order to shut down the underlying
		         // connection and release it back to the connection manager.
		         httpget.abort();
		         throw ex;
	
		     } finally {
	
		         // Closing the input stream will trigger connection release
		         instream.close();
	
		     }
	
		     // When HttpClient instance is no longer needed,
		     // shut down the connection manager to ensure
		     // immediate deallocation of all system resources
		     httpclient.getConnectionManager().shutdown();
		 }
		 return a;
	}

}
