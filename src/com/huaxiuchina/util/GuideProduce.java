package com.huaxiuchina.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.huaxiuchina.dao.DaydealDao;
import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.model.Daydeal;
import com.huaxiuchina.model.Gp;

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

	public ByteArrayInputStream check(String name) throws Exception {
		List only = new GuideProduce().getOnly(name);
		// 遍历查看股票买卖情况
		for (int i = 0; i < only.size(); i++) {
			GpDao gpDao = new GpDao();

			java.text.DecimalFormat df = new DecimalFormat("#.00"); // 格式化double
			double k = 0; // k值
			double j = 0; // J值
			int flag = 0; // 标志，看该股票什么情况，0代表只买，满值只卖。
			String dm1 = null; // 表格第一列数据
			String mc1 = null; // 表格第二列数据
			List temp = daydealDao.selectByDm(only.get(i).toString(), name,
					new GetDate().getDate());
			for (int l = 0; l < temp.size(); l++) {
				daydeal = (Daydeal) temp.get(l);
				dm1 = daydeal.getDm();
				mc1 = daydeal.getMc();
				System.out.println(new GetDate().getDate() + "  " + dm1);
				k = ((Gp) gpDao.selectByDmAndDate(new GetDate().getDate(), dm1)
						.get(0)).getK();
				j = ((Gp) gpDao.selectByDmAndDate(new GetDate().getDate(), dm1)
						.get(0)).getJ();
				;
				if (daydeal.getMmbz().toString().equals("买入")) {
					flag++;
				}
			}
			Gp tempGp = (Gp) gpDao.selectByDm(dm1).get(0);
			System.out.println("flag" + flag);
			// 只买
			if (flag == temp.size()) {
				Double price = 0.00;
				int sum = 0;
				// 代码
				both.add(dm1);
				// 名称
				both.add(mc1);
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
				// 买入价格
				both.add(df.format(price * k));
				// 明日跌停价格
				both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 0.9));
				daydeal = (Daydeal) temp.get(temp.size() - 1);
				int model = daydeal.getModel();
				int base = Integer.valueOf(daydeal.getBase());
				if (model < 10) {
					model = model;
					// 建仓数量
					both.add(base * ((int) Math.pow(2, (model))));
					// 预计成交金额
					both.add(df.format((price * k)
							* (int) (base * ((int) Math.pow(2, (model))))));
					// 卖出价格
					both.add(df.format(price * j));
					// 明日涨停价格
					both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
					// 建议卖出数量
					both.add(base * ((int) Math.pow(2, (model))));
					// 预计成交金额
					both.add(df.format((price * j)
							* (int) (base * ((int) Math.pow(2, (model))))));
					System.out.println("buy");
					// return new GuideOut().both(both, name);
				} else {
					model -= 10;
					// 建仓数量
					both.add(base * ((int) Math.pow(1.5, (model))));
					// 预计成交金额
					both.add(df.format((price * k)
							* (int) (base * ((int) Math.pow(1.5, (model))))));
					// 卖出价格
					both.add(df.format(price * j));
					// 明日涨停价格
					both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
					// 建议卖出数量
					both.add(base * ((int) Math.pow(1.5, (model))));
					// 预计成交金额
					both.add(df.format((price * j)
							* (int) (base * ((int) Math.pow(1.5, (model))))));
					System.out.println("buy");
					// return new GuideOut().both(both, name);
				}
			}

			// 如果只卖
			else if (flag == 0) {
				Double price = 0.00;
				int sum = 0;
				// 代码
				both.add(dm1);
				// 名称
				both.add(mc1);
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
				// 买入价格
				both.add(df.format(price * k));
				// 明日跌停价格
				both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 0.9));
				daydeal = (Daydeal) temp.get(temp.size() - 1);
				int model = daydeal.getModel();
				int base = Integer.valueOf(daydeal.getBase());
				if (model < 10) {
					model = model;
					// 建仓数量
					both.add(base * ((int) Math.pow(2, (model))));
					// 预计成交金额
					both.add(df.format((price * k)
							* (int) (base * ((int) Math.pow(2, (model))))));
					// 卖出价格
					both.add(df.format(price * j));
					// 明日涨停价格
					both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
					// 建议卖出数量
					both.add(base * ((int) Math.pow(2, (model))));
					// 预计成交金额
					both.add(df.format((price * j)
							* (int) (base * ((int) Math.pow(2, (model))))));
					System.out.println("sell");
					// return new GuideOut().both(both, name);
				} else {
					model -= 10;
					// 建仓数量
					both.add(base * ((int) Math.pow(1.5, (model))));
					// 预计成交金额
					both.add(df.format((price * k)
							* (int) (base * ((int) Math.pow(1.5, (model))))));
					// 卖出价格
					both.add(df.format(price * j));
					// 明日涨停价格
					both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
					// 建议卖出数量
					both.add(base * ((int) Math.pow(1.5, (model))));
					// 预计成交金额
					both.add(df.format((price * j)
							* (int) (base * ((int) Math.pow(1.5, (model))))));
					System.out.println("sell");
					// return new GuideOut().both(both, name);
				}
			}
			// 如果有买有卖
			else {

			}
			int temp2 = 0;
			Double price = 0.00;
			int sumBuy = 0, sumSell = 0;
			int d = 0, e = 0;
			List buy = new ArrayList<Daydeal>();
			List sell = new ArrayList<Daydeal>();
			for (int l = 0; l < temp.size(); l++) {
				daydeal = (Daydeal) temp.get(l);
				if (daydeal.getMmbz().equals("买入")) {
					buy.add(daydeal);
				} else if (daydeal.getMmbz().equals("卖出")) {
					sell.add(daydeal);
				}
			}
			Comparator<Daydeal> comparator = new Comparator<Daydeal>() {
				public int compare(Daydeal d1, Daydeal d2) {
					// 先排年龄
					double temp = Double.valueOf(d1.getCjjg())
							- Double.valueOf(d2.getCjjg());
					if (temp >= 0) {
						return 1;
					} else {
						return -1;
					}
				}
			};

			Collections.sort(sell, comparator);
			Collections.sort(buy, comparator);
			for (int l = 0; l < buy.size(); l++) {
				daydeal = (Daydeal) buy.get(l);
				sumBuy += Integer.valueOf(daydeal.getCjsl());
			}
			for (int l = 0; l < sell.size(); l++) {
				daydeal = (Daydeal) sell.get(l);
				sumSell += Integer.valueOf(daydeal.getCjsl());
			}
			if (sumBuy >= sumSell) {
				e = buy.size();
				for (int l = sell.size() - 1; l >= 0; l--) {
					System.out.println("!");
					int temp1 = Integer.valueOf(((Daydeal) sell.get(l))
							.getCjsl());
					while (true) {
						if (buy.size() != 0) {
							if (e == buy.size()) {
								System.out.println("time");
								temp2 = Integer.valueOf(((Daydeal) buy.get(0))
										.getCjsl());
								int v = temp2 + d;
								System.out.println("buy: " + temp1
										+ "&&sell:  " + v);
							}
						}
						if (temp2 + d - temp1 >= 0) {
							sumBuy -= (temp1);
							d = temp2 + d - temp1;
							System.out.println("asdasd" + d);
							temp2 = 0;
							System.out.println("   " + d);
							e--;
							System.out.println(e);
							break;
						} else {
							d = temp2;
							if (buy.size() != 0) {
								buy.remove(0);
							}
						}
					}
					// 代码
					both.add(dm1);
					// 名称
					both.add(mc1);
					// 遍历算股票的交易总数量
					sumBuy = sumBuy;
					// 遍历算股票的加权均价
					daydeal = (Daydeal) buy.get(0);
					price += Double.valueOf(daydeal.getCjjg()) * d / sumBuy;
					if (buy.size() > 1) {
						for (int m = 1; m < temp.size(); m++) {
							daydeal = (Daydeal) buy.get(m);
							price += Double.valueOf(daydeal.getCjjg())
									* Double.valueOf(daydeal.getCjsl())
									/ sumBuy;
						}
					}
					// 买入价格
					both.add(df.format(price * k));
					
					// 明日跌停价格
					both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 0.9));
					daydeal = (Daydeal) temp.get(temp.size() - 1);
					int model = daydeal.getModel();
					int base = Integer.valueOf(daydeal.getBase());
					if (model < 10) {
						model = model;
						// 建仓数量
						both.add(base * ((int) Math.pow(2, (model))));
						// 预计成交金额
						both.add(df.format((price * k)
								* (int) (base * ((int) Math.pow(2, (model))))));
						// 卖出价格
						both.add(df.format(price * j));
						// 明日涨停价格
						both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
						// 建议卖出数量
						both.add(base * ((int) Math.pow(2, (model))));
						// 预计成交金额
						both.add(df.format((price * j)
								* (int) (base * ((int) Math.pow(2, (model))))));
						System.out.println("both");
						// return new GuideOut().both(both, name);
					} else {
						model -= 10;
						// 建仓数量
						both.add(base * ((int) Math.pow(1.5, (model))));
						// 预计成交金额
						both.add(df.format((price * k)
								* (int) (base * ((int) Math.pow(1.5, (model))))));
						// 卖出价格
						both.add(df.format(price * j));
						// 明日涨停价格
						both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
						// 建议卖出数量
						both.add(base * ((int) Math.pow(1.5, (model))));
						// 预计成交金额
						both.add(df.format((price * j)
								* (int) (base * ((int) Math.pow(1.5, (model))))));
						System.out.println("both");
						// return new GuideOut().both(both, name);
					}

				}
			} else if (sumBuy < sumSell) {
				e = sell.size();
				for (int l = buy.size() - 1; l >= 0; l--) {
					System.out.println("!");
					int temp1 = Integer.valueOf(((Daydeal) buy.get(l))
							.getCjsl());
					while (true) {
						if (sell.size() != 0) {
							if (e == sell.size()) {
								temp2 = Integer.valueOf(((Daydeal) sell
										.get(sell.size() - 1)).getCjsl());
								int v = temp2 + d;
								System.out.println("buy: " + temp1
										+ "&&sell:  " + v);
							}
						}
						if (temp2 + d - temp1 >= 0) {
							sumSell -= (temp1);
							d = temp2 + d - temp1;
							temp2 = 0;
							System.out.println("   " + d);
							e--;
							break;
						} else {
							d = temp2;
							if (sell.size() != 0) {
								sell.remove(sell.size() - 1);
							}
						}
					}
				}

				// 代码
				both.add(dm1);
				// 名称
				both.add(mc1);
				// 遍历算股票的交易总数量
				sumSell = sumSell;
				// 遍历算股票的加权均价
				daydeal = (Daydeal) sell.get(sell.size() - 1);
				price += Double.valueOf(daydeal.getCjjg()) * d / sumSell;
				if (sell.size() > 1) {
					for (int m = 0; m < temp.size() - 1; m++) {
						daydeal = (Daydeal) sell.get(m);
						price += Double.valueOf(daydeal.getCjjg())
								* Double.valueOf(daydeal.getCjsl()) / sumSell;
					}
				}
				// 买入价格
				both.add(df.format(price * k));
				// 明日跌停价格
				both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 0.9));
				daydeal = (Daydeal) temp.get(temp.size() - 1);
				int model = daydeal.getModel();
				int base = Integer.valueOf(daydeal.getBase());
				if (model < 10) {
					model = model;
					// 建仓数量
					both.add(base * ((int) Math.pow(2, (model))));
					// 预计成交金额
					both.add(df.format((price * k)
							* (int) (base * ((int) Math.pow(2, (model))))));
					// 卖出价格
					both.add(df.format(price * j));
					// 明日涨停价格
					both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
					// 建议卖出数量
					both.add(base * ((int) Math.pow(2, (model))));
					// 预计成交金额
					both.add(df.format((price * j)
							* (int) (base * ((int) Math.pow(2, (model))))));
					System.out.println("both");
					return new GuideOut().both(both, name);
				} else {
					model -= 10;
					// 建仓数量
					both.add(base * ((int) Math.pow(1.5, (model))));
					// 预计成交金额
					both.add(df.format((price * k)
							* (int) (base * ((int) Math.pow(1.5, (model))))));
					// 卖出价格
					both.add(df.format(price * j));
					// 明日涨停价格
					both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
					// 建议卖出数量
					both.add(base * ((int) Math.pow(1.5, (model))));
					// 预计成交金额
					both.add(df.format((price * j)
							* (int) (base * ((int) Math.pow(1.5, (model))))));
					System.out.println(both);
					// return new GuideOut().both(both, name);
				}
			}
		}
System.out.println(both);
		return new GuideOut().both(both, name);

	}

	public static void main(String[] args) throws Exception {
		new GuideProduce().check("wang");
	}
}
