package rendering;

import game.MovingRect;
import game.Player;

import java.awt.*;

public class Camera extends Point3D {

    protected float angleX = 0;
    protected float angleY = 0;
    private int centerX;
    private int centerY;

    private float lastDistance;

    private Graphics g;

    private float speed = 0.05F;

    private Polygon[] render;
    private int renderCount;
    private int renderIndex;

    public Camera(Graphics g, float x, float y, float z, int centerX, int centerY) {
        super(x, y, z);
        this.g = g;

        this.centerX = centerX;
        this.centerY = centerY;
    }

    public static void mergeSortHelper(int low, int high, Polygon[] arr, Polygon[] cb, Point3D from) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSortHelper(low, mid, arr, cb, from);
            mergeSortHelper(mid + 1, high, arr, cb, from);
            merge(low, mid, high, arr, cb, from);
        }
    }

    public static void mergeSort(Polygon[] arr, Point3D from) {
        //create the copy buffer - size of arr
        Polygon[] cb = new Polygon[arr.length];
        mergeSortHelper(0, arr.length - 1, arr, cb, from);
    }

    public static void merge(int low, int mid, int high, Polygon[] a, Polygon[] cb, Point3D from) {

        int i1 = low;
        int i2 = mid + 1;

        for (int k = low; k <= high; k++) {
            if (i1 > mid) {

                cb[k] = a[i2];
                i2++;

            } else if (i2 > high) {

                cb[k] = a[i1];
                i1++;

            } else if (a[i1].compareTo(a[i2], from) > 0) {

                cb[k] = a[i1];
                i1++;

            } else {

                cb[k] = a[i2];
                i2++;

            }
        }

        for (int k = low; k <= high; k++) {
            a[k] = cb[k];
        }

    }

    public void renderView() {

        sortRender();

        draw(render);
        render = null;
        renderCount = 0;
        renderIndex = 0;

    }

    public void sortRender() {

        mergeSort(render, this);

    }

    public void preRender(Polygon[] polygons) {

        renderCount += polygons.length;

    }

    public void preRender(Rect3D rect) {

        for (Quad quad : rect.quads) {
            preRender(quad);
        }

    }

    public void preRender(Quad quad) {
        preRender(quad.polygons);
    }

    public void preRender(Model model) {
        preRender(model.polygons);
    }

    public void renderAdd(Polygon[] polygons) {

        if (render == null) {
            render = new Polygon[renderCount];
        }

        for (Polygon poly : polygons) {

            render[renderIndex] = poly;
            renderIndex++;

        }

    }

    public void renderAdd(Quad quad) {
        renderAdd(quad.polygons);
    }

    public void renderAdd(Rect3D rect) {
        for (Quad quad : rect.quads) {
            renderAdd(quad);
        }
    }

    public void renderAdd(Model model) {
        renderAdd(model.polygons);
    }

    public void draw(Point3D[] points) {

        for (Point3D point : points) {
            draw(point);
        }

    }

    public void draw(Rect3D rect) {
        draw(rect.quads);
    }

    public void draw(Quad[] quads) {

        for (Quad quad : quads) {
            if (quad != null) {
                draw(quad);
            }
        }

    }

    public void draw(Quad quad) {
        draw(quad.polygons);
    }

    public void draw(Polygon[] polygons) {

        for (Polygon poly : polygons) {
            if (poly != null) {
                draw(poly);
            }
        }

    }

    public void draw(Polygon poly) {

        if (poly.color != null) {
            fill(poly);
        } else {
            drawSkeleton(poly);
        }

    }

    public void fill(Polygon poly) {

        if (canSee(poly)) {

			/*Point p1 = toScreen(poly.points[0].rotated(this, angleX, angleY));
			Point p2 = toScreen(poly.points[1].rotated(this, angleX, angleY));
			Point p3 = toScreen(poly.points[2].rotated(this, angleX, angleY));

			if (p1 != null && p2 != null && p3 != null) {
				int[] xPoints = {p1.x, p2.x, p3.x};
				int[] yPoints = {p1.y, p2.y, p3.y};
				g.setColor(poly.color);
				g.fillPolygon(xPoints, yPoints, 3);
			}*/

            Point[] screenPoints = new Point[poly.points.length];

            for (int i = 0; i < screenPoints.length; i++) {
                screenPoints[i] = toScreen(poly.points[i].rotated(this, angleX, angleY));
            }

            fill(poly.color, screenPoints);
        }

    }

    public void fill(Color color, Point[] screenPoints) {

        boolean renderable = true;

        int[] xPoints = new int[screenPoints.length];
        int[] yPoints = new int[screenPoints.length];

        for (int i = 0; i < screenPoints.length; i++) {

            Point point = screenPoints[i];

            if (point != null) {

                xPoints[i] = point.x;
                yPoints[i] = point.y;

            } else {

                renderable = false;
                break;

            }

        }

        if (renderable) {

            g.setColor(color);
            g.fillPolygon(xPoints, yPoints, screenPoints.length);

        }
    }

    public void drawSkeleton(Polygon poly) {

        if (canSee(poly)) {

            //drawFacing(poly);

            Point p1 = toScreen(poly.points[0].rotated(this, angleX, angleY));
            Point p2 = toScreen(poly.points[1].rotated(this, angleX, angleY));
            Point p3 = toScreen(poly.points[2].rotated(this, angleX, angleY));

            drawLine(p1, p2);
            drawLine(p2, p3);
            drawLine(p3, p1);
        }
    }

    public void drawFacing(Polygon poly) {

        Point from = toScreen(poly.midpoint().rotated(this, angleX, angleY));
        Point to = toScreen(poly.facingpoint().rotated(this, angleX, angleY));

        drawLine(Color.RED, from, to);
    }

    public boolean canSee(Polygon poly) {

        float dot = poly.midpoint().subtract(this).dot(poly.getFacing().unit());
        return (dot < 0);

    }

    // unused
    public Vector3D vectorAngle() {

        return new Vector3D((float) (Math.cos(angleY) * Math.cos(angleX)), (float) (Math.sin(angleY) * Math.cos(angleX)), (float) (Math.sin(angleX)));
    }

    public void drawLine(Point start, Point end) {
        drawLine(Color.WHITE, start, end);
    }

    public void drawLine(Color color, Point start, Point end) {

        if (start != null && end != null) {
            g.setColor(color);
            g.drawLine(start.x, start.y, end.x, end.y);
        }
    }

    public void draw(Point3D point) {

        Point drawPoint = toScreen(point.rotated(this, angleX, angleY));

        if (drawPoint != null) {
            int radius = (int) Math.max((lastDistance), 1);
            g.setColor(Color.WHITE);
            g.fillOval(drawPoint.x - radius, drawPoint.y - radius, radius * 2, radius * 2);
        }
    }

    public void draw(Model model) {

        draw(model.polygons);
    }

    public Point toScreen(Point3D point) {

        float cameraChange = (point.z - z) / 500;

        if (cameraChange > 0) {
            lastDistance = 1 / cameraChange;

            return new Point((int) (centerX + (point.x - x) * lastDistance), (int) (centerY + -(point.y - y) * lastDistance));
        }

        return null;

    }


    // SORTING

    public void fly(int inputY) {

        this.y += inputY * speed;

    }

    public void move(float angleAddX) {

        x += (float) Math.sin(angleX + angleAddX) * speed;
        z += (float) Math.cos(angleX + angleAddX) * speed;
    }

    public void rotate(float moveX, float moveY) {

        angleX += moveX;
        angleY += moveY;

        angleY = (float) Math.max(Math.min(angleY, Math.PI / 2), -Math.PI / 2);

    }

    public void follow(MovingRect target) {

        this.x = target.x;

    }

    public void run(Player player) {
        this.x += player.constRun;
    }

    public void setRotations(float angleX, float angleY) {
        this.angleX = angleX;
        this.angleY = angleY;
    }

    public void setLocation(float x, float y, float z) {

        this.x = x;
        this.y = y;
        this.z = z;

    }
}
