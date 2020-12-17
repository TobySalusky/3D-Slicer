package rendering;

public class LineSeg {

    public Point3D start, end;

    public LineSeg(Point3D start, Point3D end) {
        this.start = start;
        this.end = end;
    }

    public LineSeg(Point3D[] bounds) {
        this(bounds[0], bounds[1]);
    }

    public void flipEnds() {
        Point3D temp = start;
        start = end;
        end = temp;
    }

    public LineSeg x() {
        return new LineSeg(new Point3D(start.x, 0, 0), new Point3D(end.x, 0, 0));
    }

    public LineSeg y() {
        return new LineSeg(new Point3D(0, start.y, 0), new Point3D(0, end.y, 0));
    }

    public LineSeg z() {
        return new LineSeg(new Point3D(0, 0, start.z), new Point3D(0, 0, end.z));
    }
}
