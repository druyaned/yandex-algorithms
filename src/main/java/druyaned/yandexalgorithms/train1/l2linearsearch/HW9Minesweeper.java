package druyaned.yandexalgorithms.train1.l2linearsearch;

import java.util.Scanner;

public class HW9Minesweeper {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // [1, 100], rows
        int m = scanner.nextInt(); // [1, 100], columns
        int k = scanner.nextInt(); // [0, n*m], mines
        int[] y = new int[k]; // y[i] in [1, n]
        int[] x = new int[k]; // x[i] in [1, m]
        for (int i = 0; i < k; ++i) {
            y[i] = scanner.nextInt();
            x[i] = scanner.nextInt();
        }
        System.out.print(makeField(n, m, k, y, x));
    }
    
    public static String makeField(int n, int m, int k, int[] y, int[] x) {
        // with frames
        int yLim = n + 2;
        int xLim = m + 2;
        int[][] field = new int[yLim][xLim];
        for (int i = 0; i < k; ++i) {
            field[y[i]][x[i]] = -1;
        }
        final int ADJACENT_CELLS = 8;
        int[] addY = {+0, -1, -1, -1, +0, +1, +1, +1};
        int[] addX = {-1, -1, +0, +1, +1, +1, +0, -1};
        StringBuilder fieldBuilder = new StringBuilder();
        for (int row = 1; row <= n; ++row) {
            for (int column = 1; column <= m; ++column) {
                if (column != 1) {
                    fieldBuilder.append(' ');
                }
                if (field[row][column] == -1) {
                    fieldBuilder.append('*');
                } else {
                    int mines = 0;
                    for (int adjacent = 0; adjacent < ADJACENT_CELLS; ++adjacent) {
                        if (field[row + addY[adjacent]][column + addX[adjacent]] == -1) {
                            ++mines;
                        }
                    }
                    fieldBuilder.append(mines);
                }
            }
            fieldBuilder.append('\n');
        }
        return fieldBuilder.toString();
    }

}
