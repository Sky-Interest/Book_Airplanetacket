package com.example.book_airplanetacket.model;

import java.math.BigDecimal;

public class TicketOrder {
    private int orderId;
    private String flightNumber;
    private String airline;
    private String departureLocation;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private BigDecimal price;
    private String seatType;
    private String seatNumber;
    private int passengerId;
    private int ticketCount; // 新增字段：购买的票数

    // 构造函数
    public TicketOrder(int orderId, String flightNumber, String airline, String departureLocation, String destination,
                       String departureTime, String arrivalTime, BigDecimal price, String seatType, String seatNumber,
                       int passengerId, int ticketCount) {
        this.orderId = orderId;
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
        this.ticketCount = ticketCount; // 设置购买的票数
    }

    // Getter 和 Setter 方法
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
}
