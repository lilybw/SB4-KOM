package dk.sdu.mmmi.cbse.util;

import com.badlogic.gdx.math.MathUtils;

public class VMath {


    public static double signedArea(float p0x, float p0y, float p1x, float p1y, float p2x,float p2y)
    {
        return 0.5 * (-1 * p1y * p2x + p0y * (-1 * p1x + p2x) + p0x * (p1y - p2y) + p1x * p2y);
    }

    public static float mag(float vX, float vY)
    {
        return (float) Math.sqrt(vX*vX+vY*vY);
    }

    public static float[] normalize(float vX, float vY)
    {
        return normalize(vX, vY, mag(vX, vY));
    }

    public static float[] normalize(float vX, float vY, float mag)
    {
        return new float[]{vX / mag, vY / mag};
    }

    public static float dist(float aX, float aY, float bX, float bY)
    {
        return mag(aX-bX,aY-bY);
    }
}
