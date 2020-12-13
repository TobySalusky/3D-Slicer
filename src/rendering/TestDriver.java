package rendering;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class TestDriver extends JPanel {

    static final int WIDTH = 960;
    static final int HEIGHT = 540;
    private static final long serialVersionUID = 7802663692279468780L;

    private static JFrame frame;
    protected boolean wPressed, aPressed, sPressed, dPressed, spacePressed, shiftPressed;
    private BufferedImage image;
    private Graphics g;
    private Timer timer;
    private Camera camera;
    private Input input;

    //private rendering.Point3D[] points = new rendering.Point3D[100];

    //private rendering.Rect3D rect;
    private Model model;

    public TestDriver() {

        //buffered image
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = image.getGraphics();

        camera = new Camera(g, 0, 0, -10, WIDTH / 2, HEIGHT / 2);

		/*for (int i = 0; i < 25; i++) {
			points[i] = new rendering.Point3D(30,50,10*i);
		}

		for (int i = 0; i < 25; i++) {
			points[i+25] = new rendering.Point3D(-30,50,10*i);
		}

		for (int i = 0; i < 25; i++) {
			points[i+50] = new rendering.Point3D(30,-50,10*i);
		}

		for (int i = 0; i < 25; i++) {
			points[i+75] = new rendering.Point3D(-30,-50,10*i);
		}*/

        //rect = new rendering.Rect3D(points[0], points[24], points[49], points[25], -70);

        model = new Model("models//gourd.obj", new Origin(0,0,0));
        //model.shade(new Vector3D(-1, -1, 0));

        //timer
        timer = new Timer(0, new TimerListener());
        timer.start();

        // input
        input = new Input(this);
    }

    public static void main(String[] args) {

        frame = new JFrame("Render Time!");
        frame.setSize(WIDTH, HEIGHT); //+17 +48
        frame.setLocation(480, 270);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new TestDriver());
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

            //camera.draw(points);
            //camera.draw(rect);
            //camera.draw(model);

            model.origin.move(-0.01F,0F,0F);

            camera.preRender(model);
            //camera.preRender(rect);

            camera.renderAdd(model);
            //camera.renderAdd(rect);

            //model.goWild(1);
            camera.renderView();

            repaint();
        }

    }

}
