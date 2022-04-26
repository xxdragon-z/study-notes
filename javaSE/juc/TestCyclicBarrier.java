package juc;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by XXDragon on 2022/4/22 10:59
 */
public class TestCyclicBarrier {
    public static void main(String[] args) {
        ExecutorService executor = null;
        AtomicInteger integer = new AtomicInteger(0);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
            integer.incrementAndGet();
            System.out.println("入库一次");
        });
        executor = new ThreadPoolExecutor(
                100, 10000, 10L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1));
        for (int i = 0; i < 20; i++) {
            executor.execute(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
