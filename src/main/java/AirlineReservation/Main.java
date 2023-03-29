package AirlineReservation;
import AirlineReservation.Airport.Airport;

import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.time.ZonedDateTime;
import java.util.Stack;

public class Main {
    public static void main(String args[]) {
//        JFrame frame = new JFrame("My First GUI");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(300, 300);
//        frame.setLayout(new FlowLayout());
//        JButton button = new JButton("Press");
//        button.setBackground(Color.GRAY);
//        button.setSize(100, 100);
//        button.setBorderPainted(true);
//        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//        frame.add(button);
//        frame.setVisible(true);
        Flight flight1 = new Flight(AircraftType.DOUBLE_DECKER);
        Flight flight2 = new Flight(AircraftType.WIDE_BODY);
        Flight flight3 = new Flight(AircraftType.MID_BODY);
        Flight flight4 = new Flight(AircraftType.REGIONAL);
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

}

class Seat {
    private SeatClass seatClass;
    private double price;
    private boolean reserved = false;
    private int seatId;
    public String getId() {
        return String.format("%s", this.seatId);
    }

    public boolean isReserved() {
        return this.reserved;
    }

    public Seat(SeatClass seatClass, double price, int id) {
        this.seatClass = seatClass;
        this.price = price;
        this.seatId = id;
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
    private ZonedDateTime departureTime;
    private ZonedDateTime arrivalTime;
    private Airport origin;
    private Airport destination;
    private Stack<Seat> seats = new Stack<Seat>();
    private int rows = 0;
    private int maxSeats = 0;
    private int columns = 0;

    public Flight(AircraftType aircraftType) {
        Random random = new Random();
        DepartureAndArrivalDate time = new DepartureAndArrivalDate();

        this.departureTime = time.departure;
        this.arrivalTime = time.arrival;
        int columns = 0;

        // accounts for the wide variety of passenger seat configurations available on commercial aircraft.
        switch (aircraftType) {
            case REGIONAL -> {
                this.maxSeats = random.nextInt(30,75);
                columns = 3;
            }
            case MID_BODY -> {
                this.maxSeats = random.nextInt(150,220);
                columns = 4;
            }
            case WIDE_BODY -> {
                this.maxSeats = random.nextInt(350,450);
                columns = 7;
            }
            case DOUBLE_DECKER -> {
                this.maxSeats = random.nextInt(600,853);
                columns = 11;
            }
        }
        generateSeatSchedule(maxSeats);
        setSeatConfiguration(columns, maxSeats);
        System.out.println(String.format("Seat configuration set %s seats available", this.getAvailableSeats()));
        System.out.println("Flight Departure: " + this.departureTime);
        System.out.println("Flight Arrival: " + this.arrivalTime + "\n");
    }

    public int seatCount() {
        return this.maxSeats;
    }

    private void setSeatConfiguration(int columns, int maxSeats) {
        this.columns = columns;
        this.rows = maxSeats / columns;

    }

    private void generateSeatSchedule(int max) {
        Random random = new Random();

        int seatCount = 50 + random.nextInt(max);

        Price price = new Price();

        SeatClass seatClass = SeatClass.ECONOMY;
        double filledPercent = (double)this.maxSeats / seatCount;
        for(int i = 0; i < seatCount(); i++) {
            if(filledPercent >= 0.5 && filledPercent <= 0.7) {
                seatClass = SeatClass.ECONOMY_PLUS;
            }
            if(filledPercent > 0.7 && filledPercent <= 0.9) {
                seatClass = SeatClass.BUSINESS;
            }
            if (filledPercent > 0.9) {
                seatClass = SeatClass.FIRST;
            }

            Seat seat = new Seat(seatClass, price.generateRandomPrice(seatClass), i+1);
            this.seats.push(seat);
        }
    }

    public int getAvailableSeats() {
        int count = 0;

        for (int i = 0; i < seats.size(); i++) {
            if (!seats.get(i).isReserved()) {
                count++;
            }
        }
        return count;
    }
}

class DepartureAndArrivalDate {

    public DepartureAndArrivalDate() {
        generateRandomDepartureAndArrival();
    }

    public ZonedDateTime departure;
    public ZonedDateTime arrival;

    private void generateRandomDepartureAndArrival() {
        ZonedDateTime now = ZonedDateTime.now();
        Random random = new Random();

        // Generating a random number of days up to 1 year into the future
        int randomDays = random.nextInt(365 * 1);
        int randomHours = random.nextInt(24);
        int randomMinutes = random.nextInt(60);
        int randomSeconds = random.nextInt(60);

        this.departure = now
                .plus(randomDays, ChronoUnit.DAYS)
                .plus(randomHours, ChronoUnit.HOURS)
                .plus(randomMinutes, ChronoUnit.MINUTES)
                .plus(randomSeconds, ChronoUnit.SECONDS);

        this.arrival = departure.plus(randomHours, ChronoUnit.HOURS);
    }
}

class Price {
    public int generateRandomPrice(SeatClass seatClass) {
        Random random = new Random();
        int price = 0;
        switch (seatClass) {
            case FIRST ->
                price = random.nextInt(1000,34000);
            case BUSINESS ->
                price = random.nextInt(500, 16000);
            case ECONOMY_PLUS ->
                price = random.nextInt(250, 3000);
            case ECONOMY ->
                price = random.nextInt(50, 1500);
        }

        return price;
    }
}