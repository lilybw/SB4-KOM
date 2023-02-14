package dk.sdu.mmmi.cbse.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import jdk.jfr.Percentage;


public class DisplayElement implements IDisplayElement{

    protected IDisplayElement parent;
    protected float x, y, width, height;

    public DisplayElement() {}

    public DisplayElement(float x, float y, float width, float height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(ShapeRenderer sr, float offsetX, float offsetY, float scalarX, float scalarY) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.GRAY);
        sr.box(x + offsetX,y + offsetY,0,width * scalarX,height * scalarY,0);
        sr.end();
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public DisplayElement setX(float x) {
        this.x = x;
        return this;
    }

    @Override
    public DisplayElement setY(float y) {
        this.y = y;
        return this;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public IDisplayElement setWidth(float width) {
        this.width = width;
        return this;
    }

    @Override
    public IDisplayElement setHeight(float height) {
        this.height = height;
        return this;
    }

    @Override
    public IDisplayElement getParent()
    {
        return parent;
    }
    @Override
    public IDisplayElement setParent(IDisplayElement element)
    {
        this.parent = element;
        return this;
    }
}
