package com.github.druyaned.yandexalgorithms.train1.lect6;

import java.util.Scanner;

/**
 * Лекция 6 задача 1.
 * В управляющий совет школы входят родители, учителя и учащиеся школы,
 * причем родителей должно быть не менее 1/3 от общего числа членов совета.
 * В настоящий момент в совет входит N человек, из них K родители.
 * <br>Определите, сколько родителей нужно дополнительно ввести в совет,
 * чтобы их число стало составлять не менее 1/3 от чиcла членов совета.
 * 
 * @author druyaned
 */
public class Task1School {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        System.out.println(amountToAdd(n, k));
    }
    
    public static int amountToAddMathImpl(final int N, final int K) { // m >= (n - 3 * k) / 2
        if (N < 3 * K) {
            return 0;
        } else {
            return (N - 3 * K + 1) / 2;
        }
    }
    
    public static int amountToAdd(final int N, final int K) {
        int leftAmount = 0, rightAmount = N, midAmount;
        while (leftAmount < rightAmount) {
            midAmount = (leftAmount + rightAmount) / 2;
            if (3 * (K + midAmount) >= N + midAmount) {
                rightAmount = midAmount;
            } else {
                leftAmount = midAmount + 1;
            }
        }
        return leftAmount;
    }
    
}
/*
Input:
9 2
Output:
2
*/
