package dk.sdu.mmmi.cbse.gamestates;

import dk.sdu.mmmi.cbse.entities.Bullet;
import dk.sdu.mmmi.cbse.entities.IEntity;
import dk.sdu.mmmi.cbse.managers.GameStateManager;

import java.util.Collection;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	protected GameState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public abstract void init();
	public abstract void update(float dt);
	public abstract void draw();
	public abstract void handleInput();
	public abstract void dispose();
	public abstract void addEntity(IEntity e);
	public abstract void removeEntity(IEntity e);
	public abstract void addAll(Collection<IEntity> c);
	public abstract void removeAll(Collection<IEntity> c);
}
