package com.github.druyaned.yandexalgorithms.train1.lect6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW11UnionMedian2 {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static int leftMedian(int[] arr1, int[] arr2, final int l) {
        int li = 1, ri = l, mi;
        while (li < ri) {
            mi = (li + ri + 1) / 2;
            int lj = 1, rj = l, mj;
            while (lj < rj) {
                mj = (lj + rj) / 2;
                if (arr1[mi] <= arr2[mj]) {
                    rj = mj;
                } else {
                    lj = mj + 1;
                }
            }
            if (mi + lj <= l) {
                li = mi;
            } else {
                ri = mi - 1;
            }
        }
        int i = ri;
        int j = l - i;
        return Integer.max(arr1[i], arr2[j]);
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = Integer.parseInt(elems[0]);
        int l = Integer.parseInt(elems[1]);
        int[][] arrs = new int[n][l + 1];
        for (int i = 0; i < n; ++i) {
            elems = reader.readLine().split(" ");
            int x1 = Integer.parseInt(elems[0]);
            int d1 = Integer.parseInt(elems[1]);
            int a = Integer.parseInt(elems[2]);
            int c = Integer.parseInt(elems[3]);
            int m = Integer.parseInt(elems[4]);
            arrs[i][1] = (int)x1;
            int prevD = d1;
            for (int j = 2; j <= l; ++j) {
                arrs[i][j] = arrs[i][j - 1] + prevD;
                prevD = (a * prevD + c) % m;
            }
        }
        // solve
        for (int fstInd = 0; fstInd < n; ++fstInd) {
            for (int sndInd = fstInd + 1; sndInd < n; ++sndInd) {
                final int[] arr1 = arrs[fstInd];
                final int[] arr2 = arrs[sndInd];
                if (arr1[l] <= arr2[1]) { // special case
                    writer.write(arr1[l] + "\n");
                    continue;
                }
                if (arr2[l] <= arr1[1]) { // special case
                    writer.write(arr2[l] + "\n");
                    continue;
                }
                int medianI = leftMedian(arr1, arr2, l);
                int medianJ = leftMedian(arr2, arr1, l);
                int median = Integer.min(medianI, medianJ);
                writer.write(median + "\n");
            }
        }
    }
    
}
