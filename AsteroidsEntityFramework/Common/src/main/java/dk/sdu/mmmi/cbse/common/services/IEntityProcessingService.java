package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IEntityProcessingService {

    /**
     * Per game tick, evaluate the entities in accordance with game and world data.
     * @param gameData
     * @param world
     */
    void process(GameData gameData, World world);
}
