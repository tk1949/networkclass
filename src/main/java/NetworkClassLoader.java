import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NetworkClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        if (name.endsWith("ConstructorAccess")) {
            return getClass().getClassLoader().loadClass(name);
        }

        try {
            // 模拟远程获取数据
            byte[] classData = Files.readAllBytes(Paths.get(name + ".class"));
            return defineClass(name, classData, 0, classData.length);
        } catch (IOException e) {
            e.printStackTrace();
            ClassNotFoundException exception = new ClassNotFoundException();
            exception.addSuppressed(e);
            throw exception;
        }
    }
}