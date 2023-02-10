package dk.sdu.mmmi.cbse.managers;

import dk.sdu.mmmi.cbse.main.Game;

public interface IResizable {

    /**
     * Provides the not-yet-updated screen dimesions through the screenManager,
     * alongside the new width and height to adjust to accordingly.
     * @param screenManager
     * @param newWidth
     * @param newHeight
     */
    void onResize(ScreenManager screenManager, int newWidth, int newHeight);

}
