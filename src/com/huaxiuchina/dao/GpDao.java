package com.huaxiuchina.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huaxiuchina.model.Gp;
import com.huaxiuchina.util.XLSWriter;

// 当日成交
public class GpDao extends HibernateTemplate {
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

	public void add(Gp gp) {
		// TODO Auto-generated method stub
		try {
			this.hibernateTemplate.save(gp);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public void delete(Gp gp) {
		// TODO Auto-generated method stub
		try {
			this.hibernateTemplate.delete(gp);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public void update(Gp gp) {
		// TODO Auto-generated method stub
		try {
			this.hibernateTemplate.update(gp);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectByDm(String dm) {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find("from Gp where dm=?", dm);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}
	public List selectByMc(String mc) {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find("from Gp where mc=?", mc);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}
	public List selectAll() {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find("from Gp");
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	/*
	 * public static void main(String[] args) throws IOException {
	 * System.out.println("开始读取"); GpDao dao =new GpDao(); new
	 * XLSWriter().XLSWriter(dao.selectAll()); }
	 */

	public List selectAllByDate(String date) {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find("from Gp where date=?", date);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectByDmAndDate(String date, String dm) {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find("from Gp where date=? and dm=?",
					date, dm);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public void deleteByDate(String date) {
		// TODO Auto-generated method stub
		try {
			List list = this.hibernateTemplate.find("from Gp where date=?",
					date);
			System.out.println("date" + date);
			System.out.println(list.size());
			this.hibernateTemplate.deleteAll(list);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}

	}
	
}
