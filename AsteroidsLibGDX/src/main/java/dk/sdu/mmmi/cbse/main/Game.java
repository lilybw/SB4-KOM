package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import dk.sdu.mmmi.cbse.entities.IEntity;
import dk.sdu.mmmi.cbse.managers.*;
import dk.sdu.mmmi.cbse.util.ManagedBufferedCollection;

import java.util.HashSet;

public class Game implements ApplicationListener {
	
	public static OrthographicCamera cam;

	public static Game getInstance()
	{
		if(instance == null){
			new NullPointerException("A Game has not been instatiated").printStackTrace();
		}
		return instance;
	}
	private static Game instance;
	private GameStateManager gsm;
	private final ManagedBufferedCollection<IEntity> tickables = new ManagedBufferedCollection<>(new HashSet<>());
	
	@Override
	public void create() {
		cam = new OrthographicCamera(ScreenManager.WIDTH / 10F, ScreenManager.HEIGHT / 10F);
		cam.translate(ScreenManager.WIDTH / 2F, ScreenManager.HEIGHT / 2F);
		cam.update();

		Gdx.input.setInputProcessor(
			new GameInputProcessor()
		);

		gsm = new GameStateManager();
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

	public void tick(float deltaT)
	{
		gsm.update(Gdx.graphics.getDeltaTime());
		GameKeys.update();
	}
	
        @Override
	public void resize(int width, int height) {
		Main.screenManager.onGdxResize(width,height);
	}
        @Override
	public void pause() {}
        @Override
	public void resume() {}
        @Override
	public void dispose() {}
	
}
