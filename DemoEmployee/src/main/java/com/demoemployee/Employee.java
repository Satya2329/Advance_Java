package com.demoemployee;

import java.sql.Date;

public class Employee {

    private int empId;
    private String empName;
    private String phoneNo;
    private String designation;
    private Date joiningDate;
    private double salary;
    private int age;

    // Default Constructor
    public Employee() {
    }

    // Parameterized Constructor
    public Employee(int empId, String empName, String phoneNo,
                    String designation, Date joiningDate,
                    double salary, int age) {

        this.empId = empId;
        this.empName = empName;
        this.phoneNo = phoneNo;
        this.designation = designation;
        this.joiningDate = joiningDate;
        this.salary = salary;
        this.age = age;
    }

    // Getters and Setters

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
