package rendering;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Model {

    protected Polygon[] polygons;

    public Model(String fileName) {

        parseObj(fileName);

    }

    public void shade(Vector3D light) {

        light = light.reverse().unit();

        for (Polygon poly : polygons) {

            float brightness = light.dot(poly.getFacing().unit()) / 2 + 0.5F;

            int col = (int) (brightness * 255);
            poly.color = new Color(col / 2, col, col / 2);

        }

    }

    public void parseObj(String fileName) {

        try {

            File objFile = new File(fileName);
            Scanner length = new Scanner(objFile);

            int pointCount = 0;
            int faceCount = 0;


            while (length.hasNextLine()) {

                String line = length.nextLine();

                if (line.length() >= 2) if (line.substring(0, 1).equals("v")) pointCount++;
				else if (line.substring(0, 1).equals("f")) faceCount++;

            }
            length.close();


            Point3D[] points = new Point3D[pointCount];
            int pointIndex = 0;

            polygons = new Polygon[faceCount];
            int faceIndex = 0;

            Scanner reader = new Scanner(objFile);

            while (reader.hasNextLine()) {

                String line = reader.nextLine();

                if (line.length() >= 2) if (line.substring(0, 1).equals("v")) {
					points[pointIndex] = parsePoint(line.substring(2));
					pointIndex++;
				} else if (line.substring(0, 1).equals("f")) {
					polygons[faceIndex] = parseFace(line.substring(2), points);
					faceIndex++;
				}

            }
            reader.close();


        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }

    }

    private Point3D parsePoint(String line) {

        float x = Float.parseFloat(line.substring(0, line.indexOf(" ")));
        line = line.substring(line.indexOf(" ") + 1);

        float y = Float.parseFloat(line.substring(0, line.indexOf(" ")));
        line = line.substring(line.indexOf(" ") + 1);

        float z = Float.parseFloat(line);

        return new Point3D(x, y, z);

    }

    public void goWild(int times) {

        for (int i = 0; i < times; i++) {

            Point3D[] points = polygons[(int) (Math.random() * polygons.length)].points;
            points[(int) (Math.random() * points.length)].x *= 0.9F + Math.random() * 0.1F;
            points[(int) (Math.random() * points.length)].y *= 0.9F + Math.random() * 0.1F;
            points[(int) (Math.random() * points.length)].z *= 0.9F + Math.random() * 0.1F;
        }
    }

    public void rot(int times) {

        for (int i = 0; i < times; i++)
			for (Polygon poly : polygons)
				for (Point3D point : poly.points) {
					point.x *= 0.99999F + Math.random() * 0.000001F;
					point.y *= 0.99999F + Math.random() * 0.000001F;
					point.z *= 0.99999F + Math.random() * 0.000001F;
				}
    }

    private Polygon parseFace(String line, Point3D[] points) {

        int pointCount = spacesOn(line) + 1;

        Point3D[] facePoints = new Point3D[pointCount];

        int faceIndex = 0;
        while (faceIndex < pointCount - 1) {

            // abstract to func please
            facePoints[faceIndex] = points[Integer.parseInt(line.substring(0, line.indexOf(" "))) - 1];
            line = line.substring(line.indexOf(" ") + 1);
            faceIndex++;
        }

        facePoints[pointCount - 1] = points[Integer.parseInt(line) - 1];

        return new Polygon(facePoints);
    }

    private int spacesOn(String line) {

        int count = 0;

        while (line.indexOf(" ") != -1) {
            count++;
            line = line.substring(line.indexOf(" ") + 1);
        }

        return count;
    }

    private Polygon parseFace3(String line, Point3D[] points) {

        int p1 = Integer.parseInt(line.substring(0, line.indexOf(" ")));
        line = line.substring(line.indexOf(" ") + 1);

        int p2 = Integer.parseInt(line.substring(0, line.indexOf(" ")));
        line = line.substring(line.indexOf(" ") + 1);

        int p3 = Integer.parseInt(line);

        return new Polygon(points[p1 - 1], points[p2 - 1], points[p3 - 1]);

    }
}
