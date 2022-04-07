package models;

/**
 * A model class that represents a NAV Beacon object
 *
 * @author Sodiq Ali Dogo

 */
public class ControlTower   {

	/** Holds the Coordinate of this Tower */
	protected Coordinate coordinate;

	protected Airport	airport;

	public String getPassed() {
		return passed;
	}

	public void setPassed(String passed) {
		this.passed = passed;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	private String passed;
	private String heading;

	public ControlTower(Coordinate coordinate, Airport airport) {
		this.coordinate = coordinate;
		this.airport = airport;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public Airport getAirport() {
		return airport;
	}

	public void setAirport(Airport airport) {
		this.airport = airport;
	}


	@Override
	public String toString() {
		return super.toString() + 
		       String.format("\n => %-"  + "s %s"
		                     );
	}
}