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

	// ��һ�ֱ��ȡ
	public List dayCheck1(String url) throws Exception {
		// ��XLSX/ xls�ļ�������������
		FileInputStream fis = new FileInputStream(url);
		List gpList = new ArrayList();

		// ����������Workbook
		Workbook workBook = null;

		// ��ȡ2007�棬�� .xlsx ��β
		if (url.toLowerCase().endsWith("xlsx")) {
			try {
				workBook = new XSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// ��ȡ2003�棬�� .xls ��β
		else if (url.toLowerCase().endsWith("xls")) {
			try {
				workBook = new HSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Get the number of sheets in the xlsx file
		int numberOfSheets = workBook.getNumberOfSheets();

		// ѭ�� numberOfSheets
		for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum++) {

			// �õ� ������ �ĵ� N����
			Sheet sheet = workBook.getSheetAt(sheetNum);
			Row row;
			String cell;
			for (int i = 5; i <= sheet.getPhysicalNumberOfRows(); i++) {
				// ѭ������
				row = sheet.getRow(i);
				for (int j = row.getFirstCellNum(); j < row
						.getPhysicalNumberOfCells(); j++) {
					// ѭ������
					cell = row.getCell(j).toString();
					gpList.add(cell);
					// System.out.println(cell+"\t");
				}
			}
		}
		System.out.println(gpList);
		return gpList;
	}

	// ��һ�����ͱ�洢
	public void daydealTemp1(String filename, String name) throws Exception {
		List list = new DaydealCheck().dayCheck1(filename);
		System.out.println(list.size() / 13);
		// ѭ��������������
		for (int i = 1; i < list.size() / 13; i++) {
			List listTemp = new ArrayList();
			// ѭ������һ������
			for (int j = i * 13; j < (i + 1) * 13; j++) {
				listTemp.add(list.get(j));
				// �޳�
				// /��������
				if (list.get(i * 13 + 4).equals("����")) {
					listTemp.remove(listTemp.get(listTemp.size() - 1));
				}
			}
			// ���û����
			if (listTemp.size() == 13) {
				DaydealDao daydealDao = new DaydealDao();
				// ʵ�廯
				Daydeal daydeal1 = new Daydeal(listTemp.get(2).toString(),
						listTemp.get(3).toString(), listTemp.get(1).toString(),
						listTemp.get(7).toString(), listTemp.get(6).toString(),
						new GetDate().getDate(), null, name, null, null);
				new DaydealDao().add(daydeal1);
				System.out.println("�ɹ�д����ʱ���׼�¼");
			} else {
				// ��������޲���
			}
		}
		new DayDealInCheck().dayDealInCheck(name);
	}

	// �ڶ��ֱ��ȡ
	public List dayCheck2(String url) throws Exception {
		// ��XLSX/ xls�ļ�������������
		FileInputStream fis = new FileInputStream(url);
		List gpList = new ArrayList();

		// ����������Workbook
		Workbook workBook = null;

		// ��ȡ2007�棬�� .xlsx ��β
		if (url.toLowerCase().endsWith("xlsx")) {
			try {
				workBook = new XSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// ��ȡ2003�棬�� .xls ��β
		else if (url.toLowerCase().endsWith("xls")) {
			try {
				workBook = new HSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Get the number of sheets in the xlsx file
		int numberOfSheets = workBook.getNumberOfSheets();

		// ѭ�� numberOfSheets
		for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum++) {

			// �õ� ������ �ĵ� N����
			Sheet sheet = workBook.getSheetAt(sheetNum);
			Row row;
			String cell;
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				// ѭ������
				row = sheet.getRow(i);
				for (int j = row.getFirstCellNum(); j < row
						.getPhysicalNumberOfCells(); j++) {
					// ѭ������
					cell = row.getCell(j).toString();
					gpList.add(cell);
					// System.out.println(cell+"\t");
				}
			}
		}
		System.out.println(gpList);
		return gpList;
	}

	// �ڶ������ͱ�洢
	public void daydealTemp2(String filename, String name) throws Exception {
		List list = new DaydealCheck().dayCheck2(filename);
		System.out.println(list.size() / 7);
		// ѭ��������������
		for (int i = 1; i < list.size() / 7; i++) {
			List listTemp = new ArrayList();
			// ѭ������һ������
			for (int j = i * 7; j < (i + 1) * 7; j++) {
				listTemp.add(list.get(j));
				// �޳�
				// /��������
				/*
				 * if (list.get(i * 7 + 4).equals("����")) {
				 * listTemp.remove(listTemp.get(listTemp.size() - 1)); }
				 */
			}
			// ���û����
			if (listTemp.size() == 7) {
				DaydealDao daydealDao = new DaydealDao();
				// ʵ�廯
				System.out.println("��Ʊ���룺 " + listTemp.get(1).toString());
				Daydeal daydeal1 = new Daydeal(listTemp.get(1).toString(),
						listTemp.get(2).toString(), listTemp.get(3).toString(),
						String.valueOf(Double.parseDouble(listTemp.get(4)
								.toString())), String.valueOf((int) Double
								.parseDouble(listTemp.get(5).toString())),
						new GetDate().getDate(), null, name, null, null);
				new DaydealDao().add(daydeal1);

				System.out.println("�ɹ�д����ʱ���׼�¼");
			} else {
				// ��������޲���

			}
		}
		// ����
		new DayDealInCheck().dayDealInCheck(name);
	}

	// �����ֱ��ȡ
	public List dayCheck3(String url) throws Exception {
		// ��XLSX/ xls�ļ�������������
		FileInputStream fis = new FileInputStream(url);
		List gpList = new ArrayList();

		// ����������Workbook
		Workbook workBook = null;

		// ��ȡ2007�棬�� .xlsx ��β
		if (url.toLowerCase().endsWith("xlsx")) {
			try {
				workBook = new XSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// ��ȡ2003�棬�� .xls ��β
		else if (url.toLowerCase().endsWith("xls")) {
			try {
				workBook = new HSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Get the number of sheets in the xlsx file
		int numberOfSheets = workBook.getNumberOfSheets();

		// ѭ�� numberOfSheets
		for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum++) {

			// �õ� ������ �ĵ� N����
			Sheet sheet = workBook.getSheetAt(sheetNum);
			Row row;
			String cell;
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				// ѭ������
				row = sheet.getRow(i);
				for (int j = row.getFirstCellNum(); j < row
						.getPhysicalNumberOfCells(); j++) {
					// ѭ������
					cell = row.getCell(j).toString();
					gpList.add(cell);
					// System.out.println(cell+"\t");
				}
			}
		}
		System.out.println(gpList);
		return gpList;
	}

	// ���������ͱ�洢
	public void daydealTemp3(String filename, String name) throws Exception {
		List list = new DaydealCheck().dayCheck3(filename);
		System.out.println(list.size() / 11);
		// ѭ��������������
		for (int i = 1; i < list.size() / 11; i++) {
			List listTemp = new ArrayList();
			// ѭ������һ������
			for (int j = i * 11; j < (i + 1) * 11; j++) {
				listTemp.add(list.get(j));
				// �޳�
				// /��������
				/*
				 * if (list.get(i * 7 + 4).equals("����")) {
				 * listTemp.remove(listTemp.get(listTemp.size() - 1)); }
				 */
			}
			// ���û����
			if (listTemp.size() == 11) {
				DaydealDao daydealDao = new DaydealDao();
				// ʵ�廯
				System.out.println("��Ʊ���룺 " + listTemp.get(1).toString());
				Daydeal daydeal1 = new Daydeal(listTemp.get(1).toString(),
						listTemp.get(2).toString(), listTemp.get(3).toString(),
						String.valueOf((int) Double.parseDouble(listTemp.get(5)
								.toString())), String.valueOf((int) Double
								.parseDouble(listTemp.get(6).toString())),
						new GetDate().getDate(), null, name, null, null);
				new DaydealDao().add(daydeal1);

				System.out.println("�ɹ�д����ʱ���׼�¼");
			} else {
				// ��������޲���

			}
			// ����
			new DayDealInCheck().dayDealInCheck(name);
		}
	}

	// �����ֱ��ȡ
	public List dayCheck4(String url) throws Exception {
		// ��XLSX/ xls�ļ�������������
		FileInputStream fis = new FileInputStream(url);
		List gpList = new ArrayList();

		// ����������Workbook
		Workbook workBook = null;

		// ��ȡ2007�棬�� .xlsx ��β
		if (url.toLowerCase().endsWith("xlsx")) {
			try {
				workBook = new XSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// ��ȡ2003�棬�� .xls ��β
		else if (url.toLowerCase().endsWith("xls")) {
			try {
				workBook = new HSSFWorkbook(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Get the number of sheets in the xlsx file
		int numberOfSheets = workBook.getNumberOfSheets();

		// ѭ�� numberOfSheets
		for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum++) {

			// �õ� ������ �ĵ� N����
			Sheet sheet = workBook.getSheetAt(sheetNum);
			Row row;
			String cell;
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				// ѭ������
				row = sheet.getRow(i);
				for (int j = row.getFirstCellNum(); j < row
						.getPhysicalNumberOfCells(); j++) {
					// ѭ������
					cell = row.getCell(j).toString();
					gpList.add(cell);
					// System.out.println(cell+"\t");
				}
			}
		}
		System.out.println(gpList);
		return gpList;
	}

	// ���������ͱ�洢
	public void daydealTemp4(String filename, String name) throws Exception {
		List list = new DaydealCheck().dayCheck4(filename);
		System.out.println(list.size() / 9);
		// ѭ��������������
		for (int i = 1; i < list.size() / 9; i++) {
			List listTemp = new ArrayList();
			// ѭ������һ������
			for (int j = i * 9; j < (i + 1) * 9; j++) {
				listTemp.add(list.get(j));
				// �޳�
				// /��������
				/*
				 * if (list.get(i * 7 + 4).equals("����")) {
				 * listTemp.remove(listTemp.get(listTemp.size() - 1)); }
				 */
			}
			// ���û����
			if (listTemp.size() == 9) {
				DaydealDao daydealDao = new DaydealDao();
				// ʵ�廯
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

				System.out.println("�ɹ�д����ʱ���׼�¼");
			} else {
				// ��������޲���

			}
			// ����
			new DayDealInCheck().dayDealInCheck(name);
		}
	}

	// �ж���ʲô�����˻�
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
		new DaydealCheck().isType("F:/ÿ�ճɽ���¼ģ��1.1.xlsx", "2", "root");
		String a = "\"2\"";
		System.out.println(a);
		System.out.println(a.substring(1, a.length() - 1));
	}
}