package com.example.flightmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notice_boards")
public class NoticeBoard {

    @Id
    @Column(length = 64)
    @NotBlank(message = "ID is required")
    private String id; // păstrăm String id-ul tău (poți trece la Long/@GeneratedValue dacă vrei)

    @NotBlank(message = "Date is required")
    @Size(max = 100)
    private String date;

    @OneToMany(mappedBy = "noticeBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> flightsOfTheDay = new ArrayList<>();

    public NoticeBoard() {
        this.id = "";
        this.date = "";
    }

    public NoticeBoard(String id, String date) {
        this.id = id;
        this.date = date;
    }

    // getters / setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public List<Flight> getFlightsOfTheDay() { return flightsOfTheDay; }
    public void setFlightsOfTheDay(List<Flight> flightsOfTheDay) { this.flightsOfTheDay = flightsOfTheDay; }

    // helper methods to keep both sides in sync
    public void addFlight(Flight f) {
        f.setNoticeBoard(this);
        this.flightsOfTheDay.add(f);
    }

    public void removeFlight(Flight f) {
        f.setNoticeBoard(null);
        this.flightsOfTheDay.remove(f);
    }

    @Override
    public String toString() {
        return "NoticeBoard{" + "id='" + id + '\'' + ", date='" + date + '\'' + '}';
    }
}
