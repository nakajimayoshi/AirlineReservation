package AirlineReservation.Airport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Airport implements Comparable<Airport> {
    private String city;
    private String country;
    private String code;

    public Airport() {
        System.out.println("Error: Did not declare member variables");
    }

    public Airport(String city, String code) {
        this.city = city;
        this.code = code;
        this.country = "USA";
    }

    public Airport(String country, String city, String code) {
        this.city = city;
        this.country = country;
        this.code = code;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public String getCode() {
        return this.code;
    }
    @Override
    public int compareTo(Airport o) {
        /*
         *Has to be called by an Object in that class and will
         *tell us how to compare that calling object to an input o object
         *
         *smaller.compareTo(bigger) --> Negative int [smaller - bigger = NEGATIVE]
         *sameSize.compareTo(oSameSize) --> 0 [equal - equal = 0]
         *bigger.compareTo(smaller) --> Positive int [bigger - smaller = POSITIVE]
         */
        return (this.getCountry()).compareTo(o.getCountry());
    }

    public void printAirports() {
//        for(Airport ap: airports) {
//            System.out.println(ap.getCountry());
//        }
//        System.out.println("----------");
    }

    public static void main(String args[]) throws IOException, CsvValidationException {

        FileReader airports_data = new FileReader("src/main/java/AirlineReservation/Airport/airports.csv");
        CSVReader reader = new CSVReader(airports_data);

        ArrayList<Airport> airports = new ArrayList<>();
        String[] line;
        boolean isFirstLine = true;

        while ((line = reader.readNext()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
                continue; // skip the header row
            }

            String country = line[0];
            String city = line[1];
            String icao = line[2];

            airports.add(new Airport(country, city, icao));
            System.out.println("Added: " + icao);
        }

        reader.close();

    }
}