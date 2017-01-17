package com.huaxiuchina.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.huaxiuchina.dao.TDao;
import com.huaxiuchina.model.T;

public class TOut {
	public ByteArrayInputStream tOut(String name) throws Exception {
		System.out.println("开始生成");

		// 创建工作薄
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 设置字体格式
		XSSFFont font = workBook.createFont();
		font.setBoldweight(HSSFFont.ANSI_CHARSET);
		font.setFontName("宋体");

		XSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中

		XSSFCellStyle cellStyle1 = workBook.createCellStyle();
		cellStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		// 在工作薄中创建一工作表
		XSSFSheet sheet = workBook.createSheet();
		int hangshu = 0;
		// 建立表头
		int width1 = 3000;
		sheet.setColumnWidth((short) 0, (short) 2500);
		sheet.setColumnWidth((short) 1, (short) 2500);
		sheet.setColumnWidth((short) 2, (short) 2500);
		sheet.setColumnWidth((short) 3, (short) 2950);
		sheet.setColumnWidth((short) 4, (short) 2500);
		sheet.setColumnWidth((short) 5, (short) 2950);

		XSSFRow row = sheet.createRow(hangshu++);
		// sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));
		CellRangeAddress region1 = new CellRangeAddress(0, (short) 0, 0,
				(short) 6);
		sheet.addMergedRegion(region1);

		XSSFCell cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(new XSSFRichTextString("账户盈利明细"));

		row = sheet.createRow(hangshu++);

		cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("账户"));
		XSSFCell b2 = row.createCell(1);
		b2.setCellStyle(cellStyle);
		b2.setCellType(XSSFCell.CELL_TYPE_STRING);
		b2.setCellValue(new XSSFRichTextString(name));

		CellRangeAddress region2 = new CellRangeAddress(1, (short) 1, 2,
				(short) 4);
		sheet.addMergedRegion(region2);
		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(new XSSFRichTextString(""));

		XSSFCell b3 = row.createCell(3);
		b3.setCellStyle(cellStyle);
		b3.setCellType(XSSFCell.CELL_TYPE_STRING);
		b3.setCellValue(new XSSFRichTextString("生成日期"));

		XSSFCell b4 = row.createCell(4);
		b4.setCellStyle(cellStyle);
		b4.setCellType(XSSFCell.CELL_TYPE_STRING);
		b4.setCellValue(new XSSFRichTextString(new GetDate().getDate()));

		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("指导日期"));

		cell = row.createCell(6);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString(new GetDate().getDate()));

		row = sheet.createRow(hangshu++);

		cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("股票代码"));

		cell = row.createCell(1);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("股票名称"));

		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("交易方向"));

		cell = row.createCell(3);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("成交价格"));

		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("成交数量"));

		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("成交金额"));

		cell = row.createCell(6);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("收益"));

		// 数据库拿交易数据
		CellRangeAddress region3;
		// k 合并列数
		int k = 3;
		ArrayList<T> tOut = (ArrayList<T>) new TDao().selectByNameAndDate(name,
				new GetDate().getDate());
		for (int i = 0; i < tOut.size(); i++) {
			row = sheet.createRow(hangshu++);

			region3 = new CellRangeAddress(k, (short) k + 1, 0, (short) 0);
			sheet.addMergedRegion(region3);
			cell = row.createCell(0);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new XSSFRichTextString(tOut.get(i).getDm()));

			region3 = new CellRangeAddress(k, (short) k + 1, 1, (short) 1);
			sheet.addMergedRegion(region3);
			cell = row.createCell(1);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new XSSFRichTextString(tOut.get(i).getMc()));

			cell = row.createCell(2);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new XSSFRichTextString(tOut.get(i).getFc()));

			cell = row.createCell(3);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new XSSFRichTextString(tOut.get(i).getJg()));

			cell = row.createCell(4);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new XSSFRichTextString(tOut.get(i).getSl()));

			cell = row.createCell(5);
			cell.setCellStyle(cellStyle);
			int sum = Integer.valueOf(tOut.get(i).getJg())
					* Integer.valueOf(tOut.get(i).getSl());
			cell.setCellValue(new XSSFRichTextString(String.valueOf(sum)));

			region3 = new CellRangeAddress(k, (short) k + 1, 6, (short) 6);
			sheet.addMergedRegion(region3);
			cell = row.createCell(6);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(new XSSFRichTextString(tOut.get(i).getSy()));
			k += 2;
		}
		// 新建一输出流并把相应的excel文件存盘
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workBook.write(os);
		byte[] fileContent = os.toByteArray();
		ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
		return is;
	}
}
