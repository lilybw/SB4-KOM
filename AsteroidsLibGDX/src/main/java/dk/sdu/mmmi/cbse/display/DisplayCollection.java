package dk.sdu.mmmi.cbse.display;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DisplayCollection extends DisplayElement {

    protected final Set<IDisplayElement> collection = new HashSet<>();

    public DisplayCollection(Collection<IDisplayElement> collection)
    {
        this.collection.addAll(collection);
        this.collection.forEach(e -> e.setParent(this));
    }

    @Override
    public void draw(ShapeRenderer sr, float offsetX, float offsetY, float scalarX, float scalarY)
    {
        super.draw(sr,offsetX,offsetY,scalarX,scalarY);
        collection.forEach(e -> e.draw(sr,offsetX + x,offsetY + y,scalarX,scalarY));
    }


}
