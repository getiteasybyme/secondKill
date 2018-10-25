package com.secondKill.pojo;

public class Admin {
    private Integer adminId;

    private String password;

    public Admin(Integer adminId, String password) {
        this.adminId = adminId;
        this.password = password;
    }

    public Admin() {
        super();
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}