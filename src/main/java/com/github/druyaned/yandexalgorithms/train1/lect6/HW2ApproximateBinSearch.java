package com.github.druyaned.yandexalgorithms.train1.lect6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW2ApproximateBinSearch {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static int approximateBinSearch(int[] arr, int key) {
        int l = 0, r = arr.length - 1, mid;
        while (l < r) {
            mid = (l + r) / 2;
            if (key <= arr[mid]) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        if (l == 0) {
            return arr[0];
        }
        int rightDiff = arr[l] > key ? arr[l] - key : key - arr[l];
        int leftDiff = key > arr[l - 1] ? key - arr[l - 1] : arr[l - 1] - key;
        return leftDiff <= rightDiff ? arr[l - 1] : arr[l];
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = Integer.parseInt(elems[0]);
        int k = Integer.parseInt(elems[1]);
        int[] arr = new int[n];
        int[] keys = new int[k];
        elems = reader.readLine().split(" ");
        for (int i = 0; i < n; ++i) {
            arr[i] = Integer.parseInt(elems[i]);
        }
        elems = reader.readLine().split(" ");
        for (int i = 0; i < k; ++i) {
            keys[i] = Integer.parseInt(elems[i]);
        }
        //solve
        for (int i = 0; i < k; ++i) {
            writer.write(approximateBinSearch(arr, keys[i]) + "\n");
        }
    }
    
}
