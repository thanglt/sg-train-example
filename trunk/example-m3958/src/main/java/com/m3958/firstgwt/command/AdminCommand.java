package com.m3958.firstgwt.command;

import java.io.IOException;
import java.util.List;

import com.m3958.firstgwt.client.types.AdminSubOperation;
import com.m3958.firstgwt.dao.AssetFolderDao;
import com.m3958.firstgwt.dao.GroupDao;
import com.m3958.firstgwt.dao.JrxmlFileDao;
import com.m3958.firstgwt.dao.LgbDao;
import com.m3958.firstgwt.dao.ObjectClassNameDao;
import com.m3958.firstgwt.exception.SmartJpaException;
import com.m3958.firstgwt.model.AssetFolder;
import com.m3958.firstgwt.model.Group;
import com.m3958.firstgwt.model.JrxmlFile;
import com.m3958.firstgwt.model.Lgb;
import com.m3958.firstgwt.server.response.ErrorMessageResponse;
import com.wideplay.warp.persist.Transactional;

public class AdminCommand extends BaseCommand  implements Command{
	
	private String result = null;
	private boolean writeResponse = true;
	
	@Override
	public void execute() throws SmartJpaException, IOException{
		AdminSubOperation sson = AdminSubOperation.NO_SUB_OPERATION;
		if(reqPs.getSubOpType() != null)
			sson = AdminSubOperation.valueOf(reqPs.getSubOpType());
		
		switch (sson) {
		case FIX_PARENT_ID:
			result = FixParentId();
			break;
		case CREATE_OC:
			result = createOc();
			break;
		case CLEAN_UP_PERMISSIONS:
			result = cleanUpPermissions();
			break;
		default:
			break;
		}
		if(!errors.isEmpty()){
			result = injector.getInstance(ErrorMessageResponse.class).toString();
		}
		if(writeResponse)
			autils.writeJsonResponse(res,result);
	}
	
//	private void creatSpecialFolder() {
//		boolean b = getDao().createSpecialFolder();
//		jsonString = "success!";
//		
//	}

	private String cleanUpPermissions() {
//		PermissionDao pdao = injector.getInstance(PermissionDao.class);
//		int num = pdao.cleanUpPermissions();
//		jsonString = "清理了" + num + "個權限！";
		return null;
	}

//	@Transactional
//	private void assignDefaultRoles() {
//		UserDao udao = injector.getInstance(UserDao.class);
//		int num = udao.assignDefaultRoles();
//		jsonString = "处理了" + num + "个用户！";
//	}

	private String createOc(){
		ObjectClassNameDao ocDao = injector.getInstance(ObjectClassNameDao.class);
		boolean b = ocDao.createOcNames();
		if(b){
			return "Success!";
		}else{
			return  "failure";
		}
	}
	
	@Transactional
	private String FixParentId() {
		GroupDao gdao = injector.getInstance(GroupDao.class);
		List<Group> gs= gdao.fetchAll();
		for(Group g:gs){
			if(g.getParent() != null && g.getParent().getId() != g.getParentId()){
				g.setPersistedParentId(g.getParent().getId());
				gdao.merge(g);
			}
		}
		
		AssetFolderDao fdao = injector.getInstance(AssetFolderDao.class);
		List<AssetFolder> fs= fdao.fetchAll();
		for(AssetFolder f:fs){
			if(f.getParent() != null && f.getParent().getId() != f.getParentId()){
				f.setPersistedParentId(f.getParent().getId());
				fdao.merge(f);
			}
		}
		
		LgbDao ldao = injector.getInstance(LgbDao.class);
		List<Lgb> ls = ldao.fetchAll();
		for(Lgb l: ls){
			if(l.getShequ() != null && l.getShequ().getId() != l.getShequId()){
				l.setPersistedShequId(l.getShequ().getId());
			}
			if(l.getZp() != null && l.getZp().getId() != l.getZpId()){
				l.setPersistedZpId(l.getZp().getId());
			}
			ldao.merge(l);
		}
		
		JrxmlFileDao jdao = injector.getInstance(JrxmlFileDao.class);
		List<JrxmlFile> js = jdao.fetchAll();
		for(JrxmlFile j: js){
			if(j.getParent() != null && j.getParent().getId() != j.getParentId()){
				j.setPersistedParentId(j.getParent().getId());
			}
			jdao.merge(j);
		}
		
		return "success";
	}

	@Override
	public String getResult() {
		return result;
	}
	
	@Override
	public void execute(boolean writeResponse) throws SmartJpaException,
			IOException {
		this.writeResponse = writeResponse;
		execute();
	}

}
