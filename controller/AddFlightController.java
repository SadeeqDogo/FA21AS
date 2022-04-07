package controller;
import models.*;
import simulator.*;
import edu.usca.acsc492l.flightplanner.utilities.TimeSpinner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddFlightController implements Initializable  {

    @FXML
    private TableColumn<Destination,String> status;
    @FXML
    private TableView<FlightDestination> tableViewFlightStatus;

    @FXML
    private TableColumn<Destination,String> destination;
    @FXML
    private TableView<?> flightSatus;
    @FXML
    private TableColumn<Flight, Date> time;
    @FXML
    private TableColumn<Flight, Date> date;
    @FXML
    private TableView<Flight> tableViewFlights;
    @FXML
    private TableColumn<Flight, String> TableColumnDeparture;
    @FXML
    private TableColumn<Flight, String> TableColumnDestination;
    @FXML
    private TableColumn<Flight, String> TableColumnFlight;
    @FXML
    private TableColumn<Flight, String> TableColumnPlane;
    @FXML
    private TextField distanceLable;
    @FXML
    private TextField numbertextField;
    @FXML
    private Button exitButton;
    @FXML
    private ComboBox<String> flightplane1;
    @FXML
    private ListView<String> flightplaneListView;
    @FXML
    private ComboBox<String> planeCombobox;
    @FXML
    private ComboBox<String> airlineCombobox;
    @FXML
    private ComboBox<String> departureCombobox;
    @FXML
    private ComboBox<String> destinationCombobox;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<String> flightplane10;
    @FXML
    private ComboBox<String> flightplane11;
    @FXML
    private ComboBox<String> flightplane12;
    @FXML
    private ComboBox<String> flightplane13;
    @FXML
    private ComboBox<String> flightplane14;
    @FXML
    private ComboBox<String> flightplane15;
    @FXML
    private ComboBox<String> flightplane16;
    @FXML
    private ComboBox<String> flightplane17;
    @FXML
    private ComboBox<String> flightplane18;
    @FXML
    private ComboBox<String> flightplane19;
    @FXML
    private ComboBox<String> flightplane2;
    @FXML
    private ComboBox<String> flightplane20;
    @FXML
    private ComboBox<String> flightplane3;
    @FXML
    private ComboBox<String> flightplane4;
    @FXML
    private ComboBox<String> flightplane5;
    @FXML
    private ComboBox<String> flightplane6;
    @FXML
    private ComboBox<String> flightplane7;
    @FXML
    private ComboBox<String> flightplane8;

    @FXML
    private ComboBox<String> flightplane9;
    @FXML
    private TextField timeLabel;
    @FXML
    private TextField fuelLabel;
    @FXML
    private TextArea emissionLabel;

    ObservableList<Flight> flights = FXCollections.observableArrayList();

    protected Airline airline;

    /** Holds the {@link Airport} at which the user ends his flight */
    protected Airport endDestination;

    /** declare FlightPlan instance, calculates the flight plan */
    protected FlightPlan flightPlan;

    /** Declare database model used for handling database storage */
    protected DatabaseModel database;
    final double Emission_Const = 0.25;
    Map<String,ObservableList<FlightDestination>> mapList = new HashMap<String, ObservableList<FlightDestination>>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //instantiate the FlightPlan class with database data
        flightPlan = new FlightPlan();

        //instantiate DatabaseModel for database info
        try {
            database = new DatabaseModel(flightPlan);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //read dbFile to grab information from it
        try {
            database.readDatabase();
            bindAirplansCombobox();
            bindDepartureAirportCombobox();
            bindDestinationAirportCombobox();
            bindAirlineCombobox();
            bindFlightPlanComboboxes ();
            bindFlightsToTable();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (FlightPlanException e) {
            e.printStackTrace();
        }

        addButton.setOnAction(event -> {
            try {
                addFlight();
            } catch (AirportException e) {
                e.printStackTrace();
            }
        });


        //initialize the queue with flight plan destinations
        ArrayList<Stoppable> threads = new ArrayList<Stoppable>();
        Flight[] data = database.getFlights().toArray(new Flight[0]);
        BlockingQueue<ObservableList<FlightDestination>> queue = new LinkedBlockingQueue<ObservableList<FlightDestination>>();


        for (Flight flight : data) {
            mapList.put(flight.getFlight(), FXCollections.observableArrayList());

          List<String> towers =   flight.getTowers();


          towers.forEach(e -> {
              FlightDestination destination = new FlightDestination(e,"");
              mapList.get(flight.getFlight()).add(destination);
              queue.add(mapList.get(flight.getFlight()));

          });
          threads.add(new Producer(queue));

        }

        //each tower will be a consumer
        Consumer consumer = new Consumer(queue);
        threads.add(consumer);

        Simulation simulation = new Simulation();

        try {
            simulation.startThreads(threads);

            //simulation.stopThreads(threads);

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }


        tableViewFlights.setOnMouseClicked(e -> {
            getFlightPlanOb();
           double distance = getDistanceOfSelectedFlight();
            distanceLable.setText(Double.toString(distance));

        });

        cancelButton.setOnAction(e->{
            cancel();
        });


        exitButton.setOnAction(e->{
            generateReportOnExit();
            CloseApplicaction();

        });

    }

    public void cancel() {
        numbertextField.setText("");
    }
    public void CloseApplicaction() {
        System.exit(0);


    }
    public void bindAirplansCombobox(){
        //create an array of airports that match user input
        Airplane[] airplanes = database.getAirplanes().toArray(new Airplane[0]);

        //displays all airports in the database
        for (Airplane airplane : airplanes) {
            planeCombobox.getItems().add(airplane.getModel());
        }
    }
    public void bindDepartureAirportCombobox(){
        //create an array of airports that match user input
        Airport[] airplanes = database.getAirports().toArray(new Airport[0]);

        //displays all airports in the database
        for (Airport airport : airplanes) {
            departureCombobox.getItems().add(airport.getICAOid());
        }
    }
    public void bindDestinationAirportCombobox(){
        //create an array of airports that match user input
        Airport[] airplanes = database.getAirports().toArray(new Airport[0]);

        //displays all airports in the database
        for (Airport airport : airplanes) {
            destinationCombobox.getItems().add(airport.getICAOid());
        }
    }
    public void bindAirlineCombobox(){
        //create an array of airports that match user input
        Airline[] airlines = database.getAirlines().toArray(new Airline[0]);

        //displays all airports in the database
        for (Airline airline : airlines) {
            airlineCombobox.getItems().add(airline.getName());
        }
    }
    public ObservableList<Flight> getFlights(){
                Date d = new Date(2002,1,8);
                  Flight[] data = database.getFlights().toArray(new Flight[0]);

                    for (Flight flight : data) {
                        flights.add(flight);

                    }


        return flights;
    }
    public void bindFlightsToTable () {

        Flight[] flights = database.getFlights().toArray(new Flight[0]);

        TableColumnDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
        TableColumnDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        TableColumnFlight.setCellValueFactory(new PropertyValueFactory<>("flight"));
        TableColumnPlane.setCellValueFactory(new PropertyValueFactory<>("plane"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableViewFlights.setItems(getFlights());

        tableViewFlights.getColumns().addAll();

    }
    public void addFlight() throws AirportException {
        Date d = new Date(2002,1,8);


        Airline[] airlines = database.getAirlines().toArray(new Airline[0]);
        Airline flightAirline = null;
        for (Airline airline : airlines) {

            if (airline.getName() == planeCombobox.getSelectionModel().getSelectedItem()) {
                flightAirline = airline;
            }
        }

        //Construct Airport from Selection
        Airport[] airports = database.getAirports().toArray(new Airport[0]);
        Airport departureAirport = null;
        Airport destinationAirport = null;
        for (Airport airport : airports) {

            if (airport.getICAOid()== departureCombobox.getSelectionModel().getSelectedItem()) {
                departureAirport = airport;
            }
            if (airport.getICAOid()== destinationCombobox.getSelectionModel().getSelectedItem()) {
                destinationAirport = airport;
            }

        }

        ArrayList<String> towers = new ArrayList<>();
        towers = getAdditionalDestination();


        //construct plane object
        Airplane airplane=null;
        Airplane[] airplanes = database.getAirplanes().toArray(new Airplane[0]);
        for (int i = 0; i < airplanes.length; i++) {
            if(airplanes[i].getModel() ==  planeCombobox.getSelectionModel().getSelectedItem()){
                airplane = airplanes[i];
                break;
            }
        }

            planeCombobox.getSelectionModel().getSelectedItem();

        Flight flight = new Flight("see","ee","ee","ee",d,d,towers,flightAirline,departureAirport,destinationAirport);
        flight.setFlight(numbertextField.getText());
        flight.setDepartureAirport(departureCombobox.getSelectionModel().getSelectedItem());
        flight.setDestination(destinationCombobox.getSelectionModel().getSelectedItem());
        flight.setPlane(planeCombobox.getSelectionModel().getSelectedItem());
        flight.setDate(d);
        flight.setTime(d);
        flight.setDepartureAirport(departureAirport);
        flight.setDepartureAirport(destinationAirport);
        flight.setAirline(flightAirline);
        flight.setAirplane(airplane);

        System.out.println(airplane);

        flights.add(flight);

        try {
            FileWriter writer = new FileWriter("resources/flights.txt",true);
            writer.write("\n"  + flight.getFlight() +";"
                    +flight.getPlane()+
                    ";"+flight.getDeparture()+";"+flight.getDestination()+
                    ";"+flight.getDeparture()+";"+
                    flight.getDate()+";"+
                    flight.getTime()  + getAdditionalDestinationStr()

                   );
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(AddFlightController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void bindFlightPlanComboboxes (){

        //create an array of airports that match user input
        Airport[] airplanes = database.getAirports().toArray(new Airport[0]);

        //displays all airports in the database
        for (Airport airport : airplanes) {

            flightplane1.getItems().add (airport.getICAOid());
            flightplane2.getItems().add (airport.getICAOid());
            flightplane3.getItems().add (airport.getICAOid());
            flightplane4.getItems().add (airport.getICAOid());
            flightplane5.getItems().add (airport.getICAOid());
            flightplane6.getItems().add (airport.getICAOid());
            flightplane7.getItems().add (airport.getICAOid());
            flightplane8.getItems().add (airport.getICAOid());
            flightplane10.getItems().add (airport.getICAOid());
            flightplane11.getItems().add (airport.getICAOid());
            flightplane12.getItems().add (airport.getICAOid());
            flightplane13.getItems().add (airport.getICAOid());
            flightplane14.getItems().add (airport.getICAOid());
            flightplane15.getItems().add (airport.getICAOid());
            flightplane16.getItems().add (airport.getICAOid());
            flightplane17.getItems().add (airport.getICAOid());
            flightplane18.getItems().add (airport.getICAOid());
            flightplane19.getItems().add (airport.getICAOid());
            flightplane20.getItems().add (airport.getICAOid());

           flightplane9.getItems().add (airport.getICAOid());
        }

    }
    public double CalculateDistance(double  latitude1 , double longitude1,double latitude2,double longitude2){


        Integer earth_radius = 6371; //km

        double radian_latitude1 = latitude1 * (Math.PI/180);

        double radian_longitude1 = longitude1 * (Math.PI/180);

        double radian_latitude2 = latitude2 * (Math.PI/180);

        double radian_longitude2 = longitude2 * (Math.PI/180);
        double delta_latitude = radian_latitude2 - radian_latitude1;
        double delta_longitude = radian_longitude2 - radian_longitude1;
        double trigo = Math.sin((delta_latitude /2)) + Math.cos(latitude1) * Math.cos(latitude2) * Math.sin(delta_longitude/2);
        double trigoSqrt = Math.sqrt(Math.abs(trigo));
        double sinTrigoSqrt =  Math.asin(trigoSqrt);
        double multipliedEarth = 2 * earth_radius;
        double distance =sinTrigoSqrt *  multipliedEarth;

        return Math.floor(distance);
    }
    public void getFlightPlanOb() {
        TablePosition tablePosition = tableViewFlights.getSelectionModel().getSelectedCells().get(0);
        int row = tablePosition.getRow();
        Flight item = tableViewFlights.getItems().get(row);
        TableColumn tableColumn = tablePosition.getTableColumn();
        String data = (String)tableColumn.getCellObservableValue(item).getValue();

        destination.setCellValueFactory(new PropertyValueFactory<>("code"));
        status.setCellValueFactory(new PropertyValueFactory<>("state"));
        tableViewFlightStatus.setItems(mapList.get(data));

        tableViewFlights.getColumns().addAll();

        System.out.println("From get flight plan " + mapList.get(data));


    }
    public ArrayList<String> getAdditionalDestination() {

        ArrayList<String> destions = new ArrayList<>();
         if(flightplane1.getSelectionModel().getSelectedItem()!= null){
             destions.add(flightplane1.getSelectionModel().getSelectedItem());
        }
        if(flightplane2.getSelectionModel().getSelectedItem() !=null){
            destions.add(flightplane2.getSelectionModel().getSelectedItem());
        }
        if(flightplane3.getSelectionModel().getSelectedItem()!=null){
            destions.add(flightplane3.getSelectionModel().getSelectedItem());
        }
        if(flightplane4.getSelectionModel().getSelectedItem() != null){
            destions.add(flightplane4.getSelectionModel().getSelectedItem());
        }
        if(flightplane5.getSelectionModel().getSelectedItem()!= null){
            destions.add(flightplane5.getSelectionModel().getSelectedItem());
        }
        if(flightplane6.getSelectionModel().getSelectedItem() !=null){
            destions.add(flightplane6.getSelectionModel().getSelectedItem());
        }
        if(flightplane7.getSelectionModel().getSelectedItem() != null){
            destions.add(flightplane7.getSelectionModel().getSelectedItem());
        }
        if(flightplane8.getSelectionModel().getSelectedItem() != null){
            destions.add(flightplane8.getSelectionModel().getSelectedItem());
        }
        if(flightplane8.getSelectionModel().getSelectedItem()!= null){
            destions.add(flightplane8.getSelectionModel().getSelectedItem());
        }
        if(flightplane10.getSelectionModel().getSelectedItem() !=null){
            destions.add(flightplane10.getSelectionModel().getSelectedItem());
        }
        if(flightplane11.getSelectionModel().getSelectedItem() !=null){
            destions.add(flightplane11.getSelectionModel().getSelectedItem());
        }
        if(flightplane12.getSelectionModel().getSelectedItem() != null){
            destions.add(flightplane12.getSelectionModel().getSelectedItem());
        }
        if(flightplane13.getSelectionModel().getSelectedItem() != null){
            destions.add(flightplane13.getSelectionModel().getSelectedItem());
        }
        if(flightplane14.getSelectionModel().getSelectedItem() !=null){
            destions.add(flightplane14.getSelectionModel().getSelectedItem());
        }
        if(flightplane15.getSelectionModel().getSelectedItem() != null){
            destions.add(flightplane15.getSelectionModel().getSelectedItem());
        }
        if(flightplane16.getSelectionModel().getSelectedItem()!=null){
            destions.add(flightplane16.getSelectionModel().getSelectedItem());
        }
        if(flightplane17.getSelectionModel().getSelectedItem() != null){
            destions.add(flightplane17.getSelectionModel().getSelectedItem());
        }
        if(flightplane18.getSelectionModel().getSelectedItem() != null){
            destions.add(flightplane18.getSelectionModel().getSelectedItem());
        }
        if(flightplane19.getSelectionModel().getSelectedItem() !=null){
            destions.add(flightplane19.getSelectionModel().getSelectedItem());
        }
        if(flightplane20.getSelectionModel().getSelectedItem() !=null){
            destions.add(flightplane20.getSelectionModel().getSelectedItem());
        }

        return destions;
    }
    public String getAdditionalDestinationStr() {

        String destions = "";
        if(flightplane1.getSelectionModel().getSelectedItem()!= null){
            destions +=";"+flightplane1.getSelectionModel().getSelectedItem();
        }
        if(flightplane2.getSelectionModel().getSelectedItem() !=null){
            destions+=";"+flightplane2.getSelectionModel().getSelectedItem();
        }
        if(flightplane3.getSelectionModel().getSelectedItem()!=null){
            destions+=";"+flightplane3.getSelectionModel().getSelectedItem();
        }
        if(flightplane4.getSelectionModel().getSelectedItem() != null){
            destions+=";"+flightplane4.getSelectionModel().getSelectedItem();
        }
        if(flightplane5.getSelectionModel().getSelectedItem()!= null){
            destions+=";"+flightplane5.getSelectionModel().getSelectedItem();
        }
        if(flightplane6.getSelectionModel().getSelectedItem() !=null){
            destions+=";"+flightplane6.getSelectionModel().getSelectedItem();
        }
        if(flightplane7.getSelectionModel().getSelectedItem() != null){
            destions+=";"+flightplane7.getSelectionModel().getSelectedItem();
        }
        if(flightplane8.getSelectionModel().getSelectedItem() != null){
            destions+=";"+flightplane8.getSelectionModel().getSelectedItem();
        }
        if(flightplane8.getSelectionModel().getSelectedItem()!= null){
            destions+=";"+flightplane8.getSelectionModel().getSelectedItem();
        }
        if(flightplane10.getSelectionModel().getSelectedItem() !=null){
            destions+=";"+flightplane10.getSelectionModel().getSelectedItem();
        }
        if(flightplane11.getSelectionModel().getSelectedItem() !=null){
            destions+=";"+flightplane11.getSelectionModel().getSelectedItem();
        }
        if(flightplane12.getSelectionModel().getSelectedItem() != null){
            destions+=";"+flightplane12.getSelectionModel().getSelectedItem();
        }
        if(flightplane13.getSelectionModel().getSelectedItem() != null){
            destions+=";"+flightplane13.getSelectionModel().getSelectedItem();
        }
        if(flightplane14.getSelectionModel().getSelectedItem() !=null){
            destions+=";"+flightplane14.getSelectionModel().getSelectedItem();
        }
        if(flightplane15.getSelectionModel().getSelectedItem() != null){
            destions+=";"+flightplane15.getSelectionModel().getSelectedItem();
        }
        if(flightplane16.getSelectionModel().getSelectedItem()!=null){
            destions+=";"+flightplane16.getSelectionModel().getSelectedItem();
        }
        if(flightplane17.getSelectionModel().getSelectedItem() != null){
            destions+=";"+flightplane17.getSelectionModel().getSelectedItem();
        }
        if(flightplane18.getSelectionModel().getSelectedItem() != null){
            destions+=";"+flightplane18.getSelectionModel().getSelectedItem();
        }
        if(flightplane19.getSelectionModel().getSelectedItem() !=null){
            destions+=";"+flightplane19.getSelectionModel().getSelectedItem();
        }
        if(flightplane20.getSelectionModel().getSelectedItem() !=null){
            destions+=";"+flightplane20.getSelectionModel().getSelectedItem();
        }

        return destions;
    }
    public double computeTime(double speed,double distance ){
        double time  =  distance / speed;

        return time;
    }
    public double computeCOEmmission(double fuelLiterConsumption){
        return Emission_Const * fuelLiterConsumption;
    }
    public double computeFuelConsumption(double planeConsumption,double distance){
        return planeConsumption * distance / 1000;
    }
    public double getDistanceOfSelectedFlight(){

        TablePosition tablePosition = tableViewFlights.getSelectionModel().getSelectedCells().get(0);
        int row = tablePosition.getRow();
        Flight item = tableViewFlights.getItems().get(row);
        TableColumn tableColumn = tablePosition.getTableColumn();

        String data = (String)tableColumn.getCellObservableValue(item).getValue();
        Airport[] airports = database.getAirports().toArray(new Airport[0]);
        String searchIdDeparture = String.copyValueOf(item.getDeparture().toCharArray());
        String searchIdDestination = String.copyValueOf(item.getDestination().toCharArray());

        String[] AirportIds = new String[airports.length];
        Airport destination=null;
        Airport departure=null;

        for (int i = 0; i < airports.length; i++) {
            AirportIds[i] =String.copyValueOf( airports[i].getICAOid().toCharArray());
            if( AirportIds[i].equals(searchIdDeparture)) {
                 departure  = airports[i];
                System.out.println("Get the departure esd "+ departure + "search ID" + searchIdDeparture);
                break;
            }
        }

        for (int i = 0; i < airports.length; i++) {
            AirportIds[i] =String.copyValueOf( airports[i].getICAOid().toCharArray());
            if( AirportIds[i].equals(searchIdDestination)) {
                 destination  = airports[i];
                System.out.println("Get the departure esd "+ destination + "search ID" + searchIdDeparture);
                break;
            }
        }


        double distance = CalculateDistance(
                departure.getCoordinate().getLatitude(),
                departure.getCoordinate().getLongitude(),
                destination.getCoordinate().getLatitude(),
                destination.getCoordinate().getLongitude()
        );

        /*
         Update flight landing Coordinate
         */

        //do co2
        emissionLabel.setText("");
        emissionLabel.setText(Double.toString(computeCOEmmission(item.getAirplane().getLitersPerHour())));

        //do fuel
        fuelLabel.setText("");
        fuelLabel.setText(Double.toString(computeFuelConsumption(item.getAirplane().getLitersPerHour(),distance)));

        //do time
        timeLabel.setText("");
        timeLabel.setText(Double.toString(computeTime(item.getAirplane().getCruiseSpeed(),distance)));

        return distance;

    }

    public void generateReportOnExit(){

      Airline[] airlines = database.getAirlines().toArray(new Airline[0]);
      Flight[] flights =   database.getFlights().toArray(new Flight[0]);

      Map<String,Integer> maps = new HashMap<>();

        for (int i = 0; i < airlines.length; i++) {

            for (int j = 0; j < flights.length; j++) {
                if (flights[j].getAirline().getCode() == airlines[i].getCode()){
                    //if you find an airline in the flights keep count and compute it distance travelled
                    //create a keyvaluepair to keep track of the airlines

                    if(maps.get(airlines[i].getCode())== null){
                        maps.put(airlines[i].getCode(),1);
                    }else{
                      var map =  maps.get(airlines[i].getCode());
                    }
                }
            }
        }

        System.out.println("Map Values "+maps.values());

    }



}
