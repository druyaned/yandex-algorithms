package com.github.druyaned.yandexalgorithms.train1.lect6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW7Square {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        long w = Long.parseLong(reader.readLine());
        long h = Long.parseLong(reader.readLine());
        long n = Long.parseLong(reader.readLine());
        // solve
        if (h > w) { // swap so that w is bigger
            long temp = w;
            w = h;
            h = temp;
        }
        long leftD = 0, rightD = h / 2L, midD;
        final long sSquare = w * h;
        while (leftD < rightD) {
            midD = (leftD + rightD + 1) / 2L;
            long sFlowers = (w - 2 * midD) * (h - 2 * midD);
            long sRoad = sSquare - sFlowers;
            if (sRoad <= n) {
                leftD = midD;
            } else {
                rightD = midD - 1;
            }
        }
        writer.write(rightD + "\n");
    }
    
}
/*
Чтоб решение понять, надо нарисовать с подписью размеров.
n - количество плиточек 1x1;
d - ширина дорожки;
sRoad = 2 * d * (w + h - 2 * d); // площадь дорожки
sFlowers = (w - 2 * d) * (h - 2 * d); // площадь клумбы
Правый бин поиск (хорошо -> плохо).
Хорошо, когда sFlowers >= 0 && sRoad <= n.

Input:
7
16
110
Output:
3

Input:
7
16
55
Output:
3
*/