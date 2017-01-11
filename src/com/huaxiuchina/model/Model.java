package com.huaxiuchina.model;

/**
 * Model entity. @author MyEclipse Persistence Tools
 */

public class Model implements java.io.Serializable {

	// Fields

	private Integer mid;
	private String dm;
	private String user;
	private Integer model;
	private String price;
	private String base;
	private String sum;
	private String mc;

	// Constructors

	/** default constructor */
	public Model() {
	}

	/** full constructor */
	public Model(String dm, String user, Integer model, String price,
			String base, String sum, String mc) {
		this.dm = dm;
		this.user = user;
		this.model = model;
		this.price = price;
		this.base = base;
		this.sum = sum;
		this.mc = mc;
	}

	// Property accessors

	public Integer getMid() {
		return this.mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getDm() {
		return this.dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Integer getModel() {
		return this.model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBase() {
		return this.base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getSum() {
		return this.sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

}