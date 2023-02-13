package dk.sdu.mmmi.cbse.events;

public interface Observable<T> {

    void listen(ValueChangeCallback<T> onChange);
    T _value();

}
