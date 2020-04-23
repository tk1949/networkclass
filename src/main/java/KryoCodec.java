import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoCodec {

    private static final ThreadLocal<Kryo> kryoLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.setReferences(true);
        kryo.setRegistrationRequired(false);
        kryo.setClassLoader(new NetworkClassLoader());
        ((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy())
                .setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
        return kryo;
    });

    public static <T> byte[] writeToByte(T t) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try (Output output = new Output(stream)) {
            Kryo kryo = kryoLocal.get();
            kryo.writeClassAndObject(output, t);
        }
        return stream.toByteArray();
    }

    public static <T> T readFromByte(byte[] encode) {
        ByteArrayInputStream stream = new ByteArrayInputStream(encode);
        try (Input input = new Input(stream)) {
            Kryo kryo = kryoLocal.get();
            return (T) kryo.readClassAndObject(input);
        }
    }
}