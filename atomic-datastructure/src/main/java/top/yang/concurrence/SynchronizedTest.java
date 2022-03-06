package top.yang.concurrence;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import top.yang.concurrence.impl.CallableImpl;

public class SynchronizedTest {

    public static synchronized void test() {

    }

    public static void main(String[] args) {
        FutureTask<Object> objectFutureTask1 = new FutureTask<Object>(new CallableImpl(1));

        FutureTask<Object> objectFutureTask2 = new FutureTask<Object>(new CallableImpl(2));

        Thread thread = new Thread(objectFutureTask1);
        Thread thread1 = new Thread(objectFutureTask2);
        thread.start();
        thread1.start();
        System.out.println("------");
        try {
            System.out.println("---1---");
            Object o = objectFutureTask1.get();
            Object o1 = objectFutureTask2.get();
            System.out.println(o);
            System.out.println(o1);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
