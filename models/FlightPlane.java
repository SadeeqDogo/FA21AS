package models;

import javafx.collections.ObservableList;
import javafx.scene.control.TablePosition;

public class FlightPlane {
    private Flight flight;
    private ObservableList<ControlTower> towers;


    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public ObservableList<ControlTower> getTowers() {
        return towers;
    }

    public void setTowers(ObservableList<ControlTower> towers) {
        this.towers = towers;
    }


}
