
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.*;

import java.util.ArrayList;
import java.util.Scanner;


public class Main extends Application {

    /** Declare a scanner object that will grab user input */
    protected static final Scanner input = new Scanner(System.in);

    /** Points to the current {@link Airport} object */
    protected Airport airport;


    /** Holds the {@link Comm} of the current {@link Airport} object */
    protected Comm comm;


    /** Points to the current {@link Airplane} object */
    protected Airplane airplane;



    /** Points to the current {@link ControlTower} object */
    protected ControlTower navbeacon;


    /** Points to the current {@link Coordinate} object */
    protected Coordinate coordinate;

    /** Holds the {@link Airport} from which the user begins his flight */
    protected Airport startDestination;

    /** Holds the {@link Airport} at which the user ends his flight */
    protected Airport endDestination;

    protected Airline airline;

    /** declare FlightPlan instance, calculates the flight plan */
    protected FlightPlan flightPlan;

    /** Declare database model used for handling database storage */
    protected DatabaseModel database;

    /**
     * Constructs a default Main object
     */
    public Main() throws Exception {


        //instantiate the FlightPlan class with database data
        //flightPlan = new FlightPlan();

        //instantiate DatabaseModel for database info
        //database = new DatabaseModel(flightPlan);

        //read dbFile to grab information from it
        //database.readDatabase();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = FXMLLoader.load(getClass().getResource("view/AddFlight.fxml"));
        Scene scene = new Scene(grid);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main method. Instantiates the Main class and begins its execution.
     *
     * @param args Command-line arguments supplied to the application upon initiation
     */
    public static void main(String[] args)  {
        launch(args);
    }


}