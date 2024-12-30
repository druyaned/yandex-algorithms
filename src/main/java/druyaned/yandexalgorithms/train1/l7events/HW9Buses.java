package druyaned.yandexalgorithms.train1.l7events;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HW9Buses {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Point implements Comparable<Point> {
        private final int type; // departure==1, arrival=0
        private final int time, city, id;
        private Point(int type, int time, int city, int id) {
            this.type = type;
            this.time = time;
            this.city = city;
            this.id = id;
        }
        @Override public int compareTo(Point p) {
            return time == p.time ? type - p.type : time - p.time;
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = Integer.parseInt(elems[0]);
        int m = Integer.parseInt(elems[1]);
        int l = 2 * m;
        Point[] points = new Point[l];
        for (int i = 0; i < m; ++i) {
            elems = reader.readLine().split(" ");
            int dpr = Integer.parseInt(elems[0]);
            String[] time1Elems = elems[1].split(":");
            int h1 = Integer.parseInt(time1Elems[0]);
            int m1 = Integer.parseInt(time1Elems[1]);
            int arv = Integer.parseInt(elems[2]);
            String[] time2Elems = elems[3].split(":");
            int h2 = Integer.parseInt(time2Elems[0]);
            int m2 = Integer.parseInt(time2Elems[1]);
            int time1 = h1 * 60 + m1;
            int time2 = h2 * 60 + m2;
            points[2 * i] = new Point(1, time1, dpr, i);
            points[2 * i + 1] = new Point(0, time2, arv, i);
        }
        // solve
        Arrays.sort(points);
        int[] departureCounts = new int[n + 1];
        int[] arrivalCounts = new int[n + 1];
        for (int i = 0; i < l; ++i) {
            int city = points[i].city;
            if (points[i].type == 1) {
                ++departureCounts[city];
            }
            if (points[i].type == 0) {
                ++arrivalCounts[city];
            }
        }
        for (int i = 1; i <= n; ++i) {
            if (departureCounts[i] != arrivalCounts[i]) {
                writer.write("-1\n");
                return;
            }
        }
        boolean[] involved = new boolean[m];
        int[] busCounts = new int[n + 1];
        int[] maxBusCounts = new int[n + 1];
        for (int i = 0; i < l; ++i) {
            final int city = points[i].city;
            final int id = points[i].id;
            if (points[i].type == 1) {
                involved[id] = true;
                ++busCounts[city];
                if (busCounts[city] > maxBusCounts[city]) {
                    maxBusCounts[city] = busCounts[city];
                }
            }
            if (points[i].type == 0 && involved[id]) {
                --busCounts[city];
            }
            if (involved[id]) {
            }
        }
        for (int i = 0; i < l; ++i) {
            final int city = points[i].city;
            if (points[i].type == 1) {
                ++busCounts[city];
                if (busCounts[city] > maxBusCounts[city]) {
                    maxBusCounts[city] = busCounts[city];
                }
            }
            if (points[i].type == 0) {
                --busCounts[city];
            }
        }
        int cnt = 0;
        for (int i = 1; i <= n; ++i) {
            cnt += maxBusCounts[i];
        }
        writer.write(cnt + "\n");
    }
    
}
/*
Формат ввода:
n m
dpr[1] timeFrom[1] arv[1] timeTo[1]
dpr[2] timeFrom[2] arv[2] timeTo[2]
...
dpr[m] timeFrom[m] arv[m] timeTo[m]

n - количество городов in [1, n];
m - количество рейсов;
dpr (departure) - номер города отправления;
timeFrom - время отправления (HH:MM);
arv (arrival) - номер города прибытия;
timeTo - время прибытия (HH:MM);
    if (timeTo > timeFrom) { (timeTo - timeFrom) < 24:00 }
    else { 24:00 - timeFrom + timeTo < 24:00 };

Вывод: минимальное необходимое количество автобусов или -1,
если расписание невозможно обслужить в течение неограниченного периода
времени конечным числом автобусов.

Ход решения:
Сначала проверка на возможность:
departureCounts[i] != arrivalCounts[i] => -1.
Потом 2 прохода: сначала игноря тех, что начинаются позже,
чем заканчиваются, а потом финальный проход.
По ходу дела подсчитываю необходимое количество автобусов
для каждого города, и максимумы сохраняю.
Потом суммирую максимумы и получаю ответ.

input:
3 5
1 17:00 2 10:00
2 08:00 3 20:00
3 12:00 1 10:00
2 08:00 1 00:00
1 10:00 2 04:00
output:
6

input:
3 4
1 17:00 2 10:00
2 08:00 3 20:00
3 12:00 1 10:00
2 08:00 1 00:00
output:
-1

input:
2 2
1 08:00 2 20:00
2 20:00 1 08:00
output:
1
*/
