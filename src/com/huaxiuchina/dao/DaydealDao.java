package com.huaxiuchina.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huaxiuchina.model.Daydeal;
import com.huaxiuchina.util.XLSWriter;

// 当日成交
public class DaydealDao {

	/*
	 * private HibernateTemplate hibernateTemplate;
	 * 
	 * public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
	 * this.hibernateTemplate = hibernateTemplate; }
	 */

	public ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	private HibernateTemplate hibernateTemplate = (HibernateTemplate) ac.getBean("hibernateTemplate");

	public void add(Daydeal daydeal) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.save(daydeal);
	}

	public void delete(Daydeal daydeal) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.delete(daydeal);
	}

	public void update(Daydeal daydeal) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.update(daydeal);
	}

	public List select(Daydeal daydeal) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Daydeal where dm=?",
				daydeal.getDm());
	}

	public List selectAll() {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Daydeal");
	}

	public List selectAll(String username, String date) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find(
				"from Daydeal where username=? and date=?", username, date);
	}

	public List selectByDm(String dm) {
		return this.hibernateTemplate.find("from Daydeal where dm=?", dm);
	}

	public List selectByDm(String dm, String username) {
		return this.hibernateTemplate.find(
				"from Daydeal where dm=? and username=?", dm, username);
	}

	public List selectByDm(String dm, String username, String date) {
		return this.hibernateTemplate.find(
				"from Daydeal where dm=? and date=? and username=?", dm, date,
				username);
	}
	public List selectById(Integer id){
		return this.hibernateTemplate.find(
				"from Daydeal where dealid=?",id);
	}

}
