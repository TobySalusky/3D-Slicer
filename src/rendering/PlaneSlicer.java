package rendering;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class PlaneSlicer {

    public static Polygon[] slices(Polygon[] polygons, LineSeg cutAlong, int n) {
        return slices(polygons, cutAlong, n, null, null);
    }

        public static Polygon[] slices(Polygon[] polygons, LineSeg cutAlong, int n, Color startCol, Color endCol) {

        List<Polygon> output = new ArrayList<>();

        Vector3D diff = cutAlong.end.subtract(cutAlong.start);

        for (float i = 0; i < n; i++) {
            float scalar = (n == 1) ? 0.5F : i / (n - 1);
            Point3D pos = cutAlong.start.added(diff.multed(scalar));

            Plane plane = new Plane(pos, diff);

            Polygon[] currentOut = slice(polygons, plane);
            Collections.addAll(output, currentOut);

            if (startCol != null) {
                float s1 = scalar, s2 = 1 - scalar;

                Color color = new Color((int)(startCol.getRed() * s1 + endCol.getRed() * s2), (int)(startCol.getGreen() * s1 + endCol.getGreen() * s2), (int)(startCol.getBlue() * s1 + endCol.getBlue() * s2));
                for (Polygon polygon : currentOut) {
                    polygon.setColor(color);
                }
            }
        }

        return output.toArray(new Polygon[0]);
    }

    public static Polygon[] slice(Polygon[] polygons, Plane plane) {

        List<LineSeg> cutEdges = new ArrayList<>();

        // slice edges
        Arrays.stream(polygons).parallel().forEach(p -> { // TODO: test parallel performance
            Point3D start = null, end = null;

            for (LineSeg edge : p.genEdges()) {
                Point3D intersect = plane.lineSegmentIntersect(edge);
                if (intersect != null) {
                    if (start == null) {
                        start = intersect;
                    } else {
                        end = intersect; // NOTE: doesn't account for possibility of more than two intersection points
                        break;
                    }
                }
            }

            if (end != null) {
                cutEdges.add(new LineSeg(start, end));
            }
        });

        System.out.println("edges: " + cutEdges.size());

        // match edges
        List<LineSeg> current = new ArrayList<>();
        List<Polygon> output = new ArrayList<>();

        while (cutEdges.size() > 0) {

            if (cutEdges.size() == 1) break; // TODO: figure out why this is necessary (crashes in certain cases without [nextIndex of -1])

            current.add(cutEdges.get(0));

            while (true) {

                LineSeg top = current.get(current.size() - 1);
                Point3D topEnd = top.end;

                LineSeg next = null;
                int nextIndex = -1;
                boolean flipNext = false;
                float minDist = -999;

                for (int i = 0; i < cutEdges.size(); i++) { // TODO: try making parallel?
                    LineSeg e = cutEdges.get(i);
                    if (e != top) {

                        float startDist = topEnd.subtract(e.start).mag();
                        float endDist = topEnd.subtract(e.end).mag();

                        boolean flip = startDist > endDist;
                        float min = Math.min(startDist, endDist);

                        if (next == null || min < minDist) {
                            nextIndex = i;
                            next = e;
                            minDist = min;
                            flipNext = flip;
                        }
                    }
                }

                cutEdges.remove(nextIndex);

                if (next == current.get(0)) {
                    break;
                } else {
                    current.add(next);
                    if (flipNext) {
                        next.flipEnds();
                    }
                }
            }

            Point3D[] points = new Point3D[current.size()];
            IntStream.range(0, current.size()).forEach(i -> points[i] = current.get(i).start);

            Polygon currentPoly = new Polygon(points);
            currentPoly.color = Color.RED;

            output.add(currentPoly);

            current.clear();
        }

        return output.toArray(new Polygon[0]);
    }

}
