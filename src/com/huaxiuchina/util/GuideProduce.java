package com.huaxiuchina.util;

import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.ss.formula.functions.DGet;

import com.huaxiuchina.dao.DaydealDao;
import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.dao.ModelDao;
import com.huaxiuchina.dao.StatusDao;
import com.huaxiuchina.dao.TDao;
import com.huaxiuchina.model.Daydeal;
import com.huaxiuchina.model.Gp;
import com.huaxiuchina.model.Model;
import com.huaxiuchina.model.Status;
import com.huaxiuchina.model.T;

public class GuideProduce {
	GpDao gpDao = new GpDao();
	DaydealDao daydealDao = new DaydealDao();
	Daydeal daydeal; // 一个单个股票实体类
	List buyGuide = new ArrayList(); // 存放买入指导
	List sellGuide = new ArrayList(); // 存放卖出指导
	List singleBuy = new ArrayList(); // 存放一组数据
	List singleSell = new ArrayList(); // 存放一组数据
	List all = new ArrayList(); // 存放当日所有交易股票信息
	List both = new ArrayList(); // 存放当日所有交易股票信息
	List dm = new ArrayList(); // 存放当日所有交易股票代码
	List only = new ArrayList(); // 存放去重后的当日交易股票信息
	List temp = new ArrayList(); // 临时变量
	java.text.DecimalFormat df = new DecimalFormat("#.00"); // 格式化double
	double k = 0; // k值
	double j = 0; // J值
	String dm1 = null; // 表格第一列数据
	String mc1 = null; // 表格第二列数据
	Gp tempGp = new Gp();
	Model modell = new Model();
	ModelDao modelDao = new ModelDao();
	Status status = new Status();
	T t = new T();
	TDao tDao = new TDao();

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
		if (dm.size() > 1) {
			for (int i = 1; i < dm.size(); i++) {
				only.add(dm.get(i));
				for (int j = 0; j < only.size() - 1; j++) {
					if (dm.get(i).equals(only.get(j))) {
						// System.out.println("remove"+j+only);
						only.remove(only.get(only.size() - 1));
					}
				}
			}
		}
		// System.out.println("only: " + only);
		return only;
	}

	public List getOnlyByUser(String username) throws Exception {
		all = modelDao.selectByName1(username); // 拿到所有股票
		// 循环获得股票代码
		for (int i = 0; i < all.size(); i++) {
			modell = (Model) all.get(i);
			// System.out.println(temp.getDm());
			dm.add(modell.getDm());
		}
		// System.out.println(dm);
		// 去重，通过循环，先放到数组，如果遍历后该dm存在，删除。
		only.add(dm.get(0));
		// System.out.println(dm.get(0));
		if (dm.size() > 1) {
			for (int i = 1; i < dm.size(); i++) {
				only.add(dm.get(i));
				for (int j = 0; j < only.size() - 1; j++) {
					if (dm.get(i).equals(only.get(j))) {
						// System.out.println("remove"+j+only);
						only.remove(only.get(only.size() - 1));
					}
				}
			}
		}
		// System.out.println("only: " + only);
		return only;
	}

	public void check(String name) throws Exception {
		List only = new GuideProduce().getOnly(name);
		// 遍历查看股票买卖情况
		for (int i = 0; i < only.size(); i++) {
			int flag = 0; // 标志，看该股票什么情况，0代表只买，满值只卖。
			GpDao gpDao = new GpDao();
			temp = daydealDao.selectByDm(only.get(i).toString(), name,
					new GetDate().getDate());
			for (int l = 0; l < temp.size(); l++) {
				daydeal = (Daydeal) temp.get(l);
				System.out.println("t_daydealid: " + daydeal.getDealid());
				dm1 = daydeal.getDm();
				mc1 = daydeal.getMc();
				// System.out.println(new GetDate().getDate() + "  " + dm1);
				/*
				 * k = ((Gp) gpDao.selectByDmAndDate(new GetDate().getDate(),
				 * dm1) .get(0)).getK(); j = ((Gp) gpDao.selectByDmAndDate(new
				 * GetDate().getDate(), dm1) .get(0)).getJ();
				 */
				if (daydeal.getMmbz().toString().equals("买入")) {
					flag++;
				}
			}
			/* tempGp = (Gp) gpDao.selectByDm(dm1).get(0); */
			System.out.println("flag: " + flag);
			// 只买
			if (flag == temp.size()) {
				new GuideProduce().onlyBuy(name, dm1, mc1, temp);
				System.out.println("buy");

			}
			// 如果只卖
			else if (flag == 0) {
				new GuideProduce().onlySell(name, dm1, mc1, temp);
				System.out.println("sell");
			}
			// 如果有买有卖
			else {
				new GuideProduce().both(name, dm1, mc1, temp);
				System.out.println("both");
			}
		}
		// return new GuideOut().both(both, name);

	}

	// 如果只买
	public void onlyBuy(String name, String dm1, String mc1, List temp)
			throws Exception {
		Double price = 0.00;
		int sum = 0;
		// 用户
		status.setFalg("买");
		modell.setUser(name);
		status.setName(name);
		// 代码
		modell.setDm(dm1);
		modell.setMc(mc1);
		status.setDm(dm1);
		status.setMc(mc1);
		// 遍历算股票的交易总数量
		for (int l = 0; l < temp.size(); l++) {
			daydeal = (Daydeal) temp.get(l);
			sum += Integer.valueOf(daydeal.getCjsl());
		}
		// System.out.println("t_sum"+sum);
		// 遍历算股票的加权均价
		double priceToday = 0;
		double peiceLastday = 0;
		for (int l = 0; l < temp.size(); l++) {
			daydeal = (Daydeal) temp.get(l);
			price += Double.valueOf(daydeal.getCjjg())
					* Double.valueOf(daydeal.getCjsl()) / sum;
			priceToday = price;
		}
		// 买入价格
		modell.setPrice(df.format(price));
		status.setPrice(df.format(price));
		status.setDate(new GetDate().getDate());
		status.setSum(String.valueOf(sum));
		new StatusDao().add(status);
		System.out.println("今日买");
		// 明日跌停价格
		// System.out.println("t_dm1 " + dm1);
		// 获得最新一次交易记录
		daydeal = (Daydeal) temp.get(temp.size() - 1);
		// 拿到最新的model base
		int model = daydeal.getModel();
		int base = Integer.valueOf(daydeal.getBase());
		// 设立基数
		modell.setBase(String.valueOf(base));
		// 接受函数返回的model的数量和模型阶段
		Model modellTemp = new Model();
		if (model < 10) {
			modellTemp = new GuideProduce().onlyBuyModelType(model, 2, base,
					price, temp);
		} else {
			modellTemp = new GuideProduce().onlyBuyModelType(model - 10, 1.5,
					base, price, temp);
		}
		temp = modelDao.selectByDm(dm1, name,
				Integer.valueOf(modellTemp.getModel()));
		modell.setSum(modellTemp.getSum());
		modell.setModel(modellTemp.getModel());
		// 修改价格
		if (temp.size() == 0) {
			modelDao.add(modell);
		} else {
			Model modelll = new Model();
			modelll = (Model) temp.get(0);
			modelll.getPrice();
			modelll.getSum();
			price = Double.valueOf(modelll.getPrice())
					* Integer.valueOf(modelll.getSum())
					/ Integer.valueOf(modell.getSum())
					+ Double.valueOf(modell.getPrice())
					* (Integer.valueOf(modell.getSum()) - Integer
							.valueOf(modelll.getSum()))
					/ Integer.valueOf(modell.getSum());
			peiceLastday = price;
			modell.setPrice(df.format(price));
			modell.setMid(modelll.getMid());
			if (modell.getModel() < 10) {
				// 判断是否超标
				int tempNum = (int) (Integer.valueOf(modell.getSum()) - ((int) Integer
						.valueOf(modell.getBase()) * (Math.pow(2,
						(modell.getModel() - 1)))));
				if (tempNum > 0) {
					modell.setSum(String.valueOf(Integer.valueOf(modell
							.getSum()) - tempNum));
					modelDao.update(modell);
					modell.setModel(modell.getModel() + 1);
					modell.setSum(String.valueOf(tempNum));
					modell.setPrice(String.valueOf(priceToday));
					modelDao.add(modell);
				} else {
					modelDao.update(modell);
				}
			} else {
				int tempNum = (int) (Integer.valueOf(modell.getSum()) - ((int) Integer
						.valueOf(modell.getBase()) * (Math.pow(1.5,
						(modell.getModel() - 11)))));
				if (tempNum > 0) {
					modell.setSum(String.valueOf(Integer.valueOf(modell
							.getSum()) - tempNum));
					modelDao.update(modell);
					modell.setModel(modell.getModel() + 1);
					modell.setSum(String.valueOf(tempNum));
					modell.setPrice(String.valueOf(priceToday));
					modelDao.add(modell);
				} else {
					modelDao.update(modell);
				}
			}

		}
		// 如果买了两次建仓，修正上次建仓
		List aList = modelDao.selectByDm(modell.getDm(), modell.getUser());
		aList.remove(aList.size() - 1);
		System.out.println("建仓修正");
		for (int i = 0; i < aList.size(); i++) {
			Model modell1 = ((Model) aList.get(i));
			int sum11 = Integer.valueOf(modell1.getSum());
			int model1 = (modell1.getModel());
			int sum22 = 0;
			if (model1 < 10) {
				sum22 = (int) ((int) Integer.valueOf(modell1.getBase()) * (Math
						.pow(2, (modell1.getModel() - 1))));
			} else {
				sum22 = (int) ((int) Integer.valueOf(modell1.getBase()) * (Math
						.pow(1.5, (modell1.getModel() - 11))));
			}

			if (sum11 != sum22) {
				System.out.println("sum11" + sum11 + "sum22" + sum22);
				price = (Double.valueOf(modell1.getPrice()) * sum11 + priceToday
						* (sum22 - sum11))
						/ sum22;
				modell1.setPrice(df.format(price));
				modell1.setSum(String.valueOf(sum22));
				modelDao.update(modell1);
			}

		}
	}

	// 如果只买模型类别1和2
	public Model onlyBuyModelType(int model, double multiple, int base,
			double price, List temp) {
		// System.out.println("t_multiple: " + (base * Math.pow(multiple,
		// (1))));
		double p = 0;
		// 预期建仓数量
		// System.out.println("t_model"+model);
		for (int l = 0; l < model; l++) {
			p += base * (Math.pow(multiple, (l)));
		}
		int tepsum = Integer.valueOf(((Daydeal) temp.get(temp.size() - 1))
				.getSum());
		System.out.println("t_tepsum" + tepsum);
		System.out.println("t_p" + p);
		if (tepsum == p) {
			p = base * (Math.pow(multiple, (model)));
			// 设立model数量sum
			// System.out.println("t_"+String.valueOf((model * base)));
			modell.setSum(String.valueOf((int) (base * (Math.pow(multiple,
					(model - 1))))));
			// 设立model模型model
			if (multiple == 1.5) {
				modell.setModel(model + 10);
			} else {
				modell.setModel(model);
			}
		} else {
			// System.out.println("t_tepsum: " + tepsum);
			// System.out.println("t_p: " + (int)p);
			p = tepsum - p;
			modell.setSum(String.valueOf(((int) p)));
			// 设立model模型
			System.out.println("倍数： " + multiple);
			if (multiple == 1.5) {
				System.out.println();
				modell.setModel(model + 11);
			} else {
				modell.setModel(model + 1);
			}
		}
		return modell;
	}

	// 如果只卖
	public void onlySell(String name, String dm1, String mc1, List temp)
			throws Exception {
		Double price = 0.00;
		int sum = 0;
		// 用户
		status.setFalg("卖");
		modell.setUser(name);
		status.setName(name);
		// 代码
		modell.setDm(dm1);
		modell.setMc(mc1);

		status.setDm(dm1);
		status.setMc(mc1);

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
		status.setDate(new GetDate().getDate());
		status.setPrice(df.format(price));
		status.setSum(String.valueOf(sum));
		new StatusDao().add(status);
		System.out.println("今日卖");
		daydeal = (Daydeal) temp.get(temp.size() - 1);
		int model = daydeal.getModel();
		int base = Integer.valueOf(daydeal.getBase());
		System.out.println("base" + base);
		if (model < 10) {
			new GuideProduce().onlySellModelType(model, 2, base, price, temp,
					dm1, name);
		} else {
			new GuideProduce().onlySellModelType(model - 10, 1.5, base, price,
					temp, dm1, name);
		}
	}

	// 如果只卖模型类别1和2
	public Model onlySellModelType(int model, double multiple, int base,
			double price, List temp, String dm1, String name) {
		System.out.println("model= " + model + " ");
		double p = 0;
		// 应该的数量
		System.out.println("multiple" + multiple);
		for (int l = 0; l < model; l++) {
			p += base * (Math.pow(multiple, (l)));
		}
		// 数字
		int tepsum = Integer.valueOf(((Daydeal) temp.get(temp.size() - 1))
				.getSum());
		// 拿到模型信息
		temp = modelDao.selectByDm(dm1, name);
		Model modellTemp = null;
		if (temp.size() != 0) {
			modellTemp = (Model) temp.get(temp.size() - 1);
		}

		while (true) {
			if (temp.size() > 0) {
				int modelCompare = ((Model) temp.get(temp.size() - 1))
						.getModel();
				if (modelCompare > 10) {
					modelCompare -= 10;
				}
				if (modelCompare > (model + 1)) {
					// modelDao.delete(((Model) temp.get(temp.size() - 1)));
					temp.remove(temp.remove(temp.size() - 1));
				}
				break;
			}
			break;
		}
		System.out.println(temp.size());
		if (temp.size() != 0) {
			modellTemp = (Model) temp.get(temp.size() - 1);
		}
		System.out.println("t_tepsum: " + tepsum);
		System.out.println("t_p: " + p);
		if (tepsum == (int) p) {
			p = base * ((int) Math.pow(multiple, (model)));
			// 设立model数量sum
			// System.out.println("t_"+String.valueOf((model * base)));
			modell.setSum(String.valueOf((int) (base * (Math.pow(multiple,
					(model - 1))))));
			// 设立model模型model
			System.out.println("delete");
			if (temp.size() != 0) {
				modelDao.delete(modellTemp);
			}

		} else if (tepsum == 0) {
			modelDao.delete(modellTemp);
		} else {
			p = tepsum - p;
			modellTemp.setSum(String.valueOf((int) p));
			modelDao.update(modellTemp);
		}
		// 删除多余的模型

		int modelDelete = modellTemp.getModel();
		String userDelete = modellTemp.getUser();
		String dmDelete = modellTemp.getDm();
		List<Model> modellDelete = modelDao.selectByDm(dmDelete, userDelete);
		int countDelete = modellDelete.size() - 1;
		while (modelDelete < modellDelete.get(countDelete).getModel()) {
			modelDao.delete(modellDelete.get(countDelete));
			countDelete--;
		}
		// 建仓数量
		// return new GuideOut().both(both, name);
		return modell;
	}

	// 如果有买有卖
	public void both(String name, String dm1, String mc1, List temp)
			throws Exception {

		// 做T指导模型
		t.setDate(new GetDate().getDate());
		t.setUser(name);
		t.setDm(dm1);
		t.setMc(mc1);

		int temp2 = 0;
		Double price = 0.00;
		int sumBuy = 0, sumSell = 0;
		int d = 0, e = 0;
		int base = 0;
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
				//
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
		for (int i = 0; i < buy.size(); i++) {
			System.out.println(((Daydeal) buy.get(i)).getDealid());
		}
		for (int l = 0; l < buy.size(); l++) {
			daydeal = (Daydeal) buy.get(l);
			sumBuy += Integer.valueOf(daydeal.getCjsl());
		}
		for (int l = 0; l < sell.size(); l++) {
			daydeal = (Daydeal) sell.get(l);
			sumSell += Integer.valueOf(daydeal.getCjsl());
		}
		// 接受函数返回的model的数量和模型阶段
		Model modellTemp = new Model();
		status.setName(name);
		status.setMc(mc1);
		status.setDm(dm1);
		status.setDate(new GetDate().getDate());
		System.out.println("sumBuy: " + sumBuy + " sumSell: " + sumSell);
		// 做T指导
		if (sumBuy == sumSell) {
			int TBuyPrice = 0, TSellPrice = 0;
			for (int i = 0; i < buy.size(); i++) {
				TBuyPrice += Integer.valueOf(((Daydeal) buy.get(i)).getCjjg())
						* Integer.valueOf(((Daydeal) buy.get(i)).getCjsl());
			}
			for (int i = 0; i < sell.size(); i++) {
				TSellPrice += Integer
						.valueOf(((Daydeal) sell.get(i)).getCjjg())
						* Integer.valueOf(((Daydeal) sell.get(i)).getCjsl());
			}
			// 买入做T
			t.setFc("买入");
			t.setJg(String.valueOf(TBuyPrice / sumBuy));
			t.setSl(String.valueOf(sumBuy));
			tDao.add(t);
			// 卖出做T
			t.setFc("卖出");
			t.setJg(String.valueOf(TSellPrice / sumSell));
			t.setSl(String.valueOf(sumSell));
			tDao.add(t);

		} else {
			int TBuyPrice = 0, TSellPrice = 0;
			int TSum = 0;
			for (int i = 0; i < buy.size(); i++) {
				int TTBuySum = Integer
						.valueOf(((Daydeal) buy.get(i)).getCjsl());
				for (int j = 0; j < sell.size(); j++) {
					int TTSellSum = Integer.valueOf(((Daydeal) sell.get(j))
							.getCjsl());
					// 找到买入卖出相等的所有交易
					if (TTBuySum == TTSellSum) {
						TSum += TTBuySum;
						TBuyPrice += Integer.valueOf(((Daydeal) buy.get(i))
								.getCjjg())
								* Integer.valueOf(((Daydeal) buy.get(i))
										.getCjsl());
						TSellPrice += Integer.valueOf(((Daydeal) sell.get(j))
								.getCjjg())
								* Integer.valueOf(((Daydeal) sell.get(j))
										.getCjsl());
					}
				}

			}
			t.setFc("买入");
			t.setJg(String.valueOf(TBuyPrice / TSum));
			t.setSl(String.valueOf(TSum));
			t.setSy(String.valueOf(TSellPrice - TBuyPrice));
			tDao.add(t);
			// 卖出做T
			t.setFc("卖出");
			t.setJg(String.valueOf(TSellPrice / TSum));
			t.setSl(String.valueOf(TSum));
			tDao.add(t);
		}

		// 交易指导
		if (sumBuy >= sumSell) {
			e = buy.size();
			d = 0;
			double priceToday = 0;
			int model = 0;
			int flag = 0;
			System.out.println(sell.size());
			for (int l = sell.size() - 1; l >= 0; l--) {
				System.out.println("买多");
				int temp1 = Integer.valueOf(((Daydeal) sell.get(l)).getCjsl());
				System.out.println(e + " " + buy.size());
				while (true) {
					// System.out.println(buy.size());
					// System.out.println(e);
					if (buy.size() != 0) {
						if (flag == 0) {
							temp2 = Integer.valueOf(((Daydeal) buy.get(0))
									.getCjsl());
							int v = temp2 + d;
							System.out
									.println("sell: " + temp1 + " buy:  " + v);
						}
					}
					if (temp2 + d - temp1 >= 0) {
						sumBuy -= (temp1);
						d = temp2 + d - temp1;
						temp2 = 0;
						System.out.println("t_d：" + d);
						flag = 1;
						break;
					} else {
						d += temp2;
						if (buy.size() != 0) {
							flag = 0;
							buy.remove(0);
						}
					}
				}
			}
			// 用户
			modell.setUser(name);
			// 代码
			modell.setMc(mc1);
			modell.setDm(dm1);

			// 遍历算股票的交易总数量
			sumBuy = sumBuy;
			// 如果买卖抵消直接返回
			if (sumBuy == 0) {
				return;
			}
			// 遍历算股票的加权均价
			daydeal = (Daydeal) buy.get(0);
			price += Double.valueOf(daydeal.getCjjg()) * d / sumBuy;
			if (buy.size() > 1) {
				for (int m = 1; m < buy.size(); m++) {
					daydeal = (Daydeal) buy.get(m);
					System.out.println("id" + daydeal.getDealid());
					price += Double.valueOf(daydeal.getCjjg())
							* Double.valueOf(daydeal.getCjsl()) / sumBuy;
				}
			}
			status.setFalg("买");
			status.setPrice(df.format(price));
			status.setSum(String.valueOf(sumBuy));
			new StatusDao().add(status);
			System.out.println("今日买");
			System.out.println("priceToday " + price);
			priceToday = price;
			// modell的价格
			modell.setPrice(df.format(price));
			daydeal = (Daydeal) temp.get(temp.size() - 1);
			model = daydeal.getModel();
			base = Integer.valueOf(daydeal.getBase());
			// modell的base
			modell.setBase(String.valueOf(base));
			if (model < 10) {
				modellTemp = new GuideProduce().onlyBuyModelType(model, 2,
						base, price, temp);
			} else {
				modellTemp = new GuideProduce().onlyBuyModelType(model - 10,
						1.5, base, price, temp);
			}
			// 对结果进行处理
			temp = modelDao.selectByDm(dm1, name,
					Integer.valueOf(modellTemp.getModel()));
			modell.setSum(modellTemp.getSum());
			modell.setModel(modellTemp.getModel());
			// 修改价格
			System.out.println("t___temp.size()" + temp.size());
			if (temp.size() == 0) {
				modelDao.add(modell);
			} else {
				Model modelll = new Model();
				modelll = (Model) temp.get(0);
				modelll.getPrice();
				modelll.getSum();
				price = Double.valueOf(modelll.getPrice())
						* (Integer.valueOf(modelll.getSum()))
						/ Integer.valueOf(modell.getSum()) + priceToday
						* sumBuy / Integer.valueOf(modell.getSum());
				modell.setPrice(df.format(price));
				modell.setMid(modelll.getMid());
				modelDao.update(modell);
			}

			// 如果买了两次建仓，修正上次建仓
			List aList = modelDao.selectByDm(modell.getDm(), modell.getUser());
			aList.remove(aList.size() - 1);
			for (int i = 0; i < aList.size(); i++) {
				Model modell1 = ((Model) aList.get(i));
				int sum11 = Integer.valueOf(modell1.getSum());
				int model1 = (modell1.getModel());
				int sum22 = 0;
				if (model1 < 10) {
					sum22 = (int) ((int) Integer.valueOf(modell1.getBase()) * (Math
							.pow(2, (modell1.getModel() - 1))));
				} else {
					sum22 = (int) ((int) Integer.valueOf(modell1.getBase()) * (Math
							.pow(1.5, (modell1.getModel() - 11))));
				}

				if (sum11 != sum22) {
					price = (Double.valueOf(modell1.getPrice()) * sum11 + priceToday
							* (sum22 - sum11))
							/ sum22;
					modell1.setPrice(df.format(price));
					modell1.setSum(String.valueOf(sum22));
					modelDao.update(modell1);
				}

			}
		} else if (sumBuy < sumSell) {
			e = sell.size();
			System.out.println("sell.size(): " + e);
			d = 0;
			int model = 0;
			int flag = 0;
			for (int l = 0; l < buy.size(); l++) {
				System.out.println("卖多");
				// 拿到卖的每一笔交易
				int temp1 = Integer.valueOf(((Daydeal) buy.get(l)).getCjsl());
				while (true) {
					if (sell.size() != 0) {
						if (flag == 0) {
							System.out.println("sell.size(): " + sell.size()
									+ " e: " + e);
							temp2 = Integer.valueOf(((Daydeal) sell.get(sell
									.size() - 1)).getCjsl());
							int v = temp2 + d;
							System.out.println("buy: " + temp1 + "&&sell:  "
									+ v);
						}
					}
					if (temp2 + d - temp1 >= 0) {
						sumSell -= (temp1);
						d = temp2 + d - temp1;
						temp2 = 0;
						System.out.println("t_d: " + d);
						flag = 1;
						break;
					} else {
						d += temp2;
						// System.out.println("D"+d);
						if (sell.size() != 0) {
							flag = 0;
							sell.remove(sell.size() - 1);
						}
					}
				}
			}
			System.out.println("t__d:" + d);
			// 用户
			modell.setUser(name);
			// 代码
			modell.setDm(dm1);
			modell.setDm(mc1);
			// 遍历算股票的交易总数量
			sumSell = sumSell;
			// 遍历算股票的加权均价
			daydeal = (Daydeal) sell.get(sell.size() - 1);
			price += Double.valueOf(daydeal.getCjjg()) * d / sumSell;
			if (sell.size() > 1) {
				for (int m = 0; m < sell.size() - 1; m++) {
					daydeal = (Daydeal) sell.get(m);
					price += Double.valueOf(daydeal.getCjjg())
							* Double.valueOf(daydeal.getCjsl()) / sumSell;
				}
			}
			status.setFalg("卖");
			status.setPrice(df.format(price));
			status.setSum(String.valueOf(sumSell));
			new StatusDao().add(status);
			System.out.println("今日卖");
			// model price
			System.out.println("t__price:" + price);
			modell.setPrice(df.format(price));
			daydeal = (Daydeal) temp.get(temp.size() - 1);
			model = daydeal.getModel();
			base = Integer.valueOf(daydeal.getBase());
			// model base
			modell.setBase(String.valueOf(base));
			if (model < 10) {
				modellTemp = new GuideProduce().onlySellModelType(model, 2,
						base, price, temp, dm1, name);

			} else {
				modellTemp = new GuideProduce().onlySellModelType(model - 10,
						1.5, base, price, temp, dm1, name);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new GuideProduce().check("root");
	}
}
