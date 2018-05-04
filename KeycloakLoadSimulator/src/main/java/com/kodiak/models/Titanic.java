package com.kodiak.models;

//import com.opencsv.bean.CsvBindByName;
//import com.opencsv.bean.CsvBindByPosition;

public class Titanic {
	
	//"","Name","PClass","Age","Sex","Survived","SexCode"
	
	//@CsvBindByName(column = "")
	//@CsvBindByPosition(position = 0)
	private int pId;
	
	//@CsvBindByName(column = "Name")
	//@CsvBindByPosition(position = 1)
    private String name;
	
	//@CsvBindByName(column = "PClass")
	//@CsvBindByPosition(position = 2)
    private String pClass;
	
	//@CsvBindByName(column = "Age")
	//@CsvBindByPosition(position = 3)
    private double age;
	
	//@CsvBindByName(column = "Sex")
	//@CsvBindByPosition(position = 4)
    private String sex;
	
	//@CsvBindByName(column = "Survived")
	//@CsvBindByPosition(position = 5)
    private int survived;
	
	//@CsvBindByName(column = "SexCode")
	//@CsvBindByPosition(position = 6)
	private int sexcode;
    
    public Titanic() {
    	// Default Constructor
    }
    
    public Titanic(int pId, String name, String pClass, double age, String sex, int survived, int sexcode) {
		super();
		this.pId = pId;
		this.name = name;
		this.pClass = pClass;
		this.age = age;
		this.sex = sex;
		this.survived = survived;
		this.sexcode = sexcode;
	}
    
    
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpClass() {
		return pClass;
	}
	public void setpClass(String pClass) {
		this.pClass = pClass;
	}
	public double getAge() {
		return age;
	}
	public void setAge(double age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getSurvived() {
		return survived;
	}
	public void setSurvived(int survived) {
		this.survived = survived;
	}
	public int getSexcode() {
		return sexcode;
	}
	public void setSexcode(int sexcode) {
		this.sexcode = sexcode;
	}

	@Override
	public String toString() {
		return "Titanic [pId=" + pId + ", name=" + name + ", pClass=" + pClass + ", age=" + age + ", sex=" + sex
				+ ", survived=" + survived + ", sexcode=" + sexcode + "]";
	}
    
    

}
