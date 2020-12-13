package game;

import rendering.Vector3D;

import java.util.List;
import java.awt.*;

public class Player extends Entity {

    public float constRun = 0.1F;
    private float runSpeed = 0.05F;
    private float jumpPow = 0.1F;
    private boolean jumpAllowed = false;
    private float gravity = -0.0005F;

    private List<MovingRect> collisions;

    public Player(float x, float y, float z, float width, float height, float length) {
        super(x, y, z, width, height, length);
        this.color = Color.RED;

        friction = 0.5F;
    }

    public void run(float angle) {

        vel.add((float) Math.sin(angle) * runSpeed, 0, (float) Math.cos(angle) * runSpeed);

    }

    @Override
    public void tick() {

        jumpAllowed = false;

        vel.add(constRun, 0, 0);
        if (!collidesWithAt(collisions, x + vel.x, y + vel.y, z + vel.z)) {
            move(vel);
        } else {

            if (collidesWithAt(collisions, x + vel.x, y, z)) {
                vel.x = 0;
            }

            if (collidesWithAt(collisions, x, y + vel.y, z)) {

                if (vel.y < 0) {
                    jumpAllowed = true;
                }

                vel.y = 0;
            }

            if (collidesWithAt(collisions, x, y, z + vel.z)) {
                vel.z = 0;
            }
            move(vel);
        }
        if (vel.x > 0) {
            vel.sub(constRun, 0, 0);
        }
        //vel.multAll(1F - friction); // change PLEASE
        vel.x *= 1F - friction;
        vel.z *= 1F - friction;

        vel.add(0, gravity, 0);

    }

    public void jump() {

        if (jumpAllowed) {
            vel.add(0,jumpPow,0);
        }

    }

    public void setCollisions(List<MovingRect> collisions) {
        this.collisions = collisions;
    }
}
