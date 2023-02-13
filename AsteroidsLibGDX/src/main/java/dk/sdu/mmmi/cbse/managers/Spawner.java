package dk.sdu.mmmi.cbse.managers;

import dk.sdu.mmmi.cbse.entities.IEntity;
import dk.sdu.mmmi.cbse.main.Game;

public class Spawner<T extends IEntity> {

    @FunctionalInterface
    public interface GetNewOfType<T> {
        T create();
    }

    private final float frequencyMS;
    private final GetNewOfType<T> createNewFunction;
    private float timeSinceLastSpawn = 0;

    public Spawner(float frequencyMS, GetNewOfType<T> createNewFunction)
    {
        this.frequencyMS = frequencyMS;
        this.createNewFunction = createNewFunction;
    }

    public void update(float deltaT)
    {
        timeSinceLastSpawn += deltaT;
        if(timeSinceLastSpawn >= frequencyMS){
            Game.getInstance().getState().addEntity(createNewFunction.create());
            timeSinceLastSpawn = 0;
        }
    }

}
