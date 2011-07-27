package com.m3958.firstgwt.client.utils;

import java.util.Date;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.m3958.firstgwt.client.jso.BaseModelJso;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.tree.TreeNode;

/**
 * 
 * @author Administrator
 * 
 */


public class JsoUtils {
	
	public static final String addParens(String s){
		if(s.startsWith("(") && s.endsWith("(")){
			return s;
		}else{
			return "(" + s + ")";
		}
		
	}
	
	public static final String[] jsArrayStringToStringArray(JsArrayString jsas){
		String[] sa = new String[jsas.length()];
		for(int i=0;i< jsas.length();i++){
			sa[i] = jsas.get(i);
		}
		return sa;
	}
	
	
	
	public static final RecordList getRecordList(JsArray<? extends JavaScriptObject> jsArray){
		Record[] rs = new Record[jsArray.length()];
		for (int i = 0; i < jsArray.length(); i++) {
			Record r = new Record(jsArray.get(i));
			rs[i] = r;
		}
		return new RecordList(rs);
	}
	
	
	public static final TreeNode[] jsArrayToTreeNode(JsArray<? extends JavaScriptObject> jsArray){
		TreeNode[] tns = new TreeNode[jsArray.length()];
		for (int i = 0; i < jsArray.length(); i++) {
			TreeNode tn = new TreeNode(jsArray.get(i));
			tns[i] = tn;
		}
		return tns;
	}
	
	//注意eval的歧义性，string两侧必须添加括号。
	
	
//	public static final native JsArray<? extends BaseModelJso> getJsArray(String jsonStr)/*-{
//		if(jsonStr.charAt(0) == "("){
//			return eval(jsonStr);
//		}else{
//			return eval("(" + jsonStr + ")");
//		}
//	}-*/;
	
	
	
	public static final native JsArray<? extends BaseModelJso> getJsArray(String jsonStr)/*-{
		var isString = function (object) {
        	if (object == null) return false;
        	if(!object)return false;
            return typeof object == "string";
        };
        
        var regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}(:([0-9]{2}))?(\.[0-9]+)?$"
		var patt=new RegExp(regexp);
		
		var ao = null;
		if(jsonStr.charAt(0) == "("){
			ao = eval(jsonStr);
		}else{
			ao = eval("(" + jsonStr + ")");
		}
		if(ao == null)return null;
		var j = 0;
		for(var j = 0;j<ao.length;j++){
			var o = ao[j];
			if(o != null){
			for(var i in o){
				if(isString(o[i]) && patt.test(o[i])){
					var millis = @com.m3958.firstgwt.client.utils.JsoUtils::getMillisStr(Ljava/lang/String;)(o[i]);
					var date = new Date();
					date.setTime(parseInt(millis));
					o[i] = date;
				}
			}
			}
		}
		return ao;
	}-*/;
	
	
	public static native JsArrayInteger getJsArrayInteger(String intAryStr)/*-{
		return eval(intAryStr);
	}-*/;
	
	
	
	public static final native BaseModelJso getBaseModelJso(String jsonStr)/*-{
		var isString = function (object) {
        	if (object == null) return false;
        	if(!object)return false;
            return typeof object == "string";
        };
        
        var regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}(:([0-9]{2}))?(\.[0-9]+)?$"
		var patt=new RegExp(regexp);
        
		var o = null;
		if(jsonStr.charAt(0) == "("){
			o =  eval(jsonStr);
		}else{
			o = eval("(" + jsonStr + ")");
		}
		
		if(o != null){
			for(var i in o){
				if(isString(o[i]) && patt.test(o[i])){
					var millis = @com.m3958.firstgwt.client.utils.JsoUtils::getMillisStr(Ljava/lang/String;)(o[i]);
					var date = new Date();
					date.setTime(parseInt(millis));
					o[i] = date;
				}
			}
		}
		return o;
	}-*/;
	
	
	public static JsArray<? extends BaseModelJso> getDataArrayFromResponse(String result){
		return getJsArray(getResultDataString(result));
	}

	public static BaseModelJso getBaseModelJsoFromResponse(String result){
		return getBaseModelJso(getResultDataString(result));
	}
	
	
	
//	public static final Lgb getLgb(LgbJso jso){
//		Lgb model = new Lgb();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		
//		model.setPaixu(jso.getPaixu());
//		model.setBz(jso.getBz());
//		model.setCsd(jso.getCsd());
//		model.setDepartmentIds(jso.getDepartmentIds());
//		model.setDwxz(jso.getDwxz());
//		model.setGblx(jso.getGblx());
//		model.setHjszd(jso.getHjszd());
//		model.setHyzk(jso.getHyzk());
//		model.setJg(jso.getJg());
//		model.setJjzk(jso.getJjzk());
//		model.setJkzk(jso.getJkzk());
//		model.setJy(jso.getJy());
//		model.setSszb(jso.getSszb());
//		model.setSfz(jso.getSfz());
//		model.setMinzu(jso.getMinzu());
//		model.setPogz(jso.getPogz());
//		model.setPassAway(jso.getPassAway());
//		model.setRdsj(getDate(jso.getRdsj()));
//		model.setRwsj(getDate(jso.getRwsj()));
//		model.setLtxsj(getDate(jso.getLtxsj()));
//		model.setSr(getDate(jso.getSr()));
//		model.setSwsj(getDate(jso.getSwsj()));
//		model.setXb(jso.getXb());
//		model.setXm(jso.getXm());
//		model.setXsdy(jso.getXsdy());
//		model.setXzgdw(jso.getXzgdw());
//		model.setXzjb(jso.getXzjb());
//		model.setYgzdw(jso.getYgzdw());
//		model.setYzw(jso.getYzw());
//		return model;
//	}
//	
//	public static Operation getOperation(OperationJso jso) {
//		Operation model = new Operation();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//
//		model.setName(jso.getName());
//		model.setOpCode(OpCode.valueOf(jso.getOpCode()));
//		return model;
//	}
//	
//	public static Department getDepartment(DepartmentJso jso){
//		Department model = new Department();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setShequContainer(jso.isShequContainer());
//		model.setBz(jso.getBz());
//		model.setCname(jso.getCname());
//
//		return model;
//	}
//	
//	public static Role getRole(RoleJso jso){
//		Role model = new Role();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//
//		model.setCname(jso.getCname());
//		model.setEname(jso.getEname());
//		return model;
//	}
//	
//	public static SiteConfig getSiteConfig(SiteConfigJso jso){
//		SiteConfig model = new SiteConfig();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setConfigKey(jso.getConfigKey());
//		model.setConfigValue(jso.getConfigValue());
//		return model;
//	}
//	
//	public static Permission getPermission(PermissionJso jso) {
//		Permission model = new Permission();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setName(jso.getName());
//		model.setDescription(jso.getDescription());
//		model.setClassPermission(jso.isClassPermission());
//		model.setObjectClassName(jso.getObjectClassName());
//		return model;
//	}
//
//	public static User getUser(UserJso jso) {
//		User model = new User();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setLoginName(jso.getLoginName());
//		model.setEmail(jso.getEmail());
//		model.setPassword(jso.getPassword());
//		model.setMobile(jso.getMobile());
//		return model;
//	}
//
//	public static Ftl getUser(FtlJso jso) {
//		Ftl model = new Ftl();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setName(jso.getName());
//		model.setDescription(jso.getDescription());
//		model.setFtl(jso.getFtl());
//		return model;
//	}
//
//	public static Shequ getShequ(ShequJso jso){
//		Shequ model = new Shequ();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setName(jso.getName());
//		model.setBz(jso.getBz());
//		model.setDianhua(jso.getDianhua());
//		model.setSqfzr(jso.getSqfzr());
//		model.setShouji(jso.getShouji());
//		return model;
//	}
//	
//	public static Address getAddress(AddressJso jso){
//		Address model = new Address();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setBz(jso.getBz());
//		model.setDianhua(jso.getDianhua());
//		model.setDizhi(jso.getDizhi());
//		model.setMainAddress(jso.isMainAddress());
//		model.setShouji(jso.getShouji());
//		return model;
//	}
//	
//	public static Asset getAsset(AssetJso jso){
//		Asset model = new Asset();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setDescription(jso.getDescription());
//		return model;
//	}
//
//	
//	public static Reward getReward(RewardJso jso){
//		Reward model = new Reward();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setBz(jso.getBz());
//		model.setJlqk(jso.getJlqk());
//		model.setCfqk(jso.getCfqk());
//		return model;
//	}
//
//	
//	public static House getHouse(HouseJso jso){
//		House model = new House();
//		
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setBz(jso.getBz());
//		model.setZfxz(jso.getZfxz());
//		model.setArea(jso.getArea());
//		model.setStructure(jso.getStructure());
//		return model;
//	}
//	
//	public static Career getCareer(CareerJso jso){
//		Career model = new Career();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setBz(jso.getBz());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setStart(getDate(jso.getStart()));
//		model.setEnd(getDate(jso.getEnd()));
//		model.setZhiwu(jso.getZhiwu());
//		model.setDanwei(jso.getDanwei());
//		return model;
//	}
//	
//	public static CalEvent getCalEvent(CalEventJso jso){
//		CalEvent model = new CalEvent();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setName(jso.getName());
//		model.setStartDate(getDate(jso.getStartDate()));
//		model.setEndDate(getDate(jso.getEndDate()));
//		model.setDescription(jso.getDescription());
//		model.setCalEventType(jso.getCalEventType());
//		return model;
//	}
//
//	public static StepCareer getStepCareer(StepCareerJso jso){
//		StepCareer model = new StepCareer();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setBz(jso.getBz());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setStart(getDate(jso.getStart()));
//		model.setEnd(getDate(jso.getEnd()));
//		model.setZysj(jso.getZysj());
//		return model;
//	}
//
//	
//	public static Family getFamily(FamilyJso jso){
//		Family model = new Family();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setBz(jso.getBz());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setXm(jso.getXm());
//		model.setXb(jso.getXb());
//		model.setGx(jso.getGx());
//		model.setBirthday(getDate(jso.getBirthday()));
//		model.setDianhua(jso.getDianhua());
//		model.setDizhi(jso.getDizhi());
//		model.setJkzk(jso.getJkzk());
//		model.setZzmm(jso.getZzmm());
//		model.setShouji(jso.getShouji());
//		return model;
//	}
//
//	public static ObjectClassName getObjectClassName(ObjectClassNameJso jso) {
//		ObjectClassName model = new ObjectClassName();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setCname(jso.getCname());
//		model.setEname(jso.getEname());
//		return model;
//	}
//
//	public static Feedback getFeedback(FeedbackJso jso) {
//		Feedback model = new Feedback();
//		model.setId(jso.getId());
//		model.setVersion(jso.getVersion());
//		model.setCreatedAt(getDate(jso.getCreatedAt()));
//		model.setFeedbackType(jso.getFeedbackType());
//		model.setFeedbackStatus(jso.getfeedbackStatus());
//		model.setContent(jso.getContent());
//		model.setReplyContent(jso.getReplyContent());
//		return model;
//	}
	
	public static String getResultDataString(String response){
		JSONObject jo = JSONParser.parse(response).isObject();
		if(jo.containsKey("response")){
			jo = jo.get("response").isObject();
		}
		String s = jo.get("data").toString();
		return s;
	}
	
    public static native Date getDate(JavaScriptObject elem) /*-{
		return (elem === undefined || elem == null) ? null: @com.m3958.firstgwt.client.utils.JsoUtils::toDate(D)(elem.getTime());
	}-*/;
    
    
    @SuppressWarnings("deprecation")
	public static Date getDateFromString(String dateStr){
    	if(dateStr == null)return null;
    	String[] str = dateStr.split("T");
    	
    	if(str.length != 2){
    		return null;
    	}
    	
    	String[] ymd = str[0].split("-");
    	String[] hms = str[1].split(":");
    	int y = Integer.parseInt(ymd[0]);
    	int m = Integer.parseInt(ymd[1]);
    	int d = Integer.parseInt(ymd[2]);
    	int h = Integer.parseInt(hms[0]);
    	int mm = Integer.parseInt(hms[1]);
    	int s = Integer.parseInt(hms[2]);
    	return new Date(y,m,d,h,mm,s);

	};
    
    public static Date toDate(double millis) {
    	Date d = new Date((long) millis);
        return d;
    }
    
    public static boolean isIsoDateFormat(String ds){
    	
    	return false;
    }
    
    public static String getMillisStr(String isodateformat){
    	if(isodateformat == null)return null;
    	Date d = getDateFromString(isodateformat);
    	return String.valueOf(d.getTime());
    }
    
    
//	public static native void initGlobalFunctions()/*-{
//		$wnd.globalFunctions = {
//			afterUpload: function(resStr){
//				@com.m3958.firstgwt.client.utils.JsoUtils::afterUpload(Ljava/lang/String;)(resStr);
//			}
//		};
//	}-*/;
	
	
//	public static final native JavaScriptObject getJavascriptObject(String s)/*-{
//		return eval("(" + s + ")");
//	}-*/;
	
//	public static void afterUpload(String resStr){
//		UploadResponseJso urjso = (UploadResponseJso) getJavascriptObject(resStr);
//		addToFormAndAssets(urjso);
//	}
	
//	@SuppressWarnings("unchecked")
//	private static void addToFormAndAssets(UploadResponseJso urjso) {
//		JsArray<AssetJso> jsa  = (JsArray<AssetJso>) urjso.getAssets();
//		String uploadfor = urjso.getUploadFor();
//		String askerTimpstamp = urjso.getAskerTimeStamp();
//		JSONArray ja = new JSONArray();
//		
//		for(int i =0;i<jsa.length();i++){
//			JSONObject jo = new JSONObject();
//			jo.put(AssetAllField.JASSETID,new JSONNumber(jsa.get(i).getId()));
//			jo.put(AssetAllField.JASSETFOR, new JSONString(uploadfor));
//			ja.set(i, jo);
//		}
//		if(formAndAssets.get(askerTimpstamp) == null){
//			formAndAssets.put(askerTimpstamp, ja);
//		}else{
//			JSONArray j = formAndAssets.get(askerTimpstamp);
//			int ji = j.size();
//			for(int i=0;i<ja.size();i++){
//				ja.set(ji + i, ja.get(i));
//			}
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static AssetJso getFirstAsset(UploadResponseJso urj){
//		JsArray<AssetJso> assets = (JsArray<AssetJso>)urj.getAssets();
//		if(assets.length()>0)return assets.get(0);
//		return null;
//	}
//	
	

//	public static Map<String, JSONArray> formAndAssets = new HashMap<String, JSONArray>();
	
//	public static JSONArray getFormAssets(String askForTimeStamp){
//		if(formAndAssets.get(askForTimeStamp) != null){
//			return formAndAssets.get(askForTimeStamp);
//		}else{
//			return new JSONArray();
//		}
//	}

/*    
    Type Signature
    Java Type
    Z
    boolean
    B
    byte
    C
    char
    S
    short
    I
    int
    J
    long
    F
    float
    D
    double
    L fully-qualified-class ;
    fully-qualified-class
    [ type
    type[]
    ( arg-types ) ret-type
    method type
    For example, the Java method:

    long f (int n, String s, int[] arr); 
    has the following type signature:

    (ILjava/lang/String;[I)J 
*/    
}
