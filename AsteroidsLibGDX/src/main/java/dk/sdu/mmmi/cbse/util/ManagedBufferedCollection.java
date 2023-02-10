package dk.sdu.mmmi.cbse.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Thread safe access to a buffered collection of type T
 * @param <T>
 */
public class ManagedBufferedCollection<T> {

    private final Collection<T> newborn;
    private final Collection<T> current;
    private final Collection<T> expired;

    public ManagedBufferedCollection(Collection<T> collection)
    {
        current = Collections.synchronizedCollection(collection);
        newborn = Collections.synchronizedCollection(new HashSet<>());
        expired = Collections.synchronizedCollection(new HashSet<>());
    }

    public void add(T t)
    {
        newborn.add(t);
    }

    public void remove(T t) {
        expired.add(t);
    }

    public void cycle()
    {
        current.removeAll(expired);
        current.addAll(newborn);
        expired.clear();
        newborn.clear();
    }

    public void forEachCurrent(Consumer<? super T> func)
    {
        synchronized (current){
            current.forEach(func);
        }
    }
    public void forEachNewborn(Consumer<? super T> func)
    {
        synchronized (newborn){
            current.forEach(func);
        }
    }
    public void forEachExpired(Consumer<? super T> func)
    {
        synchronized (expired){
            current.forEach(func);
        }
    }


}
