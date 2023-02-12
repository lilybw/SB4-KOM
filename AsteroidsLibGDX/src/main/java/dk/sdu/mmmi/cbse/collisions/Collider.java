package dk.sdu.mmmi.cbse.collisions;

import dk.sdu.mmmi.cbse.util.ArrayUtil;
import dk.sdu.mmmi.cbse.util.Ref;

public class Collider {

    @FunctionalInterface
    public interface CollisionFunction {
        int isInBounds(Collider collider, float pointX, float pointY);
    }

    @FunctionalInterface
    private interface PreflightCheck {
        boolean apply(float x, float y);
    }

    /**
     * Expects the center to be the first vertex of the collider mesh.
     * Expects the radius to be the collider radius.
     * NB: If the collider radius is not set, it will be -1 and cause an error.
     */
    public static CollisionFunction CIRCLE = (Collider collider, float pointX, float pointY) -> {
        Mesh.Point vertInQuestion = collider.verts()[0];
        float xDiff = vertInQuestion.x - pointX, yDiff = vertInQuestion.y - pointY;
        float distSQ = xDiff * xDiff + yDiff * yDiff;
        return distSQ < collider.radius() * collider.radius() ? 1 : 0;
    };
    /**
     * Expects the first 3 vertexes of the array to be the vertexes of the triangle.
     */
    public static CollisionFunction TRIANGLE = (Collider collider, float pointX, float pointY) -> {
        //Thx to Glenn Slayden for this nice and concise answer: https://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle
        Mesh.Point[] verts = collider.colliderMesh.verts();
        float s = (verts[0].x - verts[2].x) * (pointY - verts[2].y) - (verts[0].y - verts[2].y) * (pointX - verts[2].x);
        float t = (verts[1].x - verts[0].x) * (pointY - verts[0].y) - (verts[1].y - verts[0].y) * (pointX - verts[0].x);

        if ((s < 0) != (t < 0) && s != 0 && t != 0)
            return 0;

        float d = (verts[2].x - verts[1].x) * (pointY - verts[1].y) - (verts[2].y - verts[1].y) * (pointX - verts[1].x);
        return d == 0 || (d < 0) == (s + t <= 0) ? 1 : 0;
    };

    public static CollisionFunction POLYGON = Collider::checkPolygonCollision;
    /**
     * Expects the colliding square to be the first 4 verts in the collision mesh.
     */
    public static CollisionFunction RECTANGLE = (Collider collider, float pointX, float pointY) -> {
        float minX = collider.colliderMesh.minX().get(), maxX = collider.colliderMesh.maxX().get(),
                minY = collider.colliderMesh.minY().get(), maxY = collider.colliderMesh.maxY().get();
        return pointX > minX || pointX < maxX || pointY > minY || pointY < maxY ? 1 : 0;
    };
    /**
     * Assumes the type of collider based on its number of verts.
     */
    public static CollisionFunction ANY = (Collider collider, float pointX, float pointY) ->
            switch (collider.numVerts()){
                case 1 -> CIRCLE.isInBounds(collider,pointX,pointY);
                case 3 -> TRIANGLE.isInBounds(collider,pointX,pointY);
                case 4 -> RECTANGLE.isInBounds(collider,pointX,pointY);
                default -> POLYGON.isInBounds(collider,pointX,pointY);
            };

    private final Mesh colliderMesh;
    private final PreflightCheck preflightCheck;
    private final CollisionFunction collisionFunction;

    public Collider(float[] vertXs, float[] vertYs, CollisionFunction func)
    {
        this(vertXs,vertYs,-1,func);
    }
    public Collider(float[] vertXs, float[] vertYs, float radius, CollisionFunction func)
    {
        Mesh.Point[] polygon = new Mesh.Point[vertXs.length];
        for(int i = 0; i < vertXs.length; i++) {
            polygon[i] = new Mesh.Point(vertXs[i],vertYs[i]);
        }

        this.colliderMesh = new Mesh(polygon,vertXs.length,new Ref<>(radius),
                new Ref<>(ArrayUtil.leastMost(vertXs)),
                new Ref<>(ArrayUtil.leastMost(vertYs)),
                new Ref<>(ArrayUtil.greatest(vertXs)),
                new Ref<>(ArrayUtil.greatest(vertYs)),
                new Ref<>(true)
        );

        preflightCheck = radius != -1 ? this::radialPreflightCheck : this::preflightCheck;
        collisionFunction = func != null ? func : POLYGON;
    }

    public int isInBounds(float pointX, float pointY)
    {
        if(this.preflightCheck.apply(pointX,pointY)) return 0;
        return collisionFunction.isInBounds(this, pointX,pointY);
    }

    private boolean preflightCheck(float pointX, float pointY)
    {
        return pointX < colliderMesh.minX().get() || pointX > colliderMesh.maxX().get() || pointY < colliderMesh.minY().get() || pointY > colliderMesh.maxY().get();
    }
    private boolean radialPreflightCheck(float pointX, float pointY)
    {
        Mesh.Point center = colliderMesh.verts()[0];
        float radius = colliderMesh.radius().get();
        return pointX < center.x - radius || pointX > center.x + radius
                || pointY < center.y - radius || pointY > center.y + radius;
    }


    public void update(float[] vertXs, float[] vertYs)
    {
        colliderMesh.refresh(vertXs,vertYs);
    }
    public void update(float radius)
    {
        this.update(colliderMesh.maxX().get(),colliderMesh.maxY().get(),radius);
    }
    public void update(float x, float y)
    {
        this.update(x,y,colliderMesh.radius().get());
    }
    public void update(float x, float y, float r)
    {
        colliderMesh.refresh(x,y,r);
    }


    public float radius()
    {
        return colliderMesh.radius().get();
    }
    public Mesh.Point[] verts()
    {
        return colliderMesh.verts();
    }
    public int numVerts()
    {
        return colliderMesh.numVerts();
    }
    public Mesh getColliderMesh()
    {
        return colliderMesh;
    }

    static int onLine(Mesh.Line l1, Mesh.Point p)
    {
        // Check whether p is on the line or not
        if (p.x <= Math.max(l1.p1.x, l1.p2.x)
                && p.x <= Math.min(l1.p1.x, l1.p2.x)
                && (p.y <= Math.max(l1.p1.y, l1.p2.y)
                && p.y <= Math.min(l1.p1.y, l1.p2.y)))
            return 1;

        return 0;
    }

    static int direction(Mesh.Point a, Mesh.Point b, Mesh.Point c)
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

    static int isIntersect(Mesh.Line l1, Mesh.Line l2)
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


    static int checkPolygonCollision(Collider collider, float pointX, float pointY)
    {
        Mesh.Point samplePoint = new Mesh.Point(pointX,pointY);
        // When polygon has less than 3 edge, it is not
        // polygon
        if (collider.numVerts() < 3)
            return 0;

        // Create a point at infinity, y is same as point p
        Mesh.Point pt = new Mesh.Point(9999, pointY);
        Mesh.Line exline = new Mesh.Line(samplePoint, pt);
        int count = 0;
        int i = 0;
        do {

            // Forming a line from two consecutive points of
            // poly
            Mesh.Line side = new Mesh.Line(collider.verts()[i], collider.verts()[(i + 1) % collider.numVerts()]);
            if (isIntersect(side, exline) == 1) {

                // If side is intersects exline
                if (direction(side.p1, samplePoint, side.p2) == 0)
                    return onLine(side, samplePoint);
                count++;
            }
            i = (i + 1) % collider.numVerts();
        } while (i != 0);

        // When count is odd
        return count & 1;
    }

    // Driver code
    public static void main(String[] args)
    {
        Collider collider = new Collider(
                new float[]{1,8,8,1},
                new float[]{0,3,8,5},
                Collider.POLYGON
        );

        long timeA = System.currentTimeMillis();
        long meh = 0;
        for(int i = 0; i < 1000; i++){
            meh += checkPolygonCollision(collider, 3,5);
        }
        System.out.println("Avg. Time per evaluation: " + (System.currentTimeMillis() - timeA) / 1000 + "ns");

        if (checkPolygonCollision(collider, 3,5) == 1)
            System.out.println("Point is Inside");
        else
            System.out.println("Point is Outside");
    }
}

