package com.huaxiuchina.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

import com.huaxiuchina.dao.DaydealDao;
import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.dao.ModelDao;
import com.huaxiuchina.model.Daydeal;
import com.huaxiuchina.model.Gp;
import com.huaxiuchina.model.Guide;
import com.huaxiuchina.model.Model;

/**
 * 
 * @author Snow �򵥵�д�� excel HSSFʵ�� excel 2003(�� .xls ��β���ļ�) XSSFʵ�� excel 2007(��
 *         .xlsx ��β���ļ�)
 */
public class GuideOut {
	GpDao gpDao = new GpDao();
	DaydealDao daydealDao = new DaydealDao();
	Daydeal daydeal; // һ��������Ʊʵ����
	Guide guide = new Guide();
	List guideList = new ArrayList(); // ���ָ��
	List buyGuide = new ArrayList(); // �������ָ��
	List sellGuide = new ArrayList(); // �������ָ��
	List singleBuy = new ArrayList(); // ���һ������
	List singleSell = new ArrayList(); // ���һ������
	List modelList = new ArrayList(); // ���ȥ�غ�ĵ��ս��׹�Ʊ��Ϣ
	List temp = new ArrayList(); // ��ʱ����
	java.text.DecimalFormat df = new DecimalFormat("#.00"); // ��ʽ��double
	double k = 0; // kֵ
	double j = 0; // Jֵ
	int flag = 0; // ��־�����ù�Ʊʲô�����0����ֻ����ֵֻ����
	String dm1 = null; // ����һ������
	String mc1 = null; // ���ڶ�������
	Gp tempGp = new Gp();
	Model modell = new Model();
	Model modelTemp = new Model();
	ModelDao modelDao = new ModelDao();

	public static void main(String[] args) throws Exception {
		new GuideOut().guideProduce("HXSX0010");
	}

	public List guideProduce(String name) throws Exception {
		String date = new GetDate().getDate();
		// ��ù�ƱK J ���̼�
		modelList = modelDao.selectByName(name);
		if (modelList.size() == 0) {
			return null;
		}
		for (int i = modelList.size() - 1; i >= 0; i--) {
			// �õ�һ��ģ��ʵ��
			modelTemp = (Model) modelList.get(i);
			// �õ���Ӧ��Ʊʵ��
			Gp gp = (Gp) gpDao.selectByDmAndDate(date, modelTemp.getDm())
					.get(0);
			Double k = gp.getK();
			Double j = gp.getJ();
			double zs = Double.valueOf(gp.getZs1());
			System.out.println("t"+modelTemp.getPrice());
			double price = Double.valueOf(modelTemp.getPrice());
			// �ж������Ƿ���Ч
			if (price * k >= zs * 0.9) {
				// System.out.println("@@@" + price * j);
				Guide guide1 = new GuideOut().Buy(modelTemp, gp, price * k);
				if (guide1 != null) {
					buyGuide.add(guide1);
				}
			}
			// ����
			if (price * j <= zs * 1.1) {
				// System.out.println("&&&" + price * j);
				Guide guide1 = new GuideOut().Sell(modelTemp, gp, price * j);
				sellGuide.add(guide1);
			}
		}
		temp = gpDao.selectAllByDate(date);
		for (int i = 0; i < temp.size(); i++) {
			Gp gp1 = (Gp) temp.get(i);
			modelList = modelDao.selectByDm(gp1.getDm(), name);
			if (modelList.size() != 0) {
				modelTemp = (Model) modelList.get(modelList.size() - 1);
				double zs = Double.valueOf(gp1.getZs1());
				double price = Double.valueOf(modelTemp.getPrice());
				if (price * k >= zs * 0.9) {
					guide.setDm(gp1.getDm());
					guide.setMc(gp1.getMc());
					guide.setPrice(Double.valueOf(modelTemp.getPrice())
							* gp1.getK());
					guide.setPrice1(Double.valueOf(gp1.getZs1()) * 0.9);
					int model = modelTemp.getModel();
					int num = 0;
					// ģ��һ
					if (model < 4) {
						num = (int) (Integer.valueOf(modelTemp.getBase()) * (Math
								.pow(2, (model))));
					}
					// ģ�Ͷ�
					else if (model > 10 && model < 15) {
						num = (int) (Integer.valueOf(modelTemp.getBase()) * (Math
								.pow(1.5, (model))));
					}
					// ��ģȫ�����
					else {
						// ������
					}
					guide.setNum(num);
					guide.setSum(guide.getPrice() * guide.getNum());
					if (guide.getNum() != 0) {
						buyGuide.add(guide);
					}
				}
			}
		}
		guideList.add(buyGuide);
		guideList.add(sellGuide);
		return guideList;

	}

	// ����ָ����һ��ʵ��
	public Guide Buy(Model modelTempl, Gp gp, double price) {

		// ʵ��ֵ
		int sum = Integer.valueOf(modelTempl.getSum());
		// ����ֵ
		int sum1 = 0;
		int model = modelTempl.getModel();
		// ����ý׶�������
		if (model < 10) {
			sum1 = (int) (Integer.valueOf(modelTempl.getBase()) * Math.pow(2,
					(model - 1)));
		} else {
			sum1 = (int) (Integer.valueOf(modelTempl.getBase()) * Math.pow(1.5,
					(model - 1)));
		}
		if (sum == sum1) {
			guide = null;
		} else {
			modelTemp.getBase();
			guide.setDm(gp.getDm());
			guide.setMc(gp.getMc());
			guide.setPrice(price);
			guide.setPrice1(Double.valueOf(gp.getZs1()) * 0.9);
			guide.setNum(sum1 - sum);
			guide.setSum(guide.getPrice() * guide.getNum());
		}
		return guide;
	}

	// ����ָ����һ��ʵ��
	public Guide Sell(Model modelTempl, Gp gp, double price) {

		// ʵ��ֵ
		int sum = Integer.valueOf(modelTempl.getSum());
		modelTemp.getBase();
		guide.setDm(gp.getDm());
		guide.setMc(gp.getMc());
		guide.setPrice(price);
		guide.setPrice1(Double.valueOf(gp.getZs1()) * 1.1);
		guide.setNum(sum);
		guide.setSum(guide.getPrice() * guide.getNum());
		return guide;
	}

	public ByteArrayInputStream guideOut(String name) throws Exception {
		int hangshu = 0;
		guideList = new GuideOut().guideProduce(name);
		buyGuide = (List) guideList.get(0);
		sellGuide = (List) guideList.get(1);
		System.out.println("��ʼ����");
		// ����������
		XSSFWorkbook workBook = new XSSFWorkbook();
		// ���������ʽ
		XSSFFont font = workBook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		XSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setFont(font);
		// �ڹ������д���һ������
		XSSFSheet sheet = workBook.createSheet();
		// ������ͷ
		XSSFRow a1 = sheet.createRow(hangshu++);
		// sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));

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

		XSSFRow a2 = sheet.createRow(hangshu++);

		XSSFCell b5 = a2.createCell(0);
		b5.setCellStyle(cellStyle);
		b5.setCellType(XSSFCell.CELL_TYPE_STRING);
		b5.setCellValue(new XSSFRichTextString("������ָ��"));

		// ��ָ��������������һ��
		XSSFRow row = sheet.createRow(hangshu++);
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
		// д������
		if (buyGuide.size() > 0) {
			for (int i = 0; i < buyGuide.size(); i++) {
				guide = (Guide) buyGuide.get(i);
				XSSFRow tpr = sheet.createRow(hangshu++);
				cell = tpr.createCell(0);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(guide.getDm()));
				cell = tpr.createCell(1);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(guide.getMc()));
				cell = tpr.createCell(2);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(df.format(guide
						.getPrice())));
				cell = tpr.createCell(3);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(df.format(guide
						.getPrice1())));
				cell = tpr.createCell(4);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(String.valueOf(guide
						.getNum())));
				cell = tpr.createCell(5);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(df.format(guide
						.getSum())));
			}
		}
		// ��ָ��������������һ��
		row = sheet.createRow(hangshu++);
		// ��ָ�������������� һ�У���Ԫ��
		dm = row.createCell(0);
		// ���嵥Ԫ��Ϊ�ַ�������
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// �ڵ�Ԫ����������
		dm.setCellStyle(cellStyle);
		dm.setCellValue(new XSSFRichTextString("��Ʊ����"));
		mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellStyle(cellStyle);
		mrj.setCellValue(new XSSFRichTextString("��Ʊ����"));
		mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellStyle(cellStyle);
		mcj.setCellValue(new XSSFRichTextString("�����۸�"));
		sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellStyle(cellStyle);
		sshy.setCellValue(new XSSFRichTextString("������ͣ��"));
		zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellStyle(cellStyle);
		zg.setCellValue(new XSSFRichTextString("��������"));
		cell = row.createCell(5);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("Ԥ�Ƴɽ���"));
		// д������
		if (sellGuide.size() > 0) {
			for (int i = 0; i < sellGuide.size(); i++) {
				guide = (Guide) sellGuide.get(i);
				XSSFRow tpr = sheet.createRow(hangshu++);
				cell = tpr.createCell(0);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(guide.getDm()));
				cell = tpr.createCell(1);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(guide.getMc()));
				cell = tpr.createCell(2);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(df.format(guide
						.getPrice())));
				cell = tpr.createCell(3);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(df.format(guide
						.getPrice1())));
				cell = tpr.createCell(4);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(String.valueOf(guide
						.getNum())));
				cell = tpr.createCell(5);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(df.format(guide
						.getSum())));
			}
		}

		// �½�һ�����������Ӧ��excel�ļ�����
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workBook.write(os);
		byte[] fileContent = os.toByteArray();
		ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
		return is;
	}

	public ByteArrayInputStream onlyBuy(List onlyBuyList, String name)
			throws Exception {
		System.out.println("��ʼ����");
		// ����������
		XSSFWorkbook workBook = new XSSFWorkbook();
		// ���������ʽ
		XSSFFont font = workBook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		XSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setFont(font);
		// �ڹ������д���һ������
		XSSFSheet sheet = workBook.createSheet();
		// ������ͷ
		XSSFRow a1 = sheet.createRow(0);
		// sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));

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
			cell = tpr.createCell(0);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(1);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(2);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(3);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(4);
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
		// ���������ʽ
		XSSFFont font = workBook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		XSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setFont(font);
		// �ڹ������д���һ������
		XSSFSheet sheet = workBook.createSheet();
		// ������ͷ
		XSSFRow a1 = sheet.createRow(0);
		// sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));

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
			cell = tpr.createCell(0);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(1);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(2);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(3);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(4);
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

	public ByteArrayInputStream both(List onlyBuyList, String name)
			throws Exception {
		System.out.println("��ʼ����");
		// ����������
		XSSFWorkbook workBook = new XSSFWorkbook();
		// ���������ʽ
		XSSFFont font = workBook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		XSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setFont(font);
		// �ڹ������д���һ������
		XSSFSheet sheet = workBook.createSheet();
		// ������ͷ
		XSSFRow a1 = sheet.createRow(0);
		// sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));

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
		int j = 3;
		for (int i = 0; i < onlyBuyList.size(); i++) {

			XSSFRow tpr = sheet.createRow(j);
			cell = tpr.createCell(0);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(1);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(2);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(3);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(onlyBuyList.get(i)
					.toString()));
			i++;
			cell = tpr.createCell(4);
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