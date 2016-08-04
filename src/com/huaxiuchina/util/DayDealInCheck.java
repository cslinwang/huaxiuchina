package com.huaxiuchina.util;

import java.util.ArrayList;
import java.util.List;

import com.huaxiuchina.dao.DaydealDao;
import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.model.Daydeal;

public class DayDealInCheck {
	GpDao gpDao = new GpDao();
	DaydealDao daydealDao = new DaydealDao();
	Daydeal daydeal; // һ��������Ʊʵ����
	List onlyList = new ArrayList();
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
			// �ҵ���һ�β���ʱ��
			List lastDayList = null;
			String lastDay = daydeal.getDate().toString();
			for (int j = 0; j < 365; j++) {
				lastDay = new GetDate().lastDate(lastDay);
				lastDayList = daydealDao.selectAll(username, lastDay);
				if (lastDayList.size() != 0) {
					break;
				}
			}

			// ��ѯ���յ�ֻ��Ʊ�б�
			List tecentDatList = daydealDao.selectByDm(only.get(i).toString(),
					username, new GetDate().getDate());
			// �ù�Ʊ��һ��ʵ��
			daydeal = (Daydeal) tecentDatList.get(0);
			// ������¹�Ʊ
			if (lastDayList.size() == 0) {
				System.out.println("�¹�Ʊ����");
				// �¹�Ʊ�ĵ�һ���ɽ�����Ϊbaseֵ
				base = daydeal.getCjsl();
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
					System.out.println(Integer.valueOf(daydeal.getDm().toString()));
					if (Integer.valueOf(daydeal.getDm().toString()) < 10000) {
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
				daydeal = (Daydeal) lastDayList.get(lastDayList.size() - 1);
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
					System.out.println(daydeal.getSum());
					sum = Integer.valueOf(daydeal.getSum());
					base = daydeal.getBase();
					// ��ʱ�������洢Ԥ�ڽ���ֵ
					int sum1 = 0;
					// �����ģʽ1
					if (model < 10) {
						//������û������
						if (model == (zuorimodel + 1)) {
							model--;
						} else {
							for (int k = 0; k <= model; k++) {
								sum1 += Integer.valueOf(base)
										* ((int) Math.pow(2, k));
							}
							// �ж��Ƿ����ģ�ͣ�
							System.out.println("Ŀ�꣺" + sum1);
							if (sum == sum1) {
								model++;
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
						System.out.println(sum1);
						// �ж��Ƿ����ģ�ͣ�
						if (sum == sum1) {
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
	}

	public static void main(String[] args) throws Exception {
		new DayDealInCheck().dayDealInCheck("cuikui");
	}
}
