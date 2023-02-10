package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Bullet implements IEntity {

    private float hitRadius;

    private float x,y,velX,velY;

    public Bullet(float[] xy, float[] dir)
    {
        this.velX = dir[0];
        this.velY = dir[1];
        this.x = xy[0];
        this.y = xy[1];
    }

    @Override
    public void update(float deltaT) {
        x += velX;
        y += velY;
    }

    @Override
    public void draw(ShapeRenderer sr) {
        //thx: https://stackoverflow.com/questions/26715986/libgdx-shaperenderer-circle-drawing
        sr.setColor(Color.RED);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(x, y, hitRadius);
        sr.end();
    }

    @Override
    public boolean isInBounds(float x1, float y1) {
        float xDiff = x - x1, yDiff = y - y1;
        float distSQ = xDiff * xDiff + yDiff * yDiff;
        return distSQ < hitRadius * hitRadius;
    }
}
