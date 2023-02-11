package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.fruity.NeonColours;
import dk.sdu.mmmi.cbse.main.Game;
import dk.sdu.mmmi.cbse.managers.ScreenManager;

public class Bullet implements IEntity, IConditionalEffect<IEntity> {

    private float hitRadius;

    private float hueShiftR, hueShiftG, hueShiftB;
    private boolean doHueShift = false;

    private float x,y,velX,velY;
    private IEntity source;
    private float speed = 1, falloff = 1;

    public Bullet(float[] xy, float[] dir, IEntity source)
    {
        this(xy[0],xy[1],dir[0],dir[1],source);
    }
    public Bullet(float x, float y, float dirX, float dirY, IEntity source){
        this.velX = dirX;
        this.velY = dirY;
        this.x = x;
        this.y = y;
        this.source = source;
        this.hitRadius = 2;
    }
    public Bullet speed(float speed){
        this.speed = speed;
        return this;
    }
    public Bullet falloff(float falloff){
        this.falloff = falloff;
        return this;
    }
    public Bullet size(float size){
        this.hitRadius = size;
        return this;
    }

    public Bullet hueShift(float r, float g, float b)
    {
        this.hueShiftR = r;
        this.hueShiftG = g;
        this.hueShiftB = b;
        this.doHueShift = true;
        return this;
    }


    @Override
    public void update(float deltaT) {
        if(!isWithinBoundaryBox()){
            Game.getInstance().getState().removeEntity(this);
        }
        x += velX * speed * deltaT;
        y += velY * speed * deltaT;
        speed *= falloff;
        speed *= falloff;
    }

    private boolean isWithinBoundaryBox()
    {
        return x > 0 && y > 0 && x < ScreenManager.WIDTH && y < ScreenManager.HEIGHT;
    }

    @Override
    public void draw(ShapeRenderer sr) {
        //thx: https://stackoverflow.com/questions/26715986/libgdx-shaperenderer-circle-drawing

        sr.begin(ShapeRenderer.ShapeType.Filled);
        float[] color = NeonColours.PRIMARY;
        if(doHueShift){
            color = NeonColours.hueShift(color, hueShiftR,hueShiftG,hueShiftB);
        }
        sr.setColor(color[0],color[1],color[2],1);
        sr.circle(x, y, hitRadius);
        sr.end();
    }

    @Override
    public boolean isInBounds(float x1, float y1) {
        return Collider.RADIAL.isInBounds(x,y,x1,y1,hitRadius) == 1;
    }

    @Override
    public void ifInBounds(IEntity collidingEntity)
    {
        if(collidingEntity == source) return;
        Game.getInstance().getState().removeEntity(this);
    }

    public Bullet spawn(){
        Game.getInstance().getState().addEntity(this);
        return this;
    }
    public Bullet destroy(){
        Game.getInstance().getState().removeEntity(this);
        return this;
    }

    @Override
    public boolean isApplicableTo(IEntity obj) {
        return obj != source;
    }
}
