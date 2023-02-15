package dk.sdu.mmmi.cbse.display.reactive;

import dk.sdu.mmmi.cbse.display.KnownHudElements;
import dk.sdu.mmmi.cbse.display.TextElement;
import dk.sdu.mmmi.cbse.managers.HUDManager;

public class ReactiveTextElement extends TextElement {


    public ReactiveTextElement(KnownHudElements element) {
        super("");
        HUDManager.observe(element, (Object oldVal, Object newVal) -> super.text = newVal.toString());
    }
}
