package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface IEntity {

    /**
     *
     * @param deltaT the fraction of a second that has passed
     */
    void update(float deltaT);
    void draw(ShapeRenderer sr);
    boolean isInBounds(float x, float y);
    void ifInBounds(IEntity collidingEntity);

    IEntity spawn();
    IEntity destroy();


}
