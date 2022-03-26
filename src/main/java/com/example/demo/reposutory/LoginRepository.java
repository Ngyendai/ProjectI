package com.example.demo.reposutory;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long>{
	 Login findByUsernameAndPassword(String email, String password);
	 
	//Tìm kiếm theo tên
		//public List<Login>findByNameLikeOrderByName(String username);
	 
	//Login findbyemail(String email);
}
