package dk.sdu.mmmi.cbse.ai;

import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.collisions.Mesh;
import dk.sdu.mmmi.cbse.entities.Bullet;
import dk.sdu.mmmi.cbse.entities.Enemy;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.main.Game;
import dk.sdu.mmmi.cbse.managers.ScreenManager;
import dk.sdu.mmmi.cbse.util.VMath;

import java.util.ArrayList;
import java.util.List;

public class Movesets {

    public static List<Program<?, Player>> ENEMY(ProgramScheduler<Player> scheduler, Enemy enemy)
    {
        return new ArrayList<>(List.of(
                new Program<>(
                        scheduler, 2f,"fly to middle of screen",
                        (player, previousContext) -> {
                            if(previousContext == null){
                                previousContext = new Mesh.Point(
                                        (ScreenManager.WIDTH / 4f - enemy.getX()) / (20f),
                                        (ScreenManager.HEIGHT / 4f - enemy.getY()) / (20f)
                                );
                            }
                            return previousContext;
                        },
                        (Mesh.Point context, int timesRan, float deltaT) -> {
                            if(timesRan != 20){
                                enemy.setVelocityX(context.x);
                                enemy.setVelocityY(context.y);
                                enemy.alignToCurrentDirection();
                                return false;
                            }else{
                                enemy.setVelocityX(0);
                                enemy.setVelocityY(0);
                            }
                            return true;
                        }
                ),
                new Program<>(scheduler,1f,"fly to random location",
                        (player,previousContext) -> {
                            return new Mesh.Point(
                                    Game.rand.nextFloat() * ScreenManager.WIDTH * .25f,
                                    Game.rand.nextFloat() * ScreenManager.HEIGHT * .25f
                            );
                        },
                        (Mesh.Point context, int timesRan, float deltaT) -> {
                            if(timesRan != 20){
                                enemy.setVelocityX(context.x);
                                enemy.setVelocityY(context.y);
                                enemy.alignToCurrentDirection();
                                return false;
                            }else{
                                enemy.setVelocityX(0);
                                enemy.setVelocityY(0);
                            }
                            return true;
                        }
                ),
                new Program<>(
                        scheduler,2f,"shoot in a circle",
                        (player, previusContext) -> 0,
                        (Integer context, int timesRan, float deltaT) -> {
                            if(timesRan != 360){
                                enemy.setRadians(enemy.getRadians() + MathUtils.PI2 / 360);
                                new Bullet(enemy.getCenterOfMass().x,enemy.getCenterOfMass().y,(float) Math.cos(enemy.getRadians()), (float) Math.sin(enemy.getRadians()),enemy)
                                        .setSpeed(100f)
                                        .setSize(2 * Game.scaleX())
                                        .spawn();
                                return false;
                            }else{
                                return true;
                            }
                        }
                ),
                new Program<>(scheduler,2f,"warp to around player",
                        (player,previousContext) -> {
                            final float offsetScalar = Game.rand.nextFloat() * (ScreenManager.WIDTH / 20f);
                            final float xOff = (Game.rand.nextFloat() + .2f) * offsetScalar + player.getX(),
                                    yOff = (Game.rand.nextFloat() + .2f) * offsetScalar + player.getY();
                            return new Mesh.Point(xOff,yOff);
                        },
                        (Mesh.Point context, int timesRan, float deltaT) -> {
                            enemy.setX(context.x);
                            enemy.setY(context.y);
                            return true;
                        }
                ),
                new Program<>(scheduler, 1f, "shoot quintuple-shots at player",
                        (player, previousContext) -> {
                            final float[] towardsPlayer = VMath.normalize(
                                    player.getX() - enemy.getX(),player.getY() - enemy.getY()
                            );
                            final float[] towardsPlayer1 = VMath.normalize(
                                    (player.getX() - enemy.getX()) + 10,(player.getY() - enemy.getY()) + 10
                            );
                            final float[] towardsPlayer2 = VMath.normalize(
                                    (player.getX() - enemy.getX()) + 20,(player.getY() - enemy.getY()) + 20
                            );
                            final float[] towardsPlayer3 = VMath.normalize(
                                    (player.getX() - enemy.getX()) - 10,(player.getY() - enemy.getY()) - 10
                            );
                            final float[] towardsPlayer4 = VMath.normalize(
                                    (player.getX() - enemy.getX()) - 20,(player.getY() - enemy.getY()) - 20
                            );
                            enemy.pointAt(player);

                            return new Mesh.Point[]{
                                    new Mesh.Point(towardsPlayer[0],towardsPlayer[1]),
                                    new Mesh.Point(towardsPlayer1[0],towardsPlayer1[1]),
                                    new Mesh.Point(towardsPlayer2[0],towardsPlayer2[1]),
                                    new Mesh.Point(towardsPlayer3[0],towardsPlayer3[1]),
                                    new Mesh.Point(towardsPlayer4[0],towardsPlayer4[1])
                            };
                        },
                        (Mesh.Point[] directions, int timesRan, float deltaT) -> {
                            if (timesRan != 20 && timesRan % 5 == 0) {
                                for(Mesh.Point direction : directions){
                                    new Bullet(enemy.getCenterOfMass().x,enemy.getCenterOfMass().y,direction.x,direction.y,enemy)
                                            .setSpeed(100f)
                                            .setSize(2 * Game.scaleX())
                                            .spawn();
                                }
                                return false;
                            } else return timesRan > 20;
                        }
                )
        ));
    }

}
