package com.huaxiuchina.action;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huaxiuchina.dao.DaydealDao;
import com.huaxiuchina.model.Daydeal;
import com.huaxiuchina.model.Gp;
import com.huaxiuchina.util.GetDate;
import com.huaxiuchina.util.GuideProduce;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class DaydealAction implements ModelDriven<Daydeal> {
	private Daydeal daydeal = new Daydeal();

	public Daydeal getModel() {
		// TODO Auto-generated method stub
		return daydeal;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * public ApplicationContext ac = new ClassPathXmlApplicationContext(
	 * "applicationContext.xml"); private DaydealDao serviceImp = (DaydealDao)
	 * ac.getBean("daydealDao");
	 */
	 public Map session = ActionContext.getContext().getSession();
	 public Map request = (Map) ActionContext.getContext().get("request");

	// 按账户查询当日

	public String selectByNameDate() throws Exception{
		session.put("daydeallist", new DaydealDao().selectAll(name, new GetDate().getDate()));
		return "selectAllSuccess";
	}
}
