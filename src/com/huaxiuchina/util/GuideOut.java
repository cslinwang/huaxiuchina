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
 * @author Snow �򵥵�д�� excel HSSFʵ�� excel 2003(�� .xls ��β���ļ�) XSSFʵ�� excel 2007(��
 *         .xlsx ��β���ļ�)
 */
public class GuideOut {
	public void onlyBuy(List onlyBuyList) throws IOException {
		System.out.println("��ʼ����");
		// ����������
		XSSFWorkbook workBook = new XSSFWorkbook();
		// �ڹ������д���һ������
		XSSFSheet sheet = workBook.createSheet();
		// ������ͷ
		// ��ָ��������������һ��
		XSSFRow row = sheet.createRow(0);
		// ��ָ�������������� һ�У���Ԫ��
		XSSFCell dm = row.createCell(0);
		// ���嵥Ԫ��Ϊ�ַ�������
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// �ڵ�Ԫ����������
		dm.setCellValue(new XSSFRichTextString("��Ʊ����"));
		XSSFCell mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellValue(new XSSFRichTextString("��Ʊ����"));
		XSSFCell mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellValue(new XSSFRichTextString("�����"));
		XSSFCell sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellValue(new XSSFRichTextString("������"));
		XSSFCell zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellValue(new XSSFRichTextString("������"));
		XSSFCell zd1 = row.createCell(5);
		zd1.setCellType(XSSFCell.CELL_TYPE_STRING);
		zd1.setCellValue(new XSSFRichTextString("������"));				
		// д������
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
		// �½�һ�����������Ӧ��excel�ļ�����
		FileOutputStream fos = new FileOutputStream(
				"C:/Users/wanglin/Desktop/hos.xlsx");
		workBook.write(fos);
		fos.flush();
		// �����������ر���
		fos.close();
		System.out.println("�ļ�����");
	}
}