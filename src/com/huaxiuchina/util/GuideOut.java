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
 * @author Snow 简单的写入 excel HSSF实现 excel 2003(以 .xls 结尾的文件) XSSF实现 excel 2007(以
 *         .xlsx 结尾的文件)
 */
public class GuideOut {
	GpDao gpDao = new GpDao();
	DaydealDao daydealDao = new DaydealDao();
	Daydeal daydeal; // 一个单个股票实体类
	Guide guide = new Guide();
	List guideList = new ArrayList(); // 存放指导
	List buyGuide = new ArrayList(); // 存放买入指导
	List sellGuide = new ArrayList(); // 存放卖出指导
	List singleBuy = new ArrayList(); // 存放一组数据
	List singleSell = new ArrayList(); // 存放一组数据
	List modelList = new ArrayList(); // 存放去重后的当日交易股票信息
	List temp = new ArrayList(); // 临时变量
	java.text.DecimalFormat df = new DecimalFormat("#.00"); // 格式化double
	double k = 0; // k值
	double j = 0; // J值
	int flag = 0; // 标志，看该股票什么情况，0代表只买，满值只卖。
	String dm1 = null; // 表格第一列数据
	String mc1 = null; // 表格第二列数据
	Gp tempGp = new Gp();
	Model modell = new Model();
	Model modelTemp = new Model();
	ModelDao modelDao = new ModelDao();

	public static void main(String[] args) throws Exception {
		new GuideOut().guideProduce("HXSX0010");
	}

	public List guideProduce(String name) throws Exception {
		String date = new GetDate().getDate();
		// 获得股票K J 收盘价
		modelList = modelDao.selectByName(name);
		if (modelList.size() == 0) {
			return null;
		}
		for (int i = modelList.size() - 1; i >= 0; i--) {
			// 拿到一个模型实例
			modelTemp = (Model) modelList.get(i);
			// 拿到对应股票实例
			Gp gp = (Gp) gpDao.selectByDmAndDate(date, modelTemp.getDm())
					.get(0);
			Double k = gp.getK();
			Double j = gp.getJ();
			double zs = Double.valueOf(gp.getZs1());
			System.out.println("t"+modelTemp.getPrice());
			double price = Double.valueOf(modelTemp.getPrice());
			// 判断买入是否有效
			if (price * k >= zs * 0.9) {
				// System.out.println("@@@" + price * j);
				Guide guide1 = new GuideOut().Buy(modelTemp, gp, price * k);
				if (guide1 != null) {
					buyGuide.add(guide1);
				}
			}
			// 卖出
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
					// 模型一
					if (model < 4) {
						num = (int) (Integer.valueOf(modelTemp.getBase()) * (Math
								.pow(2, (model))));
					}
					// 模型二
					else if (model > 10 && model < 15) {
						num = (int) (Integer.valueOf(modelTemp.getBase()) * (Math
								.pow(1.5, (model))));
					}
					// 建模全部完成
					else {
						// 不处理
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

	// 买入指导的一个实例
	public Guide Buy(Model modelTempl, Gp gp, double price) {

		// 实际值
		int sum = Integer.valueOf(modelTempl.getSum());
		// 满仓值
		int sum1 = 0;
		int model = modelTempl.getModel();
		// 计算该阶段满仓数
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

	// 卖出指导的一个实例
	public Guide Sell(Model modelTempl, Gp gp, double price) {

		// 实际值
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
		System.out.println("开始生成");
		// 创建工作薄
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 设置字体格式
		XSSFFont font = workBook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		XSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setFont(font);
		// 在工作薄中创建一工作表
		XSSFSheet sheet = workBook.createSheet();
		// 建立表头
		XSSFRow a1 = sheet.createRow(hangshu++);
		// sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));

		XSSFCell b1 = a1.createCell(0);
		b1.setCellStyle(cellStyle);
		b1.setCellType(XSSFCell.CELL_TYPE_STRING);
		b1.setCellValue(new XSSFRichTextString("账户"));

		XSSFCell b2 = a1.createCell(1);
		b2.setCellStyle(cellStyle);
		b2.setCellType(XSSFCell.CELL_TYPE_STRING);
		b2.setCellValue(new XSSFRichTextString(name));

		XSSFCell b3 = a1.createCell(2);
		b3.setCellStyle(cellStyle);
		b3.setCellType(XSSFCell.CELL_TYPE_STRING);
		b3.setCellValue(new XSSFRichTextString("报告日期"));

		XSSFCell b4 = a1.createCell(3);
		b4.setCellStyle(cellStyle);
		b4.setCellType(XSSFCell.CELL_TYPE_STRING);
		b4.setCellValue(new XSSFRichTextString(new GetDate().getDate()));

		XSSFRow a2 = sheet.createRow(hangshu++);

		XSSFCell b5 = a2.createCell(0);
		b5.setCellStyle(cellStyle);
		b5.setCellType(XSSFCell.CELL_TYPE_STRING);
		b5.setCellValue(new XSSFRichTextString("购买交易指导"));

		// 在指定的索引处创建一行
		XSSFRow row = sheet.createRow(hangshu++);
		// 在指定的索引处创建 一列（单元格）
		XSSFCell dm = row.createCell(0);
		// 定义单元格为字符串类型
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// 在单元格输入内容
		dm.setCellStyle(cellStyle);
		dm.setCellValue(new XSSFRichTextString("股票代码"));
		XSSFCell mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellStyle(cellStyle);
		mrj.setCellValue(new XSSFRichTextString("股票名称"));
		XSSFCell mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellStyle(cellStyle);
		mcj.setCellValue(new XSSFRichTextString("买入价格"));
		XSSFCell sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellStyle(cellStyle);
		sshy.setCellValue(new XSSFRichTextString("明日跌停价"));
		XSSFCell zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellStyle(cellStyle);
		zg.setCellValue(new XSSFRichTextString("建仓数量"));
		XSSFCell cell = row.createCell(5);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("预计成交额"));
		// 写入数据
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
		// 在指定的索引处创建一行
		row = sheet.createRow(hangshu++);
		// 在指定的索引处创建 一列（单元格）
		dm = row.createCell(0);
		// 定义单元格为字符串类型
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// 在单元格输入内容
		dm.setCellStyle(cellStyle);
		dm.setCellValue(new XSSFRichTextString("股票代码"));
		mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellStyle(cellStyle);
		mrj.setCellValue(new XSSFRichTextString("股票名称"));
		mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellStyle(cellStyle);
		mcj.setCellValue(new XSSFRichTextString("卖出价格"));
		sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellStyle(cellStyle);
		sshy.setCellValue(new XSSFRichTextString("明日涨停价"));
		zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellStyle(cellStyle);
		zg.setCellValue(new XSSFRichTextString("卖出数量"));
		cell = row.createCell(5);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("预计成交额"));
		// 写入数据
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

		// 新建一输出流并把相应的excel文件存盘
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workBook.write(os);
		byte[] fileContent = os.toByteArray();
		ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
		return is;
	}

	public ByteArrayInputStream onlyBuy(List onlyBuyList, String name)
			throws Exception {
		System.out.println("开始生成");
		// 创建工作薄
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 设置字体格式
		XSSFFont font = workBook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		XSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setFont(font);
		// 在工作薄中创建一工作表
		XSSFSheet sheet = workBook.createSheet();
		// 建立表头
		XSSFRow a1 = sheet.createRow(0);
		// sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));

		XSSFCell b1 = a1.createCell(0);
		b1.setCellStyle(cellStyle);
		b1.setCellType(XSSFCell.CELL_TYPE_STRING);
		b1.setCellValue(new XSSFRichTextString("账户"));

		XSSFCell b2 = a1.createCell(1);
		b2.setCellStyle(cellStyle);
		b2.setCellType(XSSFCell.CELL_TYPE_STRING);
		b2.setCellValue(new XSSFRichTextString(name));

		XSSFCell b3 = a1.createCell(2);
		b3.setCellStyle(cellStyle);
		b3.setCellType(XSSFCell.CELL_TYPE_STRING);
		b3.setCellValue(new XSSFRichTextString("报告日期"));

		XSSFCell b4 = a1.createCell(3);
		b4.setCellStyle(cellStyle);
		b4.setCellType(XSSFCell.CELL_TYPE_STRING);
		b4.setCellValue(new XSSFRichTextString(new GetDate().getDate()));

		XSSFRow a2 = sheet.createRow(1);

		XSSFCell b5 = a2.createCell(0);
		b5.setCellStyle(cellStyle);
		b5.setCellType(XSSFCell.CELL_TYPE_STRING);
		b5.setCellValue(new XSSFRichTextString("交易指导"));

		// 在指定的索引处创建一行
		XSSFRow row = sheet.createRow(2);
		// 在指定的索引处创建 一列（单元格）
		XSSFCell dm = row.createCell(0);
		// 定义单元格为字符串类型
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// 在单元格输入内容
		dm.setCellStyle(cellStyle);
		dm.setCellValue(new XSSFRichTextString("股票代码"));
		XSSFCell mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellStyle(cellStyle);
		mrj.setCellValue(new XSSFRichTextString("股票名称"));
		XSSFCell mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellStyle(cellStyle);
		mcj.setCellValue(new XSSFRichTextString("买入价格"));
		XSSFCell sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellStyle(cellStyle);
		sshy.setCellValue(new XSSFRichTextString("明日跌停价"));
		XSSFCell zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellStyle(cellStyle);
		zg.setCellValue(new XSSFRichTextString("建仓数量"));
		XSSFCell cell = row.createCell(5);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("预计成交额"));
		cell = row.createCell(6);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("卖出价格"));
		cell = row.createCell(7);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("明日涨停价"));
		cell = row.createCell(8);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("建议卖出数量"));
		cell = row.createCell(9);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("预计成交金额"));

		// 写入数据
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
		// 新建一输出流并把相应的excel文件存盘

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workBook.write(os);
		byte[] fileContent = os.toByteArray();
		ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
		return is;
	}

	public ByteArrayInputStream onlySell(List onlyBuyList, String name)
			throws Exception {
		System.out.println("开始生成");
		// 创建工作薄
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 设置字体格式
		XSSFFont font = workBook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		XSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setFont(font);
		// 在工作薄中创建一工作表
		XSSFSheet sheet = workBook.createSheet();
		// 建立表头
		XSSFRow a1 = sheet.createRow(0);
		// sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));

		XSSFCell b1 = a1.createCell(0);
		b1.setCellStyle(cellStyle);
		b1.setCellType(XSSFCell.CELL_TYPE_STRING);
		b1.setCellValue(new XSSFRichTextString("账户"));

		XSSFCell b2 = a1.createCell(1);
		b2.setCellStyle(cellStyle);
		b2.setCellType(XSSFCell.CELL_TYPE_STRING);
		b2.setCellValue(new XSSFRichTextString(name));

		XSSFCell b3 = a1.createCell(2);
		b3.setCellStyle(cellStyle);
		b3.setCellType(XSSFCell.CELL_TYPE_STRING);
		b3.setCellValue(new XSSFRichTextString("报告日期"));

		XSSFCell b4 = a1.createCell(3);
		b4.setCellStyle(cellStyle);
		b4.setCellType(XSSFCell.CELL_TYPE_STRING);
		b4.setCellValue(new XSSFRichTextString(new GetDate().getDate()));

		XSSFRow a2 = sheet.createRow(1);

		XSSFCell b5 = a2.createCell(0);
		b5.setCellStyle(cellStyle);
		b5.setCellType(XSSFCell.CELL_TYPE_STRING);
		b5.setCellValue(new XSSFRichTextString("交易指导"));

		// 在指定的索引处创建一行
		XSSFRow row = sheet.createRow(2);
		// 在指定的索引处创建 一列（单元格）
		XSSFCell dm = row.createCell(0);
		// 定义单元格为字符串类型
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// 在单元格输入内容
		dm.setCellStyle(cellStyle);
		dm.setCellValue(new XSSFRichTextString("股票代码"));
		XSSFCell mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellStyle(cellStyle);
		mrj.setCellValue(new XSSFRichTextString("股票名称"));
		XSSFCell mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellStyle(cellStyle);
		mcj.setCellValue(new XSSFRichTextString("买入价格"));
		XSSFCell sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellStyle(cellStyle);
		sshy.setCellValue(new XSSFRichTextString("明日跌停价"));
		XSSFCell zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellStyle(cellStyle);
		zg.setCellValue(new XSSFRichTextString("建仓数量"));
		XSSFCell cell = row.createCell(5);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("预计成交额"));
		cell = row.createCell(6);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("卖出价格"));
		cell = row.createCell(7);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("明日涨停价"));
		cell = row.createCell(8);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("建议卖出数量"));
		cell = row.createCell(9);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("预计成交金额"));

		// 写入数据
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
		// 新建一输出流并把相应的excel文件存盘

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workBook.write(os);
		byte[] fileContent = os.toByteArray();
		ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
		return is;
	}

	public ByteArrayInputStream both(List onlyBuyList, String name)
			throws Exception {
		System.out.println("开始生成");
		// 创建工作薄
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 设置字体格式
		XSSFFont font = workBook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		XSSFCellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setFont(font);
		// 在工作薄中创建一工作表
		XSSFSheet sheet = workBook.createSheet();
		// 建立表头
		XSSFRow a1 = sheet.createRow(0);
		// sheet.addMergedRegion(new Region(0,(short)0,0,(short)1));

		XSSFCell b1 = a1.createCell(0);
		b1.setCellStyle(cellStyle);
		b1.setCellType(XSSFCell.CELL_TYPE_STRING);
		b1.setCellValue(new XSSFRichTextString("账户"));

		XSSFCell b2 = a1.createCell(1);
		b2.setCellStyle(cellStyle);
		b2.setCellType(XSSFCell.CELL_TYPE_STRING);
		b2.setCellValue(new XSSFRichTextString(name));

		XSSFCell b3 = a1.createCell(2);
		b3.setCellStyle(cellStyle);
		b3.setCellType(XSSFCell.CELL_TYPE_STRING);
		b3.setCellValue(new XSSFRichTextString("报告日期"));

		XSSFCell b4 = a1.createCell(3);
		b4.setCellStyle(cellStyle);
		b4.setCellType(XSSFCell.CELL_TYPE_STRING);
		b4.setCellValue(new XSSFRichTextString(new GetDate().getDate()));

		XSSFRow a2 = sheet.createRow(1);

		XSSFCell b5 = a2.createCell(0);
		b5.setCellStyle(cellStyle);
		b5.setCellType(XSSFCell.CELL_TYPE_STRING);
		b5.setCellValue(new XSSFRichTextString("交易指导"));

		// 在指定的索引处创建一行
		XSSFRow row = sheet.createRow(2);
		// 在指定的索引处创建 一列（单元格）
		XSSFCell dm = row.createCell(0);
		// 定义单元格为字符串类型
		dm.setCellType(XSSFCell.CELL_TYPE_STRING);
		// 在单元格输入内容
		dm.setCellStyle(cellStyle);
		dm.setCellValue(new XSSFRichTextString("股票代码"));
		XSSFCell mrj = row.createCell(1);
		mrj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mrj.setCellStyle(cellStyle);
		mrj.setCellValue(new XSSFRichTextString("股票名称"));
		XSSFCell mcj = row.createCell(2);
		mcj.setCellType(XSSFCell.CELL_TYPE_STRING);
		mcj.setCellStyle(cellStyle);
		mcj.setCellValue(new XSSFRichTextString("买入价格"));
		XSSFCell sshy = row.createCell(3);
		sshy.setCellType(XSSFCell.CELL_TYPE_STRING);
		sshy.setCellStyle(cellStyle);
		sshy.setCellValue(new XSSFRichTextString("明日跌停价"));
		XSSFCell zg = row.createCell(4);
		zg.setCellType(XSSFCell.CELL_TYPE_STRING);
		zg.setCellStyle(cellStyle);
		zg.setCellValue(new XSSFRichTextString("建仓数量"));
		XSSFCell cell = row.createCell(5);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("预计成交额"));
		cell = row.createCell(6);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("卖出价格"));
		cell = row.createCell(7);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("明日涨停价"));
		cell = row.createCell(8);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("建议卖出数量"));
		cell = row.createCell(9);
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(new XSSFRichTextString("预计成交金额"));

		// 写入数据
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
		// 新建一输出流并把相应的excel文件存盘

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workBook.write(os);
		byte[] fileContent = os.toByteArray();
		ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
		return is;
	}
}