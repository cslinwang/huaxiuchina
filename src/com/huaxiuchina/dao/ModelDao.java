package com.huaxiuchina.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huaxiuchina.model.Model;
import com.huaxiuchina.util.XLSWriter;
import com.opensymphony.xwork2.ActionContext;

// 当日成交
public class ModelDao {
	/*
	 * private HibernateTemplate hibernateTemplate;
	 * 
	 * public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
	 * this.hibernateTemplate = hibernateTemplate; }
	 */
	public ApplicationContext ac = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private HibernateTemplate hibernateTemplate = (HibernateTemplate) ac
			.getBean("hibernateTemplate");

	public void add(Model model) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.save(model);
	}

	public void delete(Model model) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.delete(model);
	}

	public void update(Model model) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.update(model);
	}

	public List selectByDm(String dm) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Model where dm=?", dm);
	}

	public List selectByDm(String dm, String name, int model) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate
				.find("from Model where dm=? and user=? and model=?", dm, name,
						model);
	}

	public List selectByDm(String dm, String name) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Model where dm=? and user=?",
				dm, name);
	}

	public List selectByName(String name) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Model where user=?", name);
	}

	public List selectAll() {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Model");
	}

	/*
	 * public static void main(String[] args) throws IOException {
	 * System.out.println("开始读取"); ModelDao dao =new ModelDao(); new
	 * XLSWriter().XLSWriter(dao.selectAll()); }
	 */

	public List selectAllByDate(String date) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Model where date=?", date);
	}

	public List selectByDmAndDate(String date, String dm) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Model where date=? and dm=?",
				date, dm);
	}

	public List selectById(Integer mid) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Model where mid=?",
				mid);
	}
}
