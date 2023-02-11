package dk.sdu.mmmi.cbse.events;

@FunctionalInterface
public interface ValueChangeCallback<T> {

    void apply(T oldVal, T newVal);
}
