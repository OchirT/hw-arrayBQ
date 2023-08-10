import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class Main {

    public static BlockingQueue<String> queueA = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queueB = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queueC = new ArrayBlockingQueue<>(100);


    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    queueA.put(generateText("abc", 100000));
                    queueB.put(generateText("abc", 100000));
                    queueC.put(generateText("abc", 100000));
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        Thread thread1 = new Thread(() -> {
            maxSize(queueA, 'a');
        });
        Thread thread2 = new Thread(() -> {
            maxSize(queueB, 'b');
        });
        Thread thread3 = new Thread(() -> {
            maxSize(queueC, 'c');
        });
        thread.start();
        thread1.start();
        thread2.start();
        thread3.start();

    }
    public static void maxSize(BlockingQueue<String> blockingQueue, char letter) {
        int max = 0;
        for (int i = 0; i < 10000; i++) {
            int count = 0;
            String text;
            try {
                text = blockingQueue.take();
                for (int j = 0; j < text.length(); j++) {
                    if (text.charAt(j) == letter) {
                        count++;
                    }
                }
                if (max < count) {
                    max = count;
                }
            } catch (InterruptedException e) {
                return;
            }

        }
        System.out.printf("Максимальное количество символов '%s' в строке %s\n",letter,max);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
