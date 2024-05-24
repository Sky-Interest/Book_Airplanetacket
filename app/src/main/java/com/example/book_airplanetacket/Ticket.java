package com.example.book_airplanetacket;

public class Ticket {
    private int id;
    private String flightNumber;
    private String airline;
    private String departureLocation;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private String seatType;
    private String seatNumber;
    private int passengerId;
    private int remainingTickets; // 新增字段：剩余票数

    public Ticket(int id, String flightNumber, String airline, String departureLocation, String destination, String departureTime, String arrivalTime, double price, String seatType, String seatNumber, int passengerId, int remainingTickets) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.departureLocation = departureLocation;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.seatType = seatType;
        this.seatNumber = seatNumber;
        this.passengerId = passengerId;
        this.remainingTickets = remainingTickets;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(int remainingTickets) {
        this.remainingTickets = remainingTickets;
    }
}
