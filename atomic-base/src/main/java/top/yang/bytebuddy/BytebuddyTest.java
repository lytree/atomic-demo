package top.yang.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class BytebuddyTest {
    @Test
    public void test() throws ClassNotFoundException {
        ArrayList<TypeDescription.Generic> list = new ArrayList<>();
        Class<?> type1 = InterfaceTestImpl.class;
        Type[] genericInterfaces1 = type1.getGenericInterfaces();
        for (Type type : genericInterfaces1) {
            Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
            Class[] classes = new Class[typeArguments.length];
            for (int i = 0; i < typeArguments.length; i++) {
                classes[i] = Class.forName(typeArguments[i].getTypeName());
            }
            Class<?> aClass = Class.forName(((ParameterizedType) type).getRawType().getTypeName());
            TypeDescription.Generic listType = TypeDescription.Generic.Builder.parameterizedType(
                    aClass, classes
            ).build();
            list.add(listType);
        }
        Class<?> subclass = new ByteBuddy()
                .subclass(type1)
                .implement(list)
                .make()
                .load(InterfaceTest.class.getClassLoader())
                .getLoaded();


        Type[] genericInterfaces = subclass.getGenericInterfaces();
        for (Type type : genericInterfaces) {
            Type rawType = ((ParameterizedType) type).getRawType();
            Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
            Class<?> aClass = Class.forName(rawType.getTypeName());
            System.out.println(aClass);
        }
    }
}
