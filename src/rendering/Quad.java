package rendering;

public class Quad { // TODO: make extends polygon holder (abstract class)

    protected Polygon[] polygons = new Polygon[2];

    public Quad(Point3D p1, Point3D p2, Point3D p3, Point3D p4) {

        polygons[0] = new Polygon(p1, p2, p3);
        polygons[1] = new Polygon(p3, p4, p1);

    }

}
