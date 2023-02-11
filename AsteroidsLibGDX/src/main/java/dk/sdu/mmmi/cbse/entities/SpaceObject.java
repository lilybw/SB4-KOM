package dk.sdu.mmmi.cbse.entities;

import dk.sdu.mmmi.cbse.managers.ScreenManager;

public abstract class SpaceObject {
	
	protected float x;
	protected float y;
	
	protected float velocityX;
	protected float velocityY;
	
	protected float radians;
	protected float speed;
	protected float rotationSpeed;
	
	protected int width;
	protected int height;
	
	protected float[] shapex;
	protected float[] shapey;
	
	protected void wrap() {
		if(x < 0) x = ScreenManager.WIDTH;
		if(x > ScreenManager.WIDTH) x = 0;
		if(y < 0) y = ScreenManager.HEIGHT;
		if(y > ScreenManager.HEIGHT) y = 0;
	}
	
}


















