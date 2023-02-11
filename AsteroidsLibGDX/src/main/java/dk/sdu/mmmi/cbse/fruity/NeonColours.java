package dk.sdu.mmmi.cbse.fruity;

import dk.sdu.mmmi.cbse.main.Game;

public class NeonColours {

    public static float[] PRIMARY, SECONDARY, TERTIARY;
    static {
        PRIMARY = new float[]{1,1,1,1};
        SECONDARY = new float[]{1,1,1,1};
        TERTIARY = new float[]{1,1,1,1};
    }

    public static float[] hueShift(float[] original, float shiftX, float shiftY, float shiftZ)
    {
        float[] shifted = new float[]{
                original[0] + shiftX,
                original[1] + shiftY,
                original[2] + shiftZ
        };
        final double mag = Math.cbrt(
                shifted[0] * shifted[0] +
                        shifted[1] * shifted[1] +
                        shifted[2] * shifted[2]
        );
        shifted[0] /= mag;
        shifted[1] /= mag;
        shifted[2] /= mag;
        return shifted;
    }

    private final float generalShift;

    public NeonColours(float shift){
        this.generalShift = shift;
    }

    public void update(long tickCount, float scalar){
        PRIMARY[0] = mapSinTo01( (float) Math.sin((tickCount / scalar) + generalShift));
        PRIMARY[1] = mapSinTo01( (float) Math.sin((tickCount / scalar) + 45 + generalShift));
        PRIMARY[2] = mapSinTo01( (float) Math.sin((tickCount / scalar) + 90 + generalShift));

        SECONDARY[0] = mapSinTo01( (float) Math.cos(tickCount / scalar) + generalShift);
        SECONDARY[1] = mapSinTo01( (float) Math.cos((tickCount / scalar) + 45) + generalShift);
        SECONDARY[2] = mapSinTo01( (float) Math.cos((tickCount / scalar) + 90) + generalShift);

        TERTIARY[0] = mapSinTo01( (float) Math.sin((tickCount / scalar) + 45) + generalShift);
        TERTIARY[1] = mapSinTo01( (float) Math.sin((tickCount / scalar) + 90) + generalShift);
        TERTIARY[2] = mapSinTo01( (float) Math.sin((tickCount / scalar) + 135) + generalShift);
    }

    private float mapSinTo01(float val)
    {
        return (val / 2) + 1;
    }

}
