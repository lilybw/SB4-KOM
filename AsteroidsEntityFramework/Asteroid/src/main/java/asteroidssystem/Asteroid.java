package asteroidssystem;

import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.common.data.Entity;

public class Asteroid extends Entity {

    public Asteroid()
    {
        super(12);
        super.setRadius(MathUtils.random(50,200));
    }


}
