package asteroidssystem;

import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import static dk.sdu.mmmi.cbse.common.data.GameKeys.*;

public class AsteroidsControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);

            movingPart.setLeft(MathUtils.randomBoolean());
            movingPart.setRight(MathUtils.randomBoolean());
            movingPart.setUp(MathUtils.randomBoolean());

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);

            updateShape(asteroid);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        for(int i = 0; i < shapex.length; i++) {
            shapex[i] = (MathUtils.cos((MathUtils.PI2 / shapex.length) * i) * entity.getRadius()) + x;
            shapey[i] = (MathUtils.sin((MathUtils.PI2 / shapex.length) * i) * entity.getRadius()) + y;
        }

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
