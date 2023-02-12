package dk.sdu.mmmi.cbse.util;

public class VMath {


    public static double signedArea(float p0x, float p0y, float p1x, float p1y, float p2x,float p2y)
    {
        return 0.5 * (-1 * p1y * p2x + p0y * (-1 * p1x + p2x) + p0x * (p1y - p2y) + p1x * p2y);
    }
}
