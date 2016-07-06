package com.tencentflea.forms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TencentFlea_Message")
public class Message {
	@Id
	@GeneratedValue
	private long id;

	@Column(name="itemId")
	private long itemId;

	@Column(name="messageType")
	private int messageType;

	@Column(name="rtxName")
	private String rtxName;

	@Column(name="actionRtxName")
	private String actionRtxName;

	@Column(name="messageDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date messageDate;

	@Column(name="messageContent")
	private String messageContent;

	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

	@Column(name="isNew")
	private int isNew;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public String getRtxName() {
		return rtxName;
	}

	public void setRtxName(String rtxName) {
		this.rtxName = rtxName;
	}

	public String getActionRtxName() {
		return actionRtxName;
	}

	public void setActionRtxName(String actionRtxName) {
		this.actionRtxName = actionRtxName;
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
}
