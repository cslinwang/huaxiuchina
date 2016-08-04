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
	Daydeal daydeal; // һ��������Ʊʵ����
	List onlyBuy = new ArrayList(); // ���ֻ���Ʊ�ı������
	List onlySell = new ArrayList(); // �������Ʊ�ı������
	List both = new ArrayList(); // ���������������Ʊ�ı������
	List all = new ArrayList(); // ��ŵ������н��׹�Ʊ��Ϣ
	List dm = new ArrayList(); // ��ŵ������н��׹�Ʊ����
	List only = new ArrayList(); // ���ȥ�غ�ĵ��ս��׹�Ʊ��Ϣ

	public List getOnly(String username) throws Exception {
		all = daydealDao.selectAll(username, new GetDate().getDate()); // �õ����й�Ʊ
		// ѭ����ù�Ʊ����
		for (int i = 0; i < all.size(); i++) {
			daydeal = (Daydeal) all.get(i);
			// System.out.println(temp.getDm());
			dm.add(daydeal.getDm());
		}
		// System.out.println(dm);
		// ȥ�أ�ͨ��ѭ�����ȷŵ����飬����������dm���ڣ�ɾ����
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
		// �����鿴��Ʊ�������
		for (int i = 0; i < only.size(); i++) {
			GpDao gpDao = new GpDao();

			java.text.DecimalFormat df = new DecimalFormat("#.00"); // ��ʽ��double
			double k = 0; // kֵ
			double j = 0; // Jֵ
			int flag = 0; // ��־�����ù�Ʊʲô�����0����ֻ����ֵֻ����
			String dm1 = null; // ����һ������
			String mc1 = null; // ���ڶ�������
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
				if (daydeal.getMmbz().toString().equals("����")) {
					flag++;
				}
			}
			Gp tempGp = (Gp) gpDao.selectByDm(dm1).get(0);
			System.out.println("flag" + flag);
			// ֻ��
			if (flag == temp.size()) {
				Double price = 0.00;
				int sum = 0;
				// ����
				both.add(dm1);
				// ����
				both.add(mc1);
				// �������Ʊ�Ľ���������
				for (int l = 0; l < temp.size(); l++) {
					daydeal = (Daydeal) temp.get(l);
					sum += Integer.valueOf(daydeal.getCjsl());
				}
				// �������Ʊ�ļ�Ȩ����
				for (int l = 0; l < temp.size(); l++) {
					daydeal = (Daydeal) temp.get(l);
					price += Double.valueOf(daydeal.getCjjg())
							* Double.valueOf(daydeal.getCjsl()) / sum;
				}
				// ����۸�
				both.add(df.format(price * k));
				// ���յ�ͣ�۸�
				both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 0.9));
				daydeal = (Daydeal) temp.get(temp.size() - 1);
				int model = daydeal.getModel();
				int base = Integer.valueOf(daydeal.getBase());
				if (model < 10) {
					model = model;
					// ��������
					both.add(base * ((int) Math.pow(2, (model))));
					// Ԥ�Ƴɽ����
					both.add(df.format((price * k)
							* (int) (base * ((int) Math.pow(2, (model))))));
					// �����۸�
					both.add(df.format(price * j));
					// ������ͣ�۸�
					both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
					// ������������
					both.add(base * ((int) Math.pow(2, (model))));
					// Ԥ�Ƴɽ����
					both.add(df.format((price * j)
							* (int) (base * ((int) Math.pow(2, (model))))));
					System.out.println("buy");
					// return new GuideOut().both(both, name);
				} else {
					model -= 10;
					// ��������
					both.add(base * ((int) Math.pow(1.5, (model))));
					// Ԥ�Ƴɽ����
					both.add(df.format((price * k)
							* (int) (base * ((int) Math.pow(1.5, (model))))));
					// �����۸�
					both.add(df.format(price * j));
					// ������ͣ�۸�
					both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
					// ������������
					both.add(base * ((int) Math.pow(1.5, (model))));
					// Ԥ�Ƴɽ����
					both.add(df.format((price * j)
							* (int) (base * ((int) Math.pow(1.5, (model))))));
					System.out.println("buy");
					// return new GuideOut().both(both, name);
				}
			}

			// ���ֻ��
			else if (flag == 0) {
				Double price = 0.00;
				int sum = 0;
				// ����
				both.add(dm1);
				// ����
				both.add(mc1);
				// �������Ʊ�Ľ���������
				for (int l = 0; l < temp.size(); l++) {
					daydeal = (Daydeal) temp.get(l);
					sum += Integer.valueOf(daydeal.getCjsl());
				}
				// �������Ʊ�ļ�Ȩ����
				for (int l = 0; l < temp.size(); l++) {
					daydeal = (Daydeal) temp.get(l);
					price += Double.valueOf(daydeal.getCjjg())
							* Double.valueOf(daydeal.getCjsl()) / sum;
				}
				// ����۸�
				both.add(df.format(price * k));
				// ���յ�ͣ�۸�
				both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 0.9));
				daydeal = (Daydeal) temp.get(temp.size() - 1);
				int model = daydeal.getModel();
				int base = Integer.valueOf(daydeal.getBase());
				if (model < 10) {
					model = model;
					// ��������
					both.add(base * ((int) Math.pow(2, (model))));
					// Ԥ�Ƴɽ����
					both.add(df.format((price * k)
							* (int) (base * ((int) Math.pow(2, (model))))));
					// �����۸�
					both.add(df.format(price * j));
					// ������ͣ�۸�
					both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
					// ������������
					both.add(base * ((int) Math.pow(2, (model))));
					// Ԥ�Ƴɽ����
					both.add(df.format((price * j)
							* (int) (base * ((int) Math.pow(2, (model))))));
					System.out.println("sell");
					// return new GuideOut().both(both, name);
				} else {
					model -= 10;
					// ��������
					both.add(base * ((int) Math.pow(1.5, (model))));
					// Ԥ�Ƴɽ����
					both.add(df.format((price * k)
							* (int) (base * ((int) Math.pow(1.5, (model))))));
					// �����۸�
					both.add(df.format(price * j));
					// ������ͣ�۸�
					both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
					// ������������
					both.add(base * ((int) Math.pow(1.5, (model))));
					// Ԥ�Ƴɽ����
					both.add(df.format((price * j)
							* (int) (base * ((int) Math.pow(1.5, (model))))));
					System.out.println("sell");
					// return new GuideOut().both(both, name);
				}
			}
			// �����������
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
				if (daydeal.getMmbz().equals("����")) {
					buy.add(daydeal);
				} else if (daydeal.getMmbz().equals("����")) {
					sell.add(daydeal);
				}
			}
			Comparator<Daydeal> comparator = new Comparator<Daydeal>() {
				public int compare(Daydeal d1, Daydeal d2) {
					// ��������
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
					// ����
					both.add(dm1);
					// ����
					both.add(mc1);
					// �������Ʊ�Ľ���������
					sumBuy = sumBuy;
					// �������Ʊ�ļ�Ȩ����
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
					// ����۸�
					both.add(df.format(price * k));
					
					// ���յ�ͣ�۸�
					both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 0.9));
					daydeal = (Daydeal) temp.get(temp.size() - 1);
					int model = daydeal.getModel();
					int base = Integer.valueOf(daydeal.getBase());
					if (model < 10) {
						model = model;
						// ��������
						both.add(base * ((int) Math.pow(2, (model))));
						// Ԥ�Ƴɽ����
						both.add(df.format((price * k)
								* (int) (base * ((int) Math.pow(2, (model))))));
						// �����۸�
						both.add(df.format(price * j));
						// ������ͣ�۸�
						both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
						// ������������
						both.add(base * ((int) Math.pow(2, (model))));
						// Ԥ�Ƴɽ����
						both.add(df.format((price * j)
								* (int) (base * ((int) Math.pow(2, (model))))));
						System.out.println("both");
						// return new GuideOut().both(both, name);
					} else {
						model -= 10;
						// ��������
						both.add(base * ((int) Math.pow(1.5, (model))));
						// Ԥ�Ƴɽ����
						both.add(df.format((price * k)
								* (int) (base * ((int) Math.pow(1.5, (model))))));
						// �����۸�
						both.add(df.format(price * j));
						// ������ͣ�۸�
						both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
						// ������������
						both.add(base * ((int) Math.pow(1.5, (model))));
						// Ԥ�Ƴɽ����
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

				// ����
				both.add(dm1);
				// ����
				both.add(mc1);
				// �������Ʊ�Ľ���������
				sumSell = sumSell;
				// �������Ʊ�ļ�Ȩ����
				daydeal = (Daydeal) sell.get(sell.size() - 1);
				price += Double.valueOf(daydeal.getCjjg()) * d / sumSell;
				if (sell.size() > 1) {
					for (int m = 0; m < temp.size() - 1; m++) {
						daydeal = (Daydeal) sell.get(m);
						price += Double.valueOf(daydeal.getCjjg())
								* Double.valueOf(daydeal.getCjsl()) / sumSell;
					}
				}
				// ����۸�
				both.add(df.format(price * k));
				// ���յ�ͣ�۸�
				both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 0.9));
				daydeal = (Daydeal) temp.get(temp.size() - 1);
				int model = daydeal.getModel();
				int base = Integer.valueOf(daydeal.getBase());
				if (model < 10) {
					model = model;
					// ��������
					both.add(base * ((int) Math.pow(2, (model))));
					// Ԥ�Ƴɽ����
					both.add(df.format((price * k)
							* (int) (base * ((int) Math.pow(2, (model))))));
					// �����۸�
					both.add(df.format(price * j));
					// ������ͣ�۸�
					both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
					// ������������
					both.add(base * ((int) Math.pow(2, (model))));
					// Ԥ�Ƴɽ����
					both.add(df.format((price * j)
							* (int) (base * ((int) Math.pow(2, (model))))));
					System.out.println("both");
					return new GuideOut().both(both, name);
				} else {
					model -= 10;
					// ��������
					both.add(base * ((int) Math.pow(1.5, (model))));
					// Ԥ�Ƴɽ����
					both.add(df.format((price * k)
							* (int) (base * ((int) Math.pow(1.5, (model))))));
					// �����۸�
					both.add(df.format(price * j));
					// ������ͣ�۸�
					both.add(df.format(Double.parseDouble(tempGp.getZs1()) * 1.1));
					// ������������
					both.add(base * ((int) Math.pow(1.5, (model))));
					// Ԥ�Ƴɽ����
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
