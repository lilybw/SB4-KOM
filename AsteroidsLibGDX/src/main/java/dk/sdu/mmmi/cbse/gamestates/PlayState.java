package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.entities.IEntity;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.entities.SpaceObject;
import dk.sdu.mmmi.cbse.managers.GameStateManager;
import dk.sdu.mmmi.cbse.util.ManagedBufferedCollection;

import java.util.Collection;
import java.util.HashSet;

public class PlayState extends GameState {

	public static boolean IS_PAUSED = false;

	private ShapeRenderer sr;
	private SpaceObject currentFocalPoint;

	private final ManagedBufferedCollection<IEntity> entities = new ManagedBufferedCollection<>(new HashSet<>());

	public PlayState(GameStateManager gsm) {
		super(gsm);
		currentFocalPoint = new Player();
		entities.add((Player) currentFocalPoint);
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
		handleInput();
		entities.forEachCurrent(e -> e.update(dt));
	}
	
	public void draw() {
		entities.forEachCurrent(e -> e.draw(sr));
	}
	
	public void handleInput() {
	}
	
	public void dispose() {}

	
}









