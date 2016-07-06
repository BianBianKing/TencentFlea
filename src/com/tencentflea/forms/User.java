package com.tencentflea.forms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TencentFlea_User")
public class User {
	@Id
	@GeneratedValue
	private long id;

	@Column(name="icon")
	private String icon;

	@Column(name="password")
	private String password;

	@Column(name="rtxName")
	private String rtxName;

	//	@Column(name="register_date")
	//	@Temporal(TemporalType.DATE)
	//	private Date register_date;

	@Column(name="workLocation")
	private int workLocation;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRtxName() {
		return rtxName;
	}

	public void setRtxName(String rtxName) {
		this.rtxName = rtxName;
	}

	public int getWorkLocation() {
		return workLocation;
	}

	public void setWorkLocation(int workLocation) {
		this.workLocation = workLocation;
	}
}
