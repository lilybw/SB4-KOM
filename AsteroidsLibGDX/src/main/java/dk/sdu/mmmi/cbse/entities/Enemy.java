package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemy extends SpaceObject implements IEntity{


    @Override
    public void update(float deltaT) {

    }

    @Override
    public void draw(ShapeRenderer sr) {
        sr.setColor(1, 1, 1, 1);

        sr.begin(ShapeRenderer.ShapeType.Line);

        for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {

            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);

        }

        sr.end();
    }

    @Override
    public boolean isInBounds(float x, float y)
    {
        return false;
    }
}
