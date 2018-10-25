package com.secondKill.vo;

import com.secondKill.pojo.User;

import java.util.Date;

public class UserInformationVo {

    private String userName;

    private String identityCard;

    private String email;

    private String telephone;

    private Integer age;

    private Date birthday;

    private String gender;
    public UserInformationVo(){}

    public UserInformationVo(User user) {
        this.userName = user.getUserName();
        this.identityCard = user.getIdentityCard();
        this.email = user.getEmail();
        this.telephone = user.getTelephone();
        this.age = user.getAge();
        this.birthday = user.getBirthday();
        this.gender = user.getGender();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
        this.gender = gender;
    }
}
