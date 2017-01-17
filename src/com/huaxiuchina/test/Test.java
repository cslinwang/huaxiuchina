package com.huaxiuchina.test;

import java.util.ArrayList;
import java.util.List;

import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.dao.ModelDao;
import com.huaxiuchina.dao.StatusDao;
import com.huaxiuchina.dao.TDao;
import com.huaxiuchina.model.Gp;
import com.huaxiuchina.model.Model;
import com.huaxiuchina.model.Status;
import com.huaxiuchina.model.T;
import com.huaxiuchina.util.GetDate;
import com.huaxiuchina.util.XLSReader;

public class Test {

	public static void main(String[] args) throws Exception {

		// new Test().addDmForStatus();
		//new Test().addNameForModel();
		new Test().TTest();
	}

	// Ϊģ����������
	public void addNameForModel() {
		GpDao gpDao = new GpDao();
		ModelDao modelDao = new ModelDao();
		Model model = new Model();
		List list = new ArrayList<Model>();
		list = modelDao.selectAll();
		for (int i = 0; i < list.size(); i++) {
			model = (Model) list.get(i);
			System.out.println(model.getDm() + model.getMc());
			model.setMc(((Gp) gpDao.selectByDm(model.getDm()).get(0)).getMc());
			System.out.println(model.getDm() + model.getMc());
			modelDao.update(model);
		}
	}

	// Ϊ����״̬��������
	public void addDmForStatus() {
		GpDao gpDao = new GpDao();
		StatusDao modelDao = new StatusDao();
		Status model = new Status();
		List list = new ArrayList<Status>();
		list = modelDao.selectAll();
		for (int i = 0; i < list.size(); i++) {
			model = (Status) list.get(i);
			System.out.println(model.getMc() + model.getDm());
			model.setDm(((Gp) gpDao.selectByMc(model.getMc()).get(0)).getDm());
			System.out.println(model.getMc() + model.getDm());
			modelDao.update(model);
		}
	}

	public void TTest() throws Exception {

		T t = new T();
		TDao tDao = new TDao();

		t.setDate(new GetDate().getDate());
		t.setUser("admi");
		t.setDm("123");
		t.setMc("abc");
		// ������T
		t.setFc("����");
		t.setJg(String.valueOf(1000 / 10));
		t.setSl(String.valueOf(1000));
		tDao.add(t);
		// ������T
		t.setFc("����");
		t.setJg(String.valueOf(1000 / 12));
		t.setSl(String.valueOf(1000));
		tDao.add(t);

	}
}