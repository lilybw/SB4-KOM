package dk.sdu.mmmi.cbse.ai.movement;

import dk.sdu.mmmi.cbse.entities.IEntity;

public class PredefinedMove {

    private final float destX, destY, originX, originY, diffXPerTick,diffYPerTick;
    private int ticks;
    private IEntity entity;


    public PredefinedMove(float originX, float originY, float destX, float destY, int ticks)
    {
        this.destX = destX;
        this.destY = destY;
        this.originX = originX;
        this.originY = originY;
        this.ticks = ticks;
        this.diffXPerTick = (destX - originX) / ticks;
        this.diffYPerTick = (destY - originY) / ticks;
    }


    public void update(int tick)
    {

    }

}
