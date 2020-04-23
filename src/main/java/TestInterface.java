public interface TestInterface {

    default void say() {
        System.out.println("hello");
    }
}