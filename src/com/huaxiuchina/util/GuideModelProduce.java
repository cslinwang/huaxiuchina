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
	Daydeal daydeal; // һ��������Ʊʵ����
	List onlyBuy = new ArrayList(); // ���ֻ���Ʊ�ı������
	List onlySell = new ArrayList(); // �������Ʊ�ı������
	List both = new ArrayList(); // ���������������Ʊ�ı������
	List all = new ArrayList(); // ��ŵ������н��׹�Ʊ��Ϣ
	List dm = new ArrayList(); // ��ŵ������н��׹�Ʊ����
	List only = new ArrayList(); // ���ȥ�غ�ĵ��ս��׹�Ʊ��Ϣ
	
	public ByteArrayInputStream check(String name) throws Exception{
		List only = new GuideProduce().getOnly(name);
		return null;
		
	}
}
