package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.main.Game;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.ScreenManager;
import jdk.jfr.Unsigned;

public class Player extends SpaceObject implements IEntity{

    private final float maxSpeed;
    private final float acceleration;
    private final float deceleration;
    private final Collider collider;


    public Player() {

        x = ScreenManager.WIDTH / 2f;
        y = ScreenManager.HEIGHT / 2f;

        maxSpeed = 300;
        acceleration = 200;
        deceleration = 10;

        shapex = new float[4];
        shapey = new float[4];

        radians = 3.1415f / 2;
        rotationSpeed = 3;

        collider = new Collider(shapex,shapey);
    }

    @Override
    public boolean isInBounds(float x, float y)
    {
        return collider.isInBounds(new Collider.Point(x,y)) == 1;
    }

    @Override
    public void update(float dt) {

        // turning
        if (GameKeys.isDown(GameKeys.LEFT)) {
            radians += rotationSpeed * dt;
        } else if (GameKeys.isDown(GameKeys.RIGHT)) {
            radians -= rotationSpeed * dt;
        }

        // accelerating
        if (GameKeys.isDown(GameKeys.UP)) {
            dx += MathUtils.cos(radians) * acceleration * dt;
            dy += MathUtils.sin(radians) * acceleration * dt;
        }

        // deceleration
        float vec = (float) Math.sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * deceleration * dt;
            dy -= (dy / vec) * deceleration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        // set position
        x += dx * dt;
        y += dy * dt;

        // set shape
        setShape();

        // screen wrap
        wrap();

        collider.update(shapex,shapey);
    }

    @Override
    public void draw(ShapeRenderer sr) {

        sr.setColor(1, 1, 1, 1);

        sr.begin(ShapeType.Line);

        for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {

            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);

        }

        sr.end();

    }


    private void setShape() {
        shapex[0] = x + MathUtils.cos(radians) * 8;
        shapey[0] = y + MathUtils.sin(radians) * 8;

        shapex[1] = x + MathUtils.cos(radians - 4 * 3.1415f / 5) * 8;
        shapey[1] = y + MathUtils.sin(radians - 4 * 3.1145f / 5) * 8;

        shapex[2] = x + MathUtils.cos(radians + 3.1415f) * 5;
        shapey[2] = y + MathUtils.sin(radians + 3.1415f) * 5;

        shapex[3] = x + MathUtils.cos(radians + 4 * 3.1415f / 5) * 8;
        shapey[3] = y + MathUtils.sin(radians + 4 * 3.1415f / 5) * 8;
    }

}
