package com.huaxiuchina.util;

import java.util.Comparator;

import com.huaxiuchina.model.Daydeal;

public class Change {
	/*
	 * public Comparator<Daydeal> comparator = new Comparator<Daydeal>(){ public
	 * int compare(Daydeal d1, Daydeal d2) { return d1.getCjjg()-d2.getCjjg; } }
	 */
	public int changeToHhundred(int value) {
		String iValue = String.valueOf(value);
		if (Integer.valueOf(value) % 100 < 50) {
			return Integer.valueOf(String.valueOf((Integer.valueOf(iValue
					.substring(0, 1)) + 0)) + "00");
		} else {
			return Integer.valueOf(String.valueOf((Integer.valueOf(iValue
					.substring(0, 1)) + 1)) + "00");
		}
	}

	public static void main(String[] args) {
		System.out.println(new Change().changeToHhundred(810));
	}
}