package dk.sdu.mmmi.cbse.collisions;

import dk.sdu.mmmi.cbse.util.ArrayUtil;
import dk.sdu.mmmi.cbse.util.Ref;

public record Mesh(Point[] verts, int numVerts, Ref<Float> radius, Ref<Float> minX, Ref<Float> minY, Ref<Float> maxX, Ref<Float> maxY, Ref<Boolean> isValid) {

    public void refresh(float x, float y, float radius)
    {
        this.verts[0].x = x;
        this.verts[0].y = y;
        this.radius.set(radius);
        this.isValid().set(true);
    }

    public void refresh(float[] vertXs, float[] vertYs) {
        for(int i = 0; i < this.verts().length; i++) {
            this.verts()[i].x = vertXs[i];
            this.verts()[i].y = vertYs[i];
        }
        float[] greatest = ArrayUtil.greatest(this.verts);
        float[] leastMost = ArrayUtil.leastMost(this.verts);
        this.minX().set(leastMost[0]);
        this.minY().set(leastMost[1]);
        this.maxX().set(greatest[0]);
        this.maxY().set(greatest[1]);
        this.isValid().set(true);
    }

    @Override
    public String toString()
    {
        return "Mesh{vertCount:" + numVerts() + ", verts: " + ArrayUtil.toString(verts()) + ", radius: " + radius() + "}";
    }

    public static class Point {
        public float x, y;
        public Point(float x, float y)
        {
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString()
        {
            return "Mesh.Point{position: ["+x+","+y+"]}";
        }
    }

    public static class Line {
        public Point p1, p2;
        public Line(Point p1, Point p2)
        {
            this.p1 = p1;
            this.p2 = p2;
        }
    }

}
