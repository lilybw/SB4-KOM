package dk.sdu.mmmi.cbse.display;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface IDisplayElement {


    void draw(ShapeRenderer sr, float offsetX, float offsetY, float scalarX, float scalarY);
    float getX();
    float getY();
    IDisplayElement setX(float x);
    IDisplayElement setY(float y);
    float getWidth();
    float getHeight();
    IDisplayElement setWidth(float width);
    IDisplayElement setHeight(float height);
    IDisplayElement setParent(IDisplayElement element);
    IDisplayElement getParent();

}
