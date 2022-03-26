package com.example.demo.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DAO.BaseDAO;
import com.example.demo.excel.ImportExcelLogin;
import com.example.demo.excel.ImportExcelProduct;


import com.example.demo.model.Product;
import com.example.demo.reposutory.ProductRepository;

@Service
//Xảy ra exception bất kì rồi rollback lại
@org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl extends BaseDAO implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public <S extends Product> S save(S entity) {
		return productRepository.save(entity);
	}

	@Override
	public <S extends Product> Optional<S> findOne(Example<S> example) {
		return productRepository.findOne(example);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> findAll(Sort sort) {
		return productRepository.findAll(sort);
	}

	@Override
	public List<Product> findAllById(Iterable<Long> ids) {
		return productRepository.findAllById(ids);
	}

	@Override
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public <S extends Product> List<S> saveAll(Iterable<S> entities) {
		return productRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		productRepository.flush();
	}

	@Override
	public <S extends Product> S saveAndFlush(S entity) {
		return productRepository.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return productRepository.existsById(id);
	}

	@Override
	public <S extends Product> List<S> saveAllAndFlush(Iterable<S> entities) {
		return productRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Product> Page<S> findAll(Example<S> example, Pageable pageable) {
		return productRepository.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Product> entities) {
		productRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Product> long count(Example<S> example) {
		return productRepository.count(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Product> entities) {
		productRepository.deleteAllInBatch(entities);
	}

	@Override
	public <S extends Product> boolean exists(Example<S> example) {
		return productRepository.exists(example);
	}

	@Override
	public long count() {
		return productRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public <S extends Product, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return productRepository.findBy(example, queryFunction);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		productRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Product entity) {
		productRepository.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		productRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		productRepository.deleteAllInBatch();
	}

	@Override
	public Product getOne(Long id) {
		return productRepository.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Product> entities) {
		productRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		productRepository.deleteAll();
	}

	@Override
	public Product getById(Long id) {
		return productRepository.getById(id);
	}

	@Override
	public <S extends Product> List<S> findAll(Example<S> example) {
		return productRepository.findAll(example);
	}

	@Override
	public <S extends Product> List<S> findAll(Example<S> example, Sort sort) {
		return productRepository.findAll(example, sort);
	}

	@Override
	public void saveProductToDB(MultipartFile file, String productName, String description, String quanlity,
			Long price) {
		Product p = new Product();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) {
			System.out.println("not a valid file");
		}
		try {
			p.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		p.setDescription(description);

		p.setProductName(productName);
		p.setPrice(price);
		p.setQuanlity(quanlity);

		productRepository.save(p);

	}

	public void chageProductName(Long productId, String name) {
		Product p = new Product();
		p = productRepository.findById(productId).get();
		p.setProductName(name);
		productRepository.save(p);
	}

	public void changeProductDescription(Long productId, String description) {
		Product p = new Product();
		p = productRepository.findById(productId).get();
		p.setDescription(description);
		productRepository.save(p);
	}

	public void changeProductPrice(Long productId, Long price) {
		Product p = new Product();
		p = productRepository.findById(productId).get();
		p.setPrice(price);
		productRepository.save(p);
	}

	public void changeProductQuanlity(Long productId, String price) {
		Product p = new Product();
		p = productRepository.findById(productId).get();
		p.setQuanlity(price);
		productRepository.save(p);
	}

	@Override
	public void deletePro(Long productId) {
		// TODO Auto-generated method stub

	}

	

	@Override
	public void saveProduct(Product product, Integer productid, MultipartFile file, String productName,
			String description, String quanlity, Long price) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeProductQuanlity(Long productid, int quanlity) {
		// TODO Auto-generated method stub

	}


	@org.springframework.transaction.annotation.Transactional
	public void save(MultipartFile file) {
		try {
			List<Product> product = ImportExcelProduct.csvToTutorials(file.getInputStream());
			productRepository.saveAll(product);
			System.out.println("save done!");
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public ByteArrayInputStream load() {
		List<Product> product = productRepository.findAll();

		ByteArrayInputStream in = ImportExcelProduct.tutorialsToCSV(product);
		return in;
	}

	//Không bắt ngoại lệ của phương thức này
	@org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class, noRollbackFor = EntityNotFoundException.class)
	public List<Product> getAllTutorials() {
		return productRepository.findAll();
	}

	@Override
	public ByteArrayInputStream loaddata() {
		// TODO Auto-generated method stub
		return null;
	}

}
