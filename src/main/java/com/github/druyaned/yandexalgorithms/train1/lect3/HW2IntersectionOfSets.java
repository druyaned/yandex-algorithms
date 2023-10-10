package com.github.druyaned.yandexalgorithms.train1.lect3;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

public class HW2IntersectionOfSets {

    public static void main(String[] args) {
        final int MAX_N = 100000;
        int[] arr1 = new int[MAX_N];
        int[] arr2 = new int[MAX_N];
        final int N1, N2;
        try {
            N1 = fillArr(arr1);
            N2 = fillArr(arr2);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
        Arrays.sort(arr1, 0, N1);
        TreeSet<Integer> common = new TreeSet<>();
        int key, left, right, mid;
        for (int i2 = 0; i2 < N2; ++i2) {
            key = arr2[i2];
            left = 0;
            right = N1 - 1;
            while (left <= right) {
                mid = (left + right) / 2;
                if (key == arr1[mid]) {
                    common.add(key);
                    break;
                } else if (key < arr1[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        Iterator<Integer> iter = common.iterator();
        if (iter.hasNext()) {
            System.out.print(iter.next());
        }
        while (iter.hasNext()) {
            System.out.print(" " + iter.next());
        }
        System.out.println();
    }
    
    private static int fillArr(int[] arr) throws IOException {
        int n = 0;
        StringBuilder builder = new StringBuilder();
        int ch;
        while ((ch = System.in.read()) != -1 && ch != '\n') {
            if (Character.isWhitespace((char)ch)) {
                arr[n++] = Integer.parseInt(builder.toString());
                builder.setLength(0);
            } else {
                builder.append((char)ch);
            }
        }
        arr[n++] = Integer.parseInt(builder.toString());
        return n;
    }
    
}
