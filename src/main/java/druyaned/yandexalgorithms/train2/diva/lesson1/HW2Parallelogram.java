package druyaned.yandexalgorithms.train2.diva.lesson1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class HW2Parallelogram {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Point {
        public final int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        Scanner sin = new Scanner(reader);
        int n = Integer.parseInt(reader.readLine());
        for (int i = 0; i < n; ++i) {
            Point[] points = new Point[4];
            points[0] = new Point(sin.nextInt(), sin.nextInt());
            points[1] = new Point(sin.nextInt(), sin.nextInt());
            points[2] = new Point(sin.nextInt(), sin.nextInt());
            points[3] = new Point(sin.nextInt(), sin.nextInt());
            Arrays.sort(points, (p1, p2) -> p1.x == p2.x ? p1.y - p2.y : p1.x - p2.x);
            boolean equalWidths = points[0].x - points[2].x == points[1].x - points[3].x;
            boolean equalHeights = points[0].y - points[2].y == points[1].y - points[3].y;
            if (equalWidths && equalHeights) {
                writer.write("YES\n");
            } else {
                writer.write("NO\n");
            }
        }
    }
    
}
/*
input:
7
1 1 4 2 3 0 2 3
1 1 5 2 2 3 3 0
0 0 5 1 6 3 1 2
2 2 0 0 2 0 0 2
2 1 2 3 0 2 0 0
1 1 0 0 3 1 2 0
1 0 2 1 0 1 3 0
output:
YES
NO
YES
YES
YES
YES
YES
*/