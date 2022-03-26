package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

import com.example.demo.annotation.ValidEmail;







@Entity
public class Login {

	@Id
	@GeneratedValue
	private Long userId;

	// @ValidEmail // Chú thích
	//@Size(min = 1, max = 150, message = "Phải trên 2 kí tự")
	private String username;

	
	private String password;
	
	//@ValidEmail(message = "Such Email already exists!")
	private String email;
	private String gender;

	public Login() {
		super();
	}

	public Login(Long userId, String username, String password, String email, String gender, String image) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.image = image;
	}

	public Long getUserId() {
		return userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String image;

}
