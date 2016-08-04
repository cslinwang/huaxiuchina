package com.huaxiuchina.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huaxiuchina.model.User;
import com.huaxiuchina.util.XLSWriter;

// 当日成交
public class UserDao {
	
	 private HibernateTemplate hibernateTemplate;
	  
	  public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
	  this.hibernateTemplate = hibernateTemplate; }
	

	/*public ApplicationContext ac = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private HibernateTemplate hibernateTemplate = (HibernateTemplate) ac
			.getBean("hibernateTemplate");*/

	public List login(User user) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from User where name=? and pwd=?",
				user.getName(), user.getPwd());
	}

	public void add(User user) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.save(user);
	}

	public void delete(User user) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.delete(user);
	}

	public void update(User user) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.update(user);
	}

	public List selectById(int id) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from User where uid=?",
				id);
	}

	public List selectAll() {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from User");
	}
}
