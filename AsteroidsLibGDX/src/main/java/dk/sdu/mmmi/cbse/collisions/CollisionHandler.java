package dk.sdu.mmmi.cbse.collisions;

import dk.sdu.mmmi.cbse.entities.IEntity;
import dk.sdu.mmmi.cbse.util.ManagedBufferedCollection;

import java.util.Collection;

public class CollisionHandler {

    public static void check(ManagedBufferedCollection<IEntity> entities)
    {
        IEntity[] asArray = entities.__unsafeAccessCurrent(new IEntity[0]);

        for(int i = 0; i < asArray.length; i++){
            for(int j = i + 1; j < asArray.length; j++){
                if(asArray[i].isInBounds(asArray[j].getX(), asArray[j].getY())){
                    asArray[i].ifInBounds(asArray[j]);
                }
            }
        }

        entities.cycle();
    }


}
