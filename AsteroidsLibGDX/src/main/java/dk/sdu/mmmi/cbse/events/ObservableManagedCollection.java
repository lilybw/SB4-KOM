package dk.sdu.mmmi.cbse.events;

import dk.sdu.mmmi.cbse.util.ManagedBufferedCollection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Provides observability for add, remove and clear.
 * Do note that due to performance conciderations, bulk operations like addAll and removeAll are not supported.
 * @param <T>
 */
public class ObservableManagedCollection<T> extends ManagedBufferedCollection<T> {

    private final ManagedBufferedCollection<ValueChangeCallback<T>> onAddListener = new ManagedBufferedCollection<>(new HashSet<>());
    private final ManagedBufferedCollection<ValueChangeCallback<T>> onRemoveListener = new ManagedBufferedCollection<>(new HashSet<>());
    private final ManagedBufferedCollection<ValueChangeCallback<T>> onClearListener = new ManagedBufferedCollection<>(new HashSet<>());

    public ObservableManagedCollection(Collection<T> collection) {
        super(collection);
    }

    public void addOnAddListener(ValueChangeCallback<T> listener)
    {
        onAddListener.add(Objects.requireNonNull(listener));
    }
    public void addOnRemoveListener(ValueChangeCallback<T> listener)
    {
        onRemoveListener.add(Objects.requireNonNull(listener));
    }
    public void addOnClearListener(ValueChangeCallback<T> listener)
    {
        onClearListener.add(Objects.requireNonNull(listener));
    }

    @Override
    public void add(T obj)
    {
        onAddListener.forEachCurrent(e -> e.apply(null,obj));
        super.add(obj);
    }

    @Override
    public void remove(T obj)
    {
        onRemoveListener.forEachCurrent(e -> e.apply(obj, null));
        super.remove(obj);
    }

    @Override
    public void clear()
    {
        onClearListener.forEachCurrent(e -> e.apply(null,null));
        super.clear();
    }
}
