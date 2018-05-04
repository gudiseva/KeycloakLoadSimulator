package com.kodiak.models;

import com.opencsv.bean.CsvBindByName;

public class BabyName {

	public BabyName() {
		// Default Constructor
	}
	
	// "year","name","percent","sex"
	
	@CsvBindByName(column = "year")
	private int year;
	
	@CsvBindByName(column = "name")
	private String name;
	
	@CsvBindByName(column = "percent")
	private double percent;
	
	@CsvBindByName(column = "sex")
	private String sex;
	
	
	public BabyName(int year, String name, double percent, String sex) {
		super();
		this.year = year;
		this.name = name;
		this.percent = percent;
		this.sex = sex;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "BabyName [year=" + year + ", name=" + name + ", percent=" + percent + ", sex=" + sex + "]";
	}
	
}
