package ru.nsu.ccfit.borodin.transportCompany;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Station {
    private final BlockingQueue<Train> trains;
    private final Map<String, Warehouse> warehouseMap;

    public Station(int trackNumbers, Map<String, Warehouse> warehouseMap) {
        this.warehouseMap = warehouseMap;
        trains = new ArrayBlockingQueue<>(trackNumbers);
    }

    public void acceptTrain(Train train) throws InterruptedException {
        trains.put(train);
    }

    public void sendTrain(Train train) {
        trains.remove(train);
    }

    public Warehouse getWarehouse(String name) {
        return warehouseMap.get(name);
    }
}
