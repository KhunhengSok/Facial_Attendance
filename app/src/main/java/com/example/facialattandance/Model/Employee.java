package com.example.facialattandance.Model;

public class Employee {
    private int id;
    private String name;
    private String department;
    private String role;
    private String profile_url;
    private String position;
    private String birth_of_date;
    private String email;
    private String employed_date;
    private String updated_at;
    private String created_at;
    private int organization_id;


    public Employee(int id, String name, String department, String role, String profile_url, String position, String birth_of_date, String email, String employed_date, String updated_at, String created_at, int organization_id) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.role = role;
        this.profile_url = profile_url;
        this.position = position;
        this.birth_of_date = birth_of_date;
        this.email = email;
        this.employed_date = employed_date;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.organization_id = organization_id;
    }

    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }
}
