package com.github.druyaned.yandexalgorithms.train1.lect7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;

public class HW6Contemporaries {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Point implements Comparable<Point> {
        private final int type; // 1==start, 0==finish
        private final LocalDate date;
        private final int id;
        public Point(int type, LocalDate date, int id) {
            this.type = type;
            this.date = date;
            this.id = id;
        }
        @Override public int compareTo(Point p) {
            return date.equals(p.date) ? type - p.type : date.compareTo(p.date);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = Integer.parseInt(reader.readLine());
        Point[] points = new Point[2 * n];
        int l = 0;
        for (int i = 0; i < n; ++i) {
            String[] elems = reader.readLine().split(" ");
            int d1 = Integer.parseInt(elems[0]);
            int m1 = Integer.parseInt(elems[1]);
            int y1 = Integer.parseInt(elems[2]);
            int d2 = Integer.parseInt(elems[3]);
            int m2 = Integer.parseInt(elems[4]);
            int y2 = Integer.parseInt(elems[5]);
            LocalDate birthDate = LocalDate.of(y1, m1, d1);
            LocalDate deathDate = LocalDate.of(y2, m2, d2);
            LocalDate start = birthDate.plus(18, ChronoUnit.YEARS);
            LocalDate ageOf80 = birthDate.plus(80, ChronoUnit.YEARS);
            LocalDate finish = deathDate.compareTo(ageOf80) < 0 ? deathDate : ageOf80;
            if (start.compareTo(finish) < 0) {
                points[l++] = new Point(1, start, i + 1);
                points[l++] = new Point(0, finish, i + 1);
            }
        }
        // solve
        if (l == 0) { // special case
            writer.write("0\n");
            return;
        }
        Arrays.sort(points, 0, l);
        HashSet<Integer> atParty = new HashSet<>(n);
        atParty.add(points[0].id);
        boolean prevIncrease = true;
        for (int i = 1; i < l; ++i) {
            if (points[i].type == 0 && prevIncrease) { // finish and localMax
                for (Integer id : atParty) {
                    writer.write(id + " ");
                }
                writer.write('\n');
                prevIncrease = false;
            }
            if (points[i].type == 0) { // finish
                atParty.remove(points[i].id);
            }
            if (points[i].type == 1) { // start
                atParty.add(points[i].id);
                prevIncrease = true;
            }
        }
    }
    
}
/*
Заменю даты смерти и рождения на start и finish, где
start - это момент совершеннолетия, а
finish - это min(death, ageOf80).
Отлично, я разобрался со входными данными. Теперь надо решить задачу.

Я рассмотрел пример на рисунке. Количество ребят либо растет, либо убывает.
Если график начертить, то будут такие горочки, где меня интересуют вершинки.
В этих вершинках мои ответы. Добуду эти вершинки с помощью prevIncrease.
*/
