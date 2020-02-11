package rendering;

public class Rect3D {

    protected Quad[] quads = new Quad[6];

    public Rect3D(Point3D p1, Point3D p2, Point3D p3, Point3D p4, Point3D p5, Point3D p6, Point3D p7, Point3D p8) {

        quads[0] = new Quad(p4, p3, p2, p1);
        quads[1] = new Quad(p7, p8, p5, p6);
        quads[2] = new Quad(p1, p2, p6, p5);
        quads[3] = new Quad(p2, p3, p7, p6);
        quads[4] = new Quad(p3, p4, p8, p7);
        quads[5] = new Quad(p4, p1, p5, p8);

    }

    public Rect3D(Point3D p1, Point3D p2, Point3D p3, Point3D p4, float height) {

        this(p1, p2, p3, p4, new Point3D(p1.x, p1.y + height, p1.z), new Point3D(p2.x, p2.y + height, p2.z), new Point3D(p3.x, p3.y + height, p3.z), new Point3D(p4.x, p4.y + height, p4.z));

    }

}
