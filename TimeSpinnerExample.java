package edu.usca.acsc492l.flightplanner;

import edu.usca.acsc492l.flightplanner.utilities.TimeSpinner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

public class TimeSpinnerExample extends Application {
    @Override
    public void start(Stage primaryStage) {

        TimeSpinner spinner = new TimeSpinner();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        spinner.valueProperty().addListener((obs, oldTime, newTime) ->
                System.out.println(formatter.format(newTime)));

        StackPane root = new StackPane(spinner);
        Scene scene = new Scene(root, 350, 120);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
