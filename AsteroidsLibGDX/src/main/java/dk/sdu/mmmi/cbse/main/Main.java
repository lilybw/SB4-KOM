package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dk.sdu.mmmi.cbse.managers.ScreenManager;

public class Main {

	public static ScreenManager screenManager = new ScreenManager();

	public static void main(String[] args) {
		
		LwjglApplicationConfiguration cfg =
			new LwjglApplicationConfiguration();
		cfg.title = "Asteroids";
		cfg.width = ScreenManager.WIDTH;
		cfg.height = ScreenManager.HEIGHT;
		cfg.useGL30 = false;
		cfg.resizable = false;
		
		new LwjglApplication(new Game(), cfg);
		
	}
	
}
