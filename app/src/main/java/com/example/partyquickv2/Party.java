package com.example.partyquickv2;

import java.util.Objects;

public class Party {

    private String title;
    private String location;
    private Date date;
    private String image;

    public Party() {
        title = "";
        location = "";
        date = new Date();
        image = "";
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public Party setTitle(String title) {
        this.title = title;
        return this;
    }

    public Party setLocation(String location) {
        this.location = location;
        return this;
    }

    public Party setDate(Date date) {
        this.date = new Date(date);
        return this;
    }

    public String getImage() {
        return image;
    }

    public Party setImage(String image) {
        this.image = image;
        return this;
    }

    public boolean sameTitle(String p){
        return p.equalsIgnoreCase(title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Party party = (Party) o;
        return Objects.equals(title, party.title) && Objects.equals(location, party.location) && Objects.equals(date, party.date) && Objects.equals(image, party.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, location, date, image);
    }
}
