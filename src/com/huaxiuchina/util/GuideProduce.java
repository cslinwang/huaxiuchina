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

	public void check() throws Exception {
		List only = new GuideProduce().getOnly("cuikui");
		// �����鿴��Ʊ�������
		for (int i = 0; i < only.size(); i++) {
			java.text.DecimalFormat df = new DecimalFormat("#.0000"); // ��ʽ��double
			double k = 1; // kֵ
			double j = 1; // Jֵ
			int flag = 0; // ��־�����ù�Ʊʲô�����0����ֻ����ֵֻ����
			String dm1 = null; // ����һ������
			String mc1 = null; // ���ڶ�������
			List temp = daydealDao.selectByDm(only.get(i).toString(), "cuikui",new GetDate().getDate());
			for (int l = 0; l < temp.size(); l++) {
				daydeal = (Daydeal) temp.get(l);
				dm1 = daydeal.getDm();
				mc1 = daydeal.getMc();
				if (daydeal.getMmbz().toString().equals("����")) {
					flag++;
				}
			}
			System.out.println("flag" + flag);
			// ֻ��
			if (flag == temp.size()) {
				Double price = 0.00;
				int sum = 0;

				onlyBuy.add(dm1);
				onlyBuy.add(mc1);
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
	 * public int ksGuide(Daydeal daydeal) { // �����ҵ���һ�ι�Ʊ�������ڣ������������oldList
	 * String oldDate = daydeal.getDate().toString(); List oldList = new
	 * ArrayList(); for (int j = 0; j < 365; j++) { System.out.println(oldDate);
	 * oldList = daydealDao.selectByDm(daydeal.getDm() .toString(), "cuikui",
	 * new GetDate().getDate()); if (oldList.size() != 0) { break; } oldDate =
	 * new GetDate().lastDate(oldDate); } if (oldList.size() == 0) { } else { //
	 * ��������ϴΣ�����������
	 * 
	 * for (int j = 0; j < oldList.size(); j++) { Daydeal daydeal2 = (Daydeal)
	 * oldList.get(j); if (daydeal2.getMmbz().toString().equals("����")) { buySum
	 * += Integer.valueOf(daydeal2.getCjsl() .toString()); } else { saleSum +=
	 * Integer.valueOf(daydeal2.getCjsl() .toString()); } }
	 * System.out.println("buySum: " + buySum); System.out.println("saleSum: " +
	 * saleSum); if (daydeal.getModel() < 10) { // �����ģ��һ // �õ�ϵ�� int tecent =
	 * daydeal.getModel(); int actual = Integer.valueOf(daydeal.getCjsl()
	 * .toString()); // ������ֳɹ���ϵ����һ if (actual == base * tecent) { model += 1; }
	 * else if (base * tecent - actual > base (tecent - 1)) model = model;
	 * 
	 * } else { // �����ģ�Ͷ� }
	 * 
	 * 
	 * }
	 */

	public static void main(String[] args) throws Exception {
		new GuideProduce().check();
	}
}
