package com.example.demo.excel;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Login;
import com.example.demo.model.Product;

public class ImportExcelProduct {
	public static String TYPE = "text/csv";
	static String[] HEADERs = { "Id", "Name", "Quanlity","Description","Price","Image" };

	// Kiểm tra Định dạng tệp là CSV hay không
	public static boolean hasCSVFormat(MultipartFile file) {
		if (TYPE.equals(file.getContentType()) || file.getContentType().equals("application/vnd.ms-excel")) {
			return true;
		}

		return false;
	}

	// Đọc dữ liệu từ tệp CSV
	public static List<Product> csvToTutorials(InputStream is) {
		// Tạo 1 đối tượng BufereReader từ InputStream
		 Base64.Encoder simpleEncoder = Base64.getEncoder();
		try (BufferedReader fileReader =  new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
				// Next tạo 1 đối tượng CSVParser từ BuffereReader và InputStream
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<Product> productList = new ArrayList<>();

			// Trả về nội dung có trong tệp CSV ở dạng Bản Ghi
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			// Lặp lại từng bản ghi trong tệp với sự trợ giúp của method get(), nhận được
			// giá trị của từng trường
			for (CSVRecord csvRecord : csvRecords) {
				Product productTutorial = new Product(Long.parseLong(csvRecord.get("Id")), csvRecord.get("Name"),
					csvRecord.get("Quanlity"),csvRecord.get("Description"),	Long.parseLong(csvRecord.get("Price")),
						csvRecord.get("Image")

				);

				productList.add(productTutorial);
			}

			return productList;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

	// Dùng để ghi dữ liệu trong tệp CSV từ Database
	public static ByteArrayInputStream tutorialsToCSV(List<Product> laptopList) {
		final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

		// Để ghi tệp tạo ra đối tượng ByteArrayOutputStream
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();

				// Tạo đối tượng CSVPrinter để in giá trị trong tệp CSV
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			// Lặp lại danh sách các đối tượng và lưu trữ từng giá trị ở dạng Danh Sách
			// chuỗi
			for (Product productTutorial : laptopList) {
				List<Object> data = Arrays.asList(String.valueOf(productTutorial.getProductId()),
						productTutorial.getProductName(), productTutorial.getQuanlity(), productTutorial.getDescription(),
						productTutorial.getPrice(), productTutorial.getImage()

				);

				//sau đó gọi lại method 
				csvPrinter.printRecord(data);
			}

			// chuyển tất cả kí tự hoặc luồng dữ liệu vào tệp CSV
			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
		}
	}
}
