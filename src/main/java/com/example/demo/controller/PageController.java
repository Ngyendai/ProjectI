package com.example.demo.controller;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.excel.ExportLoginExcel;
import com.example.demo.excel.ExportProductExcel;
import com.example.demo.excel.ImportExcelLogin;
import com.example.demo.excel.ImportExcelProduct;

import com.example.demo.mes.ResponseMessage;
import com.example.demo.model.Laptop;
import com.example.demo.model.Login;
import com.example.demo.model.Product;

import com.example.demo.services.LoginService;
import com.example.demo.services.LoginServiceImpl;
import com.example.demo.services.ProductService;
import com.example.demo.services.ProductServiceImpl;

@Controller
//@RestController
public class PageController {

	@Autowired
	private ProductService productService;

	@Autowired
	private LoginServiceImpl loginServiceImpl;

	@Autowired
	private LoginService loginService;

	// Home
	@RequestMapping("/home")
	public String showhome(Model model) {
		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		return "home";
	}

	// Liên hệ
	@RequestMapping("/aboutus")
	public String showaboutus() {
		return "page/Aboutus";
	}

	// Gioi thiệu
	@RequestMapping("/contactus")
	public String showcontactus() {
		return "page/Contactus";
	}

	// Tin tức
	@RequestMapping("/news")
	public String shownews() {
		return "page/news";
	}

	// login
	@RequestMapping("/login")
	public String showLogin() {
		return "page/Login";
	}

	// singup
	@RequestMapping("/singup")
	public String showregister() {
		return "page/SingUp";
	}

	// dashboard
	@GetMapping("/dashboard")
	public String showDashboard() {
		return "page/dashboard";
	}

	/* ---------- DASHBOARD ------------ */
	/*------ PRODUCT --------*/

	// Add Product
	@GetMapping("/product")
	public String addProduct(ModelMap model) {
		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		return "page/Product";
	}

	// Add Product
	@GetMapping("/importexcelproduct")
	public String importexcelProduct(ModelMap model) {
		return "page/importProduct";
	}

	// Show Product
	@GetMapping("/listproduct")
	public String showProduct(ModelMap model) {

		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		return "page/listproduct";
	}

	// Hiển thị dữ liệu API
	@RequestMapping(value = "/listProduct")
	public List<Product> getAll() {
		return (List<Product>) productService.findAll();
	}

	// Insert Product
	@PostMapping("/addP")
	public String saveProduct(@RequestParam("file") MultipartFile file, @RequestParam("pname") String productName,
			@RequestParam("quanlity") String quanlity, @RequestParam("price") Long price,
			@RequestParam("desc") String description, ModelMap model) {

		productService.saveProductToDB(file, productName, description, quanlity, price);
		List<Product> products = productService.findAll();
		model.addAttribute("products", products);

		return "page/listproduct";
	}

	// Update Product Name
	@PostMapping("/changeName")
	public String changePname(@RequestParam("productId") Long productId, @RequestParam("newPname") String name) {
		productService.chageProductName(productId, name);
		return "redirect:/listproduct";
	}

	// Update Product description
	@PostMapping("/changeDescription")
	public String changeDescription(@RequestParam("productId") Long productId,
			@RequestParam("newDescription") String description) {
		productService.changeProductDescription(productId, description);
		return "redirect:/listproduct";
	}

	// Update Product price
	@PostMapping("/changePrice")
	public String changePrice(@RequestParam("productId") Long productId, @RequestParam("newPrice") Long price) {
		productService.changeProductPrice(productId, price);
		return "redirect:/listproduct";
	}

	// Update Product quanlity
	@PostMapping("/changQuanlity")
	public String changQuanlity(@RequestParam("productId") Long productId, @RequestParam("newQuanlity") int quanlity) {
		productService.changeProductQuanlity(productId, quanlity);
		return "redirect:/listproduct";
	}

	// Delete Product Update

	@RequestMapping(value = "/deleteProduct/{id}")
	public String deleteProduct(ModelMap model, @PathVariable(value = "id") long id) {
		this.productService.deleteById(id);
		model.addAttribute("products", productService.findAll());
		return "redirect:/listproduct";
	}

	// Xóa dữ liệu theo id API
	@DeleteMapping("/deleteProduct/{id}")
	public void deleteProdata(@PathVariable Long id) {
		productService.deleteById(id);

	}

	// Detail Product

	@RequestMapping(value = "/{productId}")
	public String detailProduct(Model model, @PathVariable(value = "productId") long productId) {
		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		Product product = productService.findById(productId).get();

		model.addAttribute("product", product);
		return "page/detailProduct";
	}

	
	//load file CSV Product
	// Lưu dữ liệu vào DB
	// MANDATORY: Bắt buộc phải có 1 transaction đã được tạo trước đó
	// @org.springframework.transaction.annotation.Transactional(propagation =
	// Propagation.MANDATORY)
	@PostMapping("/uploadProduct")
	@Transactional
	public ResponseEntity<ResponseMessage> uploadFileProduct(@RequestParam("file") MultipartFile file) {
		String message = "";

		if (ImportExcelProduct.hasCSVFormat(file)) {
			try {
				productService.save(file);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();

				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadProduct/")
						.path(file.getOriginalFilename()).toUriString();

				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, fileDownloadUri));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, ""));
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message, ""));
	}

	//list Product
	@GetMapping("/listProduct")
	public ResponseEntity<List<Product>> getAllProduct() {
		try {
			List<Product> product = productService.getAllTutorials();

			if (product.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(product, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// download Csv Product
	@GetMapping("/downloadProduct/{fileName:.+}")
	public ResponseEntity<InputStreamResource> downloadFileProduct(@PathVariable String fileName) {
		InputStreamResource file = new InputStreamResource(productService.loaddata());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}

	// Export Excel Product
	@GetMapping("/product/export/excel")
	public void exportProductToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=product_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Product> listproduct = productService.findAll();

		ExportProductExcel excelExporter = new ExportProductExcel(listproduct);

		excelExporter.export(response);
	}

	/*--------END PRODUCT ----------*/

	/*------ USER --------*/

	// Add User
	@GetMapping("/adduser")
	public String addUser() {
		return "page/adduser";
	}

	// Add Product
	@GetMapping("/importexcellogin")
	public String importexcelLogin(ModelMap model) {
		return "page/importLogin";
	}

	// Show User
	@GetMapping("/listuser")
	public String showlistUser(ModelMap model) {

		List<Login> logins = loginServiceImpl.findAll();
		model.addAttribute("logins", logins);
		return "page/listuser";
	}

	// Hiển thị dữ liệu API
	@RequestMapping(value = "/listUser")
	public List<Login> getUserAll() {
		return (List<Login>) loginServiceImpl.findAll();
	}

	// Insert User no DashBoard
	@PostMapping("/Login")
	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public String saveUser(@RequestParam("file") MultipartFile file, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("gender") String gender,
			@RequestParam("email") String email, ModelMap model, @Valid @ModelAttribute("login") Login login) {

		loginServiceImpl.saveLoginToDB(file, username, password, gender, email);
		Login oauthUser = loginServiceImpl.login(login.getUsername(), login.getPassword());
		return "page/Login";
	}

	// Insert User in DashBoard
	@PostMapping("/listuser")
	public String saveUsers(@RequestParam("file") MultipartFile file, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("gender") String gender,
			@RequestParam("email") String email, ModelMap model, @Valid @ModelAttribute("login") Login logins,
			BindingResult result) {

		loginServiceImpl.saveLoginToDB(file, username, password, gender, email);
		List<Login> login = loginServiceImpl.findAll();
		model.addAttribute("login", login);

		return "redirect:/listuser";
	}

	// Update User Name
	@PostMapping("/changeUserName")
	public String changeUserName(@RequestParam("userId") Long userId, @RequestParam("newName") String username) {
		loginServiceImpl.chageUserName(userId, username);
		return "redirect:/listuser";
	}

	// Update User Email
	@PostMapping("/changeUserEmail")
	public String changeUserEmail(@RequestParam("userId") Long userId, @RequestParam("newEmail") String email) {
		loginServiceImpl.chageUserEmail(userId, email);
		return "redirect:/listuser";
	}

	// Update User Password
	@PostMapping("/changeUserPassword")
	public String changeUserPassword(@RequestParam("userId") Long userId,
			@RequestParam("newPassword") String password) {
		loginServiceImpl.chageUserPasswrod(userId, password);
		return "redirect:/listuser";
	}

	// Update User Gender
	@PostMapping("/changeUserGender")
	public String changeUserGender(@RequestParam("userId") Long userId, @RequestParam("newgender") String gender) {
		loginServiceImpl.chageUserGender(userId, gender);
		return "redirect:/listuser";
	}

	// Delete User
	@RequestMapping(value = "/deleteUser/{id}")
	public String deleteUser(ModelMap model, @PathVariable(value = "id") long id) {
		this.loginServiceImpl.deleteById(id);
		model.addAttribute("login", loginServiceImpl.findAll());
		return "redirect:/listuser";
	}

	// Xóa dữ liệu theo id API
	@DeleteMapping("/deleteUser/{id}")
	public void deleteUsedata(@PathVariable Long id) {
		loginServiceImpl.deleteById(id);

	}

	// Login in Dashboard
	@PostMapping("/login")
	public String login(@ModelAttribute("login") Login login) {
		Login oauthUser = loginServiceImpl.login(login.getUsername(), login.getPassword());

		System.out.print(oauthUser);
		if (Objects.nonNull(oauthUser)) {
			return "home";

		} else {
			return "page/dashboard";

		}

	}

	// load file CSV Login
	// Lưu dữ liệu vào DB
	// MANDATORY: Bắt buộc phải có 1 transaction đã được tạo trước đó
	// @org.springframework.transaction.annotation.Transactional(propagation =
	// Propagation.MANDATORY)
	@PostMapping("/uploadUser")
	@Transactional
	public ResponseEntity<ResponseMessage> uploadFileUser(@RequestParam("file") MultipartFile file) {
		String message = "";

		if (ImportExcelLogin.hasCSVFormat(file)) {
			try {
				loginService.save(file);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();

				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadUser/")
						.path(file.getOriginalFilename()).toUriString();

				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, fileDownloadUri));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, ""));
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message, ""));
	}

	// list Login
	@GetMapping("/listLogin")
	public ResponseEntity<List<Login>> getAllLogin() {
		try {
			List<Login> login = loginService.getAllTutorials();

			if (login.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(login, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// download Csv Login
	@GetMapping("/downloadUser/{fileName:.+}")
	public ResponseEntity<InputStreamResource> downloadFileUser(@PathVariable String fileName) {
		InputStreamResource file = new InputStreamResource(loginService.loaddata());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}

	// Import Excel Login
	@GetMapping("/users/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=login_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Login> listUsers = loginServiceImpl.findAll();

		ExportLoginExcel excelExporter = new ExportLoginExcel(listUsers);

		excelExporter.export(response);
	}

	// Logout Dashboard
	@RequestMapping(value = { "/home" }, method = RequestMethod.POST)
	public String logoutDo(HttpServletRequest request, HttpServletResponse response) {
		return "home";
	}

}
