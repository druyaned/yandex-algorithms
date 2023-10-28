package com.github.druyaned.yandexalgorithms.train1.l7events;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HW2PointsAndSegments {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Point {
        
        private final int type, pos, ind; // 2==start, 1==point, 0==end
        
        private Point(int type, int pos) {
            this.type = type;
            this.pos = pos;
            this.ind = -1;
        }
        
        private Point(int type, int pos, int ind) {
            this.type = type;
            this.pos = pos;
            this.ind = ind;
        }
        
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = Integer.parseInt(elems[0]);
        int m = Integer.parseInt(elems[1]);
        int pointsN = 2 * n + m;
        Point[] points = new Point[pointsN];
        for (int i = 0; i < n; ++i) {
            elems = reader.readLine().split(" ");
            int start = Integer.parseInt(elems[0]);
            int end = Integer.parseInt(elems[1]);
            if (start > end) {
                int temp = start;
                start = end;
                end = temp;
            }
            points[i * 2] = new Point(2, start);
            points[i * 2 + 1] = new Point(0, end);
        }
        elems = reader.readLine().split(" ");
        int[] x = new int[m];
        for (int i = 0; i < m; ++i) {
            x[i] = Integer.parseInt(elems[i]);
            points[i + 2 * n] = new Point(1, x[i], i);
        }
        // solve
        Arrays.sort(points, (p1, p2) -> p1.pos == p2.pos ? p2.type - p1.type : p1.pos - p2.pos);
        int[] segmCnt = new int[m];
        int curSegmCnt = 0;
        for (int i = 0; i < pointsN; ++i) {
            switch (points[i].type) {
                case 2 -> {++curSegmCnt;} // start
                case 1 -> {segmCnt[points[i].ind] = curSegmCnt;} // point
                default -> {--curSegmCnt;} // end
            }
        }
        writer.write(Integer.toString(segmCnt[0]));
        for (int i = 1; i < m; ++i) {
            writer.write(" " + segmCnt[i]);
        }
        writer.write('\n');
    }
    
}
/*
Input:
3 2
6 0
-3 6
6 10
1 6
Output:
2 3
*/
