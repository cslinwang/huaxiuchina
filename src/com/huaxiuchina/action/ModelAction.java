package com.huaxiuchina.action;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huaxiuchina.dao.ModelDao;
import com.huaxiuchina.model.Model;
import com.huaxiuchina.model.Gp;
import com.huaxiuchina.util.GetDate;
import com.huaxiuchina.util.GuideProduce;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class ModelAction implements ModelDriven<Model> {
	private Model model = new Model();
	private ModelDao modelDao = new ModelDao();
	public Model getModel() {
		// TODO Auto-generated method stub
		return model;
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
	 * "applicationContext.xml"); private ModelDao serviceImp = (ModelDao)
	 * ac.getBean("modelDao");
	 */
	 public Map session = ActionContext.getContext().getSession();
	 public Map request = (Map) ActionContext.getContext().get("request");

	// 按账户查询当日

	public String selectByNameDate() throws Exception{
		session.put("modellist", new ModelDao().selectByName(name));
		return "selectAllSuccess";
		
	}
	//
	public String modelUpdate(){
		session.put("modeldetail", modelDao.selectById(model.getMid()).get(0));
		return "modelUpdate";
	}
	//
	public String modelUpdate1() throws Exception{
		Model model1=(Model) modelDao.selectById(model.getMid()).get(0);
		model1.setModel(model.getModel());
		model1.setPrice(model.getPrice());
		model1.setBase(model.getBase());
		model1.setSum(model.getSum());
		modelDao.update(model1);
		session.put("modellist", new ModelDao().selectByName(name));
		return "modelUpdate1";
	}
	//股票
		public String modelUpdate2() throws Exception{
			//模型修正
			System.out.println("模型修正开始");
			new GuideProduce().check(name);
			session.put("modellist", new ModelDao().selectByName(name));
			return "modelUpdate2";
		}
}
