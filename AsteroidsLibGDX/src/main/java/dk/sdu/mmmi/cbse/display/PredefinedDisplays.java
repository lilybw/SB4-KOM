package dk.sdu.mmmi.cbse.display;

import java.util.List;

public enum PredefinedDisplays {

    IN_GAME(new DisplayCollection(
            List.of(
                    new DisplayElement().setHeight(100).setWidth(100),
                    new DisplayElement().setHeight(150).setWidth(50).setX(50).setY(400),
                    new DisplayElement().setHeight(200).setWidth(200).setX(0).setY(800),
                    new DisplayElement().setHeight(150).setWidth(50).setX(350).setY(100),
                    new TextElement("ABCDEFGHIJKLMOPQRSTUVXYZ").setX(0).setY(400).setWidth(1000).setHeight(100)
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
