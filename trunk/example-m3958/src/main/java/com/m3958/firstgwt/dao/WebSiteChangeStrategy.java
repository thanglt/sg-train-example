package com.m3958.firstgwt.dao;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import com.m3958.firstgwt.client.types.ServerErrorCode;
import com.m3958.firstgwt.model.Link;
import com.m3958.firstgwt.model.Section;
import com.m3958.firstgwt.model.WebHost;
import com.m3958.firstgwt.model.WebSite;
import com.m3958.firstgwt.server.types.Error;
import com.m3958.firstgwt.service.FilePathService;

public class WebSiteChangeStrategy extends BaseModelChangeStrategy implements ModelChangeStrategy<WebSite> {

	@Override
	public boolean extraPersistTask(WebSite model){
		createPerRoleAsignToUser(model);
		return true;
	}

	private void createSiteDir(WebSite model) {
		String s = getScs().getSiteRoot();
		File f = new File(s,model.getId()+"");
		if(!f.exists()){
			f.mkdirs();
		}
	}

	@Override
	public boolean extraRemoveTask(WebSite model) {
		String s = getScs().getSiteRoot();
		
		File[] fs;
		File f;
		try {
			f = new File(s,model.getId()+"");
			fs = f.listFiles();
		} catch (Exception e) {
			return true;
		}
		if(f.exists()){
			if(fs == null){
				f.delete();
				return true;
			}
		}else{
			return true;
		}
		
		if(fs.length > 0){
			getErrors().addError(new Error("請先清空站點目錄！", ServerErrorCode.DEPENDENCY_ERROR));
			return false;
		}
		if(model.getWebhosts().size() > 0){
			getErrors().addError(new Error("請先删除对应的主机名！", ServerErrorCode.DEPENDENCY_ERROR));
			return false;
		}
		
		SectionDao sdao = injector.getInstance(SectionDao.class);
		List<Section> ss = sdao.getTopSections(model.getId());
		if(ss.size() > 0){
			getErrors().addError(new Error("請先删除站点的文章目录！", ServerErrorCode.DEPENDENCY_ERROR));
			return false;
		}
		
		LinkDao ldao = injector.getInstance(LinkDao.class);
		List<Link> lcs = ldao.getTopLinks(model.getId());
		
		if(lcs.size() > 0){
			getErrors().addError(new Error("請先删除站点的链接条目！", ServerErrorCode.DEPENDENCY_ERROR));
			return false;
		}
		
		if(f.exists())f.delete();
		return true;
	}

	@Override
	public boolean extraUpdateTask(WebSite model,WebSite newModel){
		if(newModel.isStop()){
			CachedContentDao ccDao = injector.getInstance(CachedContentDao.class);
			ccDao.removeAll(model);
		}
		return true;
	}

	@Override
	public boolean afterPersist(WebSite model) {
		createSiteDir(model);
		WebHost wh = new WebHost();
		wh.setWebSite(model);
		wh.setName(model.getEname() + "." + getScs().getAppDomain());
		wh.setTheme("default");
		wh.setAudit(true);
		model.getWebhosts().add(wh);
		
		File f = new File(getScs().getSiteRoot(),model.getId()+"");
		f = new File(f,wh.getTheme());
		if(!f.exists()){
			f.mkdir();
			FilePathService fps = injector.getInstance(FilePathService.class);
			ServletContext sc = injector.getInstance(ServletContext.class);
			File indexftl = new File(sc.getRealPath("/"),"indexftl.ftl");
			File indexzhcnftl = new File(sc.getRealPath("/"),"indexzhcnftl.ftl");
			fps.copyFile(indexzhcnftl, new File(f,"index_zh_CN.ftl"));
			fps.copyFile(indexftl, new File(f,"index.ftl"));
		}
		
		SectionDao sdao = injector.getInstance(SectionDao.class);
		Section s = new Section();
		s.setName(model.getCname() + "文章目录");
		s.setSiteId(model.getId());
		sdao.smartPersistBaseModel(s);
		return true;
	}
}
