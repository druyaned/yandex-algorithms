package com.github.druyaned.yandexalgorithms.train1.l6binsearch;

import java.util.Scanner;

/**
 * Лекция 6 задача 3.
 * Михаил читает лекции по алгоритмам. За кадром стоит доска размером W * H см.
 * Михаилу нужно разместить на доске N квадратных стикеров со шпаргалками,
 * при этом длина стороны стикера в сантиметрах должна быть целым числом.
 * <br>Определите максимальную длину стороны стикера, чтобы все стикеры
 * поместились на доске.
 * 
 * @author druyaned
 */
public class Task3Blackboard {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int w = scanner.nextInt();
        int h = scanner.nextInt();
        int n = scanner.nextInt();
        System.out.println(maxStickerLength(w, h, n));
    }
    
    private static int maxStickerLength(final int W, final int H, final int N) {
        int leftLength = 0, rightLength = W < H ? W : H, midLength;
        while (leftLength < rightLength) {
            midLength = (leftLength + rightLength + 1) / 2;
            if (N * midLength * midLength <= W * H) {
                leftLength = midLength;
            } else {
                rightLength = midLength - 1;
            }
        }
        return rightLength;
    }
    
}
/*
Input:
3 4 1
Output:
3

Input:
6 9 12
Output:
2
*/
