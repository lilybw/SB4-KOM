package dk.sdu.mmmi.cbse.util;

import java.util.*;
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

    private boolean newbornModified, expiredModified;

    /**
     * Clears the "current" buffer.
     * NB: Does not cycle.
     */
    public void clear()
    {
        current.clear();
    }

    public void addAll(Collection<T> c)
    {
        newborn.addAll(c);
        newbornModified = true;
    }

    public void removeAll(Collection<T> c)
    {
        expired.addAll(c);
        expiredModified = true;
    }

    public void add(T t)
    {
        newborn.add(t);
        newbornModified = true;
    }

    public void remove(T t) {
        expired.add(t);
        expiredModified = true;
    }

    public void cycle()
    {
        current.removeAll(expired);
        current.addAll(newborn);
        expired.clear();
        newborn.clear();
        newbornModified = false;
        expiredModified = false;
    }

    public void forEachCurrent(Consumer<? super T> func)
    {
        if(newbornModified || expiredModified) cycle();

        synchronized (current){
            current.forEach(func);
        }
    }
    public void forEachNewborn(Consumer<? super T> func)
    {
        if(newbornModified) cycle();

        synchronized (newborn){
            current.forEach(func);
        }
    }
    public void forEachExpired(Consumer<? super T> func)
    {
        if(expiredModified) cycle();

        synchronized (expired){
            current.forEach(func);
        }
    }

    public T[] __unsafeAccessCurrent(T[] empty)
    {
        return current.toArray(empty);
    }


}
