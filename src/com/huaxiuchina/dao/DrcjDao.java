package com.huaxiuchina.dao;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huaxiuchina.model.Drcj;

// 当日成交
public class DrcjDao {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	/*public ApplicationContext ac = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private HibernateTemplate ht =  (HibernateTemplate) ac.getBean("hibernateTemplate");
*/
	public void add(Drcj drcj) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.save(drcj);
	}

	public void delete(Drcj drcj) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.delete(drcj);
	}

	public void update(Drcj drcj) {
		// TODO Auto-generated method stub
		this.hibernateTemplate.update(drcj);
	}

	public List select(Drcj drcj) {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Drcj where drcjid=?",
				drcj.getDrcjid());
	}

	public List selectAll() {
		// TODO Auto-generated method stub
		return this.hibernateTemplate.find("from Drcj");
	}
	/*public static void main(String[] args) {
		
		Drcj drcj = new Drcj();
		drcj.setDrcjid(2);
		Drcj a=(Drcj) new DrcjDao().select(drcj).get(0);
		System.out.println(a.getCjje());
		new DrcjDao().selectAll();
	}*/
}
 
