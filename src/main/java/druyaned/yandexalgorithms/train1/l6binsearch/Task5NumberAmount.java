package druyaned.yandexalgorithms.train1.l6binsearch;

import java.util.Scanner;

/**
 * Лекция 6 задача 5.
 * Задана отсортированная по неубыванию последовательность из N чисел и число X.
 * <br>Необходимо определить сколько раз число X входит в последовательность.
 * 
 * @author druyaned
 */
public class Task5NumberAmount {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        int x = scanner.nextInt();
        String[] elements = line.split(" ");
        int n = elements.length;
        int[] sequence = new int[n];
        for (int i = 0; i < n; ++i) {
            sequence[i] = Integer.parseInt(elements[i]);
        }
        System.out.println(amountOfX(sequence, x));
    }
    
    public static int amountOfX(final int[] sequence, final int X) {
        int left = BinarySearch.left(sequence, X);
        if (left == -1 || sequence[left] != X) {
            return 0;
        }
        int right = BinarySearch.right(sequence, X);
        return right - left + 1;
    }
    
}
/*
Input:
1 3 4 5 5 7 8
5
Output:
2

Input:
1 3 4 5 5 7 8
6
Output:
0

Input:
1 3 4 5 5 7 8
1
Output:
1

Input:
1 3 4 5 5 7 8
0
Output:
0

Input:
1 3 4 5 5 7 8
9
Output:
0
*/