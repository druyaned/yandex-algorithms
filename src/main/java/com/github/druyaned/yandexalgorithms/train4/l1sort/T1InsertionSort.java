package com.github.druyaned.yandexalgorithms.train4.l1sort;

import java.util.Random;

public class T1InsertionSort {
    
    public static void main(String[] args) {
        Random generator = new Random();
        int n = 8;
        int[] arr = generateArray(generator, n);
        showArr(n, arr);
        System.out.println("Running insertion sort...");
        insertionSort(n, arr);
        showArr(n, arr);
    }
    
    private static void insertionSort(int n, int[] arr) {
        for (int i = 1; i < n; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }
    
    private static void swap(int[] arr, int i1, int i2) {
        int toSwap = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = toSwap;
    }
    
    private static int[] generateArray(Random generator, int n) {
        int[] arr = new int[n];
        int maxVal = 2 * n, minVal = -2 * n;
        for (int i = 0; i < n; i++) {
            arr[i] = minVal + generator.nextInt(maxVal - minVal + 1);
        }
        return arr;
    }
    
    private static void showArr(int n, int[] arr) {
        System.out.print("arr:");
        for (int i = 0; i < n; i++) {
            System.out.printf(" %d", arr[i]);
        }
        System.out.println();
    }
    
}
