import java.util.concurrent.Phaser;

public class Consumer extends Thread {
    private String consumerName;
    private Phaser phaser;

    public Consumer(String name, Phaser phaser) {
        consumerName = name;
        this.phaser = phaser;
        Store.DataBase.addNewConsumer(this);
        start();
    }

    @Override
    public void run() {
        while (Store.getGoods() != 0) {
            int quantity = 1 + (int) (Math.random() * 9);
            Store.tryToBuy(this, quantity);
            phaser.arriveAndAwaitAdvance();
        }
        phaser.arriveAndDeregister();
        Store.DataBase.getResults(this);
    }

    @Override
    public String toString() {
        return consumerName;
    }
}
