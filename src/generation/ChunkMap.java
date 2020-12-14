package generation;

import rendering.Point3D;

import java.util.HashMap;

public class ChunkMap {

    private HashMap<Point3D, Chunk> map = new HashMap<>();

    public Chunk getRaw(int x, int y, int z) {
        return map.get(new Point3D(x, y, z));
    }

    public Chunk getUngenerated(int x, int y, int z) {

        Point3D key = new Point3D(x, y, z);

        Chunk existing = map.get(key);

        if (existing == null) {
            existing = new Chunk(this, x, y, z);
            map.put(key, existing);
        }
        return existing;
    }

    public Chunk get(int x, int y, int z) {

        Point3D key = new Point3D(x, y, z);

        Chunk existing = map.get(key);

        if (existing != null) {

            if (!existing.isGenerated()) {
                existing.generate();
            }

            return existing;
        }

        Chunk newChunk = new Chunk(this, x, y, z);

        newChunk.generate();

        map.put(key, newChunk);
        return newChunk;
    }

    private void createIfNull(int x, int y, int z) {

        Point3D key = new Point3D(x, y, z);

        if (map.get(key) == null) {
            map.put(key, new Chunk(this, x, y, z));
        }
    }

    private int toTileIndex(int totalIndex) {
        return (totalIndex < 0) ? (-totalIndex % Chunk.CHUNK_SIZE == 0) ? 0 : Chunk.CHUNK_SIZE - (-totalIndex % Chunk.CHUNK_SIZE)
                : totalIndex % Chunk.CHUNK_SIZE;
    }

    public TileNode getNode(int x, int y, int z) { // indices, not coordinates


        if (x < 0) x -= Chunk.CHUNK_SIZE; // sus
        if (y < 0) y -= Chunk.CHUNK_SIZE;
        if (z < 0) z -= Chunk.CHUNK_SIZE;

        return getUngenerated(x / Chunk.CHUNK_SIZE, y / Chunk.CHUNK_SIZE, z / Chunk.CHUNK_SIZE).getNodes()[toTileIndex(x)][toTileIndex(y)][toTileIndex(z)];
    }
}
