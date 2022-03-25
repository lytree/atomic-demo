package top.yang.object;

public class ObjectTest {

    public static void main(String[] args) {

        try {
//            Class a = Class.forName("top.yang.object.Test");
            Class b = Test.class;
            System.out.println("class ---------");
            Test test = new Test();
            System.out.println("----------------");
            Class<? extends Test> aClass = test.getClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class Test {

    static {
        System.out.println("执行静态块，初始化类");
    }

    public Test() {
        System.out.println("构造方法执行");
    }
}