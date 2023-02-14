package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.collisions.CollisionHandler;
import dk.sdu.mmmi.cbse.display.PredefinedDisplays;
import dk.sdu.mmmi.cbse.entities.*;
import dk.sdu.mmmi.cbse.managers.GameStateManager;
import dk.sdu.mmmi.cbse.managers.ScreenManager;
import dk.sdu.mmmi.cbse.managers.Spawner;
import dk.sdu.mmmi.cbse.util.ManagedBufferedCollection;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class PlayState extends GameState {

	public static boolean IS_PAUSED = false;

	private ShapeRenderer sr;
	private SpaceObject currentFocalPoint;
	private final Player player;
	private final Spawner<Asteroid> asteroids;
	private final Spawner<Enemy> enemies;


	private final ManagedBufferedCollection<IEntity> entities = new ManagedBufferedCollection<>(new HashSet<>());

	public PlayState(GameStateManager gsm) {
		super(gsm);
		player = new Player();
		Enemy enemy = new Enemy();
		currentFocalPoint = player;
		entities.addAll(List.of(player,enemy));
		asteroids = new Spawner<>(1,Asteroid::new);
		enemies = new Spawner<>(5,Enemy::new);
	}

	public SpaceObject getFocalPoint()
	{
		return currentFocalPoint;
	}
	
	public void init() {
		sr = new ShapeRenderer();
	}

	public void addEntity(IEntity e) {
		entities.add(e);
	}

	public void removeEntity(IEntity e){
		entities.remove(e);
	}
	public void addAll(Collection<IEntity> c){
		entities.addAll(c);
	}
	public void removeAll(Collection<IEntity> c){
		entities.removeAll(c);
	}
	
	public void update(float dt) {
		if(IS_PAUSED) return;
		CollisionHandler.check(entities);
		entities.forEachCurrent(e -> e.update(dt));
		asteroids.update(dt);
		enemies.update(dt);
	}

	public Player getActivePlayer()
	{
		return player;
	}
	
	public void draw() {
		entities.forEachCurrent(e -> e.draw(sr));
		PredefinedDisplays.IN_GAME.display.draw(sr,0,0, ScreenManager.getNormalizationScalarX(),ScreenManager.getNormalizationScalarY());
	}
	
	public void dispose() {}

	
}









