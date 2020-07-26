package com.example.facialattandance.Model;

public class Employee {
    private String name;
    private String department;
    private String profile_url;
    private String position;
    private String birth_of_date;
    private String email;
    private String employed_date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBirth_of_date() {
        return birth_of_date;
    }

    public void setBirth_of_date(String birth_of_date) {
        this.birth_of_date = birth_of_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployed_date() {
        return employed_date;
    }

    public void setEmployed_date(String employed_date) {
        this.employed_date = employed_date;
    }

    public Employee(String name, String department, String profile_url, String position, String birth_of_date, String email, String employed_date) {
        this.name = name;
        this.department = department;
        this.profile_url = profile_url;
        this.position = position;
        this.birth_of_date = birth_of_date;
        this.email = email;
        this.employed_date = employed_date;
    }

    public Employee() {
    }
}
