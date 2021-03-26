package com.st.dianping.threads;

import java.util.concurrent.Callable;

/**
 * @author ShaoTian
 * @date 2020/12/4 17:36
 */
public class MyCallable implements Callable<Integer> {

    private Integer from;

    private Integer to;

    public MyCallable() {
    }

    public MyCallable(Integer from, Integer to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = from; i < to; i++) {
            sum += i;
        }

        return sum;
    }
}
