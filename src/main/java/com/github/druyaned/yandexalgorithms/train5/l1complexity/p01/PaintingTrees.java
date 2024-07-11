// https://contest.yandex.ru/contest/59539/problems/
package com.github.druyaned.yandexalgorithms.train5.l1complexity.p01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class PaintingTrees {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int vasyaPosition = readInt(reader);
        int vasyaDistance = readInt(reader);
        int mashaPosition = readInt(reader);
        int mashaDistance = readInt(reader);
        int n = 4;
        Point[] points = new Point[n];
        points[0] = new Point(vasyaPosition - vasyaDistance, 1);
        points[1] = new Point(vasyaPosition + vasyaDistance, 2);
        points[2] = new Point(mashaPosition - mashaDistance, 1);
        points[3] = new Point(mashaPosition + mashaDistance, 2);
        Arrays.sort(points, (p1, p2) ->
                p1.position == p2.position ? p1.type - p2.type : p1.position - p2.position);
        showPoints(n, points); // TODO: debug
        int openCount = 0, openPosition = 0, treeCount = 0;
        for (int i = 0; i < n; i++) {
            if (points[i].type == 1) {
                if (openCount == 0) {
                    openPosition = points[i].position;
                }
                openCount++;
            }
            if (points[i].type == 2) {
                if (openCount == 1) {
                    treeCount += points[i].position - openPosition + 1;
                }
                openCount--;
            }
        }
        System.out.printf("treeCount=%d\n", treeCount); // TODO: debug
        writer.write(Integer.toString(treeCount)+ "\n");
    }
    
    private static class Point {
        public final int position, type; // type==1 - open, type==2 - close
        public Point(int position, int type) {
            this.position = position;
            this.type = type;
        }
    }
    
    private static void showPoints(int n, Point[] points) {
        System.out.println("points:");
        for (int i = 0; i < n; i++) {
            if (points[i].type == 1) {
                System.out.printf(" O[%d]", points[i].position);
            } else {
                System.out.printf(" c[%d]", points[i].position);
            }
        }
        System.out.println();
    }
    
    private static final char[] buf = new char[16];
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
    
}
/*
inputs:
3 5
20 5

20 5
3 5

3 0
5 0

5 0
3 0

3 0
3 0

-2 3
1 2

1 2
-2 3

4 2
4 3

4 3
4 2
*/