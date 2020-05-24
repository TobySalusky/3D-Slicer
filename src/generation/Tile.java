package generation;

import rendering.Point3D;
import rendering.Polygon;
import rendering.Vector3D;

import java.awt.*;

public class Tile {

    public static final float TILE_SIZE = 4F;

    private Polygon[] polygons;

    public Tile() {
    }

    public void shade(Vector3D light) {

        light = light.reverse().unit();

        for (Polygon poly : polygons) {

            float brightness = light.dot(poly.getFacing().unit()) / 2 + 0.5F;

            int col = (int) (brightness * 255);
            poly.setColor(new Color(col / 2, col, col / 2));

        }

    }

    public Polygon[] getPolygons() {
        return polygons;
    }

    public void setPolygons(Polygon[] polygons) {
        this.polygons = polygons;
    }
}
