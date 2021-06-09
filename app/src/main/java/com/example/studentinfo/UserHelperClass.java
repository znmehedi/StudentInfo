package com.example.studentinfo;

public class UserHelperClass {
   private String fullName, id_Number_txt, email, phone, password, balance, semester, cgpa;

    public UserHelperClass() {
    }

    public UserHelperClass(String fullName, String id_Number_txt, String email, String phone, String password, String balance, String semester, String cgpa) {
        this.fullName = fullName;
        this.id_Number_txt = id_Number_txt;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.balance = balance;
        this.semester = semester;
        this.cgpa = cgpa;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId_Number_txt() {
        return id_Number_txt;
    }

    public void setId_Number_txt(String id_Number_txt) {
        this.id_Number_txt = id_Number_txt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }
}
