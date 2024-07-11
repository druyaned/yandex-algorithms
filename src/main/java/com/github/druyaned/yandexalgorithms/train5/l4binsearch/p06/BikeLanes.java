package com.github.druyaned.yandexalgorithms.train5.l4binsearch.p06;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class BikeLanes {
    
    private static final char[] buffer = new char[(int)2e6];
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int width = readInt(reader);
        int height = readInt(reader);
        int tileCount = readInt(reader);
        Tile[] tiles = new Tile[tileCount];
        for (int i = 0; i < tileCount; i++) {
            int x = readInt(reader);
            int y = readInt(reader);
            tiles[i] = new Tile(x, y);
        }
        Arrays.sort(tiles, (t1, t2) -> t1.x == t2.x ? t1.y - t2.y : t1.x - t2.x);
        Projection[] projs = makeProjs(tileCount, tiles);
        int projCount = projs.length;
        int last = projCount - 1;
        int[] forwMinY = new int[projCount];
        int[] forwMaxY = new int[projCount];
        int[] backMinY = new int[projCount];
        int[] backMaxY = new int[projCount];
        forwMinY[0] = projs[0].minY;
        forwMaxY[0] = projs[0].maxY;
        backMinY[last] = projs[last].minY;
        backMaxY[last] = projs[last].maxY;
        for (int i = 1; i < projCount; i++) {
            forwMinY[i] = Integer.min(forwMinY[i - 1], projs[i].minY);
            forwMaxY[i] = Integer.max(forwMaxY[i - 1], projs[i].maxY);
        }
        for (int i = last - 1; i >= 0; i--) {
            backMinY[i] = Integer.min(backMinY[i + 1], projs[i].minY);
            backMaxY[i] = Integer.max(backMaxY[i + 1], projs[i].maxY);
        }
        int leftLen = 1;
        int rightLen = Integer.min(width, height);
        while (leftLen < rightLen) {
            int midLen = (leftLen + rightLen) / 2;
            boolean coverAllTiles = false;
            for (int bgn = 0, end = 0; end < projCount; bgn++) {
                while (end < projCount && calcLen(projs, bgn, end) <= midLen) {
                    end++;
                }
                int minY;
                int maxY;
                if (bgn > 0 && end < projCount) {
                    minY = backMinY[end];
                    maxY = backMaxY[end];
                    if (minY > forwMinY[bgn - 1]) {
                        minY = forwMinY[bgn - 1];
                    }
                    if (maxY < forwMaxY[bgn - 1]) {
                        maxY = forwMaxY[bgn - 1];
                    }
                } else if (bgn == 0 && end < projCount) {
                    minY = backMinY[end];
                    maxY = backMaxY[end];
                } else if (bgn > 0 && end == projCount) {
                    minY = forwMinY[bgn - 1];
                    maxY = forwMaxY[bgn - 1];
                } else {
                    coverAllTiles = true;
                    break;
                }
                if (maxY - minY + 1 <= midLen) {
                    coverAllTiles = true;
                    break;
                }
            }
            if (coverAllTiles) {
                rightLen = midLen;
            } else {
                leftLen = midLen + 1;
            }
        }
        int len = leftLen;
        writer.write(Integer.toString(len) + "\n");
    }
    
    private static Projection[] makeProjs(int tileCount, Tile[] tiles) {
        int projCount = 1;
        for (int i = 1; i < tileCount; i++) {
            if (tiles[i].x != tiles[i - 1].x) {
                projCount++;
            }
        }
        Projection[] projs = new Projection[projCount];
        int minY = tiles[0].y;
        int maxY = tiles[0].y;
        for (int i = 1, j = 0; i < tileCount; i++) {
            if (tiles[i].x == tiles[i - 1].x) {
                if (minY > tiles[i].y) {
                    minY = tiles[i].y;
                }
                if (maxY < tiles[i].y) {
                    maxY = tiles[i].y;
                }
            } else {
                projs[j++] = new Projection(tiles[i - 1].x, minY, maxY);
                minY = tiles[i].y;
                maxY = tiles[i].y;
            }
        }
        projs[projCount - 1] = new Projection(tiles[tileCount - 1].x, minY, maxY);
        return projs;
    }
    
    private static int calcLen(Projection[] projs, int fst, int lst) {
        return projs[lst].x - projs[fst].x + 1;
    }
    
    private static int readInt(Reader reader) throws IOException {
        int bufferLength = nextTokenInBuffer(reader);
        return Integer.parseInt(new String(buffer, 0, bufferLength));
    }
    
    private static int nextTokenInBuffer(Reader reader) throws IOException {
        int ch;
        while ((ch = reader.read()) != -1 && Character.isWhitespace(ch)) {}
        if (ch == -1) {
            return 0;
        }
        buffer[0] = (char)ch;
        int bufferLength = 1;
        while ((ch = reader.read()) != -1 && !Character.isWhitespace(ch)) {
            buffer[bufferLength++] = (char)ch;
        }
        return bufferLength;
    }
    
}

class Tile {
    
    public final int x, y;
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
}

class Projection {
    
    public final int x;
    public final int minY, maxY;
    
    public Projection(int x, int yMin, int yMax) {
        this.x = x;
        this.minY = yMin;
        this.maxY = yMax;
    }
    
}
/*
8 6 7
6 5
4 3
7 4
2 3
4 2
3 4
4 4
output:
3

6| * * * * * * * *
5| * * * * * 6 * *
4| * * 2 5 * * 7 *
3| * 1 * 4 * * * *
2| * * * 3 * * * *
1| * * * * * * * *
-+----------------
0 1 2 3 4 5 6 7 8

Projections:
(2: 3->3)
(3: 4->4)
(4: 2->4)
(6: 5->5)
(7: 4->4)

forwMinY: 3 3 2 2 2
forwMaxY: 3 4 4 5 5
backMinY: 2 2 2 4 4
backMaxY: 5 5 5 5 4

l=3 bgn=0 end=3 minY=4 maxY=5
l=2 bgn=0 end=2 minY=2 maxY=5
l=2 bgn=1 end=3 minY=3 maxY=5
l=2 bgn=2 end=3 minY=3 maxY=5
l=2 bgn=3 end=5 minY=2 maxY=4
3
*/
