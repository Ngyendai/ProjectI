package com.example.demo;




import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;






@SpringBootApplication
public class FashionBackendApplication extends SpringBootServletInitializer{
	/*
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FashionBackendApplication.class);
	}
	*/
	public static void main(String[] args) {
		SpringApplication.run(FashionBackendApplication.class, args);
		/*
		 public List<Category> getContactList() {
		        List<Category> listContact = new ArrayList<>();
		         
		        listContact.add(new Category("Marry John", "marry.john@gmail.com", "USA"));
		        listContact.add(new Category("Tom Smith", "tomsmith@outlook.com", "England"));
		        listContact.add(new Category("John Purcell", "john123@yahoo.com", "Australia"));
		        listContact.add(new Category("Siva Krishna", "sivakrishna@gmail.com", "India"));
		         
		        return listContact;
		    }
		    */
	}

	
}
