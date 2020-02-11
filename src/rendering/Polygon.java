package rendering;

import java.awt.*;

public class Polygon {

    protected Point3D[] points;
    protected Color color; // TODO: allow more than 3 faces

    public Polygon(Point3D p1, Point3D p2, Point3D p3) {
        points = new Point3D[3];

        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
    }

    public Polygon(Point3D[] points) {
        this.points = points;
    }

    public float compareTo(Polygon poly, Point3D from) {

        Vector3D thisVec = midpoint().subtract(from);
        Vector3D otherVec = poly.midpoint().subtract(from);

        return thisVec.mag() - otherVec.mag();
    }

    public Vector3D getFacing() {
        return points[1].subtract(points[0]).cross(points[points.length - 1].subtract(points[0]));
    }

    public Point3D midpoint() {

        float x = 0F;
        float y = 0F;
        float z = 0F;

        for (Point3D point : points) {

            x += point.x;
            y += point.y;
            z += point.z;

        }

        int count = points.length;

        return new Point3D(x / count, y / count, z / count);
    }

    public Point3D facingpoint() {
        Point3D from = midpoint();
        Vector3D facing = getFacing();
        return new Point3D(from.x + facing.x, from.y + facing.y, from.z + facing.z);
    }
}
