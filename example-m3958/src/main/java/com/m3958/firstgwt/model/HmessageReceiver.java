package com.m3958.firstgwt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.m3958.firstgwt.client.types.HmessageReceiverField;
import com.m3958.firstgwt.client.types.HmessageStatus;
import com.m3958.firstgwt.client.types.JsonExcludeFields;
import com.m3958.firstgwt.utils.JsonlibDateValueProssor;

@Entity
@Table(name="HM_RECEIVER")
@NamedQueries({
	@NamedQuery(name="findMyHmrByStatus",
			query="SELECT h FROM HmessageReceiver AS h INNER JOIN FETCH h.hMessage WHERE h.receiver = :receiver AND h.status = :status ORDER BY h.createdAt DESC"),
	@NamedQuery(name="findMyHmr",
			query="SELECT h FROM HmessageReceiver AS h INNER JOIN FETCH h.hMessage WHERE h.receiver = :receiver ORDER BY h.createdAt DESC"),
	@NamedQuery(name="findMyHmrByStatuses",
			query="SELECT h FROM HmessageReceiver AS h INNER JOIN FETCH h.hMessage WHERE h.receiver = :receiver AND h.status IN :statuses ORDER BY h.createdAt DESC")
})
public class HmessageReceiver extends BaseModel<HmessageReceiver>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5343457320033103336L;
	
	public static class NamedQueries{
		public final static String FIND_MY_HMESSAGE_BY_STATUS = "findMyHmrByStatus";
		public final static String FIND_MY_HMESSAGE = "findMyHmr";
		public final static String FIND_MY_HMESSAGE_BY_STATUSES = "findMyHmrByStatuses";
	}
	
	
	@Override
	public JSONObject toJson(){
		JsonConfig jc = new JsonConfig();
			jc.registerJsonValueProcessor(Date.class, injector.getInstance(JsonlibDateValueProssor.class));
			jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			jc.setExcludes(JsonExcludeFields.HMESSAGE_RECEIVER_EXCLUDES);

		JSONObject jo = JSONObject.fromObject(this, jc);
		jo.element(HmessageReceiverField.SENDER.getEname(), hMessage.getSender().getLoginName());
		jo.element(HmessageReceiverField.TITLE.getEname(), hMessage.getTitle());
		jo.element(HmessageReceiverField.MSG_BODY.getEname(), hMessage.getMessage());
		return jo;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false,name="HM_RECEIVER_ID")
	protected int id;
	
	@OneToOne
	@JoinColumn(name="RECEIVER_ID",referencedColumnName="USER_ID")
	private User receiver;
	
	@OneToOne
	@JoinColumn(name="HMESSAGE_ID",referencedColumnName="HMESSAGE_ID")
	private Hmessage hMessage;
	
	
	@Enumerated(EnumType.STRING)
	private HmessageStatus status;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date readTime;
	
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public Hmessage gethMessage() {
		return hMessage;
	}

	public void sethMessage(Hmessage hMessage) {
		this.hMessage = hMessage;
	}

	public void setStatus(HmessageStatus status) {
		this.status = status;
	}

	public HmessageStatus getStatus() {
		return status;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public Date getReadTime() {
		return readTime;
	}

}
