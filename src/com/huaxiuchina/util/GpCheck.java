package com.huaxiuchina.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.model.Gp;

public class GpCheck {
	public List gpCheck(String url) throws Exception {
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
					if (j != 0 & j != 6 & j != 7 & j != 26 & j != 27 & j != 28) {
						gpList.add(cell);
					}
					// System.out.println(cell+"\t");
				}
			}
		}
		return gpList;
	}

	public void gpUpload(String url) throws Exception {
		GpDao gpDao = new GpDao();

		List list = new GpCheck().gpCheck(url);
		// System.out.println(list);
		System.out.println(list.size() / 32);
		for (int i = 1; i < list.size() / 32; i++) {
			List listTemp = new ArrayList();
			for (int j = i * 32; j < (i + 1) * 32; j++) {
				listTemp.add(list.get(j));
			}
			// 实例化实体类
			double k = 0;
			double j = 0;
			Gp gp1 = null;
			String lastDate = new GetDate().getDate();
			for (int l = 0; l < 5; l++) {
				if (gpDao.selectByDmAndDate(lastDate,
						listTemp.get(0).toString()).size() != 0) {
					gp1 = (Gp) gpDao.selectByDmAndDate(lastDate,
							listTemp.get(0).toString()).get(0);
					k = gp1.getK();
					j = gp1.getJ();
				}
				if (k != 0) {
					break;
				}
				lastDate = new GetDate().lastDate(lastDate);
			}

			Gp gp = new Gp(listTemp.get(0).toString(), listTemp.get(1)
					.toString(), listTemp.get(2).toString(), listTemp.get(3)
					.toString(), listTemp.get(4).toString(), listTemp.get(5)
					.toString(), listTemp.get(6).toString(), listTemp.get(7)
					.toString(), listTemp.get(8).toString(), listTemp.get(9)
					.toString(), listTemp.get(10).toString(), listTemp.get(11)
					.toString(), listTemp.get(12).toString(), listTemp.get(13)
					.toString(), listTemp.get(14).toString(), listTemp.get(15)
					.toString(), listTemp.get(16).toString(), listTemp.get(17)
					.toString(), listTemp.get(18).toString(), listTemp.get(19)
					.toString(), listTemp.get(20).toString(), listTemp.get(21)
					.toString(), listTemp.get(22).toString(), listTemp.get(23)
					.toString(), listTemp.get(24).toString(), listTemp.get(25)
					.toString(), listTemp.get(26).toString(), listTemp.get(27)
					.toString(), listTemp.get(28).toString(), listTemp.get(29)
					.toString(), listTemp.get(30).toString(), listTemp.get(31)
					.toString(), new GetDate().getDate().toString(), k, j);
			new GpDao().add(gp);
		}
	}
}
