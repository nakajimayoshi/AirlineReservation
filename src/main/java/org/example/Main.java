package org.example;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Random;
import java.time.ZonedDateTime;

public class Main {
    public static void main(String args[]) {
        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new FlowLayout());
        JButton button = new JButton("Press");
        button.setBackground(Color.GRAY);
        button.setSize(100, 100);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        frame.add(button);

        frame.setVisible(true);
    }
}

class Ticket {
    private double price;
    private ZonedDateTime departure;
    private ZonedDateTime arrival;
    private int checkedBags;
    private boolean insurance;
}

enum SeatClass {
    ECONOMY,
    ECONOMY_PLUS,
    BUSINESS,
    FIRST;
}
class SeatId {
    private int row;
    private int column;

    public SeatId(int row, int column) {
        this.row = row;
        this.column = column;
    }
    public String getId() {
        return String.format("%s%s", this.row, this.column);
    }
}

class Seat {
    private SeatClass seatClass;
    private double price;

    private boolean reserved = false;

    public boolean isReserved() {
        return reserved;
    }

    private SeatId seatId;
    public Seat(SeatClass seatClass, double price, int row, int column) {
        this.seatClass = seatClass;
        this.price = price;
        this.seatId = new SeatId(row, column);
    }
}

enum AircraftType {
    REGIONAL,
    MID_BODY,
    WIDE_BODY,
    DOUBLE_DECKER,
}

class Flight {

    private AircraftType aircraftType;
    private ZonedDateTime departure;
    private ZonedDateTime arrival;
    private Seat[] seats;

    private int rows;
    private int columns;

    public Flight(ZonedDateTime departure, ZonedDateTime arrival, AircraftType aircraftType) {
        this.departure = departure;
        this.arrival = arrival;

        int maxSeats = 0;
        switch (aircraftType) {
            case REGIONAL ->
                maxSeats = 70;
            case MID_BODY ->
                maxSeats = 200;
            case WIDE_BODY ->
                maxSeats = 440;
            case DOUBLE_DECKER ->
                maxSeats = 853;
        }

        if (maxSeats < 50) {
            // 1-2 configuration
            setSeatConfiguration(3, maxSeats);
        }

        if(maxSeats > 100 && maxSeats < 250) {
            // 2-2 configuration
            setSeatConfiguration(4, maxSeats);
        }

        if(maxSeats > 251 && maxSeats < 500) {
            // 2-3-2 configuration
            setSeatConfiguration(7, maxSeats);
        }

        if(maxSeats > 501 && maxSeats < 854) {
            // 3-5-3 configuration
            setSeatConfiguration(11, maxSeats);
        }

        generateSeatSchedule(maxSeats);
    }

    public int seatCount() {
        return seats.length;
    }

    private void setSeatConfiguration(int columns, int maxSeats) {
        this.columns = columns;
        this.rows = maxSeats / columns;
    }

    private void generateSeatSchedule(int max) {
        Random random = new Random();
        int seatCount = 50 + random.nextInt(max);
        this.seats = new Seat[seatCount];
    }

    public int getAvailibleSeats() {
        int count = 0;

        for (int i = 0; i < seats.length; i++) {
            if (!seats[i].isReserved()) {
                count++;
            }
        }
        return count;
    }
}