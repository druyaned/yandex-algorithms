package druyaned.yandexalgorithms.train5.l3setmap.p07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class BuildSquare {
    
    private static final char[] BUFFER = new char[(int)2e6];
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int CAPACITY = 2000;
        int pointCount = readInt(reader);
        Point[] points = new Point[CAPACITY];
        HashSet<Point> table = new HashSet(CAPACITY);
        for (int i = 0; i < pointCount; i++) {
            int x = readInt(reader);
            int y = readInt(reader);
            points[i] = new Point(x, y);
            table.add(points[i]);
        }
        int minPointCountToAdd = 3;
        Point[] pointsToAdd = new Point[minPointCountToAdd];
        pointsToAdd[0] = new Point(points[0].x, points[0].y + 1);
        pointsToAdd[1] = new Point(points[0].x + 1, points[0].y + 1);
        pointsToAdd[2] = new Point(points[0].x + 1, points[0].y);
        for (int i = 0; i < pointCount; i++) {
            Point p1 = points[i];
            for (int j = i + 1; j < pointCount; j++) {
                Point p2 = points[j];
                int dx = p1.x - p2.x;
                int dy = p1.y - p2.y;
                Point p3v1 = new Point(p2.x - dy, p2.y + dx);
                Point p3v2 = new Point(p2.x + dy, p2.y - dx);
                Point p4v1 = new Point(p1.x - dy, p1.y + dx);
                Point p4v2 = new Point(p1.x + dy, p1.y - dx);
                boolean zeroV1 = table.contains(p3v1) && table.contains(p4v1);
                boolean zeroV2 = table.contains(p3v2) && table.contains(p4v2);
                boolean oneV1 = table.contains(p3v1);
                boolean oneV2 = table.contains(p4v1);
                boolean oneV3 = table.contains(p3v2);
                boolean oneV4 = table.contains(p4v2);
                if (zeroV1 || zeroV2) {
                    writer.write("0\n");
                    return;
                } else if (oneV1 && minPointCountToAdd > 1) {
                    minPointCountToAdd = 1;
                    pointsToAdd[0] = p4v1;
                } else if (oneV2 && minPointCountToAdd > 1) {
                    minPointCountToAdd = 1;
                    pointsToAdd[0] = p3v1;
                } else if (oneV3 && minPointCountToAdd > 1) {
                    minPointCountToAdd = 1;
                    pointsToAdd[0] = p4v2;
                } else if (oneV4 && minPointCountToAdd > 1) {
                    minPointCountToAdd = 1;
                    pointsToAdd[0] = p3v2;
                } else if (minPointCountToAdd > 2) {
                    minPointCountToAdd = 2;
                    pointsToAdd[0] = p3v1;
                    pointsToAdd[1] = p4v1;
                }
            }
        }
        writer.write(Integer.toString(minPointCountToAdd) + "\n");
        for (int i = 0; i < minPointCountToAdd; i++) {
            writer.write(Integer.toString(pointsToAdd[i].x) + " " + pointsToAdd[i].y + "\n");
        }
    }
    
    private static int readInt(Reader reader) throws IOException {
        int bufferLength = nextTokenInBuffer(reader);
        return Integer.parseInt(new String(BUFFER, 0, bufferLength));
    }
    
    private static int nextTokenInBuffer(Reader reader) throws IOException {
        int ch;
        while ((ch = reader.read()) != -1 && Character.isWhitespace(ch)) {}
        if (ch == -1) {
            return 0;
        }
        BUFFER[0] = (char)ch;
        int bufferLength = 1;
        while ((ch = reader.read()) != -1 && !Character.isWhitespace(ch)) {
            BUFFER[bufferLength++] = (char)ch;
        }
        return bufferLength;
    }
    
}

class Point {
    
    public static final int MODULO = 30011;
    
    public final int x, y, hash;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        int h = MODULO * (x % MODULO) + y % MODULO;
        this.hash = h >= 0 ? h : MODULO * MODULO + h;
    }
    
    @Override public int hashCode() {
        return hash;
    }
    
    @Override public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final Point other = (Point)obj;
        return x == other.x && y == other.y;
    }
    
}
