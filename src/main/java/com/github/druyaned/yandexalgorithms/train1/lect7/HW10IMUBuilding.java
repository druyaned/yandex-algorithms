package com.github.druyaned.yandexalgorithms.train1.lect7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HW10IMUBuilding {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Point implements Comparable<Point> {
        private final int id;
        private final int type; // 1==in, 0==out; out first
        private final int s, z;
        private Point(int id, int type, int s, int z) {
            this.id = id;
            this.type = type;
            this.s = s;
            this.z = z;
        }
        @Override public int compareTo(Point s) {
            return z == s.z ? type - s.type : z - s.z;
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = Integer.parseInt(elems[0]);
        int w = Integer.parseInt(elems[1]);
        int l = Integer.parseInt(elems[2]);
        final int LEN = 2 * n;
        Point[] points = new Point[LEN];
        for (int i = 0; i < n; ++i) {
            elems = reader.readLine().split(" ");
            int x1 = Integer.parseInt(elems[0]);
            int y1 = Integer.parseInt(elems[1]);
            int z1 = Integer.parseInt(elems[2]);
            int x2 = Integer.parseInt(elems[3]);
            int y2 = Integer.parseInt(elems[4]);
            int z2 = Integer.parseInt(elems[5]);
            int s = (x2 - x1) * (y2 - y1);
            points[2 * i] = new Point(i + 1, 1, s, z1);
            points[2 * i + 1] = new Point(i + 1, 0, s, z2);
        }
        // solve
        Arrays.sort(points);
        int targetS = l * w;
        int curS = 0;
        int cnt = 0;
        int minCnt = n + 1;
        for (int i = 0; i < LEN; ++i) {
            final int type = points[i].type;
            final int s = points[i].s;
            if (type == 1) { // in
                curS += s;
                ++cnt;
            }
            if (type == 0) {
                curS -= s;
                --cnt;
            }
            if (curS == targetS && minCnt > cnt) {
                minCnt = cnt;
            }
        }
        if (minCnt == n + 1) {
            writer.write("NO\n");
            return;
        }
        boolean[] involved = new boolean[n + 1];
        for (int i = 0; i < LEN; ++i) {
            final int type = points[i].type;
            final int s = points[i].s;
            final int id = points[i].id;
            if (type == 1) { // in
                curS += s;
                involved[id] = true;
                ++cnt;
            }
            if (type == 0) {
                curS -= s;
                involved[id] = false;
                --cnt;
            }
            if (curS == targetS && minCnt == cnt) {
                writer.write("YES\n" + minCnt + "\n");
                for (int j = 1; j <= n; ++j) {
                    if (involved[j]) {
                        writer.write(j + "\n");
                    }
                }
                return;
            }
        }
    }
    
}
/*
Ввод:
n w l
x1[1] y1[1] z1[1] x2[1] y2[1] z2[1]
x1[2] y1[2] z1[2] x2[2] y2[2] z2[2]
...
x1[n] y1[n] z1[n] x2[n] y2[n] z2[n]
Вывод:
ans
cnt
involved[1]
...
involved[m]

n - количество возможных блоков;
w - ширина площадки;
l - длина площадки;
x1, y1, z1, x2, y2, z2 - противоположные углы места для блока;
ans - YES / NO, т.е. можно ли построить перекрытие;
cnt - минимальное число блоков для этого;
involved - используемые блоки в порядке следования в input.txt;
гарантируется, что места установки блоков не пересекаются друг с другом.

Перекрытие - это сплошной горизонтальный слой ненулевой толщины.
Надо найти минимальное число блоков, которое образует этот монотонный слой,
с учетом того, что места монтажа не пересекаются.
Монотонный слой образуется, когда сумма площадей блоков == w * l.
Ну и все, дальше все понятно. Стоит отметить, что проходить все будет в 2 этапа:
сначала поиск минимального количества, а потом, если все чики, будут искать involved[].

input:
4 12 8
0 0 5 8 4 9
8 0 4 12 8 7
0 4 6 3 8 11
3 4 6 8 8 8
output:
YES
4
1 2 3 4

input:
5 12 8
0 0 5 8 4 9
8 0 4 12 8 7
0 4 6 3 8 11
3 4 6 8 8 8
0 0 11 12 8 14
output:
YES
1
5
*/
