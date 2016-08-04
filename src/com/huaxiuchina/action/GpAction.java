package com.huaxiuchina.action;

import java.io.IOException;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huaxiuchina.dao.GpDao;
import com.huaxiuchina.model.Gp;
import com.huaxiuchina.util.GetDate;
import com.huaxiuchina.util.XLSWriter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class GpAction implements ModelDriven<Gp> {
	private Gp gp = new Gp();

	public Gp getModel() {
		// TODO Auto-generated method stub
		return gp;
	}

	private String dm;

	private GpDao serviceImp = new GpDao();

	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public Map session = ActionContext.getContext().getSession();
	public Map request = (Map) ActionContext.getContext().get("request");

	// 查询所有
	public String selectAllByDate() throws Exception {
		session.put("gplist",
				serviceImp.selectAllByDate(new GetDate().getDate()));
		System.out.println(serviceImp.selectAll().size());
		return "gpSelectAllByDate";
	}

	public String download() throws IOException {
		new XLSWriter().XLSWriter(serviceImp.selectAll());
		return "downloadSuccess";
	}

	public String gpUpdate() throws Exception {
		System.out.println(gp.getDm());
		session.put(
				"gpdetail",
				(Gp) (serviceImp.selectByDmAndDate(new GetDate().getDate(),
						gp.getDm()).get(0)));
		return "gpUpdate";
	}

	public String gpUpdate1() throws Exception {
		double k = gp.getK();
		double j = gp.getJ();
		System.out.println("123124124124124" + k + "  " + j);
		gp = (Gp) (serviceImp.selectByDmAndDate(new GetDate().getDate(),
				gp.getDm()).get(0));
		System.out.println("修改前" + k);
		gp.setK(k);
		gp.setJ(j);
		System.out.println("更新后"+gp.getK());
		serviceImp.update(gp);
		
		session.put("gplist",
				serviceImp.selectAllByDate(new GetDate().getDate()));
		return "gpUpdate1";
	}
}
