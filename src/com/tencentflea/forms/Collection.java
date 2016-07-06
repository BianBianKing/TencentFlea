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
@Table(name="TencentFlea_Collection")
public class Collection {
	@Id
	@GeneratedValue
	private long id;

	@Column(name="itemId")
	private long itemId;

	@Column(name="rtxName")
	private String rtxName;

	@Column(name="collectionDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date collectionDate;

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

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

	public String getRtxName() {
		return rtxName;
	}

	public void setRtxName(String rtxName) {
		this.rtxName = rtxName;
	}
}
