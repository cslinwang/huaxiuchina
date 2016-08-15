package com.huaxiuchina.test;

import java.util.List;

import com.huaxiuchina.util.XLSReader;

public class Test {
	public static void main(String[] args) throws Exception {
		double a =  24.55;
		int c = 700;
		double b = 23.5373125*0.95;
		int d = 3300;
		double e = (a * c + b * d) / (c + d);

		// double n = 21.77*1000
		System.out.println(e);
		System.out.println(1000*Math.pow(1.5, 3));
	}
}
