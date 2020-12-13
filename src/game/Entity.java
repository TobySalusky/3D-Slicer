package game;

import rendering.Rect3D;
import rendering.Vector3D;

import java.awt.*;

public class Entity extends MovingRect {

    protected Vector3D vel = new Vector3D();
    protected float friction = 0.1F;
    protected Color color;

    public Entity(float x, float y, float z, float width, float height, float length) {
        super(x, y, z, width, height, length);

        this.color = Color.WHITE;
    }

    public void tick() {

        //move(vel);
        //vel.multAll(1F - friction); // change PLEASE

    }

    public void shade(Vector3D light) {
        getModel().shade(light, color);
    }
}
