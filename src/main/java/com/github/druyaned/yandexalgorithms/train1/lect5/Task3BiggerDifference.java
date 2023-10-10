package com.github.druyaned.yandexalgorithms.train1.lect5;

import java.util.Scanner;

/**
 * Лекция 5 задача 3 (два указателя).
 * Дана отсортированная последовательность чисел длиной N и число K.
 * Найти количество пар чисел A, B, таких что B - A > K.
 * 
 * @author druyaned
 */
public class Task3BiggerDifference {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] sequence = new int[n];
        for (int i = 0; i < n; ++i) {
            sequence[i] = scanner.nextInt();
        }
        int k = scanner.nextInt();
        System.out.println(pairCount(n, sequence, k));
    }
    
    public static int pairCount(int n, int[] sequence, final int K) {
        /*
        B - A > K
        1 2 3 6 7 9
        5
        ----
        ind: 0 1 2 3 4 5
        arr: 1 2 3 6 7 9
        ptr: ^       ^   | 2
        ptr:   ^       ^ | 1
        ptr:     ^     ^ | 1
        */
        int pairCount = 0;
        for (int left = 0, right = 1; left < n && right < n; ++left) {
            while (right < n && sequence[right] - sequence[left] <= K) {
                ++right;
            }
            pairCount += n - right;
        }
        return pairCount;
    }
    
}
/*
Input:
4
1 3 7 8
4
Output:
3

Input:
6
1 2 3 6 7 9
5
Output:
4
*/
