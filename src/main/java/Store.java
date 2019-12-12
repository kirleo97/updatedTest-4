import java.util.concurrent.atomic.AtomicInteger;

public class Store {
    private static AtomicInteger goods = new AtomicInteger(1000);

    public static int getGoods() { return goods.get(); }

    public synchronized static int tryToBuy(int quantity) {
        if (areAnyGoodsLeft()) {
            quantity = howManyGoodsIsPossibleToBuy(quantity);
            sellGoods(quantity);
            printDeal(quantity);
            return quantity;
        }
        return 0;
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
}