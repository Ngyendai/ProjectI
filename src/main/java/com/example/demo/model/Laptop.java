package com.example.demo.model;

import javax.persistence.*;

@Entity
public class Laptop {
	@Id
	private Long id;
	private String name;
	private String description;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public Laptop() {
		
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	/*
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	*/
	private String image;

	public Laptop(Long id, String name, String description, String image) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
