package game;

import rendering.Camera;
import rendering.Vector3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class Driver extends JPanel {

    static final int WIDTH = 960;
    static final int HEIGHT = 540;
    private static final long serialVersionUID = 7802663692279468780L;

    private static JFrame frame;
    public boolean wPressed, aPressed, sPressed, dPressed, spacePressed, shiftPressed;
    private BufferedImage image;
    private Graphics g;
    private Timer timer;
    private Camera camera;
    private Input input;

    private Vector3D light = new Vector3D(-0.5F, -1, 0);

    private Player player;
    private boolean playerControl = true;

    private List<MovingRect> blocks = new LinkedList<>();

    private float cameraY = 30F;
    private float cameraZ = -75F;

    public Driver() {

        //buffered image
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = image.getGraphics();

        camera = new Camera(g, 0, cameraY, cameraZ, WIDTH / 2, HEIGHT / 2);
        camera.setRotations(0, 0.25F);

        player = new Player(0,10,0,3,5,1.5F);
        player.shade(light);

        createBlocks();
        player.setCollisions(blocks);


        //timer
        timer = new Timer(0, new TimerListener());
        timer.start();

        // input
        input = new Input(this);
    }

    public void switchModes() {

        if (!playerControl) {
            //camera.setLocation(0, cameraY, cameraZ);
            //camera.setRotations(0, 0);
        }

        playerControl = !playerControl;

    }

    public void createBlocks() {

        float length = 15F;
        float height = 20F;

        float x = 0F;
        for (int i = 0; i < 50; i++) {

            float width = 50F + (float)Math.random() * 40F;
            float z = (int)(Math.random() * 4 - 1) * length;

            if (i == 0) {
                z = 0;
            }

            Block block = new Block(x, -height / 2F, z, width, height, length);
            block.shade(light);
            blocks.add(block);

            x += width + (int) (Math.random() * 7 - 5) * 5F;
            if (Math.random() < 0.2F) {
                x -= width;
            }
        }

    }

    public static void main(String[] args) {

        frame = new JFrame("Render Time!");
        frame.setSize(WIDTH, HEIGHT); //+17 +48
        frame.setLocation(480, 270);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Driver());
        frame.setVisible(true);
    }

    public void mouseMoved(float moveX, float moveY) {

        camera.rotate(-moveX / 100, moveY / 100);

    }

    public void input() {

        int inputX = 0;
        int inputZ = 0;

        if (aPressed) {
            inputX++;
        }
        if (dPressed) {
            inputX--;
        }

        if (wPressed) {
            inputZ++;
        }
        if (sPressed) {
            inputZ--;
        }

        int inputY = 0;
        if (spacePressed) {
            inputY++;
        }
        if (shiftPressed) {
            inputY--;
        }

        if (playerControl) {
            playerMove(inputX, inputY, inputZ);
            player.tick();
            camera.run(player);
        } else {
            cameraMove(inputX, inputY, inputZ);
        }

    }

    public void playerMove(int inputX, int inputY, int inputZ) {

        if (inputZ != 0 || inputX != 0) {
            player.run((float) Math.atan2(-inputX, inputZ));
        }

        if (inputY == 1) {
            player.jump();
        }

    }

    public void cameraMove(int inputX, int inputY, int inputZ) {

        if (inputZ != 0 || inputX != 0) {
            camera.move((float) Math.atan2(-inputX, inputZ));
        }

        camera.fly(inputY);

    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    public void draw() {

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        camera.preRender(player.getModel());
        for (MovingRect block : blocks) {
            camera.preRender(block.getModel());
        }

        camera.renderAdd(player.getModel());
        for (MovingRect block : blocks) {
            camera.renderAdd(block.getModel());
        }


        camera.renderView();

        repaint();

    }

    public void tick() {

        input();

        if (player.getY() < -100F) {
            reset();
        }
    }

    public void reset() {
        player.moveTo(0,10,0);
        camera.follow(player);
    }

    public class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            tick();
            draw();
        }

    }

}
