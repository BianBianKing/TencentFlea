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
@Table(name="TencentFlea_Comment")
public class Comment {
	@Id
	@GeneratedValue
	private long id;

	@Column(name="itemId")
	private long itemId;

	@Column(name="icon")
	private String icon;

	@Column(name="rtxName")
	private String rtxName;

	@Column(name="commentDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date commentDate;

	@Column(name="commentContent")
	private String commentContent;

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRtxName() {
		return rtxName;
	}

	public void setRtxName(String rtxName) {
		this.rtxName = rtxName;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
}
