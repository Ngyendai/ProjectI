package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table
public class Product {
	
	@Id
	@GeneratedValue
	private Long productId;
	private String productName;
	private String quanlity;
	private String description;
	
	private Long price;
	
	
	public Long getPrice() {
		return price;
	}
	
	public Product(Long productId, String productName, String quanlity, String description, Long price, String image) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quanlity = quanlity;
		this.description = description;
		this.price = price;
		this.image = image;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	
	private String image;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Product() {
		super();
	}

	public String getQuanlity() {
		return quanlity;
	}
	public void setQuanlity(String quanlity) {
		this.quanlity = quanlity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}
