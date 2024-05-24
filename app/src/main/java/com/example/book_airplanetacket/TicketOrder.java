package com.example.book_airplanetacket;

public class TicketOrder extends Ticket {
    private int orderId;
    private int ticketCount; // 购买的票数

    // 构造函数
    public TicketOrder(int orderId, int id, String flightNumber, String airline, String departureLocation,
                       String destination, String departureTime, String arrivalTime, double price,
                       String seatType, String seatNumber, int passengerId, int remainingTickets, int ticketCount) {
        super(id, flightNumber, airline, departureLocation, destination, departureTime, arrivalTime, price, seatType, seatNumber, passengerId, remainingTickets);
        this.orderId = orderId;
        this.ticketCount = ticketCount; // 设置购买的票数
    }

    // Getter 和 Setter 方法
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
}
