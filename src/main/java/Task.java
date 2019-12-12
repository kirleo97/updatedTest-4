import java.util.concurrent.Phaser;

public class Task {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(10);
        for (int i = 0; i < 10; i++)
            new Consumer(String.valueOf(i), phaser);
    }
}