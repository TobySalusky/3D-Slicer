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

    public void move(Vector3D vec) {
        move(vec.x, vec.y, vec.z);
    }

    public void moveTo(float x, float y, float z) {
        move(new Vector3D(x - this.x, y - this.y, z - this.z));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    public float getZ() {
        return z;
    }
}
