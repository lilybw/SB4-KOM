package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IGamePluginService {

    /**
     * Upon loading the plugin.
     * @param gameData
     * @param world
     */
    void start(GameData gameData, World world);

    /**
     * Upon disabling and/or removing the plugin.
     * @param gameData
     * @param world
     */
    void stop(GameData gameData, World world);
}
