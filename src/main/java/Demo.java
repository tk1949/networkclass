import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Demo {

    public static void main(String[] args) throws
            ClassNotFoundException,
            NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException
    {
        NetworkClassLoader loader = new NetworkClassLoader();
        Class<?> clazz = loader.loadClass("TestClass");

        System.err.println("-------- network loader class --------");
        System.err.println(clazz.getClassLoader());

        System.err.println("--------  local loader class  --------");
        Object instance = clazz.getDeclaredConstructor().newInstance();
        System.err.println(instance);

        System.err.println("--------    serialization     --------");
        byte[] bytes = KryoCodec.writeToByte(instance);
        System.err.println(Arrays.toString(bytes));

        System.err.println("--------   deserialization    --------");
        TestInterface o = KryoCodec.readFromByte(bytes);
        o.say();
        System.err.println(o);
    }
}