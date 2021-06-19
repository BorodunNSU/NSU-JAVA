import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Depot {
    private final Config config;
    private final ScheduledExecutorService executor;
    private final RailwaySystem system;
    private final Queue<Train> trains;

    public Depot(Config config, RailwaySystem system) {
        this.config = config;
        executor = Executors.newScheduledThreadPool(config.getTrainsNum());
        this.system = system;
        trains = new ConcurrentLinkedQueue<>();
    }

    public void addNewOrder(String name) throws ConfigException {
        Log.logInfo("Add order to train '" + name + "' to depot");
        Log.logInfo("Creating train '" + name + "'");
        int createTime = config.getTrainCreateTime(name);
        int speed = config.getTrainSpeed(name);
        int amortizationTime = config.getTrainAmortizationTime(name);
        Depot depot = this;
        executor.schedule(() -> {
            Train train = new Train(name, speed, amortizationTime, config, system, depot);
            trains.add(train);
            Log.logInfo("Train " + name + " was created");
            train.start();
        }, createTime, TimeUnit.SECONDS);
    }

    public void stop() throws InterruptedException {
        executor.shutdownNow();
        if (executor.awaitTermination(5, TimeUnit.SECONDS)) {
            trains.forEach(Thread::interrupt);
        }
        Log.logInfo("Depot has been stopped");
    }
}





