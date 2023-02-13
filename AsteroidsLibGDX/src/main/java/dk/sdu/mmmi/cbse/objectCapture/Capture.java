package dk.sdu.mmmi.cbse.objectCapture;

import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.entities.SpaceObject;

public class Capture {

    public static class PlayerCapture{
        public float health;
        public SpaceObjectCapture soc;
        public PlayerCapture(float health, SpaceObjectCapture soc)
        {
            this.health = health;
            this.soc = soc;
        }
    }

    public static PlayerCapture getCaptureOfPlayer(Player player, PlayerCapture previous)
    {
        if(previous == null){
            previous = new PlayerCapture(player.getCurrentHealth(),getCaptureOfSpaceObject(player,null));
        }else{
            previous.health = player.getCurrentHealth();
            previous.soc = getCaptureOfSpaceObject(player,previous.soc);
        }
        return previous;
    }

    public static class SpaceObjectCapture{
        public float x, y, velX, velY, radians, speed;
        public SpaceObjectCapture(float x, float y, float velX, float velY, float radians, float speed)
        {
            this.x = x;
            this.y = y;
            this.velX = velX;
            this.velY = velY;
            this.radians = radians;
            this.speed = speed;
        }
    }


    public static SpaceObjectCapture getCaptureOfSpaceObject(SpaceObject so, SpaceObjectCapture previous)
    {
        if(previous == null){
            previous = new SpaceObjectCapture(so.getX(),so.getY(),so.getVelX(),so.getVelY(),so.getRadians(),so.getSpeed());
        }else{
            previous.radians = so.getRadians();
            previous.velX = so.getVelX();
            previous.velY = so.getVelY();
            previous.x = so.getX();
            previous.y = so.getY();
            previous.speed = so.getSpeed();
        }
        return previous;
    }
}
