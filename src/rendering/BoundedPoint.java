package rendering;

public class BoundedPoint extends Point3D {

    protected Point3D origin;

    protected float angleX;
    protected float angleY;

    public BoundedPoint(float x, float y, float z, Point3D origin) {
        super(x, y, z);

        this.origin = origin;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }
}
