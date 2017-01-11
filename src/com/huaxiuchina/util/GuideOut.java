package com.huaxiuchina.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.util.CellRangeAddress;
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
import com.huaxiuchina.dao.StatusDao;
import com.huaxiuchina.model.Daydeal;
import com.huaxiuchina.model.Gp;
import com.huaxiuchina.model.Guide;
import com.huaxiuchina.model.Model;
import com.huaxiuchina.model.Status;

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
	StatusDao statusDao = new StatusDao();
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	public static void main(String[] args) throws Exception {
		new GuideOut().guideProduce("HXSX0019");
		// new GuideOut().test();
	}

	// ��������
	public void test() {
		Guide e = new Guide();
		e.setDm("1");
		e.setPrice(2);
		buyGuide.add(e);
		Guide e1 = new Guide();
		e1.setDm("1");
		e1.setPrice(1);
		buyGuide.add(e1);
		Guide e2 = new Guide();
		e2.setDm("2");
		e2.setPrice(1);
		buyGuide.add(e2);
		Guide e3 = new Guide();
		e3.setDm("4");
		e3.setPrice(1);
		buyGuide.add(e3);
		Guide e4 = new Guide();
		e4.setDm("3");
		e4.setPrice(3);
		buyGuide.add(e4);
		Guide e5 = new Guide();
		e5.setDm("1");
		e5.setPrice(6);
		buyGuide.add(e5);
		Guide e6 = new Guide();
		e6.setDm("3");
		e6.setPrice(1);
		buyGuide.add(e6);
		Guide e7 = new Guide();
		e7.setDm("1");
		e7.setPrice(5.23);
		buyGuide.add(e7);
		for (int i = 0; i < buyGuide.size(); i++) {
			System.out.println(((Guide) buyGuide.get(i)).getDm() + "   old"
					+ ((Guide) buyGuide.get(i)).getPrice());
		}
		buyGuide = new GuideOut().sort(buyGuide);
		for (int i = 0; i < buyGuide.size(); i++) {
			System.out.println(((Guide) buyGuide.get(i)).getDm() + "  "
					+ ((Guide) buyGuide.get(i)).getPrice());
		}
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
			System.out.println("���̼ۣ�" + gp.getZx());
			double zs = Double.valueOf(gp.getZx());
			double priceToday = Double.valueOf(modelTemp.getPrice());
			List preModelList = modelDao.selectByDm(modelTemp.getDm(), name,
					modelTemp.getModel() - 1);
			double priceLastDay = 0;
			List statusList = statusDao.byDm(name, new GetDate().getDate(),
					gp.getDm(), "��");
			if (statusList.size() != 0) {
				System.out.println("������");
				priceLastDay = Double.valueOf(((Status) statusList.get(0))
						.getPrice());
			} else {
				if (preModelList.size() != 0) {
					priceLastDay = Double.valueOf(((Model) preModelList.get(0))
							.getPrice());
				} else {
					priceLastDay = priceToday;
				}
			}
			// �ж������Ƿ���Ч
			if (priceLastDay * k >= zs * 0.9) {
				// System.out.println("@@@" + price * j);
				System.out.println("t_price" + priceLastDay);
				Guide guide1 = new GuideOut().Buy(modelTemp, gp, priceLastDay
						* k);
				if (guide1 != null) {
					buyGuide.add(guide1);
				}
			}

			// ����
			if (priceToday * j <= zs * 1.1) {
				System.out.println("&&&" + priceToday * j);
				Guide guide1 = new GuideOut().Sell(modelTemp, gp, priceToday
						* j);
				sellGuide.add(guide1);
			}
		}
		temp = gpDao.selectAllByDate(date);

		// �½׶ν���
		for (int i = 0; i < temp.size(); i++) {
			Gp gp1 = null;
			gp1 = (Gp) temp.get(i);
			System.out.println("asdasdas" + gp1.getMc());
			Double k = gp1.getK();
			Double j = gp1.getJ();
			String dm = gp1.getDm();
			modelList = modelDao.selectByDm(gp1.getDm(), name);
			if (modelList.size() != 0) {
				Guide guide = new Guide();
				modelTemp = (Model) modelList.get(modelList.size() - 1);
				double zs = Double.valueOf(gp1.getZx());
				int model = modelTemp.getModel();
				int numTecent = 0;
				if (model < 4) {
					numTecent = (int) (Integer.valueOf(modelTemp.getBase()) * (Math
							.pow(2, (model - 1))));
				}
				// ģ�Ͷ�
				else if (model > 10 && model < 15) {
					numTecent = (int) (Integer.valueOf(modelTemp.getBase()) * (Math
							.pow(1.5, (model - 11))));
				}

				double priceOld = Double.valueOf(modelTemp.getPrice());
				double priceNew = Double.valueOf(modelTemp.getPrice());
				double price1qwe = 0;
				List statusList = statusDao.byDm(name, new GetDate().getDate(),
						gp1.getDm().toString(), "��");

				if (statusList.size() != 0) {
					System.out.println("������");
					priceOld = Double.valueOf(((Status) statusList.get(0))
							.getPrice());
				}
				List preModelList = modelDao.selectByDm(modelTemp.getDm(),
						name, modelTemp.getModel() - 1);
				if (preModelList.size() != 0) {
					priceNew = Double.valueOf(((Model) preModelList.get(0))
							.getPrice());
				}
				int sumOld = Integer.valueOf(modelTemp.getSum());
				double price1 = (priceOld * sumOld + priceNew * k
						* (numTecent - sumOld))
						/ numTecent;

				if (price1 * k >= zs * 0.9) {
					guide.setDm(dm);
					guide.setMc(gp1.getMc());
					System.out.println("guide" + guide.getMc());
					guide.setPrice(price1 * k);
					guide.setPrice1(Double.valueOf(gp1.getZx()) * 0.9);

					int num = 0;
					// ģ��һ
					if (model < 4) {
						num = (int) (Integer.valueOf(modelTemp.getBase()) * (Math
								.pow(2, (model))));
					}
					// ģ�Ͷ�
					else if (model > 10 && model < 15) {
						num = (int) (Integer.valueOf(modelTemp.getBase()) * (Math
								.pow(1.5, (model - 10))));
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
		buyGuide = new GuideOut().sort(buyGuide);
		sellGuide = new GuideOut().sort(sellGuide);
		guideList.add(buyGuide);
		guideList.add(sellGuide);
		System.out.println("buySize" + buyGuide.size());
		System.out.println("sellSize" + sellGuide.size());
		return guideList;

	}

	// ����ָ����һ��ʵ��
	public Guide Buy(Model modelTempl, Gp gp, double price) {

		// ʵ��ֵ
		int sum = Integer.valueOf(modelTempl.getSum());
		// ����ֵ
		int sum1 = 0;
		int model = modelTempl.getModel();
		System.out.println("t_model:" + model);

		// ����ý׶�������
		if (model < 10) {
			sum1 = (int) (Integer.valueOf(modelTempl.getBase()) * Math.pow(2,
					(model - 1)));
		} else {
			sum1 = (int) (Integer.valueOf(modelTempl.getBase()) * Math.pow(1.5,
					(model - 11)));
		}
		System.out.println(sum1);
		if (sum == sum1) {
			guide = null;
		} else {
			modelTemp.getBase();
			guide.setDm(gp.getDm());
			guide.setMc(gp.getMc());
			System.out.println("t_price1" + price);
			guide.setPrice(price);
			guide.setPrice1(Double.valueOf(gp.getZx()) * 0.9);
			guide.setNum(sum1 - sum);
			guide.setSum(guide.getPrice() * guide.getNum());
		}
		return guide;
	}

	// ����ָ����һ��ʵ��
	public Guide Sell(Model modelTempl, Gp gp, double price) {

		// ʵ��ֵ
		int sum = Integer.valueOf(modelTempl.getSum());
		int modelSell = modelTempl.getModel();
		if (modelSell == 1) {
			sum = sum / 2;
		} else if (modelSell == 11) {
			sum = sum / 2;
		}
		guide.setDm(gp.getDm());
		guide.setMc(gp.getMc());
		guide.setPrice(price);
		guide.setPrice1(Double.valueOf(gp.getZx()) * 1.1);
		guide.setNum(sum);
		guide.setSum(guide.getPrice() * guide.getNum());
		return guide;
	}

	public List sort(List<Guide> list) {
		/** �������Ȱ����ִ棬��ΰ����� **/

		List<Guide> listTemp = new ArrayList<Guide>();
		List<Guide> listNew = new ArrayList<Guide>();
		Guide guideSort = new Guide();
		/*
		 * // ���۸�ð�� for (int i = 0; i < list.size(); i++) {
		 * 
		 * for (int j = i + 1; j < list.size(); j++) { double a = (double)
		 * list.get(i).getPrice(); double b = (double) list.get(j).getPrice();
		 * if (a > b) { guideSort = list.get(i); list.set(i, list.get(j));
		 * list.set(j, guideSort); } } }
		 */
		for (int i = 0; i < list.size(); i++) {
			int flag = 0;
			for (int j = 0; j < listTemp.size(); j++) {
				if (listTemp.get(j).getDm()
						.equals(((Guide) list.get(i)).getDm())) {
					flag = 1;
					break;
				}
			}
			if (flag == 0)
				listTemp.add((Guide) list.get(i));

		}

		for (int i = 0; i < listTemp.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).getDm()
						.equals(((Guide) listTemp.get(i)).getDm()))
					listNew.add((Guide) list.get(j));
			}

		}

		return listNew;

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
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����

		XSSFCellStyle cellStyle1 = workBook.createCellStyle();
		cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
		cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
		cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
		cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�
		cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����
		// �ڹ������д���һ������
		XSSFSheet sheet = workBook.createSheet();
		// ������ͷ
		int width1 = 3000;
		sheet.setColumnWidth((short) 0, (short) 2500);
		sheet.setColumnWidth((short) 1, (short) 2500);
		sheet.setColumnWidth((short) 2, (short) 2500);
		sheet.setColumnWidth((short) 3, (short) 2950);
		sheet.setColumnWidth((short) 4, (short) 2500);
		sheet.setColumnWidth((short) 5, (short) 2950);

		XSSFRow row = sheet.createRow(hangshu++);
		// sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));
		CellRangeAddress region1 = new CellRangeAddress(0, (short) 0, 0,
				(short) 5);
		sheet.addMergedRegion(region1);

		XSSFCell cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(new XSSFRichTextString("����ָ����ϸ"));

		row = sheet.createRow(hangshu++);

		cell = row.createCell(0);
		cell.setCellStyle(cellStyle);

		cell.setCellValue(new XSSFRichTextString("�˻�"));

		XSSFCell b2 = row.createCell(1);
		b2.setCellStyle(cellStyle);
		b2.setCellType(XSSFCell.CELL_TYPE_STRING);
		b2.setCellValue(new XSSFRichTextString(name));

		XSSFCell b3 = row.createCell(2);
		b3.setCellStyle(cellStyle);
		b3.setCellType(XSSFCell.CELL_TYPE_STRING);
		b3.setCellValue(new XSSFRichTextString("��������"));

		XSSFCell b4 = row.createCell(3);
		b4.setCellStyle(cellStyle);
		b4.setCellType(XSSFCell.CELL_TYPE_STRING);
		b4.setCellValue(new XSSFRichTextString(new GetDate().getDate()));

		cell = row.createCell(4);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("ָ������"));

		// ���ǲ���������
		String tecentDate = new GetDate().getDate();
		String nextDate = new GetDate().nextDate(tecentDate);
		Calendar c = Calendar.getInstance();
		Date day = sdf.parse(nextDate);
		c.setTime(day);
		System.out.println(c.get(Calendar.DAY_OF_WEEK));
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			nextDate = new GetDate().nextDate(nextDate);
			nextDate = new GetDate().nextDate(nextDate);
		}
		cell = row.createCell(5);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString(nextDate));
		/*
		 * b4=a1.createCell(4); b4.setCellValue(new XSSFRichTextString(new
		 * GetDate().getDate()));
		 */

		XSSFRow a2 = sheet.createRow(hangshu++);
		region1 = new CellRangeAddress(hangshu - 1, (short) hangshu - 1, 0,
				(short) 5);
		sheet.addMergedRegion(region1);

		XSSFCell b5 = a2.createCell(0);
		b5.setCellStyle(cellStyle);
		b5.setCellType(XSSFCell.CELL_TYPE_STRING);
		b5.setCellValue(new XSSFRichTextString("���ֽ���"));

		// ��ָ��������������һ��
		row = sheet.createRow(hangshu++);
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
		cell = row.createCell(5);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("Ԥ�Ƴɽ���"));
		// д������
		if (buyGuide.size() > 0) {
			for (int i = 0; i < buyGuide.size(); i++) {
				guide = (Guide) buyGuide.get(i);
				XSSFRow tpr = sheet.createRow(hangshu++);
				cell = tpr.createCell(0);
				cell.setCellStyle(cellStyle1);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(guide.getDm()));
				cell = tpr.createCell(1);
				cell.setCellStyle(cellStyle1);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(guide.getMc()));
				cell = tpr.createCell(2);
				cell.setCellStyle(cellStyle1);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(df.format(guide
						.getPrice())));
				cell = tpr.createCell(3);
				cell.setCellStyle(cellStyle1);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(df.format(guide
						.getPrice1())));
				cell = tpr.createCell(4);
				cell.setCellStyle(cellStyle1);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(String.valueOf(guide
						.getNum())));
				cell = tpr.createCell(5);
				cell.setCellStyle(cellStyle1);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(df.format(guide
						.getSum())));
			}
		}
		row = sheet.createRow(hangshu++);
		System.out.println(hangshu);
		int maichu = hangshu;
		// ����1���к� ����2����ʼ�к� ����3���к� ����4����ֹ�к�
		region1 = new CellRangeAddress(hangshu - 1, (short) hangshu - 1, 0,
				(short) 5);
		sheet.addMergedRegion(region1);
		cell = row.createCell(0);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("����ָ��"));
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
				cell.setCellStyle(cellStyle1);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(guide.getDm()));
				cell = tpr.createCell(1);
				cell.setCellStyle(cellStyle1);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(guide.getMc()));
				cell = tpr.createCell(2);
				cell.setCellStyle(cellStyle1);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(df.format(guide
						.getPrice())));
				cell = tpr.createCell(3);
				cell.setCellStyle(cellStyle1);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(df.format(guide
						.getPrice1())));
				cell = tpr.createCell(4);
				cell.setCellStyle(cellStyle1);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new XSSFRichTextString(String.valueOf(guide
						.getNum())));
				cell = tpr.createCell(5);
				cell.setCellStyle(cellStyle1);
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