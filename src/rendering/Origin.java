package rendering;

public class Origin extends Point3D {

    BoundedPoint[] points;

    public Origin(float x, float y, float z) {

        super(x, y, z);
    }

    public void setPoints(BoundedPoint[] points) {
        this.points = points;
    }

    public void move(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;

        for (BoundedPoint point : points) {
            point.update(this);
        }
    }
}
