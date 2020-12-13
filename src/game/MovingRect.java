package game;

import rendering.Model;
import rendering.Origin;
import rendering.Rect3D;

import java.util.List;

public class MovingRect extends Origin {

    private Model model;

    private float width, height, length;

    public MovingRect(float x, float y, float z, float width, float height, float length) {
        super(x, y, z);

        this.width = width;
        this.height = height;
        this.length = length;

        this.model = new Model(new Rect3D(this, width, height, length), this);
    }

    public boolean collidesWithAt(MovingRect rect, float x, float y, float z) {

        if (x + width/2F >= rect.x - rect.width/2F && x - width/2F <= rect.x + rect.width/2F) {
            if (y + height/2F >= rect.y - rect.height/2F && y - height/2F <= rect.y + rect.height/2F) {
                return (z + length/2F >= rect.z - rect.length/2F && z - length/2F <= rect.z + rect.length/2F);
            }
        }

        return false;
    }

    public boolean collidesWithAt(List<MovingRect> rects, float x, float y, float z) {

        for (MovingRect rect : rects) {
            if (collidesWithAt(rect, x, y, z)) {
                return true;
            }
        }
        return false;
    }

    public Model getModel() {
        return model;
    }
}
