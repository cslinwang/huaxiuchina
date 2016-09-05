package com.huaxiuchina.dao;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

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

	public List byName(String name, String date, String mc) {
		return hibernateTemplate.find(
				"from Status where name=? and date=? and mc=?", name, date, mc);
	}
}
