package rendering;

public class Rect3D {

    private BoundedPoint[] points;
    protected Quad[] quads = new Quad[6];

    public Rect3D(BoundedPoint p1, BoundedPoint p2, BoundedPoint p3, BoundedPoint p4, BoundedPoint p5, BoundedPoint p6, BoundedPoint p7, BoundedPoint p8) {

        points = new BoundedPoint[]{p1, p2, p3, p4, p5, p6, p7, p8};
        quads[0] = new Quad(p4, p3, p2, p1);
        quads[1] = new Quad(p7, p8, p5, p6);
        quads[2] = new Quad(p1, p2, p6, p5);
        quads[3] = new Quad(p2, p3, p7, p6);
        quads[4] = new Quad(p3, p4, p8, p7);
        quads[5] = new Quad(p4, p1, p5, p8);

    }

    public Polygon[] getPolygons() {
        Polygon[] polygons = new Polygon[12];

        int i = 0;
        for (Quad quad : quads) {
            polygons[i++] = quad.polygons[i % 2];
            polygons[i++] = quad.polygons[i % 2];
        }

        return polygons;
    }

    public Rect3D(Origin origin, float width, float height, float length) {

        this(new BoundedPoint(origin.x - width/ 2F, origin.y - height / 2F, origin.z + length / 2F, origin), new BoundedPoint(origin.x + width/ 2F, origin.y - height / 2F, origin.z + length / 2F, origin),
                new BoundedPoint(origin.x + width/ 2F, origin.y - height / 2F, origin.z - length / 2F, origin), new BoundedPoint(origin.x - width/ 2F, origin.y - height / 2F, origin.z - length / 2F, origin),
                new BoundedPoint(origin.x - width/ 2F, origin.y + height / 2F, origin.z + length / 2F, origin), new BoundedPoint(origin.x + width/ 2F, origin.y + height / 2F, origin.z + length / 2F, origin),
                new BoundedPoint(origin.x + width/ 2F, origin.y + height / 2F, origin.z - length / 2F, origin), new BoundedPoint(origin.x - width/ 2F, origin.y + height / 2F, origin.z - length / 2F, origin));
    }

    public BoundedPoint[] getPoints() {
        return points;
    }

}
