package com.wzhsun.miaosha.controller.viewobject;

public class UserVO {

    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
    private String terphone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTerphone() {
        return terphone;
    }

    public void setTerphone(String terphone) {
        this.terphone = terphone;
    }
}
