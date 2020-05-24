package generation;

import rendering.Point3D;
import rendering.Polygon;

import java.util.ArrayList;
import java.util.List;

public class Chunk {

    public static final int CHUNK_SIZE = 10;

    private final TileNode[][][] nodes = new TileNode[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
    private final Tile[][][] tiles = new Tile[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];

    private final Point3D indices;
    private final ChunkMap map;
    private boolean generated = false;

    public Chunk(ChunkMap map, int x, int y, int z) {

        indices = new Point3D(x, y, z);
        this.map = map;

        generateNoise();
    }

    public static float noiseAt(Point3D point) {

        float div = 35;
        float noise = (float) SimplexNoise.noise(point.x/div, point.y/div, point.z/div) * 5;
        if (point.y < -10) {
            noise = noise - (point.y + 10);
        }

        return noise;
    }

    public void generateNoise() {

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {

                    nodes[x][y][z] = new TileNode(findPos(x, y, z), noiseAt(findPos(x, y, z)));
                }
            }
        }
    }

    public Point3D findPos(int x, int y, int z) {
        return new Point3D((indices.x * Chunk.CHUNK_SIZE + x) * Tile.TILE_SIZE, (indices.y * Chunk.CHUNK_SIZE + y) * Tile.TILE_SIZE, (indices.z * Chunk.CHUNK_SIZE + z) * Tile.TILE_SIZE);
    }

    public void generate() {

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    tiles[x][y][z] = new Tile();
                    tiles[x][y][z].setPolygons(genFaces(x, y, z));
                }
            }
        }
        generated = true;
    }

    public Point3D findMid(int x, int y, int z, int x2, int y2, int z2) {

        TileNode node = getNode(x, y, z);
        TileNode other = getNode(x2, y2, z2);

        if (isFilled(node) && !isFilled(other)) {
            return node.getPos().avg(other.getPos(), interpolateAmount(other));
        } else if (!isFilled(node) && isFilled(other)) {
            return other.getPos().avg(node.getPos(), interpolateAmount(node));
        }
        return null;
    }

    public Polygon[] genFaces(int x, int y, int z) {

        Point3D[] vertList = new Point3D[12];

        int cubeindex = 0;
        if (isFilled(getNode(x, y, z))) cubeindex += 1; // 0
        if (isFilled(getNode(x+1, y, z))) cubeindex += 2; // 1
        if (isFilled(getNode(x+1, y+1, z))) cubeindex += 4; // 2
        if (isFilled(getNode(x, y+1, z))) cubeindex += 8; // 3
        if (isFilled(getNode(x, y, z+1))) cubeindex += 16; // 4
        if (isFilled(getNode(x+1, y, z+1))) cubeindex += 32; // 5
        if (isFilled(getNode(x+1, y+1, z+1))) cubeindex += 64; // 6
        if (isFilled(getNode(x, y+1, z+1))) cubeindex += 128; // 7

        int[] indices = TriTable.table[cubeindex];

        // fill vert list
        vertList[0] = findMid(x, y, z, x+1, y, z); // 0-1
        vertList[1] = findMid(x+1, y, z, x+1, y+1, z); // 1-2
        vertList[2] = findMid(x+1, y+1, z, x, y+1, z); // 2-3
        vertList[3] = findMid(x, y+1, z, x, y, z); // 3-0

        vertList[4] = findMid(x, y, z+1, x+1, y, z+1); // 4-5
        vertList[5] = findMid(x+1, y, z+1, x+1, y+1, z+1); // 5-6
        vertList[6] = findMid(x+1, y+1, z+1, x, y+1, z+1); // 6-7
        vertList[7] = findMid(x, y+1, z+1, x, y, z+1); // 7-4

        vertList[8] = findMid(x, y, z, x, y, z+1); // 0-4
        vertList[9] = findMid(x+1, y, z, x+1, y, z+1); // 1-5
        vertList[10] = findMid(x+1, y+1, z, x+1, y+1, z+1); // 2-6
        vertList[11] = findMid(x, y+1, z, x, y+1, z+1); // 3-7



        // create polygons TODO: make array after remove -1's from table
        List<Polygon> polygons = new ArrayList<>();

        for (int i = 0; i < indices.length; i += 3) {

            if (indices[i] == -1) {
                break;
            }
            polygons.add(new Polygon(vertList[indices[i + 2]], vertList[indices[i + 1]], vertList[indices[i]]));
        }

        return polygons.toArray(new Polygon[0]);
    }

    /*public Polygon[] genFaces(int x, int y, int z) {

        Point3D[] verts = genVertices(x, y, z);

        Polygon[] polygons = new Polygon[(verts.length > 0) ? verts.length - 2 : 0];

        for (int i = 0; i < polygons.length; i++) {

            polygons[i] = new Polygon(verts[i], verts[(i + 1) % verts.length], verts[(i + 2) % verts.length]);

        }
        return polygons;

        /*List<Polygon> polys = new ArrayList<>();

        for (int i = 0; i < verts.length; i++) {
            for (int j = 0; j < verts.length; j++) {

                if (i != j) {
                    for (int k = 0; k < verts.length; k++) {

                        if (i != k && j != k) {

                            Polygon polygon = new Polygon(verts[i], verts[j], verts[k]);

                            polys.add(polygon);
                        }
                    }
                }
            }
        }

        return polys.toArray(new Polygon[0]);*/
    //}

    public Point3D[] genVertices(int x, int y, int z) {

        List<Point3D> verts = new ArrayList<>();

        for (int thisX = x; thisX <= x + 1; thisX++) {
            for (int thisY = y; thisY <= y + 1; thisY++) {
                for (int thisZ = z; thisZ <= z + 1; thisZ++) {

                    TileNode node = getNode(thisX, thisY, thisZ);

                    if (isFilled(node)) {

                        tryAddVert(verts, node, getNode(thisX - 1, thisY, thisZ));
                        tryAddVert(verts, node, getNode(thisX + 1, thisY, thisZ));
                        tryAddVert(verts, node, getNode(thisX, thisY - 1, thisZ));
                        tryAddVert(verts, node, getNode(thisX, thisY + 1, thisZ));
                        tryAddVert(verts, node, getNode(thisX, thisY, thisZ - 1));
                        tryAddVert(verts, node, getNode(thisX, thisY, thisZ + 1));

                    }
                }
            }
        }


        return verts.toArray(new Point3D[0]);
    }

    public static boolean isFilled(TileNode node) {

        //return false;
        return node.getNoise() >= 1;
    }

    public static float interpolateAmount(TileNode node) {
        return Math.min(1 - node.getNoise(), 0.5F);
    }

    public static void tryAddVert(List<Point3D> verts, TileNode node, TileNode other) {

        if (!isFilled(other)) {
            verts.add(node.getPos().avg(other.getPos()));
        }
    }

    public TileNode getNode(int x, int y, int z) {

        if (x < 0 || x >= CHUNK_SIZE || y < 0 || y >= CHUNK_SIZE || z < 0 || z >= CHUNK_SIZE) {
            return map.getNode((int) indices.x * Chunk.CHUNK_SIZE + x, (int) indices.y * Chunk.CHUNK_SIZE + y, (int) indices.z * Chunk.CHUNK_SIZE + z);
        }

        return nodes[x][y][z];
    }

    public Tile[][][] getTiles() {
        return tiles;
    }

    public boolean isGenerated() {
        return generated;
    }

    public TileNode[][][] getNodes() {
        return nodes;
    }
}
