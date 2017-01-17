package com.huaxiuchina.dao;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.huaxiuchina.model.Model;
import com.huaxiuchina.model.T;

@Transactional
public class TDao {
	public ApplicationContext ac = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private HibernateTemplate hibernateTemplate = (HibernateTemplate) ac
			.getBean("hibernateTemplate");

	public void add(T t) {
		hibernateTemplate.save(t);
	}

	/*public List byDm(String name, String date, String dm, String flag) {
		return hibernateTemplate.find(
				"from T where name=? and date=? and dm=? and falg=?",
				name, date, dm, flag);
	}*/

	public List selectAll() {
		return this.hibernateTemplate.find("from T");
	}
/*
	public void update(T t) {
		// TODO Auto-generated method stub
		try {
			this.hibernateTemplate.update(t);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}*/

	/*public List selectByName(String name) {
		return this.hibernateTemplate.find("from T where name = ?", name);
	}*/

	public List selectByNameAndDate(String name, String date) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from T where user = ? and date=?", name,date);
	}
}
