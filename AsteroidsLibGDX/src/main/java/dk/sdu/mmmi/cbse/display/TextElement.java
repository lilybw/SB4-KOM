package dk.sdu.mmmi.cbse.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TextElement extends DisplayElement{

    protected String text = "";
    protected int letterCount;

    public TextElement(String text)
    {
        this.text = text;
        this.letterCount = text.length();
    }

    @Override
    public void draw(ShapeRenderer sr, float offX, float offY, float scalarX, float scalarY)
    {
        TextRenderer.write(sr,text,offX + x,offY + y,(width / letterCount) * scalarX,height * scalarY, Color.RED);
    }
}
