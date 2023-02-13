package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import dk.sdu.mmmi.cbse.entities.IEntity;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.entities.SpaceObject;
import dk.sdu.mmmi.cbse.fruity.NeonColours;
import dk.sdu.mmmi.cbse.gamestates.GameState;
import dk.sdu.mmmi.cbse.gamestates.PlayState;
import dk.sdu.mmmi.cbse.managers.*;
import dk.sdu.mmmi.cbse.util.ManagedBufferedCollection;

import java.util.HashSet;
import java.util.Random;

public class Game implements ApplicationListener {

	public static Random rand = new Random(System.currentTimeMillis());
	public static OrthographicCamera cam;

	public static Game getInstance()
	{
		if(instance == null){
			throw new NullPointerException("A Game has not been instatiated");
		}
		return instance;
	}
	public static long TICK_COUNT = 0;
	private static Game instance;
	private GameStateManager gsm;
	private NeonColours colours;

	@Override
	public void create() {
		final int width = Gdx.graphics.getWidth(), height =  Gdx.graphics.getHeight();
		ScreenManager.updateToReflect(width,height);
		cam = new OrthographicCamera(width * 4, height * 4);
		cam.position.set(width / 2f, height / 2f, 0);
		cam.update();
		System.out.println(cam.position + " " + cam.viewportWidth + " " + cam.viewportHeight);

		Gdx.input.setInputProcessor(
			new GameInputProcessor()
		);

		gsm = new GameStateManager();
		colours = new NeonColours((float) Math.random() * 45);
		instance = this;
	}

	public Player getPlayer()
	{
		return gsm.getActivePlayer();
	}
	
        @Override
	public void render() {

		float deltaT = Gdx.graphics.getDeltaTime();
		tick(deltaT);

		// clear screen to black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.draw();
	}

	public GameState getState()
	{
		return gsm.getCurrent();
	}

	public void tick(float deltaT)
	{
		SpaceObject focalPoint = gsm.getFocalPoint();
		cam.translate(focalPoint.getX() - .5f * ScreenManager.WIDTH, focalPoint.getY() - .5f * ScreenManager.HEIGHT);
		cam.update();

		gsm.update(deltaT);
		GameKeys.update();
		colours.update(Game.TICK_COUNT,10);
		TICK_COUNT++;
	}
	
	@Override
	public void resize(int width, int height) {
		Main.screenManager.onGdxResize(width,height);
	}
	@Override
	public void pause() {
		PlayState.IS_PAUSED = true;
	}
	@Override
	public void resume() {
		PlayState.IS_PAUSED = false;
	}
	@Override
	public void dispose() {}

	public static float scaleX(){
		return ScreenManager.getNormalizationScalarX() * 2f;
	}
	public static float scaleY(){
		return ScreenManager.getNormalizationScalarY() * 2f;
	}
	
}
