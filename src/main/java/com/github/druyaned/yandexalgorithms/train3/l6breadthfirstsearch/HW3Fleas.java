package com.github.druyaned.yandexalgorithms.train3.l6breadthfirstsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HW3Fleas {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static final char[] buf = new char[15];
    
    private static int readInt(Reader reader) throws IOException {
        int c;
        while ((c = reader.read()) != -1 && c != '+' && c != '-' && !('0' <= c && c <= '9')) {}
        if (c == -1) {
            throw new NumberFormatException("empty input");
        }
        buf[0] = (char)c;
        int l = 1;
        while ((c = reader.read()) != -1 && '0' <= c && c <= '9') {
            buf[l++] = (char)c;
        }
        return Integer.parseInt(new String(buf, 0, l));
    }
    
    private static class Point {
        public final int r, c;
        public Point(int row, int column) {
            this.r = row;
            this.c = column;
        }
    }
    
    private static class Table {
        public final int n, m;
        private final int[][] table;
        public Table(int n, int m) {
            this.n = n;
            this.m = m;
            table = new int[n + 4][m + 4];
            for (int i = 0; i < n + 4; ++i) {
                table[i][0] = table[i][1] = -1;
                table[i][m + 2] = table[i][m + 3] = -1;
            }
            for (int j = 2; j < m + 2; ++j) {
                table[0][j] = table[1][j] = -1;
                table[n + 2][j] = table[n + 3][j] = -1;
            }
        }
        public int get(Point p) {
            return table[p.r + 1][p.c + 1];
        }
        public void set(Point p, int value) {
            table[p.r + 1][p.c + 1] = value;
        }
        private static final int MOVE_L = 8;
        private static final int[] moveR = {+2, +1, -1, -2, -2, -1, +1, +2};
        private static final int[] moveC = {+1, +2, +2, +1, -1, -2, -2, -1};
        public void knightMoves(Point p, int newDist, List<List<Point>> pointsOfDist) {
            for (int i = 0; i < MOVE_L; ++i) {
                int r = p.r + 1 + moveR[i], c = p.c + 1 + moveC[i];
                if (table[r][c] == 0) {
                    table[r][c] = newDist;
                    pointsOfDist.get(newDist).add(new Point(r - 1, c - 1));
                }
            }
        }
        public void show() {
            System.out.print("\ntable:\n");
            for (int i = 0; i < n + 4; ++i) {
                for (int j = 0; j < m + 4; ++j) {
                    System.out.printf(" %2d", table[i][j]);
                }
                System.out.println();
            }
        }
    }
    
    private static void show(List<List<Point>> pointsOfDist, int d) {
        System.out.printf("pointsOfDists(%d):", d);
        for (Point p : pointsOfDist.get(d)) {
            System.out.printf(" (%d %d)", p.r, p.c);
        }
        System.out.println();
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int MAX_DIST = 250;
        int n = readInt(reader);
        int m = readInt(reader);
        Point feeder = new Point(readInt(reader), readInt(reader));
        int q = readInt(reader);
        Point[] feals = new Point[q];
        for (int i = 0; i < q; ++i) {
            feals[i] = new Point(readInt(reader), readInt(reader));
        }
        Table table = new Table(n, m);
        table.set(feeder, -1);
        List<List<Point>> pointsOfDist = new ArrayList(MAX_DIST + 1);
        for (int i = 0; i <= MAX_DIST; ++i) {
            pointsOfDist.add(new ArrayList());
        }
        pointsOfDist.get(0).add(feeder);
        for (int d = 0; !pointsOfDist.get(d).isEmpty(); ++d) {
            for (Point p : pointsOfDist.get(d)) {
                table.knightMoves(p, d + 1, pointsOfDist);
            }
            table.show(); // TODO: debug
            show(pointsOfDist, d); // TODO: debug
        }
        long sumDist = 0L;
        for (int i = 0; i < q; ++i) {
            if (table.get(feals[i]) == 0) {
                writer.write("-1\n");
                System.out.print("-1\n"); // TODO: debug
                return;
            }
            if (feals[i].r != feeder.r || feals[i].c != feeder.c) {
                sumDist += table.get(feals[i]);
            }
        }
        writer.write(sumDist + "\n");
        System.out.print(sumDist + "\n"); // TODO: debug
    }
    
}
/*
input:
6 7 1 6 8
2 2
3 2
3 4
4 7
5 5
5 6
6 1
6 5
output:
24
*/