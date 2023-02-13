package dk.sdu.mmmi.cbse.managers;

import dk.sdu.mmmi.cbse.display.KnownHudElements;
import dk.sdu.mmmi.cbse.events.Observable;
import dk.sdu.mmmi.cbse.events.ValueChangeCallback;

import java.util.HashMap;
import java.util.Map;

public class HUDManager {

    private static final Map<KnownHudElements,Observable<?>> elements = new HashMap<>();

    public static <T> T observe(KnownHudElements element, ValueChangeCallback<T> callback)
    {
        try{
            Observable<T> observable = (Observable<T>) elements.get(element);
            if(observable == null) return null;
            T currentValue = observable._value();
            observable.listen(callback);
            return currentValue;
        }catch (Exception e){
            return null;
        }
    }

    public static void addObservable(KnownHudElements element, Observable<?> observable)
    {
        elements.put(element,observable);
    }
}
