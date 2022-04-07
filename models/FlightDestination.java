package models;

public class FlightDestination {
    private String code;
    private String state;

    public FlightDestination(String code, String state) {
        this.code = code;
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "FlightDestination{" +
                "code='" + code + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
