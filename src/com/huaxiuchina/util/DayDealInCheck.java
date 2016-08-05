package com.huaxiuchina.util;

import java.util.ArrayList;
import java.util.List;

import com.huaxiuchina.dao.DaydealDao;
import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.dao.ModelDao;
import com.huaxiuchina.model.Daydeal;
import com.huaxiuchina.model.Model;

public class DayDealInCheck {
	GpDao gpDao = new GpDao();
	static DaydealDao daydealDao = new DaydealDao();
	ModelDao modelDao = new ModelDao();
	Model modell = new Model();
	Daydeal daydeal; // һ��������Ʊʵ����
	List onlyList = new ArrayList();
	List temp = new ArrayList();
	String base;
	int model = 0;

	public void dayDealInCheck(String username) throws Exception {
		List only = new GuideProduce().getOnly(username);
		for (int i = 0; i < only.size(); i++) {
			onlyList.add(daydealDao.selectByDm(only.get(i).toString(),
					username, new GetDate().getDate()).get(0));
		}
		for (int i = 0; i < only.size(); i++) {
			int sum = 0;
			daydeal = (Daydeal) onlyList.get(i);
			String dm = daydeal.getDm();
			if (temp.size() == 0) {

			}
			// �ҵ���һ�β���ʱ��
			List lastDayList = null;
			String lastDay = daydeal.getDate().toString();
			/*
			 * for (int j = 0; j < 365; j++) { lastDay = new
			 * GetDate().lastDate(lastDay); lastDayList =
			 * daydealDao.selectAll(username, lastDay); if (lastDayList.size()
			 * != 0) { break; } }
			 */
			// ��ѯ��ֻ��Ʊ��ǰ�Ľ����Ƿ����
			temp = daydealDao.selectByDm(dm, username);
			for (int j = temp.size() - 1; j >= 0; j--) {
				if (((Daydeal) temp.get(j)).getDate().equals(
						new GetDate().getDate())) {
					temp.remove(j);
				} else {
					break;
				}
			}
			Daydeal lastDaydeal = null;
			if (temp.size() >= 1) {
				lastDaydeal = (Daydeal) temp.get(temp.size() - 1);
			}

			// ��ѯ���յ�ֻ��Ʊ�б�
			List tecentDatList = daydealDao.selectByDm(only.get(i).toString(),
					username, new GetDate().getDate());
			// �ù�Ʊ��һ��ʵ��
			daydeal = (Daydeal) tecentDatList.get(0);

			// ������¹�Ʊ
			if (lastDaydeal == null) {
				System.out.println("�¹�Ʊ����");
				// �¹�Ʊ�ĵ�һ���ɽ�����Ϊbaseֵ
				base = daydeal.getCjsl();
				// ��ģ��һ�׶�
				/*
				 * modell.setDm(daydeal.getDm()); modell.setModel(1);
				 * modell.setPrice(daydeal.getCjjg());
				 * modell.setUser(daydeal.getUsername()); modelDao.add(modell);
				 * System.out.println("��һ�׶ν�ģ�ɹ�");
				 */
				for (int j = 0; j < tecentDatList.size(); j++) {
					// base����
					daydeal = (Daydeal) tecentDatList.get(j);
					daydeal.setBase(base);
					daydealDao.update(daydeal);
					// �õ��µ�ֵ
					daydeal = (Daydeal) tecentDatList.get(j);
					base = daydeal.getBase();
					System.out.println("base�����ɹ�");
					// sum����
					// ��ò���ʵ��
					daydeal = (Daydeal) tecentDatList.get(j);
					if (daydeal.getMmbz().toString().equals("����")) {
						sum += Integer.valueOf(daydeal.getCjsl().toString());
						daydeal.setSum(String.valueOf(sum));
						daydealDao.update(daydeal);
						System.out.println("sum�����ɹ�");
					} else {
						sum -= Integer.valueOf(daydeal.getCjsl().toString());
						daydeal.setSum(String.valueOf(sum));
						daydealDao.update(daydeal);
						System.out.println("sum�����ɹ�");
					}
					// model����
					daydeal = (Daydeal) tecentDatList.get(j);
					int dmNum = Integer.valueOf(daydeal.getDm().toString());
					// 60****��000***Ĭ��ʹ��2��ģ��
					// 002***��300***Ĭ��ʹ��1.5��ģ��
					if (model < 2000 ^ dmNum > 600000) {
						model = 01;
						daydeal.setModel(model);
						daydealDao.update(daydeal);
						System.out.println("model�����ɹ�");
					} else {
						model = 11;
						daydeal.setModel(model);
						daydealDao.update(daydeal);
						System.out.println("model�����ɹ�");
					}
				}
			}
			// ����Ǿɹ�Ʊ
			else {
				System.out.println("�ɹ�Ʊ����");
				// �õ����յ����һ��ʵ��
				daydeal = lastDaydeal;
				// �õ����յ�base
				base = daydeal.getBase();
				// �õ����յ����sum
				sum = Integer.valueOf(daydeal.getSum());
				// �õ�����model
				int zuorimodel = daydeal.getModel();
				model = daydeal.getModel();
				// �Խ������ݽ�������
				for (int j = 0; j < tecentDatList.size(); j++) {
					// ����base
					daydeal = (Daydeal) tecentDatList.get(j);
					daydeal.setBase(base);
					daydealDao.update(daydeal);
					System.out.println("base�����ɹ�");
					// ����sum
					daydeal = (Daydeal) tecentDatList.get(j);
					// ������������
					if (daydeal.getMmbz().toString().equals("����")) {
						sum += Integer.valueOf(daydeal.getCjsl().toString());
						daydeal.setSum(String.valueOf(sum));
						daydealDao.update(daydeal);
						System.out.println("sum�����ɹ�");
					}
					// �������������
					else if (daydeal.getMmbz().toString().equals("����")) {
						sum -= Integer.valueOf(daydeal.getCjsl().toString());
						daydeal.setSum(String.valueOf(sum));
						daydealDao.update(daydeal);
						System.out.println("sum�����ɹ�");
					}
					// ����model
					daydeal = (Daydeal) tecentDatList.get(j);
					sum = Integer.valueOf(daydeal.getSum());
					base = daydeal.getBase();
					// ��ʱ�������洢Ԥ�ڽ���ֵ
					int sum1 = 0;
					System.out.println("model"+model);
					// �����ģʽ1
					if (model < 10) {
						// ������û������
						if (model == (zuorimodel + 1)) {
							model--;
						} else {
							for (int k = 0; k <= model; k++) {
								sum1 += Integer.valueOf(base)
										* ((int) Math.pow(2, k));
							}
							// �ж��Ƿ����ģ�ͣ�
							System.out.println("Ŀ�꣺" + sum1);
							if (sum > sum1) {
								model++;
								// modelDao.selectByDmAndDate(dm, username);
							}
							// ���û���
							else if (sum < sum1
									& sum >= (sum1 - Integer.valueOf(base)
											* ((int) Math.pow(2, model)))) {
								model = model;
							}
							// �������
							else if (sum < (sum1 - Integer.valueOf(base)
									* ((int) Math.pow(2, model)))) {
								model--;
							}
						}
						// д������
						daydeal.setModel(model);
						daydealDao.update(daydeal);
						System.out.println("model�����ɹ�");
					}
					// �����ģ��2
					else if (10 < model & model < 20) {
						
						model -= 10;
						for (int k = 0; k <= model; k++) {
							sum1 += (int) (1000 * (Math.pow(1.5, k)));
						}
						// �ж��Ƿ����ģ�ͣ�
						if (sum > sum1) {
							model += 11;
						}
						// ���û���
						else if (sum < sum1
								& sum > (sum1 - Integer.valueOf(base) ^ model)) {
							model += 10;
						}
						// �������
						else if (sum == (sum1 - Integer.valueOf(base) ^ model)) {
							model -= 9;
						}
						// д������
						daydeal.setModel(model);
						daydealDao.update(daydeal);
						System.out.println("model�����ɹ�");
					}
				}
			}
		}
		System.out.println("ģ�ͽ�����ʼ");
		new GuideProduce().check(username);
	}

	// �ɹ�Ʊ���ֽ���ģ�����

	public static void main(String[] args) throws Exception {
		new DayDealInCheck().dayDealInCheck("HXSX0010");

	}
}
