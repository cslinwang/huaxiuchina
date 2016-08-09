package com.huaxiuchina.test;

import java.util.List;

import com.huaxiuchina.util.XLSReader;

public class Test {
	public static void main(String[] args) throws Exception {
		double a =  20.21;
		int c = 2000;
		double b = 20.87;
		int d = 1000;
		double e = (a * c + b * d) / (c + d);

		// double n = 21.77*1000
		System.out.println(e);
	}
}
