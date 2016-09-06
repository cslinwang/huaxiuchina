package com.huaxiuchina.model;

/**
 * Status entity. @author MyEclipse Persistence Tools
 */

public class Status implements java.io.Serializable {

	// Fields

	private Integer sid;
	private String name;
	private String mc;
	private String date;
	private String price;
	private String sum;
	private String falg;

	// Constructors

	/** default constructor */
	public Status() {
	}

	/** full constructor */
	public Status(String name, String mc, String date, String price,
			String sum, String falg) {
		this.name = name;
		this.mc = mc;
		this.date = date;
		this.price = price;
		this.sum = sum;
		this.falg = falg;
	}

	// Property accessors

	public Integer getSid() {
		return this.sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSum() {
		return this.sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getFalg() {
		return this.falg;
	}

	public void setFalg(String falg) {
		this.falg = falg;
	}

}