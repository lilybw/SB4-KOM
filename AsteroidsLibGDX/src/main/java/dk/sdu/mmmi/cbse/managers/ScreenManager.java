package dk.sdu.mmmi.cbse.managers;

import dk.sdu.mmmi.cbse.util.ManagedBufferedCollection;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.util.HashSet;

public class ScreenManager {

    private static final ManagedBufferedCollection<IResizable> resizeListeners = new ManagedBufferedCollection<>(new HashSet<>());

    private static final Dimension2D screenDim = Toolkit.getDefaultToolkit().getScreenSize();
    public static int WIDTH = (int) screenDim.getWidth();
    public static int HEIGHT = (int) screenDim.getHeight();

    public static void addOnResize(IResizable func)
    {
        resizeListeners.add(func);
    }
    public static void removeOnResize(IResizable obj)
    {
        resizeListeners.remove(obj);
    }

    public static float getNormalizedWidth()
    {
        return (WIDTH * WIDTH) / 3840f;
    }
    public static float getNormalizedHeight()
    {
        return (HEIGHT * HEIGHT) / 2160f;
    }

    public static float getNormalizationScalarX(){return WIDTH / 3840f;}
    public static float getNormalizationScalarY(){return HEIGHT / 2160f;}

    public void resizeAll(int newWidth, int newHeight)
    {
        resizeListeners.cycle();
        resizeListeners.forEachCurrent(e -> e.onResize(this, newWidth,newHeight));
    }

    public void onGdxResize(int width, int height) {
        resizeAll(width,height);
    }
}
