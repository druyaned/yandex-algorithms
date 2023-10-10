package com.github.druyaned.yandexalgorithms.train1.lect5;

import java.util.Scanner;

/**
 * Лекция 5 задача 4 (два указателя).
 * Игрок в футбол обладает профессионализмом. Команда называется сплоченной,
 * если профессионализм любого игрока не превосходит суммарный профессионализм
 * любых двух других игроков из команды. Команда может состоять из
 * любого количества игроков.
 * <br>Дана отсортированная последовательность чисел длиной N -
 * профессионализм игроков.
 * Найти максимальный суммарный профессионализм сплоченной команды.
 * 
 * @author druyaned
 */
public class Task4Football {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // n >= 1
        int[] professionalism = new int[n];
        for (int i = 0; i < n; ++i) {
            professionalism[i] = scanner.nextInt();
        }
        System.out.println(bestTeam(n, professionalism));
    }
    
    public static long bestTeam(int n, int[] professionalism) {
        /*
        l + m >= r
        1 3 5 6 6 7 8 8 9
        ----
        1 3 5 6 6 7 8 8 9
        ^ 4
          ^ 8         8
            ^11         9
        */
        long currentSum = professionalism[0];
        long maxSum = currentSum;
        for (int l = 0, r = 1; l < n - 1 && r < n; ++l) {
            long firstTwo = professionalism[l] + professionalism[l + 1];
            while (r < n && firstTwo >= professionalism[r]) {
                currentSum += professionalism[r++];
            }
            if (maxSum < currentSum) {
                maxSum = currentSum;
            }
            currentSum -= professionalism[l];
        }
        return maxSum;
    }

}
/*
Input:
7
1 3 3 7 9 14 15
Output:
45

Input:
7
3 3 3 3 4 4 9
Output:
20

Input:
9
1 3 5 6 6 7 8 8 9
Output:
49
*/
