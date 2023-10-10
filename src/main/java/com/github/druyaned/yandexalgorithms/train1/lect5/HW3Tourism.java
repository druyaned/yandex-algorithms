package com.github.druyaned.yandexalgorithms.train1.lect5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW3Tourism {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = Integer.parseInt(reader.readLine());
        int[] x = new int[n + 1]; // strictly ascending
        int[] y = new int[n + 1]; // strictly ascending
        for (int i = 1; i <= n; ++i) {
            String[] elements = reader.readLine().split(" ");
            x[i] = Integer.parseInt(elements[0]);
            y[i] = Integer.parseInt(elements[1]);
        }
        int m = Integer.parseInt(reader.readLine());
        int[] starts = new int[m];
        int[] finishes = new int[m];
        for (int i = 0; i < m; ++i) {
            String[] elements = reader.readLine().split(" ");
            starts[i] = Integer.parseInt(elements[0]);
            finishes[i] = Integer.parseInt(elements[1]);
        }
        // solve
        int[] sumLR = new int[n + 1];
        int[] sumRL = new int[n + 2];
        for (int i = 2, j = n - 1; i <= n && j > 0; ++i, --j) {
            if (y[i] > y[i - 1]) {
                sumLR[i] = sumLR[i - 1] + y[i] - y[i - 1];
            } else {
                sumLR[i] = sumLR[i - 1];
            }
            if (y[j] > y[j + 1]) {
                sumRL[j] = sumRL[j + 1] + y[j] - y[j + 1];
            } else {
                sumRL[j] = sumRL[j + 1];
            }
        }
        for (int i = 0; i < m; ++i) {
            final int s = starts[i], f = finishes[i];
            if (s > f) {
                writer.write((sumRL[f] - sumRL[s]) + "\n");
            } else {
                writer.write((sumLR[f] - sumLR[s]) + "\n");
            }
        }
    }
    
}
/*
Создам суммы слева-напарво (LR) и справа-налево (RL).
Если start[i] > finish[i] => использую RL, иначе LR.
*/