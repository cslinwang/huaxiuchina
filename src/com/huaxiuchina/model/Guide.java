package com.huaxiuchina.model;

public class Guide {
	private String dm;
	private String mc;
	private double price;
	private double price1;
	private int num;
	private double sum;
	// Constructors

		/** default constructor */
		public Guide() {
		}

		/** full constructor */
		public Guide(String dm, String mc, Double price, Double price1,
				Integer num, double sum) {
			this.dm = dm;
			this.mc = mc;
			this.price = price;
			this.price1 = price1;
			this.num = num;
			this.sum = sum;
		}

		public String getDm() {
			return dm;
		}

		public void setDm(String dm) {
			this.dm = dm;
		}

		public String getMc() {
			return mc;
		}

		public void setMc(String mc) {
			this.mc = mc;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public double getPrice1() {
			return price1;
		}

		public void setPrice1(double price1) {
			this.price1 = price1;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public double getSum() {
			return sum;
		}

		public void setSum(Double sum) {
			this.sum = sum;
		}

		// Property accessors
		

}
