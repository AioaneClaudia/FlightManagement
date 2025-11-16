package com.example.flightmanagement.model;

import java.util.ArrayList;
import java.util.List;

//modificare local date
public class NoticeBoard {
    private String id;
    private String date;
    private List<Flight> flightsOfTheDay = new ArrayList<>();

    public NoticeBoard() {
        this.id = "";
        this.date = "";
    }

    public NoticeBoard(String id, String date) {
        this.id = id;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public List<Flight> getFlightsOfTheDay() {

        return flightsOfTheDay;
    }

    public void setFlightsOfTheDay(List<Flight> flightsOfTheDay) {

        this.flightsOfTheDay = flightsOfTheDay;
    }

    @Override
    public String toString() {
        return "NoticeBoard{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
