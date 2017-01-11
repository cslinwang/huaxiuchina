package com.huaxiuchina.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huaxiuchina.model.Daydeal;
import com.huaxiuchina.model.Guide;
import com.huaxiuchina.model.Model;
import com.huaxiuchina.util.GetDate;
import com.huaxiuchina.util.GuideProduce;
import com.huaxiuchina.util.XLSWriter;
import com.opensymphony.xwork2.ActionContext;

// 当日成交
public class ModelDao extends HibernateTemplate {
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
		try {
			this.hibernateTemplate.save(model);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public void delete(Model model) {
		// TODO Auto-generated method stub
		try {
			this.hibernateTemplate.delete(model);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public void update(Model model) {
		// TODO Auto-generated method stub
		try {
			this.hibernateTemplate.update(model);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectByDm(String dm) {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find("from Model where dm=?", dm);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectByDm(String dm, String name, int model) {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find(
					"from Model where dm=? and user=? and model=?", dm, name,
					model);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectByDm(String dm, String name) {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find(
					"from Model where dm=? and user=?", dm, name);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectByName(String name) throws Exception {
		// TODO Auto-generated method stub
		try {

			ArrayList<Model> newList = new ArrayList<Model>();
			List onlyList = (ArrayList<String>) new GuideProduce()
					.getOnlyByUser(name);
			ArrayList<Model> oldList = (ArrayList<Model>) this.hibernateTemplate
					.find("from Model where user=?", name);

			for (int i = 0; i < onlyList.size(); i++) {
				String dm = (String) onlyList.get(i);
				for (int j = 0; j < oldList.size(); j++) {
					String dm1 = oldList.get(j).getDm();
					if (dm1.equals(dm)) {
						newList.add(oldList.get(j));

					}

				}

			}
			return newList;
		} finally {
			hibernateTemplate.getSessionFactory().openSession().close();
		}

	}

	public List selectByName1(String name) throws Exception {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find("from Model where user=?", name);

		} finally {
			hibernateTemplate.getSessionFactory().openSession().close();
		}

	}

	public List selectAll() {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find("from Model");
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	/*
	 * public static void main(String[] args) throws IOException {
	 * System.out.println("开始读取"); ModelDao dao =new ModelDao(); new
	 * XLSWriter().XLSWriter(dao.selectAll()); }
	 */

	public List selectAllByDate(String date) {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find("from Model where date=?", date);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectByDmAndDate(String date, String dm) {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find(
					"from Model where date=? and dm=?", date, dm);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}

	public List selectById(Integer mid) {
		// TODO Auto-generated method stub
		try {
			return this.hibernateTemplate.find("from Model where mid=?", mid);
		} finally {
			hibernateTemplate.getSessionFactory().close();
		}
	}
}
