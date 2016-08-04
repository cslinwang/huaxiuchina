package com.huaxiuchina.util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import com.huaxiuchina.dao.DaydealDao;
import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.model.Daydeal;

public class GuideModelProduce {
	GpDao gpDao = new GpDao();
	DaydealDao daydealDao = new DaydealDao();
	Daydeal daydeal; // 一个单个股票实体类
	List onlyBuy = new ArrayList(); // 存放只买股票的表格数据
	List onlySell = new ArrayList(); // 存放卖股票的表格数据
	List both = new ArrayList(); // 存放有卖买有卖股票的表格数据
	List all = new ArrayList(); // 存放当日所有交易股票信息
	List dm = new ArrayList(); // 存放当日所有交易股票代码
	List only = new ArrayList(); // 存放去重后的当日交易股票信息
	
	public ByteArrayInputStream check(String name) throws Exception{
		List only = new GuideProduce().getOnly(name);
		return null;
		
	}
}
