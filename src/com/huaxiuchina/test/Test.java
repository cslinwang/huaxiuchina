package com.huaxiuchina.test;

import java.util.List;

import com.huaxiuchina.util.XLSReader;

public class Test {
	public static void main(String[] args) throws Exception {
		List list = new XLSReader().readExcelData("WebRoot/upload/1.xls");
	}
}
