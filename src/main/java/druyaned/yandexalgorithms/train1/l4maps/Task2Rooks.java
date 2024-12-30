package druyaned.yandexalgorithms.train1.l4maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Лекция 4 задача 2.
 * На шахматной доске NxN находятся M ладей (бьет по горизонтали и вертикали).
 * Определите, сколько пар лалей бьют друг друга. Ладьи задаются парой чисел I и J,
 * обозначающих координаты клетки. 1 &lt;= N &lt;= 10^9, 0 &lt;= M &lt;= 10^5.
 * 
 * @author druyaned
 */
public class Task2Rooks {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] y = new int[m];
        int[] x = new int[m];
        for (int i = 0; i < m; ++i) {
            y[i] = scanner.nextInt();
            x[i] = scanner.nextInt();
        }
        System.out.println(amount(n, m, y, x));
    }
    
    public static int amount(final int N, final int M, int[] y, int[] x) {
        int amount = 0;
        Map<Integer, Integer> rows = new HashMap<>();
        Map<Integer, Integer> columns = new HashMap<>();
        for (int i = 0; i < M; ++i) {
            Integer rooksInRow = rows.get(y[i]);
            if (rooksInRow == null) {
                rows.put(y[i], 0);
            } else {
                rows.put(y[i], rooksInRow + 1);
            }
            Integer rooksInColumn = columns.get(x[i]);
            if (rooksInColumn == null) {
                columns.put(x[i], 0);
            } else {
                columns.put(x[i], rooksInColumn + 1);
            }
        }
        for (Integer row : rows.values()) {
            amount += row;
        }
        for (Integer column : columns.values()) {
            amount += column;
        }
        return amount;
    }

}
/*
* R * * *
* * * R *
R * R * R
* * R * *
* * * * *

Input:
5 6
1 2
2 4
3 1
3 3
3 5
4 3
Output:
3
*/
