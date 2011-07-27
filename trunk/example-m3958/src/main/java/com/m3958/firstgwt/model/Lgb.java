package com.m3958.firstgwt.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.layout.AttachmentBox;
import com.m3958.firstgwt.client.layout.TitleImgBox;
import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.client.types.LgbField;
import com.m3958.firstgwt.client.types.SmartConstants;
import com.m3958.firstgwt.server.types.HasAttachments;
import com.m3958.firstgwt.server.types.HasRelation;
import com.m3958.firstgwt.server.types.IhasTitleImg;
import com.m3958.firstgwt.session.ErrorMessages;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

/*
 * INSERT INTO LGBS_ADDRESSES (LGB_LGB_ID,ADDRESSES_ADDRESS_ID) select LGB_ID,ADDRESS_ID FROM ADDRESSES WHERE LGB_ID IS NOT NULL;
 * INSERT INTO LGBS_CAREERS (LGB_LGB_ID,CAREERS_CAREER_ID) select LGB_ID,CAREER_ID FROM CAREERS WHERE LGB_ID IS NOT NULL;
 * INSERT INTO LGBS_FAMILIES (LGB_LGB_ID,FAMILIES_FAMILY_ID) select LGB_ID,FAMILY_ID FROM FAMILIES WHERE LGB_ID IS NOT NULL;
 * INSERT INTO LGBS_HOUSES (LGB_LGB_ID,HOUSES_HOUSE_ID) select LGB_ID,HOUSE_ID FROM HOUSES WHERE LGB_ID IS NOT NULL;
 * INSERT INTO LGBS_REWARDS (LGB_LGB_ID,REWARDS_REWARD_ID) select LGB_ID,REWARD_ID FROM REWARDS WHERE LGB_ID IS NOT NULL;
 * INSERT INTO LGBS_STEP_CAREERS (LGB_LGB_ID,STEPCAREERS_STEP_CAREER_ID) select LGB_ID,STEP_CAREER_ID FROM STEP_CAREERS WHERE LGB_ID IS NOT NULL;
 */

@Entity
@Table(name="LGBS")
@NamedQueries({
	@NamedQuery(name="findByDepartment",
			query = "SELECT l FROM Lgb AS l WHERE l.department = ?1"),
	@NamedQuery(name="countByDepartment",
			query = "SELECT COUNT(DISTINCT l) FROM Lgb AS l WHERE l.department = ?1")
})
public class Lgb extends BaseModel<Lgb> implements HasAttachments,HasCreator,IhasTitleImg,HasRelation{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="LGB_ID")
	protected int id;
	
	public static class LgbNamedQueries{
		public static final String FIND_BY_DEPARTMENT = "findByDepartment";
		public static final String COUNT_BY_DEPARTMENT = "countByDepartment";
	}
	

	
	public <T> void addProperty(T bm){
		if(bm instanceof Address){
			addresses.add((Address) bm);
		}else if(bm instanceof Family){
			families.add((Family) bm);
		}else if(bm instanceof House){
			houses.add((House) bm);
		}else if(bm instanceof Career){
			careers.add((Career) bm);
		}else if(bm instanceof StepCareer){
			stepCareers.add((StepCareer) bm);
		}else if(bm instanceof Reward){
			rewards.add((Reward) bm);
		}
	}
	
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.LGB_EXCLUDES);
		return JSONObject.fromObject(this, jc);
	}
	
	@Override
	public JSONObject toJsonOne(){
		JSONObject jo = toJson();
		if(zp != null){
			jo.element(LgbField.ZP.getEname(),zp.toJson());
		}
		JSONArray ja1 = new JSONArray();
		for(Asset a : attachments){
			ja1.add(a.toJson());
		}
		jo.element(LgbField.ATTACHMENTS.getEname(), ja1);
		return jo;
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	private String jbms;

	public String getJbms() {
		return jbms;
	}

	public void setJbms(String jbms) {
		this.jbms = jbms;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4451984144644452460L;
	
	@ManyToOne
	@JoinColumn(name="DEPARTMENT_ID",referencedColumnName="DEPARTMENT_ID")
	private Department department;
	
	public int getDepartmentId(){
		if(department != null){
			return department.getId();
		}
		return SmartConstants.NONE_EXIST_MODEL_ID;
	}
	
	@ManyToOne
	@JoinColumn(name="SHEQU_ID",referencedColumnName="SHEQU_ID")
	private Shequ shequ;
	
	
	private int persistedShequId =  SmartConstants.NONE_EXIST_MODEL_ID;
	
	public void setPersistedShequId(int pid){
		persistedShequId = pid;
	}
	
	public int getShequId(){
		return persistedShequId;
//		if(shequ != null){
//			return shequ.getId();
//		}
//		return SmartConstants.NONE_EXIST_MODEL_ID;
	}
	
	@OneToMany(cascade=CascadeType.REMOVE)
	private List<Asset> attachments = new ArrayList<Asset>();
	
	@OneToMany(cascade=CascadeType.REMOVE)
	private List<Family> families = new ArrayList<Family>();
	
	@OneToMany(cascade=CascadeType.REMOVE)
	private List<Career> careers = new ArrayList<Career>();
	
	@OneToMany(cascade=CascadeType.REMOVE)
	private List<StepCareer> stepCareers = new ArrayList<StepCareer>();
	
	@OneToMany(cascade=CascadeType.REMOVE)
	private List<Reward> rewards = new ArrayList<Reward>();
	
	@OneToMany(cascade=CascadeType.REMOVE)
	private List<House> houses = new ArrayList<House>();
	
	@OneToMany(cascade=CascadeType.REMOVE)
	private List<Address> addresses = new ArrayList<Address>();
	
	//,1,55,6,8,
	private String departmentIds;
	
	

	private String xm;
	
	private String sszb;
	
	private int paixu;


	private String xb;
	

	@Temporal(TemporalType.DATE)
	private Date sr;
	

	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="ZP_ID",referencedColumnName="ASSET_ID")
	private Asset zp;
	
	
	private int persistedZpId = SmartConstants.NONE_EXIST_MODEL_ID;
	
	public void setPersistedZpId(int pid){
		persistedZpId = pid;
	}
	
	public int getZpId(){
		return persistedZpId;
	}
	

	private String sfz;
	

	private String minzu;
	

	private String jg;
	

	private String csd;
	


	private String jy;
	

	@Temporal(TemporalType.DATE)
	private Date rdsj;
	

	@Temporal(TemporalType.DATE)
	private Date rwsj;
	

	@Temporal(TemporalType.DATE)
	private Date ltxsj;
	

	private String ygzdw;
	

	private String yzw;
	

	private String xzgdw;
	


	private String dwxz;
	


	private String xzjb;
	


	private String xsdy;
	


	private String gblx;
	


	private String jkzk;
	


	private String hyzk;
	


	private String jjzk;
	

	private String pogz;
	

	private String hjszd;
	

	@Temporal(TemporalType.DATE)
	private Date swsj;
	
	private boolean passAway;
	

	@Lob
	private String bz;
	



	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}


	public Date getSr() {
		return sr;
	}

	public void setSr(Date sr) {
		this.sr = sr;
	}


	public String getSfz() {
		return sfz;
	}

	public void setSfz(String sfz) {
		this.sfz = sfz;
	}

	public String getMinzu() {
		return minzu;
	}

	public void setMinzu(String minzu) {
		this.minzu = minzu;
	}

	public String getJg() {
		return jg;
	}

	public void setJg(String jg) {
		this.jg = jg;
	}

	public String getCsd() {
		return csd;
	}

	public void setCsd(String csd) {
		this.csd = csd;
	}

	public String getJy() {
		return jy;
	}

	public void setJy(String jy) {
		this.jy = jy;
	}

	public Date getRdsj() {
		return rdsj;
	}

	public void setRdsj(Date rdsj) {
		this.rdsj = rdsj;
	}

	public Date getRwsj() {
		return rwsj;
	}

	public void setRwsj(Date rwsj) {
		this.rwsj = rwsj;
	}

	public Date getLtxsj() {
		return ltxsj;
	}

	public void setLtxsj(Date ltxsj) {
		this.ltxsj = ltxsj;
	}

	public String getYgzdw() {
		return ygzdw;
	}

	public void setYgzdw(String ygzdw) {
		this.ygzdw = ygzdw;
	}

	public String getYzw() {
		return yzw;
	}

	public void setYzw(String yzw) {
		this.yzw = yzw;
	}

	public String getXzgdw() {
		return xzgdw;
	}

	public void setXzgdw(String xzgdw) {
		this.xzgdw = xzgdw;
	}

	public String getDwxz() {
		return dwxz;
	}

	public void setDwxz(String dwxz) {
		this.dwxz = dwxz;
	}

	public String getXzjb() {
		return xzjb;
	}

	public void setXzjb(String xzjb) {
		this.xzjb = xzjb;
	}

	public String getXsdy() {
		return xsdy;
	}

	public void setXsdy(String xsdy) {
		this.xsdy = xsdy;
	}

	public String getGblx() {
		return gblx;
	}

	public void setGblx(String gblx) {
		this.gblx = gblx;
	}

	public String getJkzk() {
		return jkzk;
	}

	public void setJkzk(String jkzk) {
		this.jkzk = jkzk;
	}

	public String getHyzk() {
		return hyzk;
	}

	public void setHyzk(String hyzk) {
		this.hyzk = hyzk;
	}

	public String getJjzk() {
		return jjzk;
	}

	public void setJjzk(String jjzk) {
		this.jjzk = jjzk;
	}

	public String getPogz() {
		return pogz;
	}

	public void setPogz(String pogz) {
		this.pogz = pogz;
	}

	public String getHjszd() {
		return hjszd;
	}

	public void setHjszd(String hjszd) {
		this.hjszd = hjszd;
	}

	public Date getSwsj() {
		return swsj;
	}

	public void setSwsj(Date swsj) {
		this.swsj = swsj;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}


	public void setPassAway(boolean passAway) {
		this.passAway = passAway;
	}

	public boolean isPassAway() {
		return passAway;
	}

	public void setDepartment(Department department) {
		this.department = department;
		this.departmentIds = "," + department.getId() + department.getParentIds();
	}

	public Department getDepartment() {
		return department;
	}


	public String getDepartmentIds() {
		return departmentIds;
	}

	public void setZp(Asset zp) {
		this.zp = zp;
		persistedZpId = zp.getId();
	}

	public Asset getZp() {
		return zp;
	}

	public void setAddresses(ArrayList<Address> addresses) {
		this.addresses = addresses;
	}
	
	public boolean addAddress(Address address){
		if(addresses.contains(address)){
			return false;
		}else{
			addresses.add(address);
			return true;
		}
	}
	
	public boolean dropAddress(Address address){
		if(addresses.contains(address)){
			addresses.remove(address);
			return true;
		}else{
			return false;
		}
	}


	public List<Address> getAddresses() {
		return addresses;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public String getXb() {
		return xb;
	}

	public void setShequ(Shequ shequ) {
		this.shequ = shequ;
		persistedShequId = shequ.getId();
	}

	public Shequ getShequ() {
		return shequ;
	}

	public void setFamilies(List<Family> families) {
		this.families = families;
	}
	
	public boolean addFamily(Family family){
		if(families.contains(family)){
			return false;
		}else{
			families.add(family);
			return true;
		}
	}
	
	public boolean dropFamily(Family family){
		if(families.contains(family)){
			families.remove(family);
			return true;
		}else{
			return false;
		}
	}

	public List<Family> getFamilies() {
		return families;
	}
	
	public List<Career> getCareers() {
		return careers;
	}

	public void setCareers(List<Career> careers) {
		this.careers = careers;
	}

	public List<StepCareer> getStepCareers() {
		return stepCareers;
	}

	public void setStepCareers(List<StepCareer> stepCareers) {
		this.stepCareers = stepCareers;
	}

	public List<Reward> getRewards() {
		return rewards;
	}

	public void setRewards(List<Reward> rewards) {
		this.rewards = rewards;
	}
	
	public boolean addCareer(Career career){
		if(careers.contains(career)){
			return false;
		}else{
			careers.add(career);
			return true;
		}
	}
	
	public boolean dropCareer(Career career){
		if(careers.contains(career)){
			careers.remove(career);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean addStepCareer(StepCareer stepCareer){
		if(stepCareers.contains(stepCareer)){
			return false;
		}else{
			stepCareers.add(stepCareer);
			return true;
		}
	}
	
	public boolean dropStepCareer(StepCareer stepCareer){
		if(stepCareers.contains(stepCareer)){
			stepCareers.remove(stepCareer);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean addReward(Reward reward){
		if(rewards.contains(reward)){
			return false;
		}else{
			rewards.add(reward);
			return true;
		}
	}
	
	public boolean dropReward(Reward reward){
		if(rewards.contains(reward)){
			rewards.remove(reward);
			return true;
		}else{
			return false;
		}
	}

	public void setHouses(List<House> houses) {
		this.houses = houses;
	}

	public List<House> getHouses() {
		return houses;
	}
	
	public boolean addHouse(House house){
		if(houses.contains(house)){
			return false;
		}else{
			houses.add(house);
			return true;
		}
	}
	
	public boolean dropHouse(House house){
		if(houses.contains(house)){
			houses.remove(house);
			return true;
		}else{
			return false;
		}
	}

	public void setAttachments(List<Asset> attachments) {
		this.attachments = attachments;
	}
	
	public boolean addAttachment(Asset asset){
		if(attachments.contains(asset)){
			return false;
		}else{
			attachments.add(asset);
			return true;
		}
	}
	public boolean dropAttachment(Asset asset){
		if(attachments.contains(asset)){
			attachments.remove(asset);
			if(zp != null){
				if(zp.getId() == asset.getId()){
					zp = null;
				}
			}
			return true;
		}else{
			return false;
		}
	}

	public List<Asset> getAttachments() {
		return attachments;
	}
	
	public Address getMainAddress(){
		for (Address a:addresses) {
			if(a.isMainAddress()){
				return a;
			}
		}
		if(addresses.isEmpty()){
			return null;
		}else{
			return addresses.get(0);
		}
	}

	public void setSszb(String sszb) {
		this.sszb = sszb;
	}

	public String getSszb() {
		return sszb;
	}

	public void setPaixu(int paixu) {
		this.paixu = paixu;
	}

	public int getPaixu() {
		return paixu;
	}
	
	protected String creatorIds;
	
	@ManyToOne
	@JoinColumn(name="CREATOR_ID",referencedColumnName="USER_ID")
	protected User creator;
	
	
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
		creatorIds = getCreatorPath(creator);
	}

	public String getCreatorIds() {
		return creatorIds;
	}


	@Override
	public Asset getTitleImg() {
		return zp;
	}


	@Override
	public void setTitleImg(Asset asset) {
		zp = asset;
	}
	
	private boolean addRelation(BaseModel bm,String hint,ErrorMessages errors){
		if(bm instanceof Asset){
			if(TitleImgBox.class.getName().equals(hint)){
				zp = (Asset) bm;
			}else if(AttachmentBox.class.getName().equals(bm)){
				if(attachments.contains(bm)){
					return false;
				}else{
					attachments.add((Asset) bm);
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean removeRelation(BaseModel bm,String hint,ErrorMessages errors){
		if(bm instanceof Asset){
			if(TitleImgBox.class.getName().equals(hint)){
				zp = null;
			}else if(AttachmentBox.class.getName().equals(hint)){
				if(attachments.contains(bm)){
					attachments.remove(bm);
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}


	@Override
	public boolean manageRelation(BaseModel bm, boolean isAdd, String hint,
			ErrorMessages errors) {
		if(isAdd){
			return addRelation(bm, hint, errors);
		}else{// is remove
			return removeRelation(bm, hint, errors);
		}
	}

}
