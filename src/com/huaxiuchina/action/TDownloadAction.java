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

	/** 导出Excel测试 */
	public String exportExcel() {
		try {
			ByteArrayInputStream is = new TOut().tOut(name);

			excelStream = is; // 文件流
			excelFileName = "T_"+name + "_" + new GetDate().getDate() + ".xls"; // 设置下载的文件名
			System.out.println("生成完毕: " + excelFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// -------------------------------------------------------------
	private InputStream excelStream; // 输出流变量
	private String excelFileName; // 下载文件名

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