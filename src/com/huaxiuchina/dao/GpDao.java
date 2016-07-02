package com.huaxiuchina.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huaxiuchina.model.Gp;
import com.huaxiuchina.util.XLSWriter;

// 当日成交
public class GpDao {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	/*public ApplicationContext ac = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private HibernateTemplate hibernateTemplate =  (HibernateTemplate) ac.getBean("hibernateTemplate");
*/
	public void add(Gp gp) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.save(gp);
	}

	public void delete(Gp gp) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.delete(gp);
	}

	public void update(Gp gp) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.update(gp);
	}

	public List select(Gp gp) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Gp where dm=?",
				gp.getDm());
	}

	public List selectAll() {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Gp");
	}
	/*public static void main(String[] args) throws IOException {
		System.out.println("开始读取");
		GpDao dao =new GpDao();
		new XLSWriter().XLSWriter(dao.selectAll());
	}*/
}
 
