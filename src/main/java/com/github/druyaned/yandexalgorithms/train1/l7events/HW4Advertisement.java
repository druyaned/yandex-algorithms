package com.github.druyaned.yandexalgorithms.train1.l7events;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HW4Advertisement {
    
    private static final int CLIP_DUR = 5;
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Point implements Comparable<Point> {
        private final int type; // 1==in, 0==out
        private final int pos, id;
        public Point(int type, int pos, int id) {
            this.type = type;
            this.pos = pos;
            this.id = id;
        }
        @Override public int compareTo(Point p) {
            return pos == p.pos ? p.type - type : pos - p.pos;
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        
        int n = Integer.parseInt(reader.readLine());
        Point[] points = new Point[2 * n];
        int l = 0;
        for (int i = 0; i < n; ++i) {
            String[] elems = reader.readLine().split(" ");
            int in = Integer.parseInt(elems[0]);
            int out = Integer.parseInt(elems[1]);
            if (out - in >= CLIP_DUR) {
                points[l++] = new Point(1, in, i);
                points[l++] = new Point(0, out - CLIP_DUR, i);
            }
        }
        // solve
        Arrays.sort(points, 0, l);
        boolean[] involved = new boolean[n];
        int maxCnt = 0;
        int maxT1 = 0, maxT2 = 0;
        int cnt1 = 0;
        for (int i = 0; i < l; ++i) {
            if (points[i].type == 1) {
                involved[points[i].id] = true;
                ++cnt1;
            }
            int t1 = points[i].pos;
            if (cnt1 > maxCnt) {
                maxCnt = cnt1;
                maxT1 = t1;
                maxT2 = 0;
            }
            int cnt2 = 0;
            for (int j = i + 1; j < l; ++j) {
                if (!involved[points[j].id] && points[j].type == 1) {
                    ++cnt2;
                }
                int t2 = points[j].pos;
                if (t1 + CLIP_DUR <= t2 && cnt1 + cnt2 > maxCnt) {
                    maxCnt = cnt1 + cnt2;
                    maxT1 = t1;
                    maxT2 = t2;
                }
                if (!involved[points[j].id] && points[j].type == 0) {
                    --cnt2;
                }
            }
            if (points[i].type == 0) {
                involved[points[i].id] = false;
                --cnt1;
            }
        }
        if (maxCnt == 0) {
            writer.write("0 1 6\n");
        } else {
            if (maxT1 > maxT2) {
                int temp = maxT1;
                maxT1 = maxT2;
                maxT2 = temp;
            }
            if (maxT1 == 0) {
                maxT1 = maxT2;
                maxT2 = maxT1 + CLIP_DUR;
            }
            writer.write(maxCnt + " " + maxT1 + " " + maxT2 + "\n");
        }
    }
    
}
/*
Input:
7
2 20
10 27
22 29
34 44
36 78
41 51
44 61
Output:
5 10 44

Input: (special case)
1
1 1
Output:
0 1 6

Основные идеи решения:
1) меняю out на out - 5, т.к. после этого просмотр невозможен;
2) теперь длина ролика схлопнулась, по сути, в точку;
3) буду искать 2 момента времени циклом в цикле
    i in [0, l] и j in [i + 1, l];
4) обновляю макс-значения, когда t1 + 5 <= t2;
5) не забываю учитывать тех, которые задействованы
    во внешнем цикле.
*/
