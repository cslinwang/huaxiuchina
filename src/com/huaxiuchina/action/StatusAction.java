package com.huaxiuchina.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huaxiuchina.dao.StatusDao;
import com.huaxiuchina.model.Status;
import com.huaxiuchina.model.Gp;
import com.huaxiuchina.model.Status;
import com.huaxiuchina.util.GetDate;
import com.huaxiuchina.util.GuideProduce;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class StatusAction {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map session = ActionContext.getContext().getSession();
	public Map request = (Map) ActionContext.getContext().get("request");

	public String statusUpdate() throws Exception {

		session.put("statuslist", new StatusDao().selectByNameAndDate(name, new GetDate().getDate()));
		return "statusUpdate";
	}
}
