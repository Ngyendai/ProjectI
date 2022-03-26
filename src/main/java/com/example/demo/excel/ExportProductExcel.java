package com.example.demo.excel;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.demo.model.Product;

public class ExportProductExcel {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Product> listproduct;

	public ExportProductExcel(List<Product> listproduct) {
		this.listproduct = listproduct;
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("Products");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "Product ID", style);
		createCell(row, 1, "Product Name", style);
		createCell(row, 2, "Quanlity", style);
		createCell(row, 3, "Price", style);
		createCell(row, 4, "Description", style);
		createCell(row, 5, "Image", style);

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void writeDataLines() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (Product product : listproduct) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, product.getProductId().toString(), style);
			createCell(row, columnCount++, product.getProductName().toString(), style);
			createCell(row, columnCount++, product.getQuanlity(), style);
			createCell(row, columnCount++, product.getPrice().toString(), style);
			createCell(row, columnCount++, product.getDescription().toString(), style);
			createCell(row, columnCount++, product.getImage().toString(), style);

		}
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}
}
