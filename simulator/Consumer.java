package simulator;


import javafx.collections.ObservableList;
import models.FlightDestination;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer extends Stoppable {
    public Consumer(BlockingQueue<ObservableList<FlightDestination>> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        try {

            while (!stop) {
               // String s = queue.poll(3000, TimeUnit.MILLISECONDS);
               // if (s != null) {
                    //System.out.printf("\nThreadId: %d, passed: %s", Thread.currentThread().getId(), s);
                    //totalMessagesReceived.incrementAndGet();
                    //queue.remove();
              //  }
            }
        } catch (Exception e) {
            System.out.printf("Exception in consumer: %s\n", e.getMessage());
        }
    }

    public static int getTotalMessagesReceived() {
        return totalMessagesReceived.get();
    }

    private BlockingQueue<ObservableList<FlightDestination>> queue;
    private static AtomicInteger totalMessagesReceived = new AtomicInteger(0);

}
