package com.m3958.firstgwt.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.HmessageReceiverField;
import com.m3958.firstgwt.client.types.HmessageStatus;
import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.server.types.HasAttachments;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;


@Entity
@Table(name="HMESSAGES")
@NamedQueries({
	@NamedQuery(name="findMyHmByStatus",
			query="SELECT h FROM Hmessage AS h WHERE h.sender = :sender AND h.status = :status ORDER BY h.createdAt DESC"),
	@NamedQuery(name="findMyHmWithNullStatus",
			query="SELECT h FROM Hmessage AS h WHERE h.sender = :sender AND h.status IS NULL ORDER BY h.createdAt DESC"),
	@NamedQuery(name="findMyHm",
			query="SELECT h FROM Hmessage AS h WHERE h.sender = :sender ORDER BY h.createdAt DESC"),
	@NamedQuery(name="findHmHmrCount",
			query="SELECT COUNT(h) FROM HmessageReceiver AS h WHERE h.hMessage = :hMessage")
})
public class Hmessage extends BaseModel<Hmessage> implements HasAttachments{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5343457320033103336L;
	
	public static class NamedQueries{
		public final static String FIND_MY_HMESSAGE = "findMyHm";
		public final static String FIND_MY_HMESSAGE_WITH_NULL_STATUS = "findMyHmWithNullStatus";
		public final static String FIND_MY_HMESSAGE_BY_STATUS = "findMyHmByStatus";
		public final static String FIND_HM_HMR_COUNT = "findHmHmrCount";
	}

	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
		jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jc.setExcludes(JsonExcludeFields.HMESSAGE_EXCLUDES);
		JSONObject jo = JSONObject.fromObject(this, jc);
		jo.element(HmessageReceiverField.SENDER.getEname(), sender.getLoginName());
		return jo;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="HMESSAGE_ID")
	protected int id;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="SENDER_ID",referencedColumnName="USER_ID")
	private User sender;
	
	@Lob
	private String message;
	
	private String purpose;
	
	private String title;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<Asset> attachments = new ArrayList<Asset>();
	
	
	@Enumerated(EnumType.STRING)
	private HmessageStatus status;
	
	public Long getHmrCount(){
		Query q = emp.get().createNamedQuery(NamedQueries.FIND_HM_HMR_COUNT);
		q.setParameter("hMessage", this);
		Long l = (Long) q.getSingleResult();
		if(l == null)return 0L;
		return l;
	}
	
	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Asset> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Asset> attachments) {
		this.attachments = attachments;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean addAttachment(Asset at) {
		if(attachments.contains(at)){
			return false;
		}else{
			attachments.add(at);
			return true;
		}
	}

	@Override
	public boolean dropAttachment(Asset at) {
		if(attachments.contains(at)){
			attachments.remove(at);
			return true;
		}else{
			return false;
		}
	}

	public void setStatus(HmessageStatus status) {
		this.status = status;
	}

	public HmessageStatus getStatus() {
		return status;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPurpose() {
		return purpose;
	}
}
