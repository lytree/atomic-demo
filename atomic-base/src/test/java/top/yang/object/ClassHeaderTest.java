package top.yang.object;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import java.sql.SQLOutput;

public class ClassHeaderTest {

    @Test
    public void unlock() {
        ClassHeader.L l = new ClassHeader.L();
        System.out.println(ClassLayout.parseInstance(l).toPrintable());
    }

    @Test
    public void lock() throws InterruptedException {
        ClassHeader.L l = new ClassHeader.L();
        synchronized (l) {
            Thread.sleep(1000);
            System.out.println(ClassLayout.parseInstance(l).toPrintable());
            Thread.sleep(1000);
        }
    }

    @Test
    public void lock1() {
        ClassHeader.L l = new ClassHeader.L();

        Runnable RUNNABLE = () -> {
            while (!Thread.interrupted()) {
                synchronized (l) {
                    String SPLITE_STR = "===========================================";
                    System.out.println(SPLITE_STR);
                    System.out.println(ClassLayout.parseInstance(l).toPrintable());
                    System.out.println(SPLITE_STR);
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        for (int i = 0; i < 3; i++) {
            new Thread(RUNNABLE).start();
        }
        while (true){

        }
    }
}
