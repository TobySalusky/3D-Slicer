package rendering;

public class BoundedPoint extends Point3D {

    protected float offX;
    protected float offY;
    protected float offZ;

    public BoundedPoint(float x, float y, float z, Origin origin) {
        super(x, y, z);

        findRelativePosition(origin);
    }

    @Override
    public void update(Origin origin) {

        this.x = origin.x + offX;
        this.y = origin.y + offY;
        this.z = origin.z + offZ;

    }

    public void findRelativePosition(Origin origin) {

        offX = x - origin.x;
        offY = y - origin.y;
        offZ = z - origin.z;
    }
}
