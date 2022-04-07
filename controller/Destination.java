package edu.usca.acsc492l.flightplanner.controller;

public class Destination {
    private String flightdes;
    private String status;

    public Destination(String destination, String status) {
        this.flightdes = destination;
        this.status = status;
    }

    public String getFlightdes() {
        return flightdes;
    }

    public void setFlightdes(String flightdes) {
        this.flightdes = flightdes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "destination='" + flightdes + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
