package dk.sdu.mmmi.cbse.util;

@FunctionalInterface
public interface VoidFunc<T> {

    void apply(T obj);
}
