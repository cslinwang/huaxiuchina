package com.huaxiuchina.util;

import java.util.ArrayList;
import java.util.List;

import com.huaxiuchina.dao.DaydealDao;
import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.model.Daydeal;

public class DayDealInCheck {
	GpDao gpDao = new GpDao();
	DaydealDao daydealDao = new DaydealDao();
	Daydeal daydeal; // 一个单个股票实体类
	List onlyList = new ArrayList();
	String base;
	int model = 0;

	public void dayDealInCheck(String username) throws Exception {
		List only = new GuideProduce().getOnly(username);
		for (int i = 0; i < only.size(); i++) {
			onlyList.add(daydealDao
					.selectByDm(only.get(i).toString(), username,new GetDate().getDate()).get(0));
		}
		for (int i = 0; i < only.size(); i++) {
			int sum = 0;
			daydeal = (Daydeal) onlyList.get(i);
			String lastDay = new GetDate().lastDate(daydeal.getDate()
					.toString());
			System.out.println(lastDay);
			List lastDayList = daydealDao.selectAll(username, lastDay);
			System.out.println(lastDayList);
			// 查询当日单只股票列表
			List tecentDatList = daydealDao.selectByDm(only.get(i).toString(),
					username, new GetDate().getDate());
			// 该股票第一个实例
			daydeal = (Daydeal) tecentDatList.get(0);
			// 如果是新股票
			if (lastDayList.size() == 0) {
				System.out.println("新股票修正");
				// 新股票的第一个成交数量为base值
				base = daydeal.getCjsl();
				for (int j = 0; j < tecentDatList.size(); j++) {
					// base操作
					daydeal = (Daydeal) tecentDatList.get(j);
					daydeal.setBase(base);
					daydealDao.update(daydeal);
					// 拿到新的值
					daydeal = (Daydeal) tecentDatList.get(j);
					base = daydeal.getBase();
					System.out.println("base修正成功");
					// sum操作
					// 获得操作实体
					daydeal = (Daydeal) tecentDatList.get(j);
					if (daydeal.getMmbz().toString().equals("买入 ")) {
						sum += Integer.valueOf(daydeal.getCjsl().toString());
						daydeal.setSum(String.valueOf(sum));
						daydealDao.update(daydeal);
						System.out.println("sum修正成功");
					} else {
						sum -= Integer.valueOf(daydeal.getCjsl().toString());
						daydeal.setSum(String.valueOf(sum));
						daydealDao.update(daydeal);
						System.out.println("sum修正成功");
					}
					// model操作
					daydeal = (Daydeal) tecentDatList.get(j);
					if (Integer.valueOf(daydeal.getDm().toString()) < 10000) {
						model = 01;
						daydeal.setModel(model);
						daydealDao.update(daydeal);
						System.out.println("model修正成功");
					} else {
						model = 11;
						daydeal.setModel(model);
						daydealDao.update(daydeal);
						System.out.println("model修正成功");
					}
				}
			}
			// 如果是旧股票
			else {
				System.out.println("旧股票修正");
				//拿到昨日的最后一个实例
				daydeal = (Daydeal) lastDayList.get(lastDayList.size()-1);
				//拿到昨日的base
				base = daydeal.getBase();
				//拿到昨日的最后sum
				sum = Integer.valueOf(daydeal.getSum());
				//拿到昨日model
				model = daydeal.getModel();
				//对今日数据进行修正
				for (int j = 0; j < tecentDatList.size(); j++) {
					//修正base
					daydeal= (Daydeal) tecentDatList.get(j);
					daydeal.setBase(base);
					daydealDao.update(daydeal);
					System.out.println("base修正成功");
					//修正sum
					daydeal= (Daydeal) tecentDatList.get(j);
					if (daydeal.getMmbz().toString().equals("买入")) {
						sum += Integer.valueOf(daydeal.getCjsl().toString());
						daydeal.setSum(String.valueOf(sum));
						daydealDao.update(daydeal);
						System.out.println("sum修正成功");
					} else {
						sum -= Integer.valueOf(daydeal.getCjsl().toString());
						daydeal.setSum(String.valueOf(sum));
						daydealDao.update(daydeal);
						System.out.println("sum修正成功");
					}
					//修正model
					daydeal= (Daydeal) tecentDatList.get(j);
					sum = Integer.valueOf(daydeal.getSum());
					model = daydeal.getModel();
					base=daydeal.getBase();
					//临时变量，存储预期建仓值
					int sum1 = 0;
					//如果是模式1
					if(model<10){
						for (int k = 0; k <= model; k++) {
							sum1+=Integer.valueOf(base)*((int) Math.pow(2, k));
							System.out.println(sum1);
						}
						
					}
					//如果是模型2
					else if(10<model&model<20){
						
					}
					//判断是否完成模型；
					if(sum==sum1){
						model++;
					}
					//如果没完成
					else if(sum<sum1&sum>(sum1-Integer.valueOf(base)^model)){
						model=model;
					}
					//如果抵消
					else if(sum==(sum1-Integer.valueOf(base)^model))
					model--;
				}
				//写入数据
				daydeal.setModel(model);
				daydealDao.update(daydeal);
				System.out.println("model修正成功");
			}
		}
	}
	public static void main(String[] args) throws Exception {
		new DayDealInCheck().dayDealInCheck("cuikui");
	}
}

