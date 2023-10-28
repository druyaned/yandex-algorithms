package com.github.druyaned.yandexalgorithms.train1.l2linearsearch;

import java.util.Scanner;

public class HW3NearestNumber {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int N = scanner.nextInt();
        int[] array = new int[N];
        for (int i = 0; i < N; ++i) {
            array[i] = scanner.nextInt();
        }
        int number = scanner.nextInt();
        System.out.println(nearestNumber(array, number));
    }
    
    public static int nearestNumber(int[] array, int number) {
        int difference = Math.abs(array[0] - number);
        int indexOfNearestNumber = 0;
        for (int i = 1; i < array.length; ++i) {
            int currentDifference = Math.abs(array[i] - number);
            if (currentDifference < difference) {
                difference = currentDifference;
                indexOfNearestNumber = i;
            }
        }
        return array[indexOfNearestNumber];
    }

}
