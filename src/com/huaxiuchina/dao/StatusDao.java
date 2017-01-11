package com.huaxiuchina.dao;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.huaxiuchina.model.Model;
import com.huaxiuchina.model.Status;

@Transactional
public class StatusDao {
	public ApplicationContext ac = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private HibernateTemplate hibernateTemplate = (HibernateTemplate) ac
			.getBean("hibernateTemplate");

	public void add(Status status) {
		hibernateTemplate.save(status);
	}

	public List byDm(String name, String date, String dm, String flag) {
		return hibernateTemplate.find(
				"from Status where name=? and date=? and dm=? and falg=?",
				name, date, dm, flag);
	}

	public List selectAll() {
		return this.hibernateTemplate.find("from Status");
	}

	public void update(Status status) {
		// TODO Auto-generated method stub
		try {
			this.hibernateTemplate.update(status);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectByName(String name) {
		return this.hibernateTemplate.find("from Status where name = ?", name);
	}

	public List selectByNameAndDate(String name, String date) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Status where name = ? and date=?", name,date);
	}
}
