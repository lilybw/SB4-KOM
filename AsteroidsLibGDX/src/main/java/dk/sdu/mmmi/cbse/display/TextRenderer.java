package dk.sdu.mmmi.cbse.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.util.VoidFunc;

import java.util.*;

public class TextRenderer {

    @FunctionalInterface
    public interface DrawCommand{
        void draw(ShapeRenderer sr, float offX, float offY, float scaleX, float scaleY);
    }

    /**
     * All letters steps considers the letter as in a drawing range of 0 to 1 on the X and Y axis.
     * I.e. they're monospace
     */
    public enum Letters{

        A('A',List.of(
                (sr,offX,offY,scaleX,scaleY) -> {
                    sr.line(0f + offX,0f + offY,(.5f * scaleX) + offX,(1f * scaleY) + offY); // "/"
                    sr.line(0f + offX, (.5f * scaleY) + offY,(1f * scaleX) + offX, (.5f * scaleY) + offY); // "-"
                    sr.line((1f * scaleX) + offX, 0f + offY, .5f * scaleX + offX, 1f * scaleY + offY); // "\"
                }
        )),
        B('B',List.of(
                (sr,offX,offY,scaleX,scaleY) -> {
                    sr.line((.1f * scaleX) + offX, 0f + offY, (.1f *  scaleX) + offX, (1f * scaleY) + offY); // "| "
                    sr.line(0f + offX, 0f + offY, (1f * scaleX) + offX, 0f + offY); // "_"
                    sr.line(0f + offX, (.5f * scaleY) + offY, (1f * scaleX) + offX, (.5f * scaleY) + offY); // "-"
                    sr.line(0f + offX, scaleY + offY, (1f * scaleX) + offX, scaleY + offY); // "^"
                    sr.line((1f * scaleX) + offX, 0f + offY, (1f *  scaleX) + offX, (1f * scaleY) + offY); // " |"
                }
        )),
        C('C',List.of(
                (sr,offX,offY,scaleX,scaleY) -> {
                    sr.line((.5f * scaleX) + offX, 0f + offY, scaleX + offX, 0f + offY);
                    sr.line((.5f * scaleX) + offX, scaleY + offY, scaleX + offX, scaleY + offY);
                    sr.arc((.5f * scaleX) + offX,(.5f * scaleY) + offY, .5f * scaleX);
                }
        )),
        D('D',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        E('E',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        F('F',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        G('G',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        H('H',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        I('I',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        J('J',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        K('K',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        L('L',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        M('M',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        N('N',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        O('O',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        P('P',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        Q('Q',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        R('R',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        S('S',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        T('T',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        U('U',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        V('V',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        X('X',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        Y('Y',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        Z('Z',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        ));

        public final Collection<DrawCommand> steps;
        public final char character;
        Letters(char character, Collection<DrawCommand> steps){
            this.steps = steps;
            this.character = character;
        }

    }

    private static final Map<Character,Collection<DrawCommand>> letterCommandsMap = new HashMap<>();
    static{
        for(Letters letter : Letters.values()){
            letterCommandsMap.put(letter.character,letter.steps);
        }
    }


    public static void write(ShapeRenderer sr, String text, float offX, float offY, float scaleX, float scaleY, Color color)
    {
        float cursor = 0;
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(color);
        for(byte b : text.getBytes())
        {
            final float tempCursor = cursor;
            letterCommandsMap.get((char) b).forEach(
                    step -> step.draw(sr,offX + tempCursor * scaleX,offY,scaleX,scaleY)
            );
            cursor++;
        }
        sr.end();
    }
}
