package com.github.druyaned.yandexalgorithms.train2.divb.lesson2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW2HousesAndShops {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static int dist(int[] buildings, int n, int pos) {
        int dist1 = n;
        for (int i = pos + 1; i < n; ++i) {
            if (buildings[i] == 2) {
                dist1 = i - pos;
                break;
            }
        }
        int dist2 = n;
        for (int i = pos - 1; i >= 0; --i) {
            if (buildings[i] == 2) {
                dist2 = pos - i;
                break;
            }
        }
        return dist1 < dist2 ? dist1 : dist2;
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = 10;
        String[] elems = reader.readLine().split(" ");
        int[] buildings = new int[n];
        for (int i = 0; i < n; ++i) {
            buildings[i] = Integer.parseInt(elems[i]);
        }
        int maxDist = 0;
        for (int i = 0; i < n; ++i) {
            if (buildings[i] == 1) {
                int dist = dist(buildings, n, i);
                if (maxDist < dist) {
                    maxDist = dist;
                }
            }
        }
        writer.write(maxDist + "\n");
    }
    
}
