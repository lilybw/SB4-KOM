package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.ai.Movesets;
import dk.sdu.mmmi.cbse.ai.Program;
import dk.sdu.mmmi.cbse.ai.ProgramScheduler;
import dk.sdu.mmmi.cbse.collisions.Collider;
import dk.sdu.mmmi.cbse.collisions.Mesh;
import dk.sdu.mmmi.cbse.fruity.NeonColours;
import dk.sdu.mmmi.cbse.main.Game;
import dk.sdu.mmmi.cbse.managers.ScreenManager;
import dk.sdu.mmmi.cbse.objectCapture.Capture;
import dk.sdu.mmmi.cbse.util.VMath;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends SpaceObject implements IEntity{

    private final ProgramScheduler<Player> scheduler;
    private final Collider collider;

    public Enemy()
    {
        this.scheduler = new ProgramScheduler<>(ProgramScheduler.RANDOM);
        scheduler.setPrograms(Movesets.ENEMY(scheduler,this));

        final float[] position = ScreenManager.getRandomPositionOutsideViewport(100);
        x = position[0];
        y = position[1];

        shapex = new float[4];
        shapey = new float[4];
        speed = 100;
        this.collider = new Collider(shapex,shapey, Collider.POLYGON);

        radians = 3.1415f / 2;
        rotationSpeed = 3;
    }

    public void alignToCurrentDirection()
    {
        final float[] normalizedVel = VMath.normalize(velocityX,velocityY);
        this.radians = MathUtils.atan2(normalizedVel[0],normalizedVel[1]);
    }
    public void pointAt(IEntity entity)
    {
        final float[] normalizedVel = VMath.normalize(entity.getX() - x,entity.getY() - y);
        this.radians = MathUtils.atan2(normalizedVel[0],normalizedVel[1]);
    }

    @Override
    public void update(float deltaT) {
        scheduler.update(Game.getInstance().getPlayer(),deltaT);
        updateShape();
        collider.update(shapex,shapey);
        x += velocityX * speed * deltaT;
        y += velocityY * speed * deltaT;
    }

    @Override
    public void draw(ShapeRenderer sr) {
        sr.setColor(Color.ORANGE);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {
            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
        }
        sr.end();
    }

    private void updateShape() {
        shapex[0] = x + (MathUtils.cos(radians) * 8);
        shapey[0] = y + (MathUtils.sin(radians) * 8);

        shapex[1] = x + (MathUtils.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = y + (MathUtils.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = x + (MathUtils.cos(radians + 3.1415f) * 5);
        shapey[2] = y + (MathUtils.sin(radians + 3.1415f) * 5);

        shapex[3] = x + (MathUtils.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = y + (MathUtils.sin(radians + 4 * 3.1415f / 5) * 8);

        for(int i = 0; i < shapex.length; i++){
            shapex[i] *= Game.scaleX();
            shapey[i] *= Game.scaleY();
        }
    }

    @Override
    public boolean isInBounds(float x, float y)
    {
        return collider.isInBounds(x,y) == 1;
    }

    @Override
    public void ifInBounds(IEntity collidingEntity) {
        if(collidingEntity instanceof Bullet && ((Bullet) collidingEntity).getSource() == this){
            return;
        }
        destroy();
    }

    public Enemy spawn(){
        Game.getInstance().getState().addEntity(this);
        return this;
    }
    public Enemy destroy(){
        Game.getInstance().getState().removeEntity(this);
        return this;
    }
    public void destroyedBy(IEntity entity)
    {
        System.out.println("Enemy destroyed: " + this + " by: " + entity);
        destroy();
    }

    @Override
    public float getMass()
    {
        return 200f;
    }

    @Override
    public float getMomentum()
    {
        return VMath.mag(velocityX, velocityY);
    }

    @Override
    public Mesh.Point getCenterOfMass()
    {
        //TODO: Have any polygonal collider calculate a specific center of mesh
        return collider.verts()[0];
    }

    @Override
    public String toString()
    {
        return "Enemy{position: ["+x+","+y+"]}";
    }
}
