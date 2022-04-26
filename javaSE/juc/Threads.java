package juc;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by XXDragon on 2022/4/13 14:56
 */
public class Threads {
    private ThreadGroup threadGroup = new ThreadGroup("test");

    public Thread getThread() {
        return new Thread(threadGroup, new Runnable() {
            @Override
            public void run() {
                // runnable do something
            }
        }, "children");
    }

    public Future<Integer> FutureTask() {
        return new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                // do something
                return new MyForkJoinTask<>(new FunctionC(), 100).getRawResult();
            }
        });
    }

    public static ForkJoinTask<Integer> getForkJoinTask() {
        AtomicInteger ato = new AtomicInteger(100);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.submit(new MyForkJoinTask<>(new FunctionC(), ato.get()));
    }

    interface IFunction<T, R> {
        R apply(T t) throws Exception;
    }

    static class FunctionC implements IFunction<Integer, Integer> {
        public Integer apply(Integer i) throws ExecutionException, InterruptedException {
            if (i > 10) {
                int res = new MyForkJoinTask<>(new FunctionC(), i / 2).fork().get()
                        + new MyForkJoinTask<>(new FunctionC(), i - (i / 2)).fork().get();
                return res;
            }
            int res = 0;
            for (int integer = i; integer > 0; integer--) {
                res += integer;
            }
            return res;
        }
    }

    static class MyForkJoinTask<T, R> extends ForkJoinTask<R> {
        private final IFunction<T, R> fun;
        private final T value;
        private R res;

        public MyForkJoinTask(IFunction<T, R> fun, T value) {
            if (null == fun || null == value) {
                throw new NullPointerException("fun or value can not be null");
            }
            this.fun = fun;
            this.value = value;
        }

        @Override
        public R getRawResult() {
            return res;
        }

        @Override
        protected void setRawResult(R value) {
            this.res = value;
        }

        @Override
        protected boolean exec() {
            try {
                setRawResult(fun.apply(value));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return getRawResult() != null;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        System.out.println(getForkJoinTask().get());
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 100, 4,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            executor.execute(new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }
    }
}
