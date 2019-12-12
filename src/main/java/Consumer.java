import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer extends Thread {
    private String consumerName;
    private Phaser phaser;
    private AtomicInteger goods = new AtomicInteger();

    public Consumer(String name, Phaser phaser) {
        consumerName = name;
        this.phaser = phaser;
        start();
    }

    public void buyGoods(int quantity) {
        goods.addAndGet(quantity);
    }

    @Override
    public void run() {
        while (Store.getGoods() != 0) {
            int quantity = 1 + (int) (Math.random() * 9);
            buyGoods(Store.tryToBuy(quantity));
            phaser.arriveAndAwaitAdvance();
        }
        int numberOfPurchases = phaser.arriveAndDeregister();
        System.out.println("ВСЕГО потребитель " + this + " совершил " + numberOfPurchases + " покупок и купил " + goods + " товаров");
    }

    @Override
    public String toString() {
        return consumerName;
    }
}