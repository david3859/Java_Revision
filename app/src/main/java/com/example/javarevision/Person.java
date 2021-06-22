package com.example.javarevision;

public class Person {
	
	
	private String name;
    private String email;
    private int  age;
    private String sexe;
   
    
    public Person(String name, String email,int age,String sexe) {
		this.name = name;
		this.email = email;
		this.age=age;
		this.sexe=sexe;
		
	}

	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	
	public String getName() {
		return name;
	}
	
	public int getAge() {
		return age;
	}

	/**
	 * @param age the name to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	

	@Override
	public String toString() {
		return "Firstname:" + name + "  Lastname:" + email+"  YOB:"+age+"  Sexe:"+sexe+ " ";
	}

}
