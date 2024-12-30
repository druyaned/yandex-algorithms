package druyaned.yandexalgorithms.train1.l5twopointers;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Лекция 5 задача 5.
 * Даны 2 отсортированные последовательности чисел
 * длиной N и M соответственно. Необходимо слить их
 * в одну отсортированную последовательность.
 * 
 * @author druyaned
 */
public class Task5Merge {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] arr1 = new int[n];
        int[] arr2 = new int[m];
        for (int i = 0; i < n; ++i) {
            arr1[i] = scanner.nextInt();
        }
        for (int i = 0; i < m; ++i) {
            arr2[i] = scanner.nextInt();
        }
        System.out.println(Arrays.toString(merged(n, m, arr1, arr2)));
    }
    
    public static int[] merged(final int N, final int M, int[] arr1, int[] arr2) {
        int[] merged = new int[N + M];
        for (int i1 = 0, i2 = 0, i = 0; i < N + M; ++i) {
            if (i1 < N && i2 < M) {
                if (arr1[i1] < arr2[i2]) {
                    merged[i] = arr1[i1++];
                } else {
                    merged[i] = arr2[i2++];
                }
            } else if (i1 < N) {
                merged[i] = arr1[i1++];
            } else {
                merged[i] = arr2[i2++];
            }
        }
        return merged;
    }
    
}
/*
Input:
5 4
1 3 5 6 6
2 4 7 7
Output:
1 2 3 4 5 6 7 7

Input:
5 4
2 4 7 7 7
1 3 5 6
Output:
1 2 3 4 5 6 7 7
*/
