package com.huaxiuchina.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.Region;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.model.Daydeal;
import com.huaxiuchina.model.Gp;

/**
 * 
 * @author Snow 简单的写入 excel HSSF实现 excel 2003(以 .xls 结尾的文件) XSSF实现 excel 2007(以
 *         .xlsx 结尾的文件)
 */
public class GuideOut {
	public ByteArrayInputStream onlyBuy(List onlyBuyList,String name) throws Exception {
		System.out.println("开始生成");
		// 创建工作薄
		XSSFWorkbook workBook = new XSSFWorkbook();
		//设置字体格式
		XSSFFont font = workBook.createFont(); 
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		XSSFCellStyle cellStyle= workBook.createCellStyle(); 
		cellStyle.setFont(font); 
		// 在工作薄中创建一工作表
		XSSFSheet sheet = workBook.createSheet();
		// 建立表头
		XSSFRow a1 = sheet.createRow(0);
		//sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));
		
		XSSFCell b1 = a1.createCell(0);
		b1.setCellStyle(cellStyle); 
		b1.setCellType(XSSFCell.CELL_TYPE_STRING);
		b1.setCellValue(new XSSFRichTextString("账户"));
		
		XSSFCell b2 = a1.createCell(1);
		b2.setCellStyle(cellStyle); 
		b2.setCellType(XSSFCell.CELL_TYPE_STRING);
		b2.setCellValue(new XSSFRichTextString(name));
		
		XSSFCell b3 = a1.createCell(2);
		b3.setCellStyle(cellStyle); 
		b3.setCellType(XSSFCell.CELL_TYPE_STRING);
		b3.setCellValue(new XSSFRichTextString("报告日期"));
		
		XSSFCell b4 = a1.createCell(3);
		b4.setCellStyle(cellStyle); 
		b4.setCellType(XSSFCell.CELL_TYPE_STRING);
		b4.setCellValue(new XSSFRichTextString(new GetDate().getDate()));

		XSSFRow a2 = sheet.createRow(1);
		
		XSSFCell b5 = a2.createCell(0);
		b5.setCellStyle(cellStyle); 
		b5.setCellType(XSSFCell.CELL_TYPE_STRING);
		b5.setCellValue(new XSSFRichTextString("交易指导"));
		
		// 在指定的索引处创建一行
		XSSFRow row = sheet.createRow(2);
		// 在指定的索引处创建 一列（单元格）
		XSSFCell dm = row.createCell(0);
		// 定义单元格为字符串类型
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// 在单元格输入内容
		dm.setCellStyle(cellStyle);
		dm.setCellValue(new XSSFRichTextString("股票代码"));
		XSSFCell mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellStyle(cellStyle);
		mrj.setCellValue(new XSSFRichTextString("股票名称"));
		XSSFCell mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellStyle(cellStyle);
		mcj.setCellValue(new XSSFRichTextString("买入价格"));
		XSSFCell sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellStyle(cellStyle);
		sshy.setCellValue(new XSSFRichTextString("明日跌停价"));
		XSSFCell zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellStyle(cellStyle);
		zg.setCellValue(new XSSFRichTextString("建仓数量"));
		XSSFCell cell = row.createCell(5);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("预计成交额"));
		cell = row.createCell(6);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("卖出价格"));
		cell = row.createCell(7);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("明日涨停价"));
		cell = row.createCell(8);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("建议卖出数量"));
		cell = row.createCell(9);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("预计成交金额"));
		
		
		// 写入数据
		for (int i = 0; i < onlyBuyList.size(); i++) {

			XSSFRow tpr = sheet.createRow(i + 3);
			cell= tpr.createCell(0);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell= tpr.createCell(1);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell= tpr.createCell(2);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(3);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell= tpr.createCell(4);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(5);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(6);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(7);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(8);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(9);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
		}
		// 新建一输出流并把相应的excel文件存盘

		 ByteArrayOutputStream os = new ByteArrayOutputStream();
         workBook.write(os);
         byte[] fileContent = os.toByteArray();
         ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
         return is;
	}

	public ByteArrayInputStream onlySell(List onlyBuyList, String name)
			throws Exception {
		System.out.println("开始生成");
		// 创建工作薄
		XSSFWorkbook workBook = new XSSFWorkbook();
		//设置字体格式
		XSSFFont font = workBook.createFont(); 
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		XSSFCellStyle cellStyle= workBook.createCellStyle(); 
		cellStyle.setFont(font); 
		// 在工作薄中创建一工作表
		XSSFSheet sheet = workBook.createSheet();
		// 建立表头
		XSSFRow a1 = sheet.createRow(0);
		//sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));
		
		XSSFCell b1 = a1.createCell(0);
		b1.setCellStyle(cellStyle); 
		b1.setCellType(XSSFCell.CELL_TYPE_STRING);
		b1.setCellValue(new XSSFRichTextString("账户"));
		
		XSSFCell b2 = a1.createCell(1);
		b2.setCellStyle(cellStyle); 
		b2.setCellType(XSSFCell.CELL_TYPE_STRING);
		b2.setCellValue(new XSSFRichTextString(name));
		
		XSSFCell b3 = a1.createCell(2);
		b3.setCellStyle(cellStyle); 
		b3.setCellType(XSSFCell.CELL_TYPE_STRING);
		b3.setCellValue(new XSSFRichTextString("报告日期"));
		
		XSSFCell b4 = a1.createCell(3);
		b4.setCellStyle(cellStyle); 
		b4.setCellType(XSSFCell.CELL_TYPE_STRING);
		b4.setCellValue(new XSSFRichTextString(new GetDate().getDate()));

		XSSFRow a2 = sheet.createRow(1);
		
		XSSFCell b5 = a2.createCell(0);
		b5.setCellStyle(cellStyle); 
		b5.setCellType(XSSFCell.CELL_TYPE_STRING);
		b5.setCellValue(new XSSFRichTextString("交易指导"));
		
		// 在指定的索引处创建一行
		XSSFRow row = sheet.createRow(2);
		// 在指定的索引处创建 一列（单元格）
		XSSFCell dm = row.createCell(0);
		// 定义单元格为字符串类型
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// 在单元格输入内容
		dm.setCellStyle(cellStyle);
		dm.setCellValue(new XSSFRichTextString("股票代码"));
		XSSFCell mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellStyle(cellStyle);
		mrj.setCellValue(new XSSFRichTextString("股票名称"));
		XSSFCell mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellStyle(cellStyle);
		mcj.setCellValue(new XSSFRichTextString("买入价格"));
		XSSFCell sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellStyle(cellStyle);
		sshy.setCellValue(new XSSFRichTextString("明日跌停价"));
		XSSFCell zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellStyle(cellStyle);
		zg.setCellValue(new XSSFRichTextString("建仓数量"));
		XSSFCell cell = row.createCell(5);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("预计成交额"));
		cell = row.createCell(6);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("卖出价格"));
		cell = row.createCell(7);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("明日涨停价"));
		cell = row.createCell(8);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("建议卖出数量"));
		cell = row.createCell(9);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("预计成交金额"));
		
		
		// 写入数据
		for (int i = 0; i < onlyBuyList.size(); i++) {

			XSSFRow tpr = sheet.createRow(i + 3);
			cell= tpr.createCell(0);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell= tpr.createCell(1);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell= tpr.createCell(2);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(3);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell= tpr.createCell(4);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(5);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(6);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(7);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(8);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(9);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
		}
		// 新建一输出流并把相应的excel文件存盘

		 ByteArrayOutputStream os = new ByteArrayOutputStream();
         workBook.write(os);
         byte[] fileContent = os.toByteArray();
         ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
         return is;
	}
	public ByteArrayInputStream both(List onlyBuyList,String name) throws Exception {
		System.out.println("开始生成");
		// 创建工作薄
		XSSFWorkbook workBook = new XSSFWorkbook();
		//设置字体格式
		XSSFFont font = workBook.createFont(); 
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		XSSFCellStyle cellStyle= workBook.createCellStyle(); 
		cellStyle.setFont(font); 
		// 在工作薄中创建一工作表
		XSSFSheet sheet = workBook.createSheet();
		// 建立表头
		XSSFRow a1 = sheet.createRow(0);
		//sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));
		
		XSSFCell b1 = a1.createCell(0);
		b1.setCellStyle(cellStyle); 
		b1.setCellType(XSSFCell.CELL_TYPE_STRING);
		b1.setCellValue(new XSSFRichTextString("账户"));
		
		XSSFCell b2 = a1.createCell(1);
		b2.setCellStyle(cellStyle); 
		b2.setCellType(XSSFCell.CELL_TYPE_STRING);
		b2.setCellValue(new XSSFRichTextString(name));
		
		XSSFCell b3 = a1.createCell(2);
		b3.setCellStyle(cellStyle); 
		b3.setCellType(XSSFCell.CELL_TYPE_STRING);
		b3.setCellValue(new XSSFRichTextString("报告日期"));
		
		XSSFCell b4 = a1.createCell(3);
		b4.setCellStyle(cellStyle); 
		b4.setCellType(XSSFCell.CELL_TYPE_STRING);
		b4.setCellValue(new XSSFRichTextString(new GetDate().getDate()));

		XSSFRow a2 = sheet.createRow(1);
		
		XSSFCell b5 = a2.createCell(0);
		b5.setCellStyle(cellStyle); 
		b5.setCellType(XSSFCell.CELL_TYPE_STRING);
		b5.setCellValue(new XSSFRichTextString("交易指导"));
		
		// 在指定的索引处创建一行
		XSSFRow row = sheet.createRow(2);
		// 在指定的索引处创建 一列（单元格）
		XSSFCell dm = row.createCell(0);
		// 定义单元格为字符串类型
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// 在单元格输入内容
		dm.setCellStyle(cellStyle);
		dm.setCellValue(new XSSFRichTextString("股票代码"));
		XSSFCell mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellStyle(cellStyle);
		mrj.setCellValue(new XSSFRichTextString("股票名称"));
		XSSFCell mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellStyle(cellStyle);
		mcj.setCellValue(new XSSFRichTextString("买入价格"));
		XSSFCell sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellStyle(cellStyle);
		sshy.setCellValue(new XSSFRichTextString("明日跌停价"));
		XSSFCell zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellStyle(cellStyle);
		zg.setCellValue(new XSSFRichTextString("建仓数量"));
		XSSFCell cell = row.createCell(5);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("预计成交额"));
		cell = row.createCell(6);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("卖出价格"));
		cell = row.createCell(7);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("明日涨停价"));
		cell = row.createCell(8);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("建议卖出数量"));
		cell = row.createCell(9);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("预计成交金额"));
		
		
		// 写入数据
		int j =3;
		for (int i = 0; i < onlyBuyList.size(); i++) {

			XSSFRow tpr = sheet.createRow(j);
			cell= tpr.createCell(0);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell= tpr.createCell(1);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell= tpr.createCell(2);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(3);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell= tpr.createCell(4);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(5);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(6);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(7);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(8);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(9);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			j++;
		}
		// 新建一输出流并把相应的excel文件存盘

		 ByteArrayOutputStream os = new ByteArrayOutputStream();
         workBook.write(os);
         byte[] fileContent = os.toByteArray();
         ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
         return is;
	}
}