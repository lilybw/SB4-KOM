package dk.sdu.mmmi.cbse.managers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class GameInputProcessor extends InputAdapter {
	
	public boolean keyDown(int k) {
		return setValueOf(k,true);
	}

	private boolean setValueOf(int key, boolean upOrDown)
	{
		switch(key){
			case Keys.UP, Keys.W -> GameKeys.setKey(GameKeys.UP,upOrDown);
			case Keys.LEFT, Keys.A -> GameKeys.setKey(GameKeys.LEFT,upOrDown);
			case Keys.DOWN, Keys.S -> GameKeys.setKey(GameKeys.DOWN,upOrDown);
			case Keys.RIGHT, Keys.D -> GameKeys.setKey(GameKeys.RIGHT,upOrDown);
			case Keys.ENTER -> GameKeys.setKey(GameKeys.ENTER,upOrDown);
			case Keys.ESCAPE -> GameKeys.setKey(GameKeys.ESCAPE,upOrDown);
			case Keys.SHIFT_LEFT -> GameKeys.setKey(GameKeys.SHIFT,upOrDown);
		}
		return true;
	}
	
	public boolean keyUp(int k) {
		return setValueOf(k,false);
	}
	
}








