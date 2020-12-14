package generation;

import rendering.Point3D;

public class TileNode {

    private final Point3D pos;
    private float noise;

    public TileNode(Point3D pos, float noise) {
        this.pos = pos;
        this.noise = noise;
    }


    public Point3D getPos() {
        return pos;
    }

    public float getNoise() {
        return noise;
    }

    public void setNoise(float noise) {
        this.noise = noise;
    }
}
