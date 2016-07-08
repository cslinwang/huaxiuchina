package com.huaxiuchina.util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.huaxiuchina.dao.DaydealDao;
import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.model.Daydeal;

public class GuideProduce {
	GpDao gpDao = new GpDao();
	DaydealDao daydealDao = new DaydealDao();
	Daydeal daydeal; // 一个单个股票实体类
	List onlyBuy = new ArrayList(); // 存放只买股票的表格数据
	List onlySell = new ArrayList(); // 存放卖股票的表格数据
	List both = new ArrayList(); // 存放有卖买有卖股票的表格数据
	List all = new ArrayList(); // 存放当日所有交易股票信息
	List dm = new ArrayList(); // 存放当日所有交易股票代码
	List only = new ArrayList(); // 存放去重后的当日交易股票信息

	public List getOnly(String username) throws Exception {
		all = daydealDao.selectAll(username, new GetDate().getDate()); // 拿到所有股票
		// 循环获得股票代码
		for (int i = 0; i < all.size(); i++) {
			daydeal = (Daydeal) all.get(i);
			// System.out.println(temp.getDm());
			dm.add(daydeal.getDm());
		}
		// System.out.println(dm);
		// 去重，通过循环，先放到数组，如果遍历后该dm存在，删除。
		only.add(dm.get(0));
		// System.out.println(dm.get(0));
		for (int i = 1; i < dm.size(); i++) {
			only.add(dm.get(i));
			for (int j = 0; j < only.size() - 1; j++) {
				if (dm.get(i).equals(only.get(j))) {
					// System.out.println("remove"+j+only);
					only.remove(only.get(only.size() - 1));
				}
			}
		}
		System.out.println("only: " + only);
		return only;
	}

	public void check() throws Exception {
		List only = new GuideProduce().getOnly("cuikui");
		// 遍历查看股票买卖情况
		for (int i = 0; i < only.size(); i++) {
			java.text.DecimalFormat df = new DecimalFormat("#.0000"); // 格式化double
			double k = 1; // k值
			double j = 1; // J值
			int flag = 0; // 标志，看该股票什么情况，0代表只买，满值只卖。
			String dm1 = null; // 表格第一列数据
			String mc1 = null; // 表格第二列数据
			List temp = daydealDao.selectByDm(only.get(i).toString(), "cuikui",new GetDate().getDate());
			for (int l = 0; l < temp.size(); l++) {
				daydeal = (Daydeal) temp.get(l);
				dm1 = daydeal.getDm();
				mc1 = daydeal.getMc();
				if (daydeal.getMmbz().toString().equals("买入")) {
					flag++;
				}
			}
			System.out.println("flag" + flag);
			// 只买
			if (flag == temp.size()) {
				Double price = 0.00;
				int sum = 0;

				onlyBuy.add(dm1);
				onlyBuy.add(mc1);
				// 遍历算股票的交易总数量
				for (int l = 0; l < temp.size(); l++) {
					daydeal = (Daydeal) temp.get(l);
					sum += Integer.valueOf(daydeal.getCjsl());
				}
				// 遍历算股票的加权均价
				for (int l = 0; l < temp.size(); l++) {
					daydeal = (Daydeal) temp.get(l);
					price += Double.valueOf(daydeal.getCjjg())
							* Double.valueOf(daydeal.getCjsl()) / sum;
				}
				onlyBuy.add(df.format(price * k));
				onlyBuy.add(df.format(price * j));
				daydeal = (Daydeal) temp.get(temp.size()-1);
				onlyBuy.add(daydeal.getModel());
				onlyBuy.add(daydeal.getModel());
				System.out.println(onlyBuy);
				new GuideOut().onlyBuy(onlyBuy);
			} else if (flag == 0) {
				onlySell.add("");
			} else {
				both.add("");
			}
		}

	}

	/*
	 * public void test() { int[] a = { 1, 2, 2, 1, 2 }; List b = new
	 * ArrayList(); b.add(a[0]); for (int i = 1; i < a.length; i++) {
	 * b.add(a[i]); for (int j = 0; j < b.size() - 1; j++) {
	 * System.out.println(i + "=" + b.get(j)); if (a[i] ==
	 * Integer.valueOf(b.get(j).toString())) { b.remove(b.get(b.size() - 1)); }
	 * } } System.out.println(b); }
	 */

	/*
	 * public int ksGuide(Daydeal daydeal) { // 遍历找到上一次股票操作日期，并将结果存入oldList
	 * String oldDate = daydeal.getDate().toString(); List oldList = new
	 * ArrayList(); for (int j = 0; j < 365; j++) { System.out.println(oldDate);
	 * oldList = daydealDao.selectByDm(daydeal.getDm() .toString(), "cuikui",
	 * new GetDate().getDate()); if (oldList.size() != 0) { break; } oldDate =
	 * new GetDate().lastDate(oldDate); } if (oldList.size() == 0) { } else { //
	 * 遍历获得上次，买入卖出数
	 * 
	 * for (int j = 0; j < oldList.size(); j++) { Daydeal daydeal2 = (Daydeal)
	 * oldList.get(j); if (daydeal2.getMmbz().toString().equals("买入")) { buySum
	 * += Integer.valueOf(daydeal2.getCjsl() .toString()); } else { saleSum +=
	 * Integer.valueOf(daydeal2.getCjsl() .toString()); } }
	 * System.out.println("buySum: " + buySum); System.out.println("saleSum: " +
	 * saleSum); if (daydeal.getModel() < 10) { // 如果是模型一 // 拿到系数 int tecent =
	 * daydeal.getModel(); int actual = Integer.valueOf(daydeal.getCjsl()
	 * .toString()); // 如果建仓成功，系数加一 if (actual == base * tecent) { model += 1; }
	 * else if (base * tecent - actual > base (tecent - 1)) model = model;
	 * 
	 * } else { // 如果是模型二 }
	 * 
	 * 
	 * }
	 */

	public static void main(String[] args) throws Exception {
		new GuideProduce().check();
	}
}
