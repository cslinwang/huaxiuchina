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
import com.huaxiuchina.model.Gp;

/**
 * 
 * @author Snow 简单的写入 excel HSSF实现 excel 2003(以 .xls 结尾的文件) XSSF实现 excel 2007(以
 *         .xlsx 结尾的文件)
 */
public class XLSWriter {
	public void XLSWriter(List gpList) throws IOException {
		System.out.println("开始生成");
		// 创建工作薄
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在工作薄中创建一工作表
		XSSFSheet sheet = workBook.createSheet();
		// 建立表头
		// 在指定的索引处创建一行
		XSSFRow row = sheet.createRow(0);
		// 在指定的索引处创建一列（单元格）
		XSSFCell dm = row.createCell(0);
		// 定义单元格为字符串类型
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// 在单元格输入内容
		dm.setCellValue(new XSSFRichTextString("代码"));
		XSSFCell mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellValue(new XSSFRichTextString("买入价"));
		XSSFCell mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellValue(new XSSFRichTextString("卖出价"));
		XSSFCell sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellValue(new XSSFRichTextString("所属行业"));
		XSSFCell zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellValue(new XSSFRichTextString("最高"));
		XSSFCell zd1 = row.createCell(5);
		zd1.setCellType(XSSFCell.CELL_TYPE_STRING);
		zd1.setCellValue(new XSSFRichTextString("最低"));
		XSSFCell kp = row.createCell(6);
		kp.setCellType(XSSFCell.CELL_TYPE_STRING);
		kp.setCellValue(new XSSFRichTextString("开盘"));
		XSSFCell zs1 = row.createCell(7);
		zs1.setCellType(XSSFCell.CELL_TYPE_STRING);
		zs1.setCellValue(new XSSFRichTextString("昨收"));
		// 写入数据
		for (int i = 0; i < gpList.size(); i++) {
			Gp gp = (Gp) gpList.get(i);
			XSSFRow tpr = sheet.createRow(i+1);
			XSSFCell tp1 = tpr.createCell(0);
			tp1.setCellType(XSSFCell.CELL_TYPE_STRING);
			tp1.setCellValue(new XSSFRichTextString(gp.getDm()));
			XSSFCell tp2 = tpr.createCell(1);
			tp2.setCellType(XSSFCell.CELL_TYPE_STRING);
			tp2.setCellValue(new XSSFRichTextString(gp.getMrj()));
			XSSFCell tp3 = tpr.createCell(2);
			tp3.setCellType(XSSFCell.CELL_TYPE_STRING);
			tp3.setCellValue(new XSSFRichTextString(gp.getMcj()));
			XSSFCell tp4 = tpr.createCell(3);
			tp4.setCellType(XSSFCell.CELL_TYPE_STRING);
			tp4.setCellValue(new XSSFRichTextString(gp.getSshy()));
			XSSFCell tp5 = tpr.createCell(4);
			tp5.setCellType(XSSFCell.CELL_TYPE_STRING);
			tp5.setCellValue(new XSSFRichTextString(gp.getZg()));
			XSSFCell tp6 = tpr.createCell(5);
			tp6.setCellType(XSSFCell.CELL_TYPE_STRING);
			tp6.setCellValue(new XSSFRichTextString(gp.getZd1()));
			XSSFCell tp7 = tpr.createCell(6);
			tp7.setCellType(XSSFCell.CELL_TYPE_STRING);
			tp7.setCellValue(new XSSFRichTextString(gp.getKp()));
			XSSFCell tp8 = tpr.createCell(7);
			tp8.setCellType(XSSFCell.CELL_TYPE_STRING);
			tp8.setCellValue(new XSSFRichTextString(gp.getZs1()));
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