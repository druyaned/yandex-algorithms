package druyaned.yandexalgorithms.train1.l7events;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HW8Security {
    
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
        private final int time, id;
        private Point(int type, int time, int id) {
            this.type = type;
            this.time = time;
            this.id = id;
        }
        @Override public int compareTo(Point p) {
            return time == p.time ? p.type - type : time - p.time;
        }
    }
    
    private static Point[] readPoints(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        int chVal;
        while ((chVal = reader.read()) != ' ') {
            builder.append((char)chVal);
        }
        int n = Integer.parseInt(builder.toString());
        Point[] points = new Point[2 * n];
        for (int i = 0; i < n; ++i) {
            builder.setLength(0);
            while ((chVal = reader.read()) != ' ') {
                builder.append((char)chVal);
            }
            int in = Integer.parseInt(builder.toString());
            builder.setLength(0);
            while ((chVal = reader.read()) != -1 && chVal != ' ' && chVal != '\n') {
                builder.append((char)chVal);
            }
            int out = Integer.parseInt(builder.toString());
            points[2 * i] = new Point(1, in, i);
            points[2 * i + 1] = new Point(0, out, i);
        }
        return points;
    }
    
    private static String answer(Point[] points, final int MAX_TIME) {
        final int L = points.length;
        if (points[0].time != 0 || points[L - 1].time != MAX_TIME) { // wrong edges
            return("Wrong Answer\n");
        }
        if (points[1].time == 0 || points[L - 2].time == MAX_TIME) { // equal ins or equal outs
            return("Wrong Answer\n");
        }
        int cnt = 1;
        for (int i = 1; i < L - 1; ++i) {
            if (points[i].type == 1) {
                if (cnt == 2) { // more than 2
                    return("Wrong Answer\n");
                }
                ++cnt;
            }
            if (points[i].type == 0) {
                if (cnt == 1) { // empty cut
                    return("Wrong Answer\n");
                }
                if (cnt == 2 && points[i].id == points[i - 1].id) { // subcut
                    return("Wrong Answer\n");
                }
                --cnt;
            }
        }
        return("Accepted\n");
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int MAX_TIME = 10000;
        int k = Integer.parseInt(reader.readLine());
        for (int testInd = 0; testInd < k; ++testInd) {
            Point[] points = readPoints(reader);
            Arrays.sort(points);
            writer.write(answer(points, MAX_TIME));
        }
    }
    
}
/*
1) объект охраняется в любой момент времени хотя бы одним охранником
2) удаление любого из них приводит к появлению промежутка времени,
    когда объект не охраняется.

input:
8
2 1 2 2 10000
2 0 2 2 9999
3 0 6000 2000 7000 4000 10000
2 0 2000 0 10000
3 0 2000 2001 8000 3000 10000
3 0 4000 1000 3000 4000 10000
3 0 2000 2000 10000 3000 10000
3 0 2000 2000 8000 3000 10000
output:
Wrong Answer
Wrong Answer
Wrong Answer
Wrong Answer
Wrong Answer
Wrong Answer
Wrong Answer
Accepted
*/
