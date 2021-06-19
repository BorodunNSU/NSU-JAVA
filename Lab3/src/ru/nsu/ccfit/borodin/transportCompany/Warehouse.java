import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Warehouse {
    private final BlockingQueue<Item> items;

    public Warehouse(int capacity) {
        items = new ArrayBlockingQueue<>(capacity);
    }

    public void addItem(Item item) throws InterruptedException {
        items.put(item);
        Log.logInfo("Added '" + item.getName() + "' to warehouse");
    }

    public Item getItem() throws InterruptedException {
        Item item = items.take();
        Log.logInfo("Got '" + item.getName() + "' from warehouse");
        return item;
    }
}
