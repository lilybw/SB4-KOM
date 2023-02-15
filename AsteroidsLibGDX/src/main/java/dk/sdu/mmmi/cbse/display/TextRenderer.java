package dk.sdu.mmmi.cbse.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.util.VoidFunc;

import java.util.*;

public class TextRenderer {

    @FunctionalInterface
    public interface DrawCommand{
        void draw(ShapeRenderer sr, float offX, float offY, float charWidth, float charHeight);
    }

    public enum LetterParts{
        VERT_LINE_START(
                (sr,offX,offY,scaleX,scaleY) -> {
                    sr.line(offX, 0f + offY, offX, scaleY + offY);
                }
        ),
        VERT_LINE_MIDDLE(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(.5f * charWidth + offX, 0f + offY, .5f * charWidth + offX, charHeight + offY);
                }
        ),
        VERT_LINE_END(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(charWidth + offX, 0f + offY, charWidth + offX, charHeight + offY);
                }
        ),
        HALF_VERT_LINE_END_BOTTOM(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(charWidth + offX, 0f + offY, charWidth + offX, .5f * charHeight + offY);
                }
        ),
        HALF_VERT_LINE_END_TOP(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(charWidth + offX, .5f * charHeight + offY, charWidth + offX,  charHeight + offY);
                }
        ),
        HALF_VERT_LINE_START_TOP(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(offX, .5f * charHeight + offY, offX,  charHeight + offY);
                }
        ),
        HALF_VERT_LINE_START_BOTTOM(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(offX, .5f * charHeight + offY, offX,  offY);
                }
        ),
        HORI_LINE_MIDDLE(
                (sr,offX,offY,scaleX,scaleY) -> {
                    sr.line(offX, (.5f * scaleY) + offY, scaleX + offX, (.5f * scaleY) + offY);
                }
        ),
        HORI_LINE_BOTTOM(
                (sr,offX,offY,scaleX,scaleY) -> {
                    sr.line(offX, offY, scaleX + offX, offY);
                }
        ),
        HORI_LINE_TOP(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(offX, charHeight + offY, charWidth + offX, charHeight + offY);
                }
        ),
        SLASH_RIGHT(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(0f + offX,0f + offY, charWidth + offX,charHeight + offY); // "/"
                }
        ),
        SLASH_LEFT(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(charWidth + offX, 0f + offY, charWidth + offX, charHeight + offY); // "\"
                }
        ),
        HALF_SLASH_RIGHT(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(0f + offX,0f + offY,(.5f * charWidth) + offX,charHeight + offY); // "/"
                }
        ),
        HALF_SLASH_LEFT(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(charWidth + offX, 0f + offY, .5f * charWidth + offX, charHeight + offY); // "\"
                }
        ),
        HALF_DASH_RIGHT_BOTTOM(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(.5f * charWidth + offX, offY,charWidth + offX, offY);
                }
        ),
        HALF_DASH_RIGHT_TOP(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(.5f * charWidth + offX, charHeight + offY,charWidth + offX,charHeight + offY);
                }
        ),
        HALF_DASH_RIGHT_MIDDLE(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(.5f * charWidth + offX, .5f * charHeight + offY,charWidth + offX,.5f * charHeight + offY);
                }
        ),
        HALF_ARC_LEFT(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.arc((.5f * charWidth) + offX,(.5f * charHeight) + offY, .5f * charWidth,90,180);
                }
        ),
        ARC_LEFT(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.arc(charWidth + offX,(.5f * charHeight) + offY, charWidth,90,180);
                }
        ),
        ARC_RIGHT(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.arc(offX,(.5f * charHeight) + offY, charWidth,180,90);
                }
        ),
        ARC_TOP(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.arc(.5f * charWidth + offX, charHeight + offY, charWidth,45,235);
                }
        ),
        CIRCLE(
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.arc(.5f * charWidth + offX, .5f * charHeight + offY, .5f * charWidth,0,360);
                }
        );
        public final DrawCommand step;
        LetterParts(DrawCommand step){
            this.step = step;
        }
    }

    /**
     * All letters steps considers the letter as in a drawing range of 0 to 1 on the X and Y axis.
     * I.e. they're monospace
     */
    public enum Letters{
        SPACE(' ',List.of()),
        A('A',List.of(
                LetterParts.HALF_SLASH_RIGHT.step,
                LetterParts.HORI_LINE_MIDDLE.step,
                LetterParts.HALF_SLASH_LEFT.step
        )),
        B('B',List.of(
                (sr,offX,offY,scaleX,scaleY) -> {
                    sr.line((.1f * scaleX) + offX, 0f + offY, (.1f *  scaleX) + offX, (1f * scaleY) + offY); // "| "
                },
                LetterParts.HORI_LINE_MIDDLE.step,
                LetterParts.HORI_LINE_BOTTOM.step,
                LetterParts.HORI_LINE_TOP.step
        )),
        C('C',List.of(
                LetterParts.HALF_DASH_RIGHT_BOTTOM.step,
                LetterParts.HALF_DASH_RIGHT_TOP.step,
                LetterParts.ARC_LEFT.step
        )),
        D('D',List.of(
                LetterParts.ARC_RIGHT.step,
                LetterParts.VERT_LINE_START.step
        )),
        E('E',List.of(
                LetterParts.VERT_LINE_START.step,
                LetterParts.HORI_LINE_MIDDLE.step,
                LetterParts.HORI_LINE_BOTTOM.step,
                LetterParts.HORI_LINE_TOP.step
        )),
        F('F',List.of(
                LetterParts.VERT_LINE_START.step,
                LetterParts.HORI_LINE_MIDDLE.step,
                LetterParts.HORI_LINE_TOP.step
        )),
        G('G',List.of(
                LetterParts.ARC_LEFT.step,
                LetterParts.HALF_DASH_RIGHT_MIDDLE.step,
                LetterParts.HALF_VERT_LINE_END_BOTTOM.step
        )),
        H('H',List.of(
                LetterParts.VERT_LINE_START.step,
                LetterParts.HORI_LINE_MIDDLE.step,
                LetterParts.VERT_LINE_END.step
        )),
        I('I',List.of(
                LetterParts.HORI_LINE_BOTTOM.step,
                LetterParts.HORI_LINE_TOP.step,
                LetterParts.VERT_LINE_MIDDLE.step
        )),
        J('J',List.of(
                LetterParts.ARC_TOP.step,
                LetterParts.HALF_VERT_LINE_END_TOP.step,
                LetterParts.HALF_DASH_RIGHT_TOP.step
        )),
        K('K',List.of(
                LetterParts.VERT_LINE_START.step,
                LetterParts.ARC_RIGHT.step
        )),
        L('L',List.of(
                LetterParts.VERT_LINE_START.step,
                LetterParts.HORI_LINE_BOTTOM.step
        )),
        M('M',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        N('N',List.of(
                LetterParts.VERT_LINE_START.step,
                LetterParts.SLASH_RIGHT.step,
                LetterParts.VERT_LINE_END.step
        )),
        O('O',List.of(
                LetterParts.CIRCLE.step
        )),
        P('P',List.of(
                LetterParts.VERT_LINE_START.step,
                LetterParts.HALF_DASH_RIGHT_MIDDLE.step,
                LetterParts.HALF_DASH_RIGHT_TOP.step,
                LetterParts.HALF_VERT_LINE_END_TOP.step
        )),
        Q('Q',List.of(
                LetterParts.CIRCLE.step,
                (sr,offX,offY,charWidth,charHeight) -> {
                    sr.line(.5f * charWidth + offX, .5f * charHeight + offY, charWidth + offX, offY);
                }
        )),
        R('R',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        S('S',List.of(
                LetterParts.HORI_LINE_BOTTOM.step,
                LetterParts.HORI_LINE_MIDDLE.step,
                LetterParts.HORI_LINE_TOP.step,
                LetterParts.HALF_VERT_LINE_END_BOTTOM.step,
                LetterParts.HALF_VERT_LINE_START_TOP.step
        )),
        T('T',List.of(
                LetterParts.VERT_LINE_MIDDLE.step,
                LetterParts.HORI_LINE_TOP.step
        )),
        U('U',List.of(
                LetterParts.ARC_TOP.step
        )),
        V('V',List.of(
                LetterParts.HALF_SLASH_LEFT.step,
                LetterParts.HALF_SLASH_RIGHT.step
        )),
        X('X',List.of(
                LetterParts.SLASH_LEFT.step,
                LetterParts.SLASH_RIGHT.step
        )),
        Y('Y',List.of(
                (sr,offX,offY,scaleX,scalyY) -> {

                }
        )),
        Z('Z',List.of(
                LetterParts.SLASH_LEFT.step,
                LetterParts.HORI_LINE_TOP.step,
                LetterParts.HORI_LINE_BOTTOM.step
        )),
        ZERO('0',List.of(
                LetterParts.CIRCLE.step
        )),
        ONE('1',List.of(
                LetterParts.VERT_LINE_MIDDLE.step
        )),
        TWO('2',List.of(
                LetterParts.HORI_LINE_TOP.step,
                LetterParts.HALF_VERT_LINE_END_TOP.step,
                LetterParts.HORI_LINE_MIDDLE.step,
                LetterParts.HALF_VERT_LINE_START_BOTTOM.step,
                LetterParts.HORI_LINE_BOTTOM.step
        )),
        THREE('3',List.of(
                LetterParts.HORI_LINE_TOP.step,
                LetterParts.HORI_LINE_MIDDLE.step,
                LetterParts.HORI_LINE_BOTTOM.step,
                LetterParts.VERT_LINE_END.step
        )),
        FOUR('4',List.of(
                LetterParts.HALF_VERT_LINE_START_TOP.step,
                LetterParts.HORI_LINE_MIDDLE.step,
                LetterParts.VERT_LINE_END.step
        )),
        FIVE('5',List.of(
                LetterParts.HORI_LINE_BOTTOM.step,
                LetterParts.HORI_LINE_MIDDLE.step,
                LetterParts.HORI_LINE_TOP.step,
                LetterParts.HALF_VERT_LINE_END_BOTTOM.step,
                LetterParts.HALF_VERT_LINE_START_TOP.step
        )),
        SIX('6',List.of(
                LetterParts.HORI_LINE_TOP.step,
                LetterParts.HORI_LINE_BOTTOM.step,
                LetterParts.HORI_LINE_MIDDLE.step,
                LetterParts.HALF_VERT_LINE_END_BOTTOM.step,
                LetterParts.VERT_LINE_START.step
        )),
        SEVEN('7',List.of(
                LetterParts.HORI_LINE_TOP.step,
                LetterParts.SLASH_RIGHT.step
        )),
        EIGHT('8',List.of(
                LetterParts.HORI_LINE_TOP.step,
                LetterParts.HORI_LINE_BOTTOM.step,
                LetterParts.HORI_LINE_MIDDLE.step,
                LetterParts.VERT_LINE_START.step,
                LetterParts.VERT_LINE_END.step
        )),
        NINE('9',List.of(
                LetterParts.HORI_LINE_TOP.step,
                LetterParts.HORI_LINE_BOTTOM.step,
                LetterParts.HORI_LINE_MIDDLE.step,
                LetterParts.VERT_LINE_END.step,
                LetterParts.HALF_VERT_LINE_START_TOP.step
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

    public static void write(ShapeRenderer sr, String text, float offX, float offY, float charWidth, float charHeight, float perCharacterSpacing, Color color)
    {
        float cursor = 0;
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(color);
        for(byte b : text.getBytes())
        {
            final float tempCursor = cursor + cursor * perCharacterSpacing * charWidth;
            letterCommandsMap.get((char) b).forEach(
                    step -> step.draw(sr,offX + tempCursor * charWidth,offY,charWidth,charHeight)
            );
            cursor++;
        }
        sr.end();
    }

    public static void write(ShapeRenderer sr, String text, float offX, float offY, float scaleX, float scaleY, Color color)
    {
        write(sr,text,offX,offY,scaleX,scaleY,0f,color);
    }
}
