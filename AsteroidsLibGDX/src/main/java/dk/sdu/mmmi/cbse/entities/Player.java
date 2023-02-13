package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.collisions.Collider;
import dk.sdu.mmmi.cbse.collisions.Mesh;
import dk.sdu.mmmi.cbse.display.KnownHudElements;
import dk.sdu.mmmi.cbse.events.ObservableRef;
import dk.sdu.mmmi.cbse.fruity.NeonColours;
import dk.sdu.mmmi.cbse.main.Game;
import dk.sdu.mmmi.cbse.managers.AbilityManager;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.HUDManager;
import dk.sdu.mmmi.cbse.managers.ScreenManager;
import dk.sdu.mmmi.cbse.util.Touple;
import dk.sdu.mmmi.cbse.util.VMath;
import dk.sdu.mmmi.cbse.util.VoidFunc;

import java.util.ArrayList;
import java.util.List;

public class Player extends SpaceObject implements IEntity {

    private final float maxSpeed;
    private final float acceleration;
    private final float deceleration;
    private final Collider collider;
    private final List<VoidFunc<Float>> onUpdateBehaviour = new ArrayList<>();
    private final AbilityManager<Boolean,Float> shootOnSpace;
    private final AbilityManager<Boolean, Touple<Boolean,Float>> dashOnShift;
    private final ObservableRef<Float> health = new ObservableRef<>(200f);

    private int Iframes = 0;

    public Player() {
        HUDManager.addObservable(KnownHudElements.PlayerHealth,health);

        x = ScreenManager.WIDTH / 4f;
        y = ScreenManager.HEIGHT / 4f;

        maxSpeed = 300;
        acceleration = 200;
        deceleration = 10;

        shapex = new float[4];
        shapey = new float[4];

        radians = 3.1415f / 2;
        rotationSpeed = 3;

        collider = new Collider(shapex,shapey, Collider.POLYGON);

        onUpdateBehaviour.addAll(List.of(
                this::basicMovementHandling,
                e -> updateShape(),
                e -> wrap(),
                e -> collider.update(shapex,shapey),
                e -> Iframes--
        ));

        shootOnSpace = new AbilityManager<>(
                5f,
                (trackedVariable, correctKeyDown) -> {
                    trackedVariable.set(trackedVariable.get() - 1);
                    return correctKeyDown && trackedVariable.get() <= 0;
                },
                (trackedVariable,deltaT) -> {
                    final double mag = VMath.mag(velocityX,velocityY);
                    new Bullet(shapex[0],shapey[0],(float) Math.cos(radians), (float) Math.sin(radians),this)
                            .setSpeed(100f + (float) (Math.abs(velocityX) + Math.abs(velocityY)) / 2f)
                            .setSize(2 * Game.scaleX())
                            .spawn();
                    trackedVariable.set(5f);
                }
        );

        dashOnShift = new AbilityManager<>(
                //"dash" "ongoing" and "time"
                new Touple<>(false,3f),
                (trackedVariable, correctKeyDown) -> {
                    if(trackedVariable.get().r < 10 ){
                        trackedVariable.get().r += .2f;
                    }
                    return (correctKeyDown && trackedVariable.get().r >= 3f) || trackedVariable.get().t;
                },
                (trackedVariable, deltaT) -> {
                    if(trackedVariable.get().r <= 0f){
                        velocityY /= 5f;
                        velocityX /= 5f;
                        trackedVariable.get().t = false;

                    }else if(trackedVariable.get().r >= 3f || trackedVariable.get().t) {
                        Iframes += 4;
                        trackedVariable.get().t = true;
                        trackedVariable.get().r = trackedVariable.get().r - 1;
                    }

                    x += MathUtils.cos(radians) * maxSpeed * 5 * deltaT;
                    y += MathUtils.sin(radians) * maxSpeed * 5 * deltaT;
                }
        );
        System.out.println(this);
    }



    @Override
    public boolean isInBounds(float x, float y)
    {
        return collider.isInBounds(x,y) == 1;
    }

    @Override
    public void ifInBounds(IEntity collidingEntity) {
        if(collidingEntity instanceof Bullet){
            if(((Bullet) collidingEntity).getSource() != this){
                health.set(health.get() - collidingEntity.getMass() * collidingEntity.getMomentum());
            }
        }else if(collidingEntity instanceof Enemy){
            health.set(health.get() - collidingEntity.getMass() * collidingEntity.getMomentum());
        }else if(collidingEntity instanceof Asteroid){
            health.set(health.get() - collidingEntity.getMass() * collidingEntity.getMomentum());
        }
    }

    @Override
    public void update(float dt) {

        for(VoidFunc<Float> behaviour : onUpdateBehaviour){
            behaviour.apply(dt);
        }

        shootOnSpace.evaluate(GameKeys.isDown(GameKeys.SPACE),dt);
        dashOnShift.evaluate(GameKeys.isDown(GameKeys.SHIFT),dt);
    }

    @Override
    public void draw(ShapeRenderer sr) {

        sr.setColor(NeonColours.PRIMARY[0], NeonColours.PRIMARY[1],NeonColours.PRIMARY[2], 1);

        sr.begin(ShapeType.Line);

        for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {
            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
        }

        sr.end();
    }

    public Player spawn(){
        Game.getInstance().getState().addEntity(this);
        return this;
    }
    public Player destroy(){
        Game.getInstance().getState().removeEntity(this);
        return this;
    }


    private void updateShape() {
        shapex[0] = x + (MathUtils.cos(radians) * 8);
        shapey[0] = y + (MathUtils.sin(radians) * 8);

        shapex[1] = x + (MathUtils.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = y + (MathUtils.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = x + (MathUtils.cos(radians + 3.1415f) * 5);
        shapey[2] = y + (MathUtils.sin(radians + 3.1415f) * 5);

        shapex[3] = x + (MathUtils.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = y + (MathUtils.sin(radians + 4 * 3.1415f / 5) * 8);

        for(int i = 0; i < shapex.length; i++){
            shapex[i] *= Game.scaleX();
            shapey[i] *= Game.scaleY();
        }
    }


    private void basicMovementHandling(float deltaT)
    {
        //Handling rotation

        if (GameKeys.isDown(GameKeys.LEFT)) {
            radians += rotationSpeed * deltaT;
        } else if (GameKeys.isDown(GameKeys.RIGHT)) {
            radians -= rotationSpeed * deltaT;
        }
        // accelerating
        if (GameKeys.isDown(GameKeys.UP)) {
            velocityX += MathUtils.cos(radians) * acceleration * deltaT;
            velocityY += MathUtils.sin(radians) * acceleration * deltaT;
        }

        if (GameKeys.isDown(GameKeys.DOWN)){
            velocityX -= MathUtils.cos(radians) * acceleration * deltaT;
            velocityY -= MathUtils.sin(radians) * acceleration * deltaT;
        }

        // deceleration
        float velocityMagnitude = (float) Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        if (velocityMagnitude > 0) {
            velocityX -= (velocityX / velocityMagnitude) * deceleration * deltaT;
            velocityY -= (velocityY / velocityMagnitude) * deceleration * deltaT;
        }
        if (velocityMagnitude > maxSpeed) {
            velocityX = (velocityX / velocityMagnitude) * maxSpeed;
            velocityY = (velocityY / velocityMagnitude) * maxSpeed;
        }

        // set position
        x += velocityX * deltaT;
        y += velocityY * deltaT;
    }

    @Override
    public float getMass()
    {
        return 200f;
    }

    @Override
    public float getMomentum()
    {
        return VMath.mag(velocityX, velocityY);
    }

    @Override
    public Mesh.Point getCenterOfMass()
    {
        //TODO: Have any polygonal collider calculate a specific center of mesh
        return collider.verts()[0];
    }

    public float getCurrentHealth()
    {
        return health.get();
    }


    @Override
    public String toString()
    {
        return "Player{position: ["+x+","+y+"]}";
    }
}
