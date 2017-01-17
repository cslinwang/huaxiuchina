package com.huaxiuchina.model;

/**
 * T entity. @author MyEclipse Persistence Tools
 */

public class T implements java.io.Serializable {

	// Fields

	private Integer tid;
	private String date;
	private String user;
	private String dm;
	private String mc;
	private String fc;
	private String jg;
	private String sl;
	private String sy;

	// Constructors

	/** default constructor */
	public T() {
	}

	/** full constructor */
	public T(String date, String user, String dm, String mc, String fc,
			String jg, String sl, String sy) {
		this.date = date;
		this.user = user;
		this.dm = dm;
		this.mc = mc;
		this.fc = fc;
		this.jg = jg;
		this.sl = sl;
		this.sy = sy;
	}

	// Property accessors

	public Integer getTid() {
		return this.tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
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

	public String getFc() {
		return this.fc;
	}

	public void setFc(String fc) {
		this.fc = fc;
	}

	public String getJg() {
		return this.jg;
	}

	public void setJg(String jg) {
		this.jg = jg;
	}

	public String getSl() {
		return this.sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getSy() {
		return this.sy;
	}

	public void setSy(String sy) {
		this.sy = sy;
	}

}