package com.huaxiuchina.action;

import org.apache.poi.hssf.usermodel.*;

import com.huaxiuchina.util.GetDate;
import com.huaxiuchina.util.GuideOut;
import com.huaxiuchina.util.GuideProduce;
import com.huaxiuchina.util.TOut;
import com.opensymphony.xwork2.ActionSupport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TDownloadAction extends ActionSupport {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/** ����Excel���� */
	public String exportExcel() {
		try {
			ByteArrayInputStream is = new TOut().tOut(name);

			excelStream = is; // �ļ���
			excelFileName = "T_"+name + "_" + new GetDate().getDate() + ".xls"; // �������ص��ļ���
			System.out.println("�������: " + excelFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// -------------------------------------------------------------
	private InputStream excelStream; // ���������
	private String excelFileName; // �����ļ���

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
}