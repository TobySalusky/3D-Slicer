package rendering;

public class Point3D {

    protected float x;
    protected float y;
    protected float z;

    public Point3D(Point3D around, float distanceX, float angleX, float y) {

        this.x = around.x + (float) (Math.cos(angleX) * distanceX);

        this.y = y;

        this.z = around.z + (float) (Math.sin(angleX) * distanceX);
    }

    public Point3D(float x, Point3D around, float distanceX, float angleX) {

        this.x = x;

        this.y = around.y + (float) (Math.sin(angleX) * distanceX);

        this.z = around.z + (float) (Math.cos(angleX) * distanceX);
    }

    public Point3D(float x, float y, float z) {

        this.x = x;
        this.y = y;
        this.z = z;

    }

    public void update(Origin origin) {

    }

    public float distanceToXZ(Point3D point) {
        return (float) Math.sqrt(Math.pow(point.x - x, 2) + Math.pow(point.z - z, 2));
    }

    public float angleToXZ(Point3D point) {
        return (float) Math.atan2(point.z - z, point.x - x);
    }


    public float distanceToYZ(Point3D point) {
        return (float) Math.sqrt(Math.pow(point.y - y, 2) + Math.pow(point.z - z, 2));
    }

    public float angleToYZ(Point3D point) { // swap?
        return (float) Math.atan2(point.y - y, point.z - z);
    }

    public Point3D rotated(Point3D around, float angleX, float angleY) {
        return rotatedX(around, angleX).rotatedY(around, angleY);
    }

    public Point3D rotatedY(Point3D around, float angleY) {

        return new Point3D(x, around, distanceToYZ(around), around.angleToYZ(this) + angleY);

    }

    public Point3D rotatedX(Point3D around, float angleX) {

        return new Point3D(around, distanceToXZ(around), around.angleToXZ(this) + angleX, y);

    }

    public Vector3D subtract(Point3D point) {
        return new Vector3D(x - point.x, y - point.y, z - point.z);
    }
}
