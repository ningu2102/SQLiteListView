package com.example.sqlitelistview;

public class Contacts
{
    String name,img,phone;

    public Contacts(String name, String img, String phone) {
        this.name = name;
        this.img = img;
        this.phone = phone;
    }

    public Contacts() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

