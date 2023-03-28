package org.example;
import javax.swing.*;
import java.util.Random;
import java.time.ZonedDateTime;

public class Main {
    public static void main(String args[]) {
        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        JButton button = new JButton("Press");
        frame.getContentPane().add(button); // Adds Button to content pane of frame
        frame.setVisible(true);
    }
}

class Ticket {
    private double price;
    private ZonedDateTime departure;
    private ZonedDateTime arrival;
    private boolean checkedBags;
}

enum SeatClass {
    ECONOMY,
    ECONOMY_PLUS,
    BUSINESS,
    FIRST;
}

class Seat {
    private SeatClass seatClass;
    private double price;
    private boolean reserved = false;

    private int row;
    private int column;
    public Seat(SeatClass seatClass, double price) {
        this.seatClass = seatClass;
        this.price = price;
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
}