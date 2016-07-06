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
@Table(name="TencentFlea_Item")
public class Item {
	@Id
	@GeneratedValue
	private long id;

	@Column(name="itemTitle")
	private String itemTitle;

	@Column(name="imageUrl")
	private String imageUrl;

	@Column(name="itemDescription")
	private String itemDescription;

	@Column(name="itemType")
	private int itemType;

	@Column(name="rtxName")
	private String rtxName;

	@Column(name="saleOrBuy")
	private int saleOrBuy;

	@Column(name="itemPrice")
	private double itemPrice;

	@Column(name="onShelf")
	private int onShelf;

	@Column(name="collectionNum")
	private int collectionNum;

	@Column(name="releaseDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date releaseDate;

	public int getOnShelf() {
		return onShelf;
	}

	public void setOnShelf(int onShelf) {
		this.onShelf = onShelf;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public int getCollectionNum() {
		return collectionNum;
	}

	public void setCollectionNum(int collectionNum) {
		this.collectionNum = collectionNum;
	}

	public String getRtxName() {
		return rtxName;
	}

	public void setRtxName(String rtxName) {
		this.rtxName = rtxName;
	}

	public int getSaleOrBuy() {
		return saleOrBuy;
	}

	public void setSaleOrBuy(int saleOrBuy) {
		this.saleOrBuy = saleOrBuy;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
}
