import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

public class CountersTest {
    @Test
    public void test() {
        int x = 0;
        int y = 1;
        System.out.println(x + y);
    }

    @Test
    public void text1() throws InterruptedException {
        L l = new L();  //new 一个对象
        System.out.println(ClassLayout.parseInstance(l).toPrintable());//输出 l对象 的布局
    }

    //对象类
    class L {
        private boolean myboolean = true;

        public synchronized void text() {
            System.out.println("---------------");
        }
    }

}
