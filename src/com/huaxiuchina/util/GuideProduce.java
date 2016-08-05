package com.huaxiuchina.util;

import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.huaxiuchina.dao.DaydealDao;
import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.dao.ModelDao;
import com.huaxiuchina.model.Daydeal;
import com.huaxiuchina.model.Gp;
import com.huaxiuchina.model.Model;

public class GuideProduce {
	GpDao gpDao = new GpDao();
	DaydealDao daydealDao = new DaydealDao();
	Daydeal daydeal; // һ��������Ʊʵ����
	List buyGuide = new ArrayList(); // �������ָ��
	List sellGuide = new ArrayList(); // �������ָ��
	List singleBuy = new ArrayList(); // ���һ������
	List singleSell = new ArrayList(); // ���һ������
	List all = new ArrayList(); // ��ŵ������н��׹�Ʊ��Ϣ
	List both = new ArrayList(); // ��ŵ������н��׹�Ʊ��Ϣ
	List dm = new ArrayList(); // ��ŵ������н��׹�Ʊ����
	List only = new ArrayList(); // ���ȥ�غ�ĵ��ս��׹�Ʊ��Ϣ
	List temp = new ArrayList(); // ��ʱ����
	java.text.DecimalFormat df = new DecimalFormat("#.00"); // ��ʽ��double
	double k = 0; // kֵ
	double j = 0; // Jֵ
	String dm1 = null; // ����һ������
	String mc1 = null; // ���ڶ�������
	Gp tempGp = new Gp();
	Model modell = new Model();
	ModelDao modelDao = new ModelDao();

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
		// �����鿴��Ʊ�������
		for (int i = 0; i < only.size(); i++) {
			int flag = 0; // ��־�����ù�Ʊʲô�����0����ֻ����ֵֻ����
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
				if (daydeal.getMmbz().toString().equals("����")) {
					flag++;
				}
			}
			/* tempGp = (Gp) gpDao.selectByDm(dm1).get(0); */
			System.out.println("flag: " + flag);
			// ֻ��
			if (flag == temp.size()) {
				new GuideProduce().onlyBuy(name, dm1, mc1, temp);
				System.out.println("buy");

			}
			// ���ֻ��
			else if (flag == 0) {
				new GuideProduce().onlySell(name, dm1, mc1, temp);
				System.out.println("sell");
			}
			// �����������
			else {
				new GuideProduce().both(name, dm1, mc1, temp);
				System.out.println("both");
			}
		}
		// return new GuideOut().both(both, name);

	}

	// ���ֻ��
	public void onlyBuy(String name, String dm1, String mc1, List temp) {
		Double price = 0.00;
		int sum = 0;
		// �û�
		modell.setUser(name);
		// ����
		modell.setDm(dm1);
		// �������Ʊ�Ľ���������
		for (int l = 0; l < temp.size(); l++) {
			daydeal = (Daydeal) temp.get(l);
			sum += Integer.valueOf(daydeal.getCjsl());
		}
		// System.out.println("t_sum"+sum);
		// �������Ʊ�ļ�Ȩ����
		double priceToday = 0;
		double peiceLastday = 0;
		for (int l = 0; l < temp.size(); l++) {
			daydeal = (Daydeal) temp.get(l);
			price += Double.valueOf(daydeal.getCjjg())
					* Double.valueOf(daydeal.getCjsl()) / sum;
			priceToday = price;
		}
		// ����۸�
		modell.setPrice(df.format(price));
		// ���յ�ͣ�۸�
		// System.out.println("t_dm1 " + dm1);
		// �������һ�ν��׼�¼
		daydeal = (Daydeal) temp.get(temp.size() - 1);
		// �õ����µ�model base
		int model = daydeal.getModel();
		int base = Integer.valueOf(daydeal.getBase());
		// ��������
		modell.setBase(String.valueOf(base));
		// ���ܺ������ص�model��������ģ�ͽ׶�
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
		// �޸ļ۸�
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
				// �ж��Ƿ񳬱�
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
					System.out.println("t_shuliang");
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
		// ����������ν��֣������ϴν���
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
	}

	// ���ֻ��ģ�����1��2
	public Model onlyBuyModelType(int model, double multiple, int base,
			double price, List temp) {
		// System.out.println("t_multiple: " + (base * Math.pow(multiple,
		// (1))));
		double p = 0;
		// Ԥ�ڽ�������
		// System.out.println("t_model"+model);
		for (int l = 0; l < model; l++) {
			System.out.println("?");
			p += base * (Math.pow(multiple, (l)));
		}
		int tepsum = Integer.valueOf(((Daydeal) temp.get(temp.size() - 1))
				.getSum());
		System.out.println("t_tepsum" + tepsum);
		System.out.println("t_p" + p);
		if (tepsum == p) {			
			p = base * (Math.pow(multiple, (model)));
			// ����model����sum
			// System.out.println("t_"+String.valueOf((model * base)));
			modell.setSum(String.valueOf((int) (base * (Math.pow(multiple,
					(model - 1))))));
			// ����modelģ��model
			if(multiple==1.5){
				modell.setModel(model+10);
			}else{
				modell.setModel(model);
			}		
		} else {
			// System.out.println("t_tepsum: " + tepsum);
			// System.out.println("t_p: " + (int)p);
			p = tepsum - p;
			modell.setSum(String.valueOf(((int) p)));
			// ����modelģ��
			System.out.println("aaaaaaa"+multiple);
			if(multiple==1.5){
				System.out.println();
				modell.setModel(model + 11);
			}else{
				modell.setModel(model + 1);
			}
		}
		return modell;
	}

	// ���ֻ��
	public void onlySell(String name, String dm1, String mc1, List temp) {
		Double price = 0.00;
		int sum = 0;
		// �û�
		modell.setUser(name);
		// ����
		modell.setDm(dm1);
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
		daydeal = (Daydeal) temp.get(temp.size() - 1);
		int model = daydeal.getModel();
		int base = Integer.valueOf(daydeal.getBase());
		if (model < 10) {
			new GuideProduce().onlySellModelType(model, 2, base, price, temp,
					dm1, name);
		} else {
			new GuideProduce().onlySellModelType(model - 10, 1.5, base, price,
					temp, dm1, name);
		}
	}

	// ���ֻ��ģ�����1��2
	public Model onlySellModelType(int model, double multiple, int base,
			double price, List temp, String dm1, String name) {
		System.out.println("model= " + model + " ");
		double p = 0;
		// Ӧ�õ�����
		for (int l = 0; l < model; l++) {
			p += base * ((int) Math.pow(multiple, (l)));
		}
		// ����
		int tepsum = Integer.valueOf(((Daydeal) temp.get(temp.size() - 1))
				.getSum());
		System.out.println("tepsum=" + tepsum);
		// �õ�ģ����Ϣ
		temp = modelDao.selectByDm(dm1, name);
		while (true) {
			if (temp.size() > 0) {
				if (((Model) temp.get(temp.size() - 1)).getModel() > (model + 1)) {
					modelDao.delete(((Model) temp.get(temp.size() - 1)));
					temp.remove(temp.remove(temp.size() - 1));
				}
				break;
			}
			break;
		}
		Model modellTemp = null;
		if (temp.size() != 0) {
			modellTemp = (Model) temp.get(temp.size() - 1);
		}
		System.out.println("t_tepsum: " + tepsum);
		System.out.println("t_p: " + p);
		if (tepsum == p ^ tepsum == 0) {
			p = base * ((int) Math.pow(multiple, (model)));
			// ����model����sum
			// System.out.println("t_"+String.valueOf((model * base)));
			modell.setSum(String.valueOf((int) (base * (Math.pow(multiple,
					(model - 1))))));
			// ����modelģ��model
			modelDao.delete(modellTemp);
		} else {
			p = tepsum - p;
			modellTemp.setSum(String.valueOf((int) p));
			modelDao.update(modellTemp);
		}
		// ��������
		// return new GuideOut().both(both, name);
		return modell;
	}

	// �����������
	public void both(String name, String dm1, String mc1, List temp) {
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
		for (int l = 0; l < buy.size(); l++) {
			daydeal = (Daydeal) buy.get(l);
			sumBuy += Integer.valueOf(daydeal.getCjsl());
		}
		for (int l = 0; l < sell.size(); l++) {
			daydeal = (Daydeal) sell.get(l);
			sumSell += Integer.valueOf(daydeal.getCjsl());
		}
		// ���ܺ������ص�model��������ģ�ͽ׶�
		Model modellTemp = new Model();
		System.out.println(sumBuy + "  " + sumSell);
		if (sumBuy >= sumSell) {
			e = buy.size();
			d = 0;
			double priceToday = 0;
			for (int l = sell.size() - 1; l >= 0; l--) {
				int temp1 = Integer.valueOf(((Daydeal) sell.get(l)).getCjsl());
				while (true) {
					if (buy.size() != 0) {
						if (e == buy.size()) {
							temp2 = Integer.valueOf(((Daydeal) buy.get(0))
									.getCjsl());
							int v = temp2 + d;
							System.out
									.println("buy: " + temp1 + " sell:  " + v);
						}
					}
					if (temp2 + d - temp1 >= 0) {
						sumBuy -= (temp1);
						d = temp2 + d - temp1;
						temp2 = 0;
						if (d != 0) {
							e--;
						}
						break;
					} else {
						d += temp2;
						if (buy.size() != 0) {
							e--;
							buy.remove(0);
						}
					}
				}
				// �û�
				modell.setUser(name);
				// ����
				modell.setDm(dm1);
				// �������Ʊ�Ľ���������
				sumBuy = sumBuy;
				// �����������ֱ�ӷ���
				if (sumBuy == 0) {
					return;
				}
				// �������Ʊ�ļ�Ȩ����
				daydeal = (Daydeal) buy.get(0);
				price += Double.valueOf(daydeal.getCjjg()) * d / sumBuy;
				if (buy.size() > 1) {
					for (int m = 1; m < buy.size(); m++) {
						daydeal = (Daydeal) buy.get(m);
						price += Double.valueOf(daydeal.getCjjg())
								* Double.valueOf(daydeal.getCjsl()) / sumBuy;
					}
				}
				priceToday = price;
				// modell�ļ۸�
				modell.setPrice(df.format(price));
				daydeal = (Daydeal) temp.get(temp.size() - 1);
				int model = daydeal.getModel();
				int base = Integer.valueOf(daydeal.getBase());
				// modell��base
				modell.setBase(String.valueOf(base));
				if (model < 10) {
					modellTemp = new GuideProduce().onlyBuyModelType(model, 2,
							base, price, temp);
				} else {
					modellTemp = new GuideProduce().onlyBuyModelType(
							model - 10, 1.5, base, price, temp);
				}

			}
			// �Խ�����д���
			temp = modelDao.selectByDm(dm1, name,
					Integer.valueOf(modellTemp.getModel()));
			modell.setSum(modellTemp.getSum());
			modell.setModel(modellTemp.getModel());
			// �޸ļ۸�
			System.out.println("t___temp.size()" + temp.size());
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
				modell.setPrice(df.format(price));
				modell.setMid(modelll.getMid());
				modelDao.update(modell);
			}

			// ����������ν��֣������ϴν���
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
			for (int l = 0; l < buy.size(); l++) {
				System.out.println("����");
				// �õ�����ÿһ�ʽ���
				int temp1 = Integer.valueOf(((Daydeal) buy.get(l)).getCjsl());
				while (true) {
					if (sell.size() != 0) {
						if (e == sell.size()) {
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
						if (d != 0) {
							e--;
						}
						break;
					} else {
						d += temp2;
						// System.out.println("D"+d);
						if (sell.size() != 0) {
							e--;
							sell.remove(sell.size() - 1);
						}
					}
				}
			}
			System.out.println("t__d:" + d);
			// �û�
			modell.setUser(name);
			// ����
			modell.setDm(dm1);
			// �������Ʊ�Ľ���������
			sumSell = sumSell;
			// �������Ʊ�ļ�Ȩ����
			daydeal = (Daydeal) sell.get(sell.size() - 1);
			price += Double.valueOf(daydeal.getCjjg()) * d / sumSell;
			if (sell.size() > 1) {
				for (int m = 0; m < sell.size() - 1; m++) {
					daydeal = (Daydeal) sell.get(m);
					price += Double.valueOf(daydeal.getCjjg())
							* Double.valueOf(daydeal.getCjsl()) / sumSell;
				}
			}
			// model price
			System.out.println("t__price:" + price);
			modell.setPrice(df.format(price));
			daydeal = (Daydeal) temp.get(temp.size() - 1);
			int model = daydeal.getModel();
			int base = Integer.valueOf(daydeal.getBase());
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
		new GuideProduce().check("HXSX0019");
	}
}
