package ru.nsu.ccfit.borodin.transportCompany;

public class Consumer extends Thread {
    private final Warehouse warehouse;
    private final int itemConsumeTime;
    private final String itemName;

    public Consumer(String itemName, Warehouse warehouse, int itemConsumeTime) {
        this.itemName = itemName;
        this.warehouse = warehouse;
        this.itemConsumeTime = itemConsumeTime;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Thread.sleep(itemConsumeTime);
                warehouse.getItem();
                Log.info("Consumed '"+ itemName +"'");
            } catch (InterruptedException e) {
                Log.info("Consumer of '"+ itemName + "' has been stopped");
                return;
            }
        }
    }
}
