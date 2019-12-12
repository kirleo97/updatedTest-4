import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Store {
    private static AtomicInteger goods = new AtomicInteger(1000);

    public static int getGoods() { return goods.get(); }

    public synchronized static void tryToBuy(Consumer consumer, int quantity) {
        if (areAnyGoodsLeft()) {
            quantity = howManyGoodsIsPossibleToBuy(quantity);
            sellGoods(quantity);
            DataBase.changeConsumerData(consumer, quantity);
            printDeal(quantity);
        }
    }

    public static boolean areAnyGoodsLeft() {
        if (getGoods() > 0)
            return true;
        System.out.println("Товары закончились\n");
        return false;
    }

    public static int howManyGoodsIsPossibleToBuy(int quantity) {
        return quantity > getGoods() ? getGoods() : quantity;
    }

    public static void sellGoods(int quantity) {
        Store.goods.addAndGet(-quantity);
    }

    public static void printDeal(int quantity) {
        System.out.println("Потребитель (поток) " + Thread.currentThread().getName() + " купил " + quantity + " товаров");
        System.out.println("Сейчас товаров на складе " + goods + "\n");
    }


    public static class DataBase {
        private static Map<Consumer, int[]> map = new HashMap<>();

        public static Map<Consumer, int[]> getMap() { return map; }

        public static void addNewConsumer(Consumer consumer) {
            map.put(consumer, new int[2]);
        }

        public static void changeConsumerData(Consumer consumer, int quantity) {
            map.get(consumer)[0]++;
            map.get(consumer)[1] += quantity;
        }

        public static void getResults(Consumer consumer) {
            System.out.println("ВСЕГО потребитель " + consumer + " совершил " + map.get(consumer)[0] + " покупок и купил " + map.get(consumer)[1] + " товаров");
        }
    }
}