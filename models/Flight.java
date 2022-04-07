package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Flight {

    private Airline airline;
    private String flight;
    private String plane;
    private String departure;
    private String destination ;
    private Date  date;
    private Date time ;
    private List<String> Towers;

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    private Airplane airplane;

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    private Airport departureAirport;
    private Airport destinationAirport;


    public Flight(String flightCode, String plane, String departure,
                  String destination, Date date,
                  Date time, List<String> towers,Airline airline,Airport departureAirport,Airport destinationAirport) {
        this.flight = flightCode;
        this.plane = plane;
        this.departure = departure;
        this.destination= destination;
        this.date = date;
        this.time = time;
        Towers = towers;
        this.airline = airline;
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;

    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flightCode) {
        this.flight = flightCode;
    }

    public String getPlane() {
        return plane;
    }

    public void setPlane(String plane) {
        this.plane = plane;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departure = departureAirport;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<String> getTowers() {
        return Towers;
    }

    public void setTowers(ArrayList<String> towers) {
        Towers = towers;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return Objects.equals(getFlight(), flight.getFlight()) && Objects.equals(getPlane(), flight.getPlane()) && Objects.equals(getDeparture(), flight.getDeparture()) && Objects.equals(getDestination(), flight.getDestination()) && Objects.equals(getDate(), flight.getDate()) && Objects.equals(getTime(), flight.getTime()) && Objects.equals(getTowers(), flight.getTowers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFlight(), getPlane(), getDeparture(), getDestination(), getDate(), getTime(), getTowers());
    }

    @Override
    public String toString() {
        return "Flight{" +
                "airline=" + airline +
                ", flight='" + flight + '\'' +
                ", plane='" + plane + '\'' +
                ", departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", Towers=" + Towers +
                '}';
    }
}
