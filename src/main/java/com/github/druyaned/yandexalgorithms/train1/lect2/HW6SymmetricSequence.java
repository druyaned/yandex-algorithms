package com.github.druyaned.yandexalgorithms.train1.lect2;

import java.util.Scanner;

public class HW6SymmetricSequence {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int N = scanner.nextInt(); // [1, 100]
        int[] digits = new int[N]; // digits[i] in [1, 9]
        for (int i = 0; i < N; ++i) {
            digits[i] = scanner.nextInt();
        }
        int m = numberOfElements(digits, N);
        System.out.println(m);
        if (0 < m) {
            System.out.print(digits[m - 1]);
            for (int i = m - 2; 0 <= i; --i) {
                System.out.print(" " + digits[i]);
            }
            System.out.println();
        }
    }
    
    public static int numberOfElements(int[] digits, final int N) {
        int evenPivot = (N + 1) / 2;
        for (; evenPivot < N; ++evenPivot) {
            boolean allEquals = true;
            for (int i = 1; evenPivot + i - 1 < N && 0 <= evenPivot - i; ++i) {
                if (digits[evenPivot + i - 1] != digits[evenPivot - i]) {
                    allEquals = false;
                    break;
                }
            }
            if (allEquals) {
                break;
            }
        }
        int oddPivot = N / 2;
        for (; oddPivot < N; ++oddPivot) {
            boolean allEquals = true;
            for (int i = 1; oddPivot + i < N && 0 <= oddPivot - i; ++i) {
                if (digits[oddPivot + i] != digits[oddPivot - i]) {
                    allEquals = false;
                    break;
                }
            }
            if (allEquals) {
                break;
            }
        }
        int evenM = 2 * evenPivot - N;
        int oddM = 2 * oddPivot - N + 1;
        return evenM < oddM ? evenM : oddM;
    }

}
