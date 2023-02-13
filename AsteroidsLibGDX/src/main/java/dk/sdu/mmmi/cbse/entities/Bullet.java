package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.collisions.Collider;
import dk.sdu.mmmi.cbse.collisions.Mesh;
import dk.sdu.mmmi.cbse.fruity.NeonColours;
import dk.sdu.mmmi.cbse.main.Game;
import dk.sdu.mmmi.cbse.managers.ScreenManager;

public class Bullet extends SpaceObject implements IEntity, IConditionalEffect<IEntity> {

    private float size;

    private float hueShiftR, hueShiftG, hueShiftB;
    private boolean doHueShift = false;
    private final Collider collider;

    private IEntity source;
    private float falloff = 1;

    public Bullet(float[] xy, float[] dir, IEntity source)
    {
        this(xy[0],xy[1],dir[0],dir[1],source);
    }
    public Bullet(float x, float y, float dirX, float dirY, IEntity source){
        super.velocityX = dirX;
        super.velocityY = dirY;
        super.x = x;
        super.y = y;
        this.source = source;
        this.size = 2;
        this.collider = new Collider(x, y, size);
    }
    public IEntity getSource(){
        return source;
    }

    public Bullet setSpeed(float speed){
        super.speed = speed;
        return this;
    }
    public Bullet setFalloff(float falloff){
        this.falloff = falloff;
        return this;
    }
    public Bullet setSize(float size){
        this.size = size;
        collider.update(size);
        return this;
    }

    public Bullet setHueShift(float r, float g, float b)
    {
        this.hueShiftR = r;
        this.hueShiftG = g;
        this.hueShiftB = b;
        this.doHueShift = true;
        return this;
    }

    @Override
    public void update(float deltaT) {
        if(Collider.RECTANGLE.isInBounds(ScreenManager.BOUNDARY,x,y) != 1){
            Game.getInstance().getState().removeEntity(this);
        }
        x += velocityX * speed * deltaT;
        y += velocityY * speed * deltaT;
        speed *= falloff;
        speed *= falloff;
        collider.update(x,y, size);
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
        sr.circle(x, y, size);
        sr.end();
    }

    @Override
    public boolean isInBounds(float x1, float y1) {
        return collider.isInBounds(x1,y1) == 1;
    }

    @Override
    public void ifInBounds(IEntity collidingEntity)
    {
        if(collidingEntity == source) return;
        destroy();
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
    public float getMass()
    {
        return 2f * size * 3.1415926f;
    }

    @Override
    public float getMomentum()
    {
        return super.speed;
    }

    @Override
    public Mesh.Point getCenterOfMass()
    {
        return collider.verts()[0];
    }

    @Override
    public boolean isApplicableTo(IEntity obj) {
        return obj != source;
    }
}
