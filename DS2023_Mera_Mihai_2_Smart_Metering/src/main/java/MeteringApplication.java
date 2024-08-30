import simulator.Simulator;

public class MeteringApplication {

    public static void main(String[] args) {
        Simulator simulator = new Simulator(1000);
        Thread thread = new Thread(simulator);
        thread.start();
    }
}
