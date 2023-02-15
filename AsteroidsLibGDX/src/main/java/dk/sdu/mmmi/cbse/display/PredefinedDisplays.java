package dk.sdu.mmmi.cbse.display;

import dk.sdu.mmmi.cbse.display.reactive.ReactiveTextElement;

import java.util.List;

public enum PredefinedDisplays {

    IN_GAME(new DisplayCollection(
            List.of(
                    new ReactiveTextElement(KnownHudElements.CurrentScore).setX(100).setY(2000).setWidth(100).setHeight(100)
            ))
    ),
    MENU(new DisplayCollection(List.of(

            )
    ));

    public final DisplayCollection display;

    PredefinedDisplays(DisplayCollection collection)
    {
        this.display = collection;
    }

}
