package dk.sdu.mmmi.cbse.util;

@FunctionalInterface
public interface VoidFunc2<T,L> {
    void apply(T t, L l);
}
