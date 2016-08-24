package com.huaxiuchina.action;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huaxiuchina.dao.DaydealDao;
import com.huaxiuchina.dao.UserDao;
import com.huaxiuchina.model.User;
import com.huaxiuchina.util.GetDate;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction implements ModelDriven<User> {
	private User user = new User();

	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}

	UserDao serviceImp = new UserDao();
	public Map session = ActionContext.getContext().getSession();
	public Map request = (Map) ActionContext.getContext().get("request");

	// ��¼
	public String login() throws Exception {

		List userList = serviceImp.login(user);
		if (userList.size() == 0) {
			return "loginFalse";
		}
		DaydealDao dao = new DaydealDao();
		session.put("daydeallist",
				dao.selectAll(user.getName(), new GetDate().getDate()));
		session.put("type", ((User) (userList.get(0))).getType());
		session.put("model", ((User) (userList.get(0))).getModel());
		session.put("user", (User) (userList.get(0)));
		return "loginSuccess";
	}

	// ��ѯ����
	public String userSelectAll() {
		session.put("userlist", (serviceImp.selectAll()));
		return "userSelectAll";
	}

	// �޸�
	public String userUpdate() {
		session.put("userdetail",
				(User) (serviceImp.selectById(user.getUid()).get(0)));
		return "userUpdate";
	}

	public String userUpdate1() {
		System.out.println(user.getName() + user.getPwd() + user.getType()
				+ user.getUid());
		serviceImp.update(user);
		session.put("userlist", (serviceImp.selectAll()));
		return "userUpdate1";
	}

	// ����
	public String userAdd() {
		serviceImp.add(user);
		session.put("userlist", (serviceImp.selectAll()));
		return "userAdd";
	}

	// ɾ��
	public String userDelete() {
		serviceImp.delete(user);
		session.put("userlist", (serviceImp.selectAll()));
		return "userDelete";
	}
}
