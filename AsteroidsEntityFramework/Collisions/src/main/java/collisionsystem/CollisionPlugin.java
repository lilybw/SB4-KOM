package collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.List;

public class CollisionPlugin implements IPostEntityProcessingService {


    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = world.getEntities(Entity.class);
        List<String> cleanUp = new ArrayList<>();

        for(int i = 0; i < entities.size(); i++){
            for(int j = i + 1; j < entities.size(); j++){
                if(isInBounds(entities.get(i),entities.get(j)))
                {
                    cleanUp.add(entities.get(i).getID());
                    cleanUp.add(entities.get(j).getID());
                }
            }
        }
        world.removeEntities(cleanUp);
    }

    private boolean isInBounds(Entity e1, Entity e2)
    {
        PositionPart positionPart1 = e1.getPart(PositionPart.class);
        float e1X = positionPart1.getX();
        float e1Y = positionPart1.getY();
        PositionPart positionPart2 = e2.getPart(PositionPart.class);
        float e2X = positionPart2.getX();
        float e2Y = positionPart2.getY();

        final float dX = e1X - e2X, dY = e1Y - e2Y;
        final float distSQ = dX * dX + dY * dY;
        final float totalRadiusSQ = (e1.getRadius() + e2.getRadius()) * (e1.getRadius() + e2.getRadius());

        return distSQ <= totalRadiusSQ;
    }
}
