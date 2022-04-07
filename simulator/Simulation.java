package simulator;

import java.util.List;

public class Simulation {
    public static void startThreads(List<Stoppable> stopables) {
        for(Stoppable stopable : stopables) {
            stopable.start();
        }
    }

    public  static void stopThreads(List<Stoppable> stopables) throws InterruptedException {
        for(Stoppable stopable : stopables) {
            stopable.signalStop();
        }

        for(Stoppable stopable : stopables) {
            stopable.join();
        }
    }
}
