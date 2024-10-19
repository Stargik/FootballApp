package com.example.footballapp.ui.footballclubs;

public class FootballClub {
    private int id;
    private String name;
    private String city;
    private String country;
    private int year;
    private double rank;

    public FootballClub(){}

    public FootballClub(String name, String city, String country, int year, double rank){
        this.name = name;
        this.city = city;
        this.country = country;
        this.year = year;
        this.rank = rank;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
