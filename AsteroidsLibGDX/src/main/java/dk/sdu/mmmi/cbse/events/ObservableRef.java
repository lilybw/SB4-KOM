package dk.sdu.mmmi.cbse.events;

import dk.sdu.mmmi.cbse.util.ManagedBufferedCollection;
import dk.sdu.mmmi.cbse.util.Ref;
import dk.sdu.mmmi.cbse.util.VoidFunc;

import java.util.HashSet;

public class ObservableRef<T> extends Ref<T>  {

    private final ManagedBufferedCollection<ValueChangeCallback<T>> listeners = new ManagedBufferedCollection<>(new HashSet<>());

    public ObservableRef(T val) {
        super(val);
    }

    @Override
    public void set(T t)
    {
        listeners.forEachCurrent(e -> e.apply(super.val,t));
        super.set(t);
    }

    public void listen(ValueChangeCallback<T> onChange)
    {
        listeners.add(onChange);
    }




}
