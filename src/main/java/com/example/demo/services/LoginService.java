package com.example.demo.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Login;
import com.example.demo.model.Product;
import com.example.demo.reposutory.LoginRepository;


public interface LoginService{

	
	
	<S extends Login> List<S> findAll(Example<S> example, Sort sort);

	<S extends Login> List<S> findAll(Example<S> example);

	Login getById(Long id);

	void deleteAll();

	void deleteAll(Iterable<? extends Login> entities);

	Login getOne(Long id);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Long> ids);

	void delete(Login entity);

	void deleteAllByIdInBatch(Iterable<Long> ids);

	<S extends Login, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	void deleteById(Long id);

	long count();

	<S extends Login> boolean exists(Example<S> example);

	void deleteAllInBatch(Iterable<Login> entities);

	<S extends Login> long count(Example<S> example);

	void deleteInBatch(Iterable<Login> entities);

	<S extends Login> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends Login> List<S> saveAllAndFlush(Iterable<S> entities);

	boolean existsById(Long id);

	<S extends Login> S saveAndFlush(S entity);

	void flush();

	<S extends Login> List<S> saveAll(Iterable<S> entities);

	Optional<Login> findById(Long id);

	List<Login> findAllById(Iterable<Long> ids);

	List<Login> findAll(Sort sort);

	List<Login> findAll();

	Page<Login> findAll(Pageable pageable);

	<S extends Login> Optional<S> findOne(Example<S> example);

	<S extends Login> S save(S entity);

	

	

	Login login(String username, String password);

	void saveLoginToDB(MultipartFile file, String username, String password, String gender, String email);

	void chageUserGender(Long userId, String gender);

	void chageUserPasswrod(Long userId, String password);

	void chageUserEmail(Long userId, String email);

	void chageUserName(Long userId, String username);

	public void save(MultipartFile file);
	public ByteArrayInputStream loaddata();
	public List<Login> getAllTutorials();
	
	
	
	
}
