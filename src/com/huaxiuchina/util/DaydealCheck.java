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

	// 第一种表读取
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
		System.out.println(gpList);
		return gpList;
	}

	// 第一种类型表存储
	public void daydealTemp1(String filename, String name) throws Exception {
		List list = new DaydealCheck().dayCheck1(filename);
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
						listTemp.get(3).toString(), listTemp.get(1).toString(),
						listTemp.get(7).toString(), listTemp.get(6).toString(),
						new GetDate().getDate(), null, name, null, null);
				new DaydealDao().add(daydeal1);
				System.out.println("成功写入临时交易记录");
			} else {
				// 如果撤单无操作
			}
		}
		new DayDealInCheck().dayDealInCheck(name);
	}

	// 第二种表读取
	public List dayCheck2(String url) throws Exception {
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
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
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
		System.out.println(gpList);
		return gpList;
	}

	// 第二种类型表存储
	public void daydealTemp2(String filename, String name) throws Exception {
		List list = new DaydealCheck().dayCheck2(filename);
		System.out.println(list.size() / 7);
		// 循环遍历整个数据
		for (int i = 1; i < list.size() / 7; i++) {
			List listTemp = new ArrayList();
			// 循环存入一行数据
			for (int j = i * 7; j < (i + 1) * 7; j++) {
				listTemp.add(list.get(j));
				// 剔除
				// /撤单操作
				/*
				 * if (list.get(i * 7 + 4).equals("撤单")) {
				 * listTemp.remove(listTemp.get(listTemp.size() - 1)); }
				 */
			}
			// 如果没撤单
			if (listTemp.size() == 7) {
				DaydealDao daydealDao = new DaydealDao();
				// 实体化
				System.out.println("股票代码： " + listTemp.get(1).toString());
				Daydeal daydeal1 = new Daydeal(listTemp.get(1).toString(),
						listTemp.get(2).toString(), listTemp.get(3).toString(),
						String.valueOf(Double.parseDouble(listTemp.get(4)
								.toString())), String.valueOf((int) Double
								.parseDouble(listTemp.get(5).toString())),
						new GetDate().getDate(), null, name, null, null);
				new DaydealDao().add(daydeal1);

				System.out.println("成功写入临时交易记录");
			} else {
				// 如果撤单无操作

			}
		}
		// 修正
		new DayDealInCheck().dayDealInCheck(name);
	}

	// 第三种表读取
	public List dayCheck3(String url) throws Exception {
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
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
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
		System.out.println(gpList);
		return gpList;
	}

	// 第三种类型表存储
	public void daydealTemp3(String filename, String name) throws Exception {
		List list = new DaydealCheck().dayCheck3(filename);
		System.out.println(list.size() / 11);
		// 循环遍历整个数据
		for (int i = 1; i < list.size() / 11; i++) {
			List listTemp = new ArrayList();
			// 循环存入一行数据
			for (int j = i * 11; j < (i + 1) * 11; j++) {
				listTemp.add(list.get(j));
				// 剔除
				// /撤单操作
				/*
				 * if (list.get(i * 7 + 4).equals("撤单")) {
				 * listTemp.remove(listTemp.get(listTemp.size() - 1)); }
				 */
			}
			// 如果没撤单
			if (listTemp.size() == 11) {
				DaydealDao daydealDao = new DaydealDao();
				// 实体化
				System.out.println("股票代码： " + listTemp.get(1).toString());
				Daydeal daydeal1 = new Daydeal(listTemp.get(1).toString(),
						listTemp.get(2).toString(), listTemp.get(3).toString(),
						String.valueOf((int) Double.parseDouble(listTemp.get(5)
								.toString())), String.valueOf((int) Double
								.parseDouble(listTemp.get(6).toString())),
						new GetDate().getDate(), null, name, null, null);
				new DaydealDao().add(daydeal1);

				System.out.println("成功写入临时交易记录");
			} else {
				// 如果撤单无操作

			}
			// 修正
			new DayDealInCheck().dayDealInCheck(name);
		}
	}

	// 第四种表读取
	public List dayCheck4(String url) throws Exception {
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
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
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
		System.out.println(gpList);
		return gpList;
	}

	// 第四种类型表存储
	public void daydealTemp4(String filename, String name) throws Exception {
		List list = new DaydealCheck().dayCheck4(filename);
		System.out.println(list.size() / 9);
		// 循环遍历整个数据
		for (int i = 1; i < list.size() / 9; i++) {
			List listTemp = new ArrayList();
			// 循环存入一行数据
			for (int j = i * 9; j < (i + 1) * 9; j++) {
				listTemp.add(list.get(j));
				// 剔除
				// /撤单操作
				/*
				 * if (list.get(i * 7 + 4).equals("撤单")) {
				 * listTemp.remove(listTemp.get(listTemp.size() - 1)); }
				 */
			}
			// 如果没撤单
			if (listTemp.size() == 9) {
				DaydealDao daydealDao = new DaydealDao();
				// 实体化
				Daydeal daydeal1 = new Daydeal(
						(listTemp.get(2).toString()).substring(1,
								(listTemp.get(2).toString()).length() - 1),
						(listTemp.get(3).toString()).substring(1,
								(listTemp.get(3).toString()).length() - 1),
						(listTemp.get(4).toString()).substring(1,
								(listTemp.get(4).toString()).length() - 1),
						String.valueOf((int) Double.parseDouble(listTemp.get(5)
								.toString())), String.valueOf((int) Double
								.parseDouble(listTemp.get(6).toString())),
						new GetDate().getDate(), null, name, null, null);
				new DaydealDao().add(daydeal1);

				System.out.println("成功写入临时交易记录");
			} else {
				// 如果撤单无操作

			}
			// 修正
			new DayDealInCheck().dayDealInCheck(name);
		}
	}

	// 判断是什么类型账户
	public void isType(String filename, String model, String name)
			throws Exception {
		DaydealCheck dc = new DaydealCheck();
		if (model.equals("1")) {
			dc.daydealTemp1(filename, name);
		} else if (model.equals("2")) {
			dc.daydealTemp2(filename, name);
		} else if (model.equals("3")) {
			dc.daydealTemp3(filename, name);
		} else if (model.equals("4")) {
			dc.daydealTemp4(filename, name);
		}
	}

	public static void main(String[] args) throws Exception {
		new DaydealCheck().isType("F:/每日成交记录模板1.1.xlsx", "2", "root");
		String a = "\"2\"";
		System.out.println(a);
		System.out.println(a.substring(1, a.length() - 1));
	}
}