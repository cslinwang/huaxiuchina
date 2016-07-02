package com.huaxiuchina.action;

import java.io.IOException;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.model.Gp;
import com.huaxiuchina.util.XLSWriter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class GpAction implements ModelDriven<Gp>{
	private Gp gp = new Gp();

	public Gp getModel() {
		// TODO Auto-generated method stub
		return gp;
	}
	
	public ApplicationContext ac = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private GpDao serviceImp = (GpDao) ac.getBean("gpDao");
	public Map session = ActionContext.getContext().getSession();
	public Map request = (Map) ActionContext.getContext().get("request");
	//²éÑ¯ËùÓÐ
	public String selectAll(){
		session.put("gpList", serviceImp.selectAll());
		System.out.println(serviceImp.selectAll().size());
		return "gpSelectAll";
	}
	public String download() throws IOException{
		new XLSWriter().XLSWriter(serviceImp.selectAll());
		return "downloadSuccess";
	}
}
