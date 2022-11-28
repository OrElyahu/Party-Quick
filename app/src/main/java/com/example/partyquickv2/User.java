package com.example.partyquickv2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {

    private String fullName, phone, password;

    private List<Integer> partyList;

    private int partyListCount;

    public User() { }

    public User(String fullName, String phone, String password) {
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
        this.partyListCount = 0;
        this.partyList = new ArrayList<>();
    }

    public User setPartyListCount(int partyListCount) {
        this.partyListCount = partyListCount;
        return this;
    }

    public int getPartyListCount() {
        return partyListCount;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<Integer> getPartyList() {
        return partyList;
    }

    public User setPartyList(List<Integer> partyList) {
        this.partyList = partyList;
        return this;
    }
}
