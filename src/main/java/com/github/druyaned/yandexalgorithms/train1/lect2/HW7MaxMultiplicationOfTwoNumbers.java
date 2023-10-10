package com.github.druyaned.yandexalgorithms.train1.lect2;

import java.util.Scanner;

public class HW7MaxMultiplicationOfTwoNumbers {
    
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] elements = line.split(" ");
        final int N = elements.length;
        int[] numbers = new int[N];
        for (int i = 0; i < N; ++i) {
            numbers[i] = Integer.parseInt(elements[i]);
        }
        //   min2 min1 max1 max2
        // --|----|----|----|---->
        int min2, min1, max1, max2;
        if (numbers[0] < numbers[1]) {
            max1 = min2 = numbers[0];
            max2 = min1 = numbers[1];
        } else {
            max1 = min2 = numbers[1];
            max2 = min1 = numbers[0];
        }
        for (int i = 2; i < N; ++i) {
            if (numbers[i] < min2) {
                min1 = min2;
                min2 = numbers[i];
            } else if (numbers[i] < min1) {
                min1 = numbers[i];
            }
            if (max2 < numbers[i]) {
                max1 = max2;
                max2 = numbers[i];
            } else if (max1 < numbers[i]) {
                max1 = numbers[i];
            }
        }
        long multiplicationOfMins = (long)min1 * (long)min2;
        long multiplicationOfMaxs = (long)max1 * (long)max2;
        if (multiplicationOfMins < multiplicationOfMaxs) {
            System.out.println(max1 + " " + max2);
        } else {
            System.out.println(min2 + " " + min1);
        }
    }

}
