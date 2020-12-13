package rendering;

public class Plane { // NOTE: norm must be a unit vector

    protected Point3D pos;
    protected Vector3D norm;

    public Plane(Point3D pos, Vector3D norm) {
        this.pos = pos.copy();
        this.norm = norm.unit();
    }

    public boolean doesLineSegmentIntersect(Point3D start, Point3D end) {

        return (pointInFront(start) != pointInFront(end));
    }

    public Point3D lineSegmentIntersect(LineSeg seg) {
        return lineSegmentIntersect(seg.start, seg.end);
    }

    public Point3D lineSegmentIntersect(Point3D start, Point3D end) {

        if (doesLineSegmentIntersect(start, end)) {
            float startDist = absDistFrom(start);
            float endDist = absDistFrom(end);

            float startAmount = startDist / (startDist + endDist);

            Vector3D diff = end.subtract(start).multed(startAmount);
            return start.added(diff);
        }

        return null;
    }

    public float distFrom(Point3D point) { // yields negative numbers for points behind plane normal

        Vector3D diff = point.subtract(pos);

        return diff.dot(norm);
    }

    public float absDistFrom(Point3D point) {
        return Math.abs(distFrom(point));
    }

    public boolean pointInFront(Point3D point) {
        return point.subtract(pos).dot(norm) > 0;
    }

}
