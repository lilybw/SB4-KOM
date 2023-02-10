package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.entities.IEntity;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.managers.GameStateManager;
import dk.sdu.mmmi.cbse.util.ManagedBufferedCollection;

import java.util.HashSet;

public class PlayState extends GameState {
	
	private ShapeRenderer sr;

	private final ManagedBufferedCollection<IEntity> entities = new ManagedBufferedCollection<>(new HashSet<>());

	public PlayState(GameStateManager gsm) {
		super(gsm);
		entities.add(new Player());
	}
	
	public void init() {
		sr = new ShapeRenderer();
	}
	
	public void update(float dt) {
		handleInput();
		entities.cycle();
		entities.forEachCurrent(e -> e.update(dt));
	}
	
	public void draw() {
		entities.forEachCurrent(e -> e.draw(sr));
	}
	
	public void handleInput() {
	}
	
	public void dispose() {}

	
}









