package rendering;

public class Vector3D {

    protected float x;
    protected float y;
    protected float z;

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D reverse() {
        return new Vector3D(-x, -y, -z);
    }

    public float dot(Vector3D vec) {
        return x * vec.x + y * vec.y + z * vec.z;
    }

    public Vector3D cross(Vector3D vec) { // mmmm dunno man...
        return new Vector3D((y * vec.z - z * vec.y), -(x * vec.z - z * vec.x), (x * vec.y - y * vec.x));
    }

    public Vector3D unit() {
        float mag = mag();
        return new Vector3D(x / mag, y / mag, z / mag);
    }

    public Vector3D multed(float scalar) {
        return new Vector3D(x * scalar, y * scalar, z * scalar);
    }

    public float mag() {
        return (float) (Math.sqrt(x * x + y * y + z * z));
    }

    @Override
    public String toString() {
        return "<" + super.toString() + ": x = " + x + ", y = " + y + ", z = " + z + ">";
    }
}
