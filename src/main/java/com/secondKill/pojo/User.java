package com.secondKill.pojo;

import java.util.Date;

public class User {
    private Integer userId;

    private String userName;

    private String password;

    private String identityCard;

    private String email;

    private String telephone;

    private Integer age;

    private Date birthday;

    private String gender;

    public User(Integer userId, String userName, String password, String identityCard, String email, String telephone, Integer age, Date birthday, String gender) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.identityCard = identityCard;
        this.email = email;
        this.telephone = telephone;
        this.age = age;
        this.birthday = birthday;
        this.gender = gender;
    }

    public User() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard == null ? null : identityCard.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }
}