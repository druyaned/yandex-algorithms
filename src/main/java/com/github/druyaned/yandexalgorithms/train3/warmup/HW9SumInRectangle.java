package com.github.druyaned.yandexalgorithms.train3.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW9SumInRectangle {
    
    private static final char[] buf = new char[16];
    
    private static int readInt(BufferedReader reader) throws IOException {
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
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader), m = readInt(reader), k = readInt(reader);
        int[][] field = new int[n + 1][m + 1];
        for (int y = 1; y <= n; ++y) {
            for (int x = 1; x <= m; ++x) {
                field[y][x] = readInt(reader);
            }
        }
        int[][] sums = new int[n + 1][m + 1];
        for (int y = 1; y <= n; ++y) {
            int lineSum = 0;
            for (int x = 1; x <= m; ++x) {
                lineSum += field[y][x];
                sums[y][x] = lineSum + sums[y - 1][x];
            }
        }
        for (int i = 0; i < k; ++i) {
            int y1 = readInt(reader), x1 = readInt(reader);
            int y2 = readInt(reader), x2 = readInt(reader);
            long sum = sums[y2][x2]
                    - sums[y2][x1 - 1]
                    + sums[y1 - 1][x1 - 1]
                    - sums[y1 - 1][x2];
            writer.write(sum + "\n");
        }
    }
    
}
/*
Дано:
n - количество строк;
m - количество столбцов;
k - количество запросов;
x1 - координата на OX левого нижнего угла прямоугольника;
y1 - координата на OY левого нижнего угла прямоугольника;
x2 - координата на OX правого верхнего угла прямоугольника;
y2 - координата на OY правого верхнего угла прямоугольника.
Найти:
для каждого запроса сумму всех элементов между (x1, y1) и (x2, y2).

Тут фишка в разности прямоугольников.
[1;1][x2;y2] - [1;1][x1-1;y2] - [1;1][x2;y1-1] + [1;1][x1-1;y1-1]

input:
4 5 1
 1  2  3  4  5
 6  7  8  9 10
11 12 13 14 15
16 17 18 19 20
2 2 4 3
output:
63
*/