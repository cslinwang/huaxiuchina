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
 * @author Snow �򵥵�д�� excel HSSFʵ�� excel 2003(�� .xls ��β���ļ�) XSSFʵ�� excel 2007(��
 *         .xlsx ��β���ļ�)
 */
public class GuideOut {
	public ByteArrayInputStream onlyBuy(List onlyBuyList,String name) throws Exception {
		System.out.println("��ʼ����");
		// ����������
		XSSFWorkbook workBook = new XSSFWorkbook();
		//���������ʽ
		XSSFFont font = workBook.createFont(); 
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		XSSFCellStyle cellStyle= workBook.createCellStyle(); 
		cellStyle.setFont(font); 
		// �ڹ������д���һ������
		XSSFSheet sheet = workBook.createSheet();
		// ������ͷ
		XSSFRow a1 = sheet.createRow(0);
		//sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));
		
		XSSFCell b1 = a1.createCell(0);
		b1.setCellStyle(cellStyle); 
		b1.setCellType(XSSFCell.CELL_TYPE_STRING);
		b1.setCellValue(new XSSFRichTextString("�˻�"));
		
		XSSFCell b2 = a1.createCell(1);
		b2.setCellStyle(cellStyle); 
		b2.setCellType(XSSFCell.CELL_TYPE_STRING);
		b2.setCellValue(new XSSFRichTextString(name));
		
		XSSFCell b3 = a1.createCell(2);
		b3.setCellStyle(cellStyle); 
		b3.setCellType(XSSFCell.CELL_TYPE_STRING);
		b3.setCellValue(new XSSFRichTextString("��������"));
		
		XSSFCell b4 = a1.createCell(3);
		b4.setCellStyle(cellStyle); 
		b4.setCellType(XSSFCell.CELL_TYPE_STRING);
		b4.setCellValue(new XSSFRichTextString(new GetDate().getDate()));

		XSSFRow a2 = sheet.createRow(1);
		
		XSSFCell b5 = a2.createCell(0);
		b5.setCellStyle(cellStyle); 
		b5.setCellType(XSSFCell.CELL_TYPE_STRING);
		b5.setCellValue(new XSSFRichTextString("����ָ��"));
		
		// ��ָ��������������һ��
		XSSFRow row = sheet.createRow(2);
		// ��ָ�������������� һ�У���Ԫ��
		XSSFCell dm = row.createCell(0);
		// ���嵥Ԫ��Ϊ�ַ�������
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// �ڵ�Ԫ����������
		dm.setCellStyle(cellStyle);
		dm.setCellValue(new XSSFRichTextString("��Ʊ����"));
		XSSFCell mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellStyle(cellStyle);
		mrj.setCellValue(new XSSFRichTextString("��Ʊ����"));
		XSSFCell mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellStyle(cellStyle);
		mcj.setCellValue(new XSSFRichTextString("����۸�"));
		XSSFCell sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellStyle(cellStyle);
		sshy.setCellValue(new XSSFRichTextString("���յ�ͣ��"));
		XSSFCell zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellStyle(cellStyle);
		zg.setCellValue(new XSSFRichTextString("��������"));
		XSSFCell cell = row.createCell(5);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("Ԥ�Ƴɽ���"));
		cell = row.createCell(6);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("�����۸�"));
		cell = row.createCell(7);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("������ͣ��"));
		cell = row.createCell(8);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("������������"));
		cell = row.createCell(9);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("Ԥ�Ƴɽ����"));
		
		
		// д������
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
		// �½�һ�����������Ӧ��excel�ļ�����

		 ByteArrayOutputStream os = new ByteArrayOutputStream();
         workBook.write(os);
         byte[] fileContent = os.toByteArray();
         ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
         return is;
	}

	public ByteArrayInputStream onlySell(List onlyBuyList, String name)
			throws Exception {
		System.out.println("��ʼ����");
		// ����������
		XSSFWorkbook workBook = new XSSFWorkbook();
		//���������ʽ
		XSSFFont font = workBook.createFont(); 
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		XSSFCellStyle cellStyle= workBook.createCellStyle(); 
		cellStyle.setFont(font); 
		// �ڹ������д���һ������
		XSSFSheet sheet = workBook.createSheet();
		// ������ͷ
		XSSFRow a1 = sheet.createRow(0);
		//sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));
		
		XSSFCell b1 = a1.createCell(0);
		b1.setCellStyle(cellStyle); 
		b1.setCellType(XSSFCell.CELL_TYPE_STRING);
		b1.setCellValue(new XSSFRichTextString("�˻�"));
		
		XSSFCell b2 = a1.createCell(1);
		b2.setCellStyle(cellStyle); 
		b2.setCellType(XSSFCell.CELL_TYPE_STRING);
		b2.setCellValue(new XSSFRichTextString(name));
		
		XSSFCell b3 = a1.createCell(2);
		b3.setCellStyle(cellStyle); 
		b3.setCellType(XSSFCell.CELL_TYPE_STRING);
		b3.setCellValue(new XSSFRichTextString("��������"));
		
		XSSFCell b4 = a1.createCell(3);
		b4.setCellStyle(cellStyle); 
		b4.setCellType(XSSFCell.CELL_TYPE_STRING);
		b4.setCellValue(new XSSFRichTextString(new GetDate().getDate()));

		XSSFRow a2 = sheet.createRow(1);
		
		XSSFCell b5 = a2.createCell(0);
		b5.setCellStyle(cellStyle); 
		b5.setCellType(XSSFCell.CELL_TYPE_STRING);
		b5.setCellValue(new XSSFRichTextString("����ָ��"));
		
		// ��ָ��������������һ��
		XSSFRow row = sheet.createRow(2);
		// ��ָ�������������� һ�У���Ԫ��
		XSSFCell dm = row.createCell(0);
		// ���嵥Ԫ��Ϊ�ַ�������
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// �ڵ�Ԫ����������
		dm.setCellStyle(cellStyle);
		dm.setCellValue(new XSSFRichTextString("��Ʊ����"));
		XSSFCell mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellStyle(cellStyle);
		mrj.setCellValue(new XSSFRichTextString("��Ʊ����"));
		XSSFCell mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellStyle(cellStyle);
		mcj.setCellValue(new XSSFRichTextString("����۸�"));
		XSSFCell sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellStyle(cellStyle);
		sshy.setCellValue(new XSSFRichTextString("���յ�ͣ��"));
		XSSFCell zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellStyle(cellStyle);
		zg.setCellValue(new XSSFRichTextString("��������"));
		XSSFCell cell = row.createCell(5);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("Ԥ�Ƴɽ���"));
		cell = row.createCell(6);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("�����۸�"));
		cell = row.createCell(7);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("������ͣ��"));
		cell = row.createCell(8);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("������������"));
		cell = row.createCell(9);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("Ԥ�Ƴɽ����"));
		
		
		// д������
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
		// �½�һ�����������Ӧ��excel�ļ�����

		 ByteArrayOutputStream os = new ByteArrayOutputStream();
         workBook.write(os);
         byte[] fileContent = os.toByteArray();
         ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
         return is;
	}
	public ByteArrayInputStream both(List onlyBuyList,String name) throws Exception {
		System.out.println("��ʼ����");
		// ����������
		XSSFWorkbook workBook = new XSSFWorkbook();
		//���������ʽ
		XSSFFont font = workBook.createFont(); 
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		XSSFCellStyle cellStyle= workBook.createCellStyle(); 
		cellStyle.setFont(font); 
		// �ڹ������д���һ������
		XSSFSheet sheet = workBook.createSheet();
		// ������ͷ
		XSSFRow a1 = sheet.createRow(0);
		//sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));
		
		XSSFCell b1 = a1.createCell(0);
		b1.setCellStyle(cellStyle); 
		b1.setCellType(XSSFCell.CELL_TYPE_STRING);
		b1.setCellValue(new XSSFRichTextString("�˻�"));
		
		XSSFCell b2 = a1.createCell(1);
		b2.setCellStyle(cellStyle); 
		b2.setCellType(XSSFCell.CELL_TYPE_STRING);
		b2.setCellValue(new XSSFRichTextString(name));
		
		XSSFCell b3 = a1.createCell(2);
		b3.setCellStyle(cellStyle); 
		b3.setCellType(XSSFCell.CELL_TYPE_STRING);
		b3.setCellValue(new XSSFRichTextString("��������"));
		
		XSSFCell b4 = a1.createCell(3);
		b4.setCellStyle(cellStyle); 
		b4.setCellType(XSSFCell.CELL_TYPE_STRING);
		b4.setCellValue(new XSSFRichTextString(new GetDate().getDate()));

		XSSFRow a2 = sheet.createRow(1);
		
		XSSFCell b5 = a2.createCell(0);
		b5.setCellStyle(cellStyle); 
		b5.setCellType(XSSFCell.CELL_TYPE_STRING);
		b5.setCellValue(new XSSFRichTextString("����ָ��"));
		
		// ��ָ��������������һ��
		XSSFRow row = sheet.createRow(2);
		// ��ָ�������������� һ�У���Ԫ��
		XSSFCell dm = row.createCell(0);
		// ���嵥Ԫ��Ϊ�ַ�������
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// �ڵ�Ԫ����������
		dm.setCellStyle(cellStyle);
		dm.setCellValue(new XSSFRichTextString("��Ʊ����"));
		XSSFCell mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellStyle(cellStyle);
		mrj.setCellValue(new XSSFRichTextString("��Ʊ����"));
		XSSFCell mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellStyle(cellStyle);
		mcj.setCellValue(new XSSFRichTextString("����۸�"));
		XSSFCell sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellStyle(cellStyle);
		sshy.setCellValue(new XSSFRichTextString("���յ�ͣ��"));
		XSSFCell zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellStyle(cellStyle);
		zg.setCellValue(new XSSFRichTextString("��������"));
		XSSFCell cell = row.createCell(5);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("Ԥ�Ƴɽ���"));
		cell = row.createCell(6);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("�����۸�"));
		cell = row.createCell(7);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("������ͣ��"));
		cell = row.createCell(8);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("������������"));
		cell = row.createCell(9);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("Ԥ�Ƴɽ����"));
		
		
		// д������
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
		// �½�һ�����������Ӧ��excel�ļ�����

		 ByteArrayOutputStream os = new ByteArrayOutputStream();
         workBook.write(os);
         byte[] fileContent = os.toByteArray();
         ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
         return is;
	}
}