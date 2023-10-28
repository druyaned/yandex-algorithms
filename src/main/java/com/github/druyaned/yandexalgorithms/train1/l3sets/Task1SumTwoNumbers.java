package com.github.druyaned.yandexalgorithms.train1.l3sets;

import java.util.Scanner;

/**
 * Лекции 3 задача 1.
 * 
 * <p>Дана последовательность положительных чисел длиной N и число X.
 * Нужно найти два различных числа A и B из последовательности,
 * такие что {@code A + B = X} или вернуть пару {@code 0, 0},
 * если такой пары чисел нет.
 * 
 * @author ed
 */
public class Task1SumTwoNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] sequence = new int[n];
        for (int i = 0; i < n; ++i) {
            sequence[i] = scanner.nextInt();
        }
        int x = scanner.nextInt();
        System.out.println(pairOfNumbers(n, sequence, x));
    }
    
    public static String pairOfNumbers(final int N, int[] sequence, final int X) {
        CustomIntMultiSet multiSet = new CustomIntMultiSet();
        for (int i = 0; i < N; ++i) {
            if (sequence[i] < X) {
                int difference = X - sequence[i];
                if (multiSet.contains(difference)) {
                    System.out.println("multiSet: " + multiSet);
                    return difference + " " + sequence[i];
                }
                multiSet.add(sequence[i]);
            }
        }
        System.out.println("multiSet: " + multiSet);
        return "0 0";
    }

}
/*
Input
3
2 5 4
6
Output
2 4

Input
3
2 5 4
8
Output
0 0

Input
4
2 5 257 260
300
Output
0 0
*/
