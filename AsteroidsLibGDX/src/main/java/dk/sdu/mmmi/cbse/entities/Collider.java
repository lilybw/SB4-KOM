package dk.sdu.mmmi.cbse.entities;

import dk.sdu.mmmi.cbse.util.ArrayUtil;

public class Collider {

    private float xMin,yMin,xMax,yMax;
    private Point[] polygon;
    private final int numVerts;

    public Collider(float[] vertXs, float[] vertYs)
    {
        polygon = new Point[vertXs.length];
        for(int i = 0; i < vertXs.length; i++) {
            polygon[i] = new Point(vertXs[i],vertYs[i]);
        }
        xMin = ArrayUtil.leastMost(vertXs);
        yMin = ArrayUtil.leastMost(vertYs);
        xMax = ArrayUtil.greatest(vertXs);
        yMax = ArrayUtil.greatest(vertYs);

        numVerts = vertXs.length;
    }

    public void update(float[] vertXs, float[] vertYs)
    {
        for(int i = 0; i < vertXs.length; i++) {
            polygon[i] = new Point(vertXs[i],vertYs[i]);
        }
        xMin = ArrayUtil.leastMost(vertXs);
        yMin = ArrayUtil.leastMost(vertYs);
        xMax = ArrayUtil.greatest(vertXs);
        yMax = ArrayUtil.greatest(vertYs);
    }

    /**
     * Quick assessment based on the lowest and highest values in the vert array.
     * @param p
     * @return
     */
    private boolean preflightCheck(Point p)
    {
        return p.x < xMin || p.x > xMax || p.y < yMin || p.y > yMax;
    }

    public int isInBounds(Point p)
    {
        //TODO: No. No point. Only simple type.
        if(preflightCheck(p)) return 0;
        return checkPolygonCollision(polygon, numVerts, p);
    }

    //Code graciously ripped from GeeksForGeeks below

    public static class Point {
        public float x, y;
        public Point(float x, float y)
        {
            this.x = x;
            this.y = y;
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

    static int onLine(Line l1, Point p)
    {
        // Check whether p is on the line or not
        if (p.x <= Math.max(l1.p1.x, l1.p2.x)
                && p.x <= Math.min(l1.p1.x, l1.p2.x)
                && (p.y <= Math.max(l1.p1.y, l1.p2.y)
                && p.y <= Math.min(l1.p1.y, l1.p2.y)))
            return 1;

        return 0;
    }

    static int direction(Point a, Point b, Point c)
    {
        int val = (int) ((b.y - a.y) * (c.x - b.x)
                - (b.x - a.x) * (c.y - b.y));

        if (val == 0)

            // Colinear
            return 0;

        else if (val < 0)

            // Anti-clockwise direction
            return 2;

        // Clockwise direction
        return 1;
    }

    static int isIntersect(Line l1, Line l2)
    {
        // Four direction for two lines and points of other
        // line
        int dir1 = direction(l1.p1, l1.p2, l2.p1);
        int dir2 = direction(l1.p1, l1.p2, l2.p2);
        int dir3 = direction(l2.p1, l2.p2, l1.p1);
        int dir4 = direction(l2.p1, l2.p2, l1.p2);

        // When intersecting
        if (dir1 != dir2 && dir3 != dir4)
            return 1;

        // When p2 of line2 are on the line1
        if (dir1 == 0 && onLine(l1, l2.p1) == 1)
            return 1;

        // When p1 of line2 are on the line1
        if (dir2 == 0 && onLine(l1, l2.p2) == 1)
            return 1;

        // When p2 of line1 are on the line2
        if (dir3 == 0 && onLine(l2, l1.p1) == 1)
            return 1;

        // When p1 of line1 are on the line2
        if (dir4 == 0 && onLine(l2, l1.p2) == 1)
            return 1;

        return 0;
    }

    /**
     *
     * @param poly polygon
     * @param numVerts - number of verts
     * @param pointOfCollision - sample point
     * @return
     */
    static int checkPolygonCollision(Point[] poly, int numVerts, Point pointOfCollision)
    {
        // When polygon has less than 3 edge, it is not
        // polygon
        if (numVerts < 3)
            return 0;

        // Create a point at infinity, y is same as point p
        Point pt = new Point(9999, pointOfCollision.y);
        Line exline = new Line(pointOfCollision, pt);
        int count = 0;
        int i = 0;
        do {

            // Forming a line from two consecutive points of
            // poly
            Line side = new Line(poly[i], poly[(i + 1) % numVerts]);
            if (isIntersect(side, exline) == 1) {

                // If side is intersects exline
                if (direction(side.p1, pointOfCollision, side.p2) == 0)
                    return onLine(side, pointOfCollision);
                count++;
            }
            i = (i + 1) % numVerts;
        } while (i != 0);

        // When count is odd
        return count & 1;
    }

    // Driver code
    public static void main(String[] args)
    {
        Point[] polygon
                = { new Point(1, 0), new Point(8, 3),
                new Point(8, 8), new Point(1, 5) };
        Point p = new Point(13, 5);
        int n = polygon.length;

        long timeA = System.currentTimeMillis();
        long meh = 0;
        for(int i = 0; i < 1000; i++){
            meh += checkPolygonCollision(polygon,n,p);
        }
        System.out.println("Avg. Time per evaluation: " + (System.currentTimeMillis() - timeA) / 1000 + "ns");

        if (checkPolygonCollision(polygon, n, p) == 1)
            System.out.println("Point is Inside");
        else
            System.out.println("Point is Outside");
    }
}

