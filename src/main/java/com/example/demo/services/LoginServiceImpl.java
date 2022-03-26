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
import com.example.demo.excel.ExportLoginExcel;
import com.example.demo.excel.ImportExcelLogin;
import com.example.demo.excel.ImportExcelProduct;
import com.example.demo.model.Login;
import com.example.demo.model.Product;
import com.example.demo.reposutory.LoginRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl extends BaseDAO implements LoginService{

	

	@Autowired
	private LoginRepository loginRepository;

	@Override
	public <S extends Login> S save(S entity) {
		return loginRepository.save(entity);
	}

	@Override
	public <S extends Login> Optional<S> findOne(Example<S> example) {
		return loginRepository.findOne(example);
	}

	@Override
	public Page<Login> findAll(Pageable pageable) {
		return loginRepository.findAll(pageable);
	}

	@Override
	public List<Login> findAll() {
		return loginRepository.findAll();
	}

	@Override
	public List<Login> findAll(Sort sort) {
		return loginRepository.findAll(sort);
	}

	@Override
	public List<Login> findAllById(Iterable<Long> ids) {
		return loginRepository.findAllById(ids);
	}

	@Override
	public Optional<Login> findById(Long id) {
		return loginRepository.findById(id);
	}

	@Override
	public <S extends Login> List<S> saveAll(Iterable<S> entities) {
		return loginRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		loginRepository.flush();
	}

	@Override
	public <S extends Login> S saveAndFlush(S entity) {
		return loginRepository.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return loginRepository.existsById(id);
	}

	@Override
	public <S extends Login> List<S> saveAllAndFlush(Iterable<S> entities) {
		return loginRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Login> Page<S> findAll(Example<S> example, Pageable pageable) {
		return loginRepository.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Login> entities) {
		loginRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Login> long count(Example<S> example) {
		return loginRepository.count(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Login> entities) {
		loginRepository.deleteAllInBatch(entities);
	}

	@Override
	public <S extends Login> boolean exists(Example<S> example) {
		return loginRepository.exists(example);
	}

	@Override
	public long count() {
		return loginRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		loginRepository.deleteById(id);
	}

	@Override
	public <S extends Login, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return loginRepository.findBy(example, queryFunction);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		loginRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Login entity) {
		loginRepository.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		loginRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		loginRepository.deleteAllInBatch();
	}

	@Override
	public Login getOne(Long id) {
		return loginRepository.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Login> entities) {
		loginRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		loginRepository.deleteAll();
	}

	@Override
	public Login getById(Long id) {
		return loginRepository.getById(id);
	}

	@Override
	public <S extends Login> List<S> findAll(Example<S> example) {
		return loginRepository.findAll(example);
	}

	@Override
	public <S extends Login> List<S> findAll(Example<S> example, Sort sort) {
		return loginRepository.findAll(example, sort);
	}

	@Override
	public Login login(String username, String password) {
		Login login = loginRepository.findByUsernameAndPassword(username, password);
		if (login == null) {
			return login;

		} else {
			return null;
		}

	}

	@Override
	public void saveLoginToDB(MultipartFile file, String username, String password, String gender, String email) {
		Login u = new Login();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) {
			System.out.println("not a valid file");
		}
		try {
			u.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		u.setUsername(username);
		u.setPassword(password);
		u.setGender(gender);
		u.setEmail(email);

		loginRepository.save(u);

	}

	@Override
	public void chageUserName(Long userId, String username) {
		Login u = new Login();
		u = loginRepository.findById(userId).get();
		u.setUsername(username);
		loginRepository.save(u);
	}

	@Override
	public void chageUserEmail(Long userId, String email) {
		Login u = new Login();
		u = loginRepository.findById(userId).get();
		u.setEmail(email);
		loginRepository.save(u);
	}

	@Override
	public void chageUserPasswrod(Long userId, String password) {
		Login u = new Login();
		u = loginRepository.findById(userId).get();
		u.setPassword(password);
		loginRepository.save(u);
	}

	@Override
	public void chageUserGender(Long userId, String gender) {
		Login u = new Login();
		u = loginRepository.findById(userId).get();
		u.setGender(gender);
		loginRepository.save(u);
	}

	
	
	
	
	@org.springframework.transaction.annotation.Transactional
	public void save(MultipartFile file) {
		try {
			List<Login> login = ImportExcelLogin.csvToTutorials(file.getInputStream());
			loginRepository.saveAll(login);
			System.out.println("save done!");
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public ByteArrayInputStream load() {
		List<Login> login = loginRepository.findAll();

		ByteArrayInputStream in = ImportExcelLogin.tutorialsToCSV(login);
		return in;
	}

	//Không bắt ngoại lệ của phương thức này
	@org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class, noRollbackFor = EntityNotFoundException.class)
	public List<Login> getAllTutorials() {
		return loginRepository.findAll();
	}

	@Override
	public ByteArrayInputStream loaddata() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	

}
