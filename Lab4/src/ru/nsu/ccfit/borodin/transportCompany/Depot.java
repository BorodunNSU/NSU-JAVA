package ru.nsu.ccfit.borodin.transportCompany;

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
        this.system = system;
        executor = Executors.newScheduledThreadPool(config.getTrainsNum());
        trains = new ConcurrentLinkedQueue<>();
    }

    public void addNewOrder(String name) throws ConfigException {
        Log.info("Adding order to train '" + name + "' to depot");
        Log.info("Creating train '" + name + "'");
        int createTime = config.getTrainCreateTime(name);
        int speed = config.getTrainSpeed(name);
        int amortizationTime = config.getTrainAmortizationTime(name);

        Depot depot = this;
        executor.schedule(() -> {
            Train train = new Train(name, speed, amortizationTime, config, system, depot);
            trains.add(train);
            Log.info("Train " + name + " was created");
            train.start();
        }, createTime, TimeUnit.SECONDS);
    }

    public void stop() throws InterruptedException {
        Log.info("Shutting down depot");

        executor.shutdownNow();
        if (executor.awaitTermination(5, TimeUnit.SECONDS)) {
            trains.forEach(Thread::interrupt);
        }
    }
}





