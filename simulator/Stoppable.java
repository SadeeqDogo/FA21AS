package simulator;

public abstract class Stoppable extends Thread {
    public void signalStop() {
        stop = true;
    }

    protected volatile boolean stop = false;
}
