package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.collisions.Collider;
import dk.sdu.mmmi.cbse.collisions.Mesh;
import dk.sdu.mmmi.cbse.fruity.NeonColours;
import dk.sdu.mmmi.cbse.main.Game;
import dk.sdu.mmmi.cbse.managers.ScreenManager;

public class Asteroid extends SpaceObject implements IEntity{

    private final Collider collider;
    private final float size;
    private float health;

    public Asteroid()
    {
        this(
                Game.rand.nextFloat() * 100,
                Game.rand.nextFloat() * 100,
                Game.rand.nextFloat(),
                Game.rand.nextFloat()
        );
    }

    public Asteroid(float speed, float size, float dirX, float dirY)
    {
        super.speed = speed;
        this.size = size;
        super.velocityX = dirX;
        super.velocityY = dirY;
        this.collider = new Collider(0,0,size);
        this.health = size;
        final float[] position = ScreenManager.getRandomPositionOutsideViewport(size);
        super.x = position[0];
        super.y = position[1];
    }

    @Override
    public void update(float deltaT) {
        x += velocityX * speed * deltaT;
        y += velocityY * speed * deltaT;
        collider.update(x,y,size);
    }

    @Override
    public void draw(ShapeRenderer sr) {
        //thx: https://stackoverflow.com/questions/26715986/libgdx-shaperenderer-circle-drawing

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.GRAY);
        sr.circle(x, y, size);
        sr.end();
    }

    @Override
    public boolean isInBounds(float x, float y) {
        return collider.isInBounds(x,y) == 1;
    }

    @Override
    public void ifInBounds(IEntity collidingEntity) {
        health -= collidingEntity.getMass() * collidingEntity.getMomentum();
        if(health < 0)
        {
            destroy();
        }
    }

    @Override
    public IEntity spawn() {
        Game.getInstance().getState().addEntity(this);
        return this;
    }

    @Override
    public IEntity destroy() {
        Game.getInstance().getState().removeEntity(this);
        return this;
    }

    @Override
    public float getMass()
    {
        return 2f * size * 3.1415926f;
    }

    @Override
    public float getMomentum()
    {
        return super.speed;
    }

    @Override
    public Mesh.Point getCenterOfMass()
    {
        return collider.verts()[0];
    }

}
