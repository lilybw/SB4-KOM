package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.ai.Program;
import dk.sdu.mmmi.cbse.ai.ProgramScheduler;
import dk.sdu.mmmi.cbse.main.Game;

public class Enemy extends SpaceObject implements IEntity{


    private final ProgramScheduler<Enemy> scheduler;


    public Enemy()
    {
        this.scheduler = new ProgramScheduler<>(ProgramScheduler.RANDOM);
        this.scheduler.setPrograms(loadPrograms());
    }

    private Program<?,Enemy>[] loadPrograms()
    {
        return null;
    }

    @Override
    public void update(float deltaT) {
        scheduler.update();
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

    @Override
    public void ifInBounds(IEntity collidingEntity) {

    }


    public Enemy spawn(){
        Game.getInstance().getState().addEntity(this);
        return this;
    }
    public Enemy destroy(){
        Game.getInstance().getState().removeEntity(this);
        return this;
    }
}
