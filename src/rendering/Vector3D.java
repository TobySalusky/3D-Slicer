package rendering;

public class Vector3D {

    public float x;
    public float y;
    public float z;

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D() {
        this(0F, 0F,0F);
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

    public Vector3D unit() { // wrong
        float mag = mag();
        return new Vector3D(x / mag, y / mag, z / mag);
    }

    public float mag() {
        return (float) (Math.sqrt(x * x + y * y + z * z));
    }

    public void multAll(float scalar) { // awful
        x *= scalar;
        y *= scalar;
        z *= scalar;
    }

    public void add(Vector3D vec) {
        x += vec.x;
        y += vec.y;
        z += vec.z;
    }

    public void add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    public void sub(Vector3D vec) {
        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
    }

    /*public void clamp(float min, float max) {
        setMag(Math.min(Math.max(mag(), min), max));
    }

    public void mult(float scalar) {
        setMag(mag() * scalar);
    }

    public void addMag(float mag) {
        setMag(mag() + mag);
    }

    public void subMag(float mag) {
        addMag(-mag);
    }

    public void setMag(float mag) {

        resize(mag, angle());

    }

    public void resize(float mag, float angle) {
        x = mag * (float) Math.cos(angle);
        y = mag * (float) Math.sin(angle);
    }

    public void setAngle(float angle) {
        resize(mag(), angle);
    }

    public void rotate(float radians) {

        resize(mag(), angle() + radians);
    }

    public float angle() {
        return MathFunctions.findAngle(x, y);
    }*/
}
