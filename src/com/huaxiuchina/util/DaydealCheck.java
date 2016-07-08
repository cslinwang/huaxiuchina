package com.huaxiuchina.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.huaxiuchina.dao.DaydealDao;
import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.model.Daydeal;
import com.huaxiuchina.model.Gp;

public class DaydealCheck {
	
	public List dayCheck(String url) throws Exception {
		// 从XLSX/ xls文件创建的输入流
		FileInputStream fis = new FileInputStream(url);
		List gpList = new ArrayList();

		// 创建工作薄Workbook
		Workbook workBook = null;

		// 读取2007版，以 .xlsx 结尾
		if (url.toLowerCase().endsWith("xlsx")) {
			try {
				workBook = new XSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 读取2003版，以 .xls 结尾
		else if (url.toLowerCase().endsWith("xls")) {
			try {
				workBook = new HSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Get the number of sheets in the xlsx file
		int numberOfSheets = workBook.getNumberOfSheets();

		// 循环 numberOfSheets
		for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum++) {

			// 得到 工作薄 的第 N个表
			Sheet sheet = workBook.getSheetAt(sheetNum);
			Row row;
			String cell;
			for (int i = sheet.getFirstRowNum(); i < sheet
					.getPhysicalNumberOfRows(); i++) {
				// 循环行数
				row = sheet.getRow(i);
				for (int j = row.getFirstCellNum(); j < row
						.getPhysicalNumberOfCells(); j++) {
					// 循环列数
					cell = row.getCell(j).toString();
					// 排除不需要的列数
					if (j != 0 & j != 7 & j != 8 & j != 9 & j != 10) {
						gpList.add(cell);
					}
					// System.out.println(cell+"\t");
				}
			}
		}
		return gpList;
	}

	public List dayCheck1(String url) throws Exception {
		// 从XLSX/ xls文件创建的输入流
		FileInputStream fis = new FileInputStream(url);
		List gpList = new ArrayList();

		// 创建工作薄Workbook
		Workbook workBook = null;

		// 读取2007版，以 .xlsx 结尾
		if (url.toLowerCase().endsWith("xlsx")) {
			try {
				workBook = new XSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 读取2003版，以 .xls 结尾
		else if (url.toLowerCase().endsWith("xls")) {
			try {
				workBook = new HSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Get the number of sheets in the xlsx file
		int numberOfSheets = workBook.getNumberOfSheets();

		// 循环 numberOfSheets
		for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum++) {

			// 得到 工作薄 的第 N个表
			Sheet sheet = workBook.getSheetAt(sheetNum);
			Row row;
			String cell;
			for (int i = 5; i <= sheet.getPhysicalNumberOfRows(); i++) {
				// 循环行数
				row = sheet.getRow(i);
				for (int j = row.getFirstCellNum(); j < row
						.getPhysicalNumberOfCells(); j++) {
					// 循环列数
					cell = row.getCell(j).toString();
					gpList.add(cell);
					// System.out.println(cell+"\t");
				}
			}
		}
		return gpList;
	}

	public static void main(String[] args) throws Exception {
		List list = new DaydealCheck().dayCheck1("E:/ren.xls");
		 System.out.println(list);
		System.out.println(list.size() / 13);
		// 循环遍历整个数据
		for (int i = 1; i < list.size() / 13; i++) {
			List listTemp = new ArrayList();
			// 循环存入一行数据
			for (int j = i * 13; j < (i + 1) * 13; j++) {
				listTemp.add(list.get(j));
				// 剔除
				// /撤单操作
				if (list.get(i * 13 + 4).equals("撤单")) {
					listTemp.remove(listTemp.get(listTemp.size() - 1));
				}
			}
			// 如果没撤单
			if (listTemp.size() == 13) {
				DaydealDao daydealDao = new DaydealDao();
					// 实体化
					Daydeal daydeal1 = new Daydeal(listTemp.get(2).toString(),
							listTemp.get(3).toString(), listTemp.get(1)
									.toString(), listTemp.get(7).toString(),
							listTemp.get(6).toString(),
							new GetDate().getDate(), null, "cuikui",
							null,null);
					new DaydealDao().add(daydeal1);
					System.out.println("成功写入临时交易记录");				
			} else {
				// 如果撤单无操作

			}
		}
	}
}
/*int buySum = 0;
int saleSum = 0;
int model = 0;
List oldList = new ArrayList();
DaydealDao daydealDao = new DaydealDao();
List list1 = daydealDao.selectByDm(listTemp.get(2).toString(),
		"cuikui");
System.out.println(list1.size());

	int base = Integer.valueOf(daydeal.getBase().toString());
	// 遍历找到上一次股票操作日期，并将结果存入oldList
	String oldDate = daydeal.getDate().toString();
	for (int j = 0; j < 365; j++) {
		oldDate = new GetDate().lastDate(oldDate);
		System.out.println(oldDate);
		oldList = daydealDao.selectByDm(listTemp.get(2)
				.toString(), "cuikui", new GetDate().getDate());
		if (oldList.size() != 0) {
			break;
		}
	}					
	if (oldList.size() == 0) {
		// 如果是新股票
	} else {
		// 遍历获得上次，买入卖出数

		for (int j = 0; j < oldList.size(); j++) {
			Daydeal daydeal2 = (Daydeal) oldList.get(j);
			if (daydeal2.getMmbz().toString().equals("买入")) {
				buySum += Integer.valueOf(daydeal2.getCjsl()
						.toString());
			} else {
				saleSum += Integer.valueOf(daydeal2.getCjsl()
						.toString());
			}
		}
		System.out.println("buySum: " + buySum);
		System.out.println("saleSum: " + saleSum);
		if (daydeal.getModel() < 10) {
			// 如果是模型一
			// 拿到系数
			int tecent = daydeal.getModel();
			int actual = Integer.valueOf(daydeal.getCjsl()
					.toString());
			// 如果建仓成功，系数加一
			if (actual == base * tecent) {
				model += 1;
			} else if (base * tecent - actual > base
					* (tecent - 1))
				model = model;

		} else {
			// 如果是模型二
		}
	}*/