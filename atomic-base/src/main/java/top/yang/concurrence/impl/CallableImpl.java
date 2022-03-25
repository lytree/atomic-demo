package top.yang.concurrence.impl;

import java.util.concurrent.Callable;

public class CallableImpl implements Callable {

    private Integer i;

    public CallableImpl(Integer i) {
        this.i = i;
    }

    @Override
    public Object call() throws Exception {
        System.out.println("call " + i + "执行");
        Thread.sleep(11000);
        return true;
    }
}
