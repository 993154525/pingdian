package com.st.dianping.threads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author ShaoTian
 * @date 2020/12/4 17:39
 */
public class CallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable(0, 100000));
        FutureTask<Integer> futureTask2 = new FutureTask<>(new MyCallable(100000, 200000));
        FutureTask<Integer> futureTask3 = new FutureTask<>(new MyCallable(200000, 300000));
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.submit(futureTask);
        executorService.submit(futureTask2);
        executorService.submit(futureTask3);

        System.out.println(futureTask.get() + futureTask2.get() + futureTask3.get());

        executorService.shutdown();
    }

}
