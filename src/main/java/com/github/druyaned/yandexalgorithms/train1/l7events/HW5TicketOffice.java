package com.github.druyaned.yandexalgorithms.train1.l7events;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HW5TicketOffice {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Point implements Comparable<Point> {
        private final int type; // 1==open, 0==close
        private final int t, id;
        public Point(int type, int t, int id) {
            this.type = type;
            this.t = t;
            this.id = id;
        }
        @Override public int compareTo(Point p) {
            return t == p.t ? type - p.type : t - p.t;
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = Integer.parseInt(reader.readLine());
        int l = 2 * n;
        Point[] points = new Point[l];
        for (int i = 0; i < n; ++i) {
            String[] elems = reader.readLine().split(" ");
            int h1 = Integer.parseInt(elems[0]);
            int m1 = Integer.parseInt(elems[1]);
            int h2 = Integer.parseInt(elems[2]);
            int m2 = Integer.parseInt(elems[3]);
            int openTime = h1 * 60 + m1;
            int closeTime = h2 * 60 + m2;
            points[2 * i] = new Point(1, openTime, i);
            points[2 * i + 1] = new Point(0, closeTime, i);
        }
        // solve
        Arrays.sort(points);
        boolean[] opened = new boolean[n];
        int openedN = 0;
        for (int i = 0; i < l; ++i) {
            final Point p = points[i];
            if (p.type == 0 && opened[p.id]) { // close
                --openedN;
                opened[p.id] = false;
            }
            if (p.type == 1) { // open
                ++openedN;
                opened[p.id] = true;
            }
        }
        int prevStart = 0;
        int allOpenDur = 0;
        for (int i = 0; i < l; ++i) {
            final Point p = points[i];
            if (openedN == n) {
                allOpenDur += p.t - prevStart;
            }
            if (p.type == 0) { // close
                --openedN;
            }
            if (p.type == 1) { // open
                ++openedN;
            }
            if (openedN == n) {
                prevStart = p.t;
            }
        }
        if (openedN == n) {
            allOpenDur += 24 * 60 - prevStart;
        }
        writer.write(allOpenDur + "\n");
    }
    
}
/*
Input:
3
12 0 12 0
8 30 20 30
16 0 9 20
Output:
320

Ход решения:
1) сортировка;
2) потом прохожу по всем событиям 2 раза;
3) прохожу сначала по тем, которые сначала закрываются;
4) надо будет хранить тех, что открыты;
5) во 2-й заход у меня уже будет пару открытых касс;
6) ну и если это количество == n, то update(allOpenCount).
*/
