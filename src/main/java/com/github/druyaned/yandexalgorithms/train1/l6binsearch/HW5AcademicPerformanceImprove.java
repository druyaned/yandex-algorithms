package com.github.druyaned.yandexalgorithms.train1.l6binsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW5AcademicPerformanceImprove {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        long twos = Long.parseLong(reader.readLine());
        long threes = Long.parseLong(reader.readLine());
        long fours = Long.parseLong(reader.readLine());
        // solve
        final long baseSum = 2L * twos + 3L * threes + 4L * fours;
        final long baseN = twos + threes + fours;
        long leftFives = 0L, rightFives = baseN, midFives;
        while (leftFives < rightFives) {
            midFives = (leftFives + rightFives) / 2L;
            final long sum = baseSum + 5L * midFives;
            final long n = baseN + midFives;
            long integral = sum / n;
            long remainder = sum % n;
            if (integral >= 3L && remainder >= (n + 1L) / 2L || integral >= 4L) {
                rightFives = midFives;
            } else {
                leftFives = midFives + 1L;
            }
        }
        writer.write(leftFives + "\n");
    }
    
}
/*
2 2 2 3 3 4 4
(2*3 + 3*2 + 4*2)/(3 + 2 + 2) = 20/7 = 2 + 6/7
20 + 5*2 = 30
    7 + 2 = 9
    30/9 = 3 + 3/9
20 + 5*3 = 35
    7 + 3 = 10
    35/10 = 3 + 5/10

Надо, чтоб было минимум 3 + (r+/2)/r, где
r+/2 - это остаток от деления с округлением к большему;
r - это общее количество 2-к, 3-к, 4-к и 5-к.
sum = 2 * twos + 3 * threes + 4 * fours;
n = twos + threes + fours;
sum / n = integral + remainder / n;
integral >= 3 && remainder >= (n + 1) / 2 || integral >= 4.
Левый бин поиск (самый левый, когда все хорошо).
В качестве переменной поиска надо взять количество
5-к, которое способствует увеличению sum и n.
За правую границу лихо возьму n.

Input:
3
2
2
Output:
3
*/
