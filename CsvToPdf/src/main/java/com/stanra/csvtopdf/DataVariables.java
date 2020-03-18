package com.stanra.csvtopdf;


public class DataVariables {
	
	public String empid;
	public String name;
	public String Date;
	public int no_of_working_day;
	public int total_working_days;
	public String payment_mode;
	public String email;
	
	
	public double basic;
	public double hra;
	public double travel;
	public double medical;
	public double total;
	public String templateName;
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTemplateName() {
		return templateName;
	}
	public int getTotal_working_days() {
		return total_working_days;
	}
	public void setTotal_working_days(int total_working_days) {
		this.total_working_days = total_working_days;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public double getBasic() {
		return basic;
	}
	public void setBasic(double d) {
		this.basic = d;
	}
	public double getHra() {
		return hra;
	}
	public void setHra(double hra) {
		this.hra = hra;
	}
	public double getTravel() {
		return travel;
	}
	public void setTravel(double travel) {
		this.travel = travel;
	}
	public double getMedical() {
		return medical;
	}
	public void setMedical(double medical) {
		this.medical = medical;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getPayment_mode() {
		return payment_mode;
	}
	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}
	public int getNo_of_working_day() {
		return no_of_working_day;
	}
	public void setNo_of_working_day(int no_of_working_day) {
		this.no_of_working_day = no_of_working_day;
	}	
	
	@Override
	public String toString()
	{
		return  "" + empid + "		" + name + "		 " + Date + " 		" + no_of_working_day + " 		" 
				+ total_working_days + "		 " + payment_mode +"		"	+ 
				basic + " 		" + hra + "		 " + travel + "		 " + medical + "   "  + " TOTAL: "+ total;
		
	}
	

}
