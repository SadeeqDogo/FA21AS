package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import edu.usca.acsc492l.flightplanner.Main;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
public class DatabaseModel {
	
	/** Holds the {@link Airplane} objects */
	protected HashSet<Airplane> airplanes;

	/** Holds the {@link Flight} objects */
	protected HashSet<Flight> flights;

	/** Holds the {@link Airport} objects */
	protected HashSet<Airline> airlines;

	/** Holds the {@link Airport} objects */
	protected HashSet<Airport> airports;
	
	/** Holds the {@link ControlTower} objects */
	protected HashSet<ControlTower> navbeacons;
	
	/** Holds the database file name  for planes*/
	protected final File databaseFilePlane;

	/** Holds the database file name  for airports*/
	protected final File databaseFileAirport;

	/** Holds the database file name for airlines */
	protected final File databaseFileAirline;

	/** Holds the database file name for airlines */
	protected final File databaseFileFlights;

	/** Holds the database file name for airlines */
	protected Scanner scannerPlane;

	/** Holds the database file name for airlines */
	protected Scanner scannerAirport;

	/** Holds the database file name for airlines */
	protected Scanner scannerAirline;

	/** Holds the database file name for airlines */
	protected Scanner scannerFlights;
	
	/** Holds the {@link FlightPlan} object to use in creating {@link Airport}s */
	protected final FlightPlan flightPlan;
	
	/** Holds the username */
	protected String username;
	
	/** Holds the user's session */
	protected Element usernameElement;
	
	/** Maintains the XML database */
	protected Document document;
	
	/** Produces DocumentBuilder objects */
	DocumentBuilderFactory docFactory;
	
	/** Parses XML documents */
	DocumentBuilder docBuilder;

	/**
	 * Holds the names of each of the {@link Airport} and {@link NAVBeacon}
	 * objects in the database
	 */
	protected Hashtable<String, Vertex> names;

	/**
	 * Holds the ICAO ID's of each of the {@link Airport} and {@link NAVBeacon}
	 * objects in the database
	 */
	protected Hashtable<String, Vertex> ICAOids;

	/**
	 * Constructs a DatabaseModel object to read and write the flight plan database
	 *
	 * @param flightPlan The {@link FlightPlan} object to use in creating {@link Airport}s
	 * @throws NullPointerException When flightPlan is null
	 */
	public DatabaseModel(FlightPlan flightPlan) throws FlightPlanException, Exception {
		if (flightPlan == null) {
			throw new NullPointerException("flightPlan may not be null");
		}
		
		this.flightPlan = flightPlan;
		
		// Holds the location of the database file
		String databasePlanesURI = "/planes.txt";
		String databaseAirportsURI = "/airports.txt";
		String databaseAirlinesURI= "/airliness.txt";
		String databaseFlightsURI  = "/flights.txt";

		// ... instantiate databaseFile with it
		databaseFilePlane = new File(Main.class.getResource(databasePlanesURI).toURI());
		databaseFileAirport = new File(Main.class.getResource( databaseAirportsURI).toURI());
		databaseFileAirline = new File(Main.class.getResource(databaseAirlinesURI).toURI());
		databaseFileFlights = new File(Main.class.getResource(databaseFlightsURI).toURI());

		// Initialize the Object sets necessary to construct the FlightPlan database
		airplanes  = new HashSet<Airplane>();
		airports   = new HashSet<Airport>();
		navbeacons = new HashSet<ControlTower>();
		airlines = new HashSet<Airline>();
		flights = new HashSet<Flight>();

		// Instantiate the Hastables that store Vertex names and ICAO ID's
		names = new Hashtable<String, Vertex>();
		ICAOids = new Hashtable<String, Vertex>();

	}
	
	/**
	 * Initializes the flight database of the {@link FlightPlan} class
	 */
	protected void initializeFlightPlanDatabase() {
		// Initialize the airplanes HashSet of flightPlan
		flightPlan.setAirplanes(airplanes);

		// Initialize the airports HashSet of flightPlan
		flightPlan.setAirports(airports);

		// Initialize the navbeacons HashSet of flightPlan
		flightPlan.setNAVBeacons(navbeacons);


	}

	/**
	 * Constructs a database from the specified XML file.
	 *
	 * @throws IOException When database does not exist
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public void readDatabase() throws IOException,
	                                  ParserConfigurationException,
	                                  SAXException, FlightPlanException {


		//Read and load Flights objects from file
		scannerFlights = new Scanner(databaseFileFlights);
		String[] tempflights = {};
		while (scannerFlights.hasNextLine()) {
			tempflights = scannerFlights.nextLine().split(";");
			// Store all the Airplane objects in the database
			if (tempflights.length > 0) {

				// Declare some variables to hold the information for each Airplane object
				String flightCode;
				String planeCode;
				String departureAirport;
				String destinationAirport;
				Date date = new Date();
				Date time = new Date();


				// Points to the current Flight object
				Flight flight;


				int towersId = 6;
				List<String> towers = new ArrayList<String>();
				// Add each Airplane object to the database
				for (int index = 0; index < tempflights.length; index ++) {

					// Get the make of this Airplane
					//System.out.print("\n"+tempflights[index]);
					flightCode = tempflights[0];
					planeCode = tempflights[1];
					departureAirport = tempflights[2];
					destinationAirport = tempflights[3];

					//determine how to get towers code logic error
					Airline airline = null;
					/*if( tempflights.length > 5 && towersId <tempflights.length){

						towers.add(tempflights[towersId]);
						towersId++;
						System.out.println(towersId);
						//get and construct airline object
						Airline[] airlinesArray = airlines.toArray(new Airline[0]);

						for (int i = 0; i <airlinesArray.length ; i++) {
							if (airlinesArray[i].getCode() == tempflights[towersId] ) {
								 airline = airlinesArray[i];
							}
						}
					}

					 */

					towers = Arrays.asList(Arrays.copyOfRange(tempflights,6,tempflights.length));

					SimpleDateFormat sdf = new SimpleDateFormat("dd:mm:yyyy");
					SimpleDateFormat sdfT = new SimpleDateFormat("dd:mm:yyyy hh:mm");
					try {
						date = sdf.parse(tempflights[4]);
						time = sdfT.parse(tempflights[5]);
					} catch (ParseException e) {
						e.printStackTrace();
					}

					//construct and airport object from the flights file
					String finalDepartureAirport = departureAirport;
					final Airport[] departure = {null};
					final Airport[] destination = new Airport[1];
					String finalDestinationAirport = destinationAirport;
					airports.forEach(e -> {
						if (e.getICAOid() == finalDepartureAirport){
							departure[0] =e;
						}
						if(e.getICAOid()== finalDestinationAirport){
							destination[0] =e;
						}
					});




					System.out.println("Flight :"+departure[0]);


					// Instantiates a new Airplane object with the collected information
					flight = new Flight(
							flightCode,
							planeCode,
							departureAirport,
							destinationAirport,
							date,
							time,
							towers,
							airline,
							departure[0],
							destination[0]

					);


					// Add the Airplane object to the database
					flights.add(flight);

					System.out.println("Flights read from db"+ flights.size());

				}

			}
		}

		//Read and load Airline objects from file

		scannerAirline = new Scanner(databaseFileAirline);
		String[] tempAirlines = {};
		while (scannerAirline.hasNextLine()) {
			tempAirlines = scannerAirline.nextLine().split(";");
			// Store all the Airplane objects in the database
			if (tempAirlines.length > 0) {

				// Declare some variables to hold the information for each Airplane object
				String airlineCode;           // Holds the make attribute of the current Airplane
				String airlineName;          // Holds the model attribute of the current Airplane


				// Points to the current Airplane object
				Airline airline;



				// Add each Airplane object to the database
				for (int index = 0; index < tempAirlines.length; index ++) {

					// Get the make of this Airplane
					airlineCode = tempAirlines[0];
					airlineName = tempAirlines[1];

					try {
						// Instantiates a new Airplane object with the collected information
						airline = new Airline(
								airlineCode,
								airlineName
						);

						// Add the Airplane object to the database
						airlines.add(airline);
					} catch (FlightPlanException exception) {
						System.err.println(exception.getMessage());
					}
				}
			}
		}

		//Read and load Airplanes objects from file
		scannerPlane = new Scanner(databaseFilePlane);
		String[] tempPlane = {};
		while (scannerPlane.hasNextLine()){

			 tempPlane = scannerPlane.nextLine().split(";");
			// Store all the Airplane objects in the database
			if (tempPlane.length > 0) {

				// Declare some variables to hold the information for each Airplane object
				String airplaneMake;           // Holds the make attribute of the current Airplane
				String airplaneModel;          // Holds the model attribute of the current Airplane
				String airplaneType;           // Holds the type attribute of the current Airplane
				String airplaneTypeVal;      // Holds the String representation of airplaneType
				String airplaneTankSize;       // Holds the tankSize attribute of the current Airplane
				String airplaneLitersPerHour;  // Holds the litersPerHour attribute of the current Airplane
				String airplaneCruiseSpeed;    // Holds the cruiseSpeed attribute of the current Airplane

				// Points to the current Airplane object
				Airplane airplane;



				// Add each Airplane object to the database
				for (int index = 0; index < tempPlane.length; index ++) {

					// Get the make of this Airplane
					airplaneMake =

							// Get the model of this Airplane
							airplaneModel =  tempPlane[0];

					// Get the type of this Airplane
					airplaneType ="";
					// Get the tank size of this Airplane
					airplaneTankSize ="90";

					// Get the ratio of liter of fuel burned per hour of this Airplane
					airplaneLitersPerHour =  tempPlane[3];

					// Get the cruise speed of this Airplane
					airplaneCruiseSpeed = tempPlane[2];

					// Get the String representation of airplaneType
					airplaneTypeVal = "";

					try {
						// Instantiates a new Airplane object with the collected information
						airplane = new Airplane(
								airplaneMake,
								airplaneModel,
								airplaneTypeVal.equals("JET") ?
										Airplane.AirplaneType.JET :
										airplaneTypeVal.equals("PROP") ?
												Airplane.AirplaneType.PROP :
												Airplane.AirplaneType.TURBO_PROP,
								airplaneTankSize,
								airplaneLitersPerHour,
								airplaneCruiseSpeed,
								flightPlan
						);

						// Add the Airplane object to the database
						airplanes.add(airplane);
					} catch (FlightPlanException exception) {
						System.err.println(exception.getMessage());
					}
				}
			}
		}



		//Read and load Airports objects from file

		scannerAirport = new Scanner(databaseFileAirport);
		String[] tempAirport = {};
		while (scannerAirport.hasNextLine()){

			tempAirport = scannerAirport.nextLine().split(";");
			// Store all the Airport objects in the database
			if (tempAirport.length > 0) {

				// Declare some variables to hold the information for each Airport object
				String airportICAOid;     // Holds the ICAOid attribute of the current Airport object
				String airportName;       // Holds the name attribute of the current Airport object
				String airportHasAVGAS;   // Holds the hasAVGAS attribute of the current Airport object
				String airportHasJA_a;    // Holds the hasJA_a attribute of the current Airport object
				String airportElevation;  // Holds the elevation attribute of the current Airport object

				// Holds the Airport object's coordinate
				Element airportCoordinateElement;  // Holds the coordinate of the current Airport object
				String airportLatitude;              // Holds the latitude attribute of the current Airport
				String airportLongitude;             // Holds the longitude attribute of the current Airport

				// Points to the current Airport Coordinate object
				Coordinate airportCoordinate;

				// Hold the attributes of each Runway object for the Airport
				NodeList airportRunwaysElements;     // Holds all the runways elements in the document
				Element airportRunwaysElement;       // Points to the first runways element in the document
				NodeList airportRunwayElements;      // Holds all the runway elements in the runways element
				Element airportRunwayElement;        // Points to each runway element
				Element airportRunwayNumberElement;  // Holds the number of the runway
				Text airportRunwayType;              // Holds the type attribute of each runway element
				String airportRunwayTypeVal;         // Holds the String representation of airportRunwayType
				Text airportRunwayLength;            // Holds the length attribute of each runway element
				Text airportRunwayNumber;            // Holds the value of the runway number

				// Points to each Airport Runway object
				Runway airportRunway;

				// Hold the attributes of each Comm object for the Airport
				NodeList airportCommsElements;  // Holds all the comms element in each airport element
				Element airportCommsElement;    // Points to the first comms element in the document
				NodeList airportCommElements;   // Holds all the comm elements in the comms element
				Element airportCommElement;     // Points to each comm element
				Text airportCommType;           // Holds the type attribute of each comm element
				String airportCommTypeVal;      // Holds the String representation of airportCommType
				Text airportCommFreq;           // Holds the freq attribute of each comm element

				// Points to each Airport Comm object
				Comm airportComm;

				// Points to the current Airport object
				Airport airport;

				// Points to the current airplane element
				Element airportElement;

				// Add each Airport object to the database
				for (int index = 0; index < tempAirport.length; index ++) {



					// Acquire the ICAO ID of this Airport
					airportICAOid =tempAirport[0];

					// Acquire the name of this Airport
					airportName =tempAirport[1];
					// Acquire whether this Airport carries AVGAS fuel
					airportHasAVGAS ="";

					// Acquire whether this Airport carries Jet-A fuel
					airportHasJA_a = "";

					// Acquire the elevation of this Airport
					airportElevation ="23" ;

					// Get the latitude of this Airport
					airportLatitude = tempAirport[2];

					// Get the longitude of this Airport
					airportLongitude =tempAirport[3];

					try {
						// Instantiate a new Coordinate object for this Airport
						airportCoordinate = Coordinate.getValidCoordinate(
								airportLatitude, airportLongitude, null, flightPlan
						);

						// Instantiate an Airport object with the obtained attributes to add to the database
						airport = new Airport(flightPlan,
								airportICAOid,
								airportName,
								new Boolean(airportHasAVGAS).booleanValue(),
								new Boolean(airportHasJA_a).booleanValue(),
								airportCoordinate,
								airportElevation);

						// Add the current Airport to the database
						airports.add(airport);
					} catch (FlightPlanException exception) {
						System.err.println(exception.getMessage());
					}
				}
			}

		}

		// Initialize the FlightPlan database with the data obtained from the XML Document
		initializeFlightPlanDatabase();
	}
	
	/**
	 * Writes the current database to an txt file
	 */
	public void writeDatabase() throws FileNotFoundException,
	                                   IOException,
	                                   ParserConfigurationException {
		

	}
	
	/**
	 * Returns the HashSet of {@link Airplane} objects parsed from the file
	 *
	 * @return The {@link #airplanes} attribute
	 */
	public HashSet<Airplane> getAirplanes() {
		return airplanes;
	}


	/**
	 * Returns the HashSet of {@link Airline} objects parsed from the file
	 *
	 * @return The {@link #airplanes} attribute
	 */
	public HashSet<Airline> getAirlines() {
		return airlines;
	}

	/**
	 * Returns the HashSet of {@link Airport} objects parsed from the file
	 *
	 * @return The {@link #airports} attribute
	 */
	public HashSet<Airport> getAirports() {
		return airports;
	}


	public HashSet<Flight> getFlights(){
		return  flights;
	}
	
	/**
	 * Returns the HashSet of {@link ControlTower} objects parsed from the file
	 *
	 * @return The {@link #navbeacons} attribute
	 */
	public HashSet<ControlTower> getNAVBeacons() {
		return navbeacons;
	}

}