package rendering;

import generation.Chunk;
import generation.ChunkMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
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

    //private rendering.Point3D[] points = new rendering.Point3D[100];

    //private rendering.Rect3D rect;
    private List<Model> models = new ArrayList<>();

    private ChunkMap chunkMap;

    private List<Chunk> renderChunks;

    public Driver() {

        //buffered image
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = image.getGraphics();

        camera = new Camera(g, 0, 0, -10, WIDTH / 2, HEIGHT / 2);

        chunkMap = new ChunkMap();

        renderChunks = new ArrayList<>();

        addRenderChunks(-1, -1, -1, 1, 1, 1);

        // testing:
        Model model = new Model("models//mountains.obj");
        //model.shade(new Vector3D(1,0,0), Color.lightGray);
        model.polygons = PlaneSlicer.slices(model.polygons, new LineSeg(model.bounds()).x(), 70, new Color(55, 59, 92), Color.gray);
        models.add(model);

        model = new Model("models//isoSphere.obj");
        model.polygons = PlaneSlicer.slices(model.polygons, new LineSeg(model.bounds()).y(), 10, Color.red, Color.orange);
        models.add(model);




        //timer
        timer = new Timer(0, new TimerListener());
        timer.start();

        // input
        input = new Input(this);
    }

    public void addRenderChunks(int x1, int y1, int z1, int x2, int y2, int z2) {

        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    renderChunks.add(chunkMap.get(x, y, z));
                }
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

    public void move() {

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

        if (inputZ != 0 || inputX != 0) {
            camera.move((float) Math.atan2(-inputX, inputZ));
        }

        int inputY = 0;
        if (spacePressed) {
            inputY++;
        }
        if (shiftPressed) {
            inputY--;
        }

        camera.fly(inputY);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    public class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            g.setColor(Color.black);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            move();

            for (Model model : models) {
                camera.preRender(model);
            }

            for (Model model : models) {
                camera.renderAdd(model);
            }

            camera.renderView();

            repaint();
        }

    }

    public Camera getCamera() {
        return camera;
    }
}