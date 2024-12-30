package druyaned.yandexalgorithms.train1.l7events;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HW3AudienceSeating {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Point implements Comparable<Point> {
        
        private final int pos, ind;
        
        private Point(int pos, int ind) {
            this.pos = pos;
            this.ind = ind;
        }
        
        @Override
        public int compareTo(Point p) {
            return pos == p.pos ? ind - p.ind : pos - p.pos;
        }
        
    }
    
    private static int rightBinSearch(Point[] points, int pos) {
        int left = 0, right = points.length - 1, mid;
        while (left < right) {
            mid = (left + right + 1) / 2;
            if (pos >= points[mid].pos) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = Integer.parseInt(elems[0]);
        int d = Integer.parseInt(elems[1]);
        elems = reader.readLine().split(" ");
        Point[] points = new Point[n];
        for (int i = 0; i < n; ++i) {
            int pos = Integer.parseInt(elems[i]);
            points[i] = new Point(pos, i);
        }
        // solve
        Arrays.sort(points);
        int[] vars = new int[n];
        int maxVar = 1;
        for (int i = 0, curVar = 0; i < n; ++i, ++curVar) {
            int pos = points[i].pos + d;
            int j = rightBinSearch(points, pos);
            int varN = j - i + 1;
            if (varN > maxVar) {
                maxVar = varN;
            }
            curVar %= maxVar;
            vars[points[i].ind] = (curVar % maxVar) + 1;
        }
        writer.write(maxVar + "\n" + vars[0]);
        for (int i = 1; i < n; ++i) {
            writer.write(" " + vars[i]);
        }
        writer.write('\n');
    }
    
}
/*
d=6
0 3 6 8 11 15 18
----
0 3 6 (1)
3 6 8 (2)
6 8 11 (3)
8 11 (1)
11 15 (2)
15 18 (3)
18 (1)

Ход решения: 1) сортирую; 2) для каждой точки ищу
правым бин-поиском ближайшего <= x[i] + d; 3) обновляю
maxVar, если j - i + 1 > maxVar; 4) записываю (curVar % maxVar) + 1.

Input:
7 6
15 6 18 8 3 0 11
Output:
3
3 3 1 1 2 1 2
*/