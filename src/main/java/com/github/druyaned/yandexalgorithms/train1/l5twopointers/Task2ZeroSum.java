package com.github.druyaned.yandexalgorithms.train1.l5twopointers;

import java.util.HashSet;
import java.util.Scanner;

/**
 * Лекция 5 задача 2 (префиксные суммы).
 * Дана последовательность чисел длиной N.
 * Необходимо найти количество отрезков с нулевой суммой.
 * 
 * @author druyaned
 */
public class Task2ZeroSum {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] sequence = new int[n];
        for (int i = 0; i < n; ++i) {
            sequence[i] = scanner.nextInt();
        }
        System.out.println(zeroSumCount(n, sequence));
    }
    
    public static int zeroSumCount(int n, int[] sequence) {
        /*
        2 3 -5 5 0 7  8 -8  1  0  9  4 -3 -2 -3  2 -6
        0 2  5 0 5 5 12 20 12 13 13 22 26 23 21 18 20 14
        */
        int zeroSumCount = 0;
        HashSet<Long> sums = new HashSet<>();
        sums.add(0L);
        long currentSum = 0;
        for (int i = 0; i < n; ++i) {
            if (sequence[i] != 0) {
                currentSum += sequence[i];
                if (sums.contains(currentSum)) {
                    ++zeroSumCount;
                }
                sums.add(currentSum);
            }
        }
        return zeroSumCount;
    }
    
}
/*
Input:
17
2 3 -5 5 0 7 8 -8 1 0 9 4 -3 -2 -3 2 -6
Output:
4
*/