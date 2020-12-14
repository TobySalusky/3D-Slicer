package rendering;

public class LineSeg {

    public Point3D start, end;

    public LineSeg(Point3D start, Point3D end) {
        this.start = start;
        this.end = end;
    }

    public void flipEnds() {
        Point3D temp = start;
        start = end;
        end = temp;
    }
}
