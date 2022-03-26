package com.example.demo.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DAO.BaseDAO;
import com.example.demo.model.Product;
import com.example.demo.reposutory.ProductRepository;


public interface ProductService  {

	<S extends Product> List<S> findAll(Example<S> example, Sort sort);

	<S extends Product> List<S> findAll(Example<S> example);

	Product getById(Long id);

	void deleteAll();

	void deleteAll(Iterable<? extends Product> entities);

	Product getOne(Long id);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Long> ids);

	void delete(Product entity);

	void deleteAllByIdInBatch(Iterable<Long> ids);

	<S extends Product, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	void deleteById(Long id);

	long count();

	<S extends Product> boolean exists(Example<S> example);

	void deleteAllInBatch(Iterable<Product> entities);

	<S extends Product> long count(Example<S> example);

	void deleteInBatch(Iterable<Product> entities);

	<S extends Product> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends Product> List<S> saveAllAndFlush(Iterable<S> entities);

	boolean existsById(Long id);

	<S extends Product> S saveAndFlush(S entity);

	void flush();

	<S extends Product> List<S> saveAll(Iterable<S> entities);

	Optional<Product> findById(Long id);

	List<Product> findAllById(Iterable<Long> ids);

	List<Product> findAll(Sort sort);

	List<Product> findAll();

	Page<Product> findAll(Pageable pageable);

	<S extends Product> Optional<S> findOne(Example<S> example);

	<S extends Product> S save(S entity);

	void saveProductToDB(MultipartFile file, String productName, String description, String quanlity, Long price);

	void saveProduct(Product product, Integer productid, MultipartFile file, String productName, String description,
			String quanlity, Long price);
	
	void changeProductQuanlity(Long productid, int quanlity);

	void changeProductPrice(Long productid, Long price);

	void changeProductDescription(Long productid, String description);

	void chageProductName(Long productid, String username);
	
	void deletePro(Long productId);
	
	
	public void save(MultipartFile file);
	public ByteArrayInputStream loaddata();
	public List<Product> getAllTutorials();
	
	


}
