package druyaned.yandexalgorithms.train1.l5twopointers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class HW10Triangle {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Counts {
        
        private final int point, mirrored;
        
        private Counts(int point, int mirrored) {
            this.point = point;
            this.mirrored = mirrored;
        }
        
    }
    
    private static class Point {
        
        private final int x, y;
        
        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        private long squaredDist(Point p) {
            long xDiff = (long)(x - p.x) * (x - p.x);
            long yDiff = (long)(y - p.y) * (y - p.y);
            return xDiff + yDiff;
        }
        
        private Point mirrored(Point p) {
            return new Point(2 * x - p.x, 2 * y - p.y);
        }
        
        @Override
        public boolean equals(Object obj) {
            final Point other = (Point)obj;
            return x != other.x ? false : y == other.y;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + x;
            hash = 71 * hash + y;
            return hash;
        }
        
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = Integer.parseInt(reader.readLine()); // 3 <= n <= 1500
        Point[] points = new Point[n];
        HashSet<Point> pointSet = new HashSet<>();
        for (int i = 0; i < n; ++i) {
            String[] elems = reader.readLine().split(" ");
            int x = Integer.parseInt(elems[0]);
            int y = Integer.parseInt(elems[1]);
            points[i] = new Point(x, y);
            pointSet.add(points[i]);
        }
        // solve
        int cnt = 0;
        for (int i = 0; i < n; ++i) {
            HashMap<Long, Counts> distCount = new HashMap<>(n - 1);
            for (int j = 0; j < n; ++j) {
                if (i == j) {
                    continue;
                }
                Long dist = points[i].squaredDist(points[j]);
                Point mirrored = points[i].mirrored(points[j]);
                Counts counts = distCount.get(dist);
                if (counts == null) {
                    if (pointSet.contains(mirrored)) {
                        distCount.put(dist, new Counts(1, 1));
                    } else {
                        distCount.put(dist, new Counts(1, 0));
                    }
                } else {
                    if (pointSet.contains(mirrored)) {
                        distCount.put(dist, new Counts(counts.point + 1, counts.mirrored + 1));
                    } else {
                        distCount.put(dist, new Counts(counts.point + 1, counts.mirrored));
                    }
                }
            }
            for (Map.Entry<Long, Counts> distCountEntry : distCount.entrySet()) {
                Counts counts = distCountEntry.getValue();
                int mirroredCount = counts.mirrored / 2;
                int pointCount = counts.point - 1;
                cnt += (pointCount + 1) * pointCount / 2 - mirroredCount;
            }
        }
        writer.write(cnt + "\n");
    }
    
}
/*
1500^2 = 2 250 000
8 * 10^18 < Long.MAX_VALUE

Input:
8
4 3
0 0
-4 3
0 6
-8 6
1 3
-9 3
2 5
Output:
14

Для того, чтоб понять решение, надо начертить данный пример.
Ход решения.
Создаю HashSet<Point> pointSet для быстрого определения,
хранится ли конкретная точка в множестве.
Цикл i in [0, n).
Создаю HashMap<Long, Counts> distCount, где ключ - это квадрат дистанции
между points[i] и points[j], а значение counts - это количества
дистанций и "отраженных" точек, т.е. таких, что 3 точки лежат
на 1-ой прямой.
Потом цикл j in [0, n). Если i != j, то обновляю distCount.
Счетчик "отраженных" надо будет поделить на 2, т.к. для пары
зеркальных точек он будет инкрементирован 2 раза.
Потом к ответу суммирую арифметическую прогрессию от pointCount-1
и вычитаю mirroredCount/2. Арифметическую прогрессию прибавляю потому,
что если из точки выходят, например, 5 равных дистанций, то
треугольников можно будет образовать 4+3+2+1 (без учета зеркальных точек).
Вуаля!
*/
