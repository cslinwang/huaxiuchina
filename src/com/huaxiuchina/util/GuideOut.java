package com.huaxiuchina.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
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
	public void onlyBuy(List onlyBuyList) throws IOException {
		System.out.println("开始生成");
		// 创建工作薄
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在工作薄中创建一工作表
		XSSFSheet sheet = workBook.createSheet();
		// 建立表头
		// 在指定的索引处创建一行
		XSSFRow row = sheet.createRow(0);
		// 在指定的索引处创建 一列（单元格）
		XSSFCell dm = row.createCell(0);
		// 定义单元格为字符串类型
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// 在单元格输入内容
		dm.setCellValue(new XSSFRichTextString("股票代码"));
		XSSFCell mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellValue(new XSSFRichTextString("股票名称"));
		XSSFCell mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellValue(new XSSFRichTextString("买入价"));
		XSSFCell sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellValue(new XSSFRichTextString("买入数"));
		XSSFCell zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellValue(new XSSFRichTextString("卖出价"));
		XSSFCell zd1 = row.createCell(5);
		zd1.setCellType(XSSFCell.CELL_TYPE_STRING);
		zd1.setCellValue(new XSSFRichTextString("卖出数"));				
		// 写入数据
		for (int i = 0; i < onlyBuyList.size(); i++) {
			
			onlyBuyList.get(i);
			XSSFRow tpr = sheet.createRow(i+1);
			XSSFCell tp1 = tpr.createCell(0);
			tp1.setCellType(XSSFCell.CELL_TYPE_STRING);
			tp1.setCellValue(new XSSFRichTextString(onlyBuyList.get(i).toString()));
			i++;
			XSSFCell tp2 = tpr.createCell(1);
			tp2.setCellType(XSSFCell.CELL_TYPE_STRING);
			tp2.setCellValue(new XSSFRichTextString(onlyBuyList.get(i).toString()));
			i++;
			XSSFCell tp3 = tpr.createCell(2);
			tp3.setCellType(XSSFCell.CELL_TYPE_STRING);
			tp3.setCellValue(new XSSFRichTextString(onlyBuyList.get(i).toString()));
			i++;
			XSSFCell tp4 = tpr.createCell(3);
			tp4.setCellType(XSSFCell.CELL_TYPE_STRING);
			tp4.setCellValue(new XSSFRichTextString(onlyBuyList.get(i).toString()));
			i++;
			XSSFCell tp5 = tpr.createCell(4);
			tp5.setCellType(XSSFCell.CELL_TYPE_STRING);
			tp5.setCellValue(new XSSFRichTextString(onlyBuyList.get(i).toString()));
			i++;
			XSSFCell tp6 = tpr.createCell(5);
			tp6.setCellType(XSSFCell.CELL_TYPE_STRING);
			tp6.setCellValue(new XSSFRichTextString(onlyBuyList.get(i).toString()));
		}
		// 新建一输出流并把相应的excel文件存盘
		FileOutputStream fos = new FileOutputStream(
				"C:/Users/wanglin/Desktop/hos.xlsx");
		workBook.write(fos);
		fos.flush();
		// 操作结束，关闭流
		fos.close();
		System.out.println("文件生成");
	}
}