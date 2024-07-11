package com.github.druyaned.yandexalgorithms.train3.l3dinamicprog1;

/**
 * Задача про ступеньки со стоимостью и историей.
 * Можем шагать на 1 или 2 ступеньки вверх.
 * Как наступим, получаем a[i] рублей (возможно отрицательное).
 * Максимизировать сумму и выдать ступеньки, по которым прошлись.
 * @author ed
 */
public class Task1Stairs {
    
    public static void main(String[] args) {
        int n = 8;
        int a[] = {0, 100, -50, -200, -100, 200, 300, -100, 100}; // first is a floor
        int dp[] = new int[n + 1]; // dp[i] = a[i] + max(a[i-1], a[i-2]);
        dp[1] = a[1];
        for (int i = 2; i <= n; ++i) {
            if (dp[i-1] > dp[i-2]) {
                dp[i] = a[i] + dp[i-1];
            } else {
                dp[i] = a[i] + dp[i-2];
            }
        }
        int path[] = new int[n + 1];
        int pathSize = 0;
        path[pathSize++] = n;
        for (int step = n; step >= 2; ) {
            if (dp[step-1] > dp[step-2]) {
                path[pathSize++] = --step;
            } else {
                path[pathSize++] = step -= 2;
            }
        }
        if (path[pathSize - 1] != 0) {
            path[pathSize++] = 0;
        }
        System.out.printf("a:    ");
        show(a, n);
        System.out.printf("maxSum: %d\n", dp[n]);
        System.out.printf("dp:   ");
        show(dp, n);
        System.out.printf("path: ");
        showPath(path, pathSize);
    }
    
    private static void show(int arr[], int n) {
        System.out.printf("%d", arr[1]);
        for (int i = 2; i <= n; ++i) {
            System.out.printf(" %d", arr[i]);
        }
        System.out.println();
    }
    
    private static void showPath(int path[], int pathSize) {
        System.out.printf("%d", path[pathSize-1]);
        for (int i = pathSize - 1; i > 0; --i) {
            System.out.printf(" %d", path[i-1]);
        }
        System.out.println();
    }
    
}
