package com.huaxiuchina.model;

import java.util.Date;

/**
 * Drcj entity. @author MyEclipse Persistence Tools
 */

public class Drcj implements java.io.Serializable {

	// Fields

	private Integer drcjid;
	private Date rq;
	private String jylb;
	private Integer zqdm;
	private String zqmc;
	private Double cjjg;
	private Integer cjsl;
	private Integer zqye;
	private Double cjje;
	private String gpdm;

	// Constructors

	/** default constructor */
	public Drcj() {
	}

	/** full constructor */
	public Drcj(Date rq, String jylb, Integer zqdm, String zqmc, Double cjjg,
			Integer cjsl, Integer zqye, Double cjje, String gpdm) {
		this.rq = rq;
		this.jylb = jylb;
		this.zqdm = zqdm;
		this.zqmc = zqmc;
		this.cjjg = cjjg;
		this.cjsl = cjsl;
		this.zqye = zqye;
		this.cjje = cjje;
		this.gpdm = gpdm;
	}

	// Property accessors

	public Integer getDrcjid() {
		return this.drcjid;
	}

	public void setDrcjid(Integer drcjid) {
		this.drcjid = drcjid;
	}

	public Date getRq() {
		return this.rq;
	}

	public void setRq(Date rq) {
		this.rq = rq;
	}

	public String getJylb() {
		return this.jylb;
	}

	public void setJylb(String jylb) {
		this.jylb = jylb;
	}

	public Integer getZqdm() {
		return this.zqdm;
	}

	public void setZqdm(Integer zqdm) {
		this.zqdm = zqdm;
	}

	public String getZqmc() {
		return this.zqmc;
	}

	public void setZqmc(String zqmc) {
		this.zqmc = zqmc;
	}

	public Double getCjjg() {
		return this.cjjg;
	}

	public void setCjjg(Double cjjg) {
		this.cjjg = cjjg;
	}

	public Integer getCjsl() {
		return this.cjsl;
	}

	public void setCjsl(Integer cjsl) {
		this.cjsl = cjsl;
	}

	public Integer getZqye() {
		return this.zqye;
	}

	public void setZqye(Integer zqye) {
		this.zqye = zqye;
	}

	public Double getCjje() {
		return this.cjje;
	}

	public void setCjje(Double cjje) {
		this.cjje = cjje;
	}

	public String getGpdm() {
		return this.gpdm;
	}

	public void setGpdm(String gpdm) {
		this.gpdm = gpdm;
	}

}