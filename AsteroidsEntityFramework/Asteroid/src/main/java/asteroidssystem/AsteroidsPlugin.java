package asteroidssystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class AsteroidsPlugin implements IGamePluginService {

    private Entity asteroid;

    public AsteroidsPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        asteroid = createNewAsteroid(gameData);
        world.addEntity(asteroid);
    }

    private Entity createNewAsteroid(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2f;
        float y = gameData.getDisplayHeight() / 2f;
        float radians = 3.1415f / 2;

        Entity newAstroid = new Asteroid();
        newAstroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        newAstroid.add(new PositionPart(x, y, radians));

        return newAstroid;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(asteroid);
    }
}
