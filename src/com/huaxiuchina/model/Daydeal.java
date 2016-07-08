package com.huaxiuchina.model;

/**
 * Daydeal entity. @author MyEclipse Persistence Tools
 */

public class Daydeal implements java.io.Serializable {

	// Fields

	private Integer dealid;
	private String dm;
	private String mc;
	private String mmbz;
	private String cjjg;
	private String cjsl;
	private String date;
	private Integer model;
	private String username;
	private String base;
	private String sum;

	// Constructors

	/** default constructor */
	public Daydeal() {
	}

	/** full constructor */
	public Daydeal(String dm, String mc, String mmbz, String cjjg, String cjsl,
			String date, Integer model, String username, String base, String sum) {
		this.dm = dm;
		this.mc = mc;
		this.mmbz = mmbz;
		this.cjjg = cjjg;
		this.cjsl = cjsl;
		this.date = date;
		this.model = model;
		this.username = username;
		this.base = base;
		this.sum = sum;
	}

	// Property accessors

	public Integer getDealid() {
		return this.dealid;
	}

	public void setDealid(Integer dealid) {
		this.dealid = dealid;
	}

	public String getDm() {
		return this.dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getMmbz() {
		return this.mmbz;
	}

	public void setMmbz(String mmbz) {
		this.mmbz = mmbz;
	}

	public String getCjjg() {
		return this.cjjg;
	}

	public void setCjjg(String cjjg) {
		this.cjjg = cjjg;
	}

	public String getCjsl() {
		return this.cjsl;
	}

	public void setCjsl(String cjsl) {
		this.cjsl = cjsl;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getModel() {
		return this.model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
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

}