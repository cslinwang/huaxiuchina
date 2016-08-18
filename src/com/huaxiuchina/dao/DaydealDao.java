package com.huaxiuchina.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huaxiuchina.model.Daydeal;
import com.huaxiuchina.util.XLSWriter;

// 当日成交
public class DaydealDao extends HibernateTemplate {

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

	public void add(Daydeal daydeal) {
		// TODO Auto-generated method stub
		try {
			this.hibernateTemplate.save(daydeal);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public void delete(Daydeal daydeal) {
		// TODO Auto-generated method stub
		try {
			this.hibernateTemplate.delete(daydeal);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public void update(Daydeal daydeal) {
		// TODO Auto-generated method stub
		try {
			this.hibernateTemplate.update(daydeal);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List select(Daydeal daydeal) {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find("from Daydeal where dm=?",
					daydeal.getDm());
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectAll() {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find("from Daydeal");
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectAll(String username, String date) {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find(
					"from Daydeal where username=? and date=?", username, date);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectByDm(String dm) {
		try {
			return this.hibernateTemplate.find("from Daydeal where dm=?", dm);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectByDm(String dm, String username) {
		try {
			return this.hibernateTemplate.find(
					"from Daydeal where dm=? and username=?", dm, username);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectByDm(String dm, String username, String date) {
		try {
			return this.hibernateTemplate.find(
					"from Daydeal where dm=? and date=? and username=?", dm,
					date, username);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectById(Integer id) {
		try {
			return this.hibernateTemplate.find("from Daydeal where dealid=?",
					id);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	/* 删除今日交易信息 */
	public void deleteByDate(String username, String date) {
		// TODO Auto-generated method stub
		try {
			List list = this.hibernateTemplate.find(
					"from Daydeal where date=? and username=?", date, username);
			System.out.println(date + username);
			System.out.println(list.size());
			this.hibernateTemplate.deleteAll(list);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}
}
