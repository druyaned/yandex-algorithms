package com.github.druyaned.yandexalgorithms.train1.l4maps;

import java.util.Scanner;

/**
 * Лекция 4 задача 1.
 * Дано два числа X и Y без ведущих нулей.
 * Необходимо проверить, можно ли получить первое из второго перестановкой цифр.
 * 
 * @author druyaned
 */
public class Task1DigitsPermutation {
    
    public static final int N_DIGITS = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        System.out.println(answer(x, y));
    }
    
    public static String answer(int x, int y) {
        int[] xDigits = toDigits(x);
        int[] yDigits = toDigits(y);
        for (int i = 1; i < N_DIGITS; ++i) {
            if (xDigits[i] != yDigits[i]) {
                return "NO";
            }
        }
        return "YES";
    }
    
    public static int[] toDigits(int number) {
        int[] digits = new int[N_DIGITS];
        while (0 < number) {
            int digit = number % 10;
            ++digits[digit];
            number /= 10;
        }
        return digits;
    }

}
/*
Input:
23058 53820
Output:
YES

Input:
23058 5320
Output:
NO

Input:
23058 53720
Output:
NO
*/