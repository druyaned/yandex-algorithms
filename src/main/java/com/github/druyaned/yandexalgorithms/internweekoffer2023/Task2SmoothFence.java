package com.github.druyaned.yandexalgorithms.internweekoffer2023;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Task2SmoothFence {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = Integer.parseInt(elems[0]);
        int k = Integer.parseInt(elems[1]);
        int[] lengths = new int[n];
        elems = reader.readLine().split(" ");
        for (int i = 0; i < n; ++i) {
            lengths[i] = Integer.parseInt(elems[i]);
        }
        // solve
        Arrays.sort(lengths);
        int minDiff = lengths[n - 1] - lengths[0];
        for (int i = 0; i <= k; ++i) {
            int diff = lengths[n - 1 - k + i] - lengths[i];
            if (minDiff > diff) {
                minDiff = diff;
            }
        }
        writer.write(minDiff + "\n");
    }
    
}
/*
n - количество досок (width[i]==width[j], depth[i]==depth[j], l[i] != l[j]);
Чтоб он был как можно более ровным.
Неровность diff = max(lengths) - min(lengths).
k из n досок лишние и их можно не использовать.
Определить минимальную minDiff для забора из n-k досок.

Ввод:
n k
l[1] l[2] ... l[n]
Вывод:
minDiff

Сортирую, потом прохожу i in [0, k], обновляя минимум.
*/