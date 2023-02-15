package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService  {
        /**
         * Once per game tick, evaluate the state of the entities.
         * I.e. collision, viewport culling, etc...
         * @param gameData
         * @param world
         */
        void process(GameData gameData, World world);
}
