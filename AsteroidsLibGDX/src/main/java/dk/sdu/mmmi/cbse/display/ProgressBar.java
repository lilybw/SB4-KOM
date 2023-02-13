package dk.sdu.mmmi.cbse.display;

import dk.sdu.mmmi.cbse.events.Observable;
import dk.sdu.mmmi.cbse.events.ValueChangeCallback;
import dk.sdu.mmmi.cbse.managers.HUDManager;
import dk.sdu.mmmi.cbse.util.Ref;

public class ProgressBar {

    private final Ref<Float> value = new Ref<>(0f);

    public ProgressBar(KnownHudElements element)
    {
        value.set(HUDManager.observe(element, (Float oldVal, Float newVal) -> value.set(newVal)));
    }

}
