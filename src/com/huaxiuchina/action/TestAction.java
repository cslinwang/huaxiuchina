package com.huaxiuchina.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huaxiuchina.dao.DaydealDao;
import com.huaxiuchina.util.DaydealCheck;
import com.huaxiuchina.util.XLSWriter;
import com.opensymphony.xwork2.ActionContext;

public class TestAction {
	public ApplicationContext ac = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private DaydealDao serviceImp = (DaydealDao) ac.getBean("daydealDao");
	public Map session = ActionContext.getContext().getSession();
	public Map request = (Map) ActionContext.getContext().get("request");
	public String test() throws Exception{
		
		//new DaydealCheck().daydealTemp1();
		return "success";
	}
}
