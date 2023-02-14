package dk.sdu.mmmi.cbse.ai.movement;

import dk.sdu.mmmi.cbse.entities.IEntity;
import dk.sdu.mmmi.cbse.util.VMath;

public class LERP {


    public static void move(IEntity entity, float toPosX, float toPosY, int iterator, float maxMagnitudePerMove)
    {
        final float fraction = VMath.dist(entity.getX(),entity.getY(),toPosX,toPosY) / maxMagnitudePerMove;


    }


}
