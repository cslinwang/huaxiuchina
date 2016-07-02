package com.huaxiuchina.action;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huaxiuchina.dao.DrcjDao;
import com.huaxiuchina.model.Drcj;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class DrcjAction implements ModelDriven<Drcj> {
	private Drcj drcj = new Drcj();

	public Drcj getModel() {
		// TODO Auto-generated method stub
		return drcj;
	}

	public ApplicationContext ac = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private DrcjDao serviceImp = (DrcjDao) ac.getBean("drcjDao");
	public Map session = ActionContext.getContext().getSession();
	public Map request = (Map) ActionContext.getContext().get("request");
	
	//²éÑ¯ËùÓÐ
	public String selectAll(){
		session.put("decjList", serviceImp.selectAll());
		System.out.println(serviceImp.selectAll().size());
		return "decjSelectAll";
	}
}
