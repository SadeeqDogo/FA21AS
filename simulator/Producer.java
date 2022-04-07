package simulator;

import models.FlightDestination;
import javafx.collections.ObservableList;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Producer extends Stoppable {
    public Producer(BlockingQueue<ObservableList<FlightDestination>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int counter = 0;
        try {
            while (!stop) {
                if (queue.isEmpty()) {


                    //put strings in the queue
                   // queue.put(strings[counter++]);
                    //if (counter == strings.length) stop = true;
                   // totalMessagesSent++;
                }

                queue.forEach(e -> {
                    e.forEach(er -> {

                        er.setState("Passed");
                    });
                });

            }
        } catch (Exception e) {
            System.out.printf("Exception in producer: %s\n", e.getMessage());
        }
    }

    public int getTotalMessagesSent() {
        return totalMessagesSent;
    }

    private int totalMessagesSent = 0;
    private BlockingQueue<ObservableList<FlightDestination>> queue;
    private String strings[] = new String[]{"SDX", "JFK", "DUX"};
}
