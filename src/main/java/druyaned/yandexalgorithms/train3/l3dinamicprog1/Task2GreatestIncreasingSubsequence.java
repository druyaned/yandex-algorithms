package druyaned.yandexalgorithms.train3.l3dinamicprog1;

/**
 * Задача о наибольшей возрастающей подпоследовательности.
 * Дана неотсортированная последовательность.
 * Подпоследовательность - это последовательность
 * с любыми вычеркнутыми элементами. Надо найти
 * самую длинную из них.
 * 2 подхода: O(n^2), O(n*log(n)).
 * O(n^2): dp[i] = dp[lastMin] + 1.
 * O(n*log(n)): int[] minElementForLength = new int[n + 1]; binSearch.
 * @author ed
 */
public class Task2GreatestIncreasingSubsequence {
    
    public static void main(String[] args) {
        int a[] = {4, 10, 5, 12, 3, 24, 7};
        int n = a.length;
        System.out.printf("arr: %s\n", arrAsString(a, 0, n));
        int[] minElemForLen = new int[n];
        minElemForLen[0] = a[0];
        int size = 1;
        for (int i = 1; i < n; ++i) {
            int l = 0, r = size - 1, m;
            while (l < r) {
                m = (l + r) / 2;
                if (minElemForLen[m] < a[i]) {
                    l = m + 1;
                } else {
                    r = m;
                }
            }
            if (minElemForLen[l] < a[i]) {
                minElemForLen[size++] = a[i];
            } else {
                minElemForLen[l] = a[i];
            }
            System.out.printf("minElemForLen: %s\n", arrAsString(minElemForLen, 0, n));
        }
        System.out.printf("GreatestIncreasingSubsequence: %d\n", size);
    }
    
    private static String arrAsString(int a[], int begin, int end) {
        StringBuilder builder = new StringBuilder(2 * end).append(a[begin]);
        for (int i = begin + 1; i < end; ++i) {
            builder.append(' ').append(String.format("%2d", a[i]));
        }
        return builder.toString();
    }
    
}
/*
n=9
ind: 1 2 3 4 5 6 7 8 9
arr: 6 8 5 7 4 1 9 2 3
dp:  1 2 1 2 1 1 3 2 3
minElemForLen:
L: 1 2 3 4 5 6 7 8 9
1: 6 * * * * * * * *
2: 6 8 * * * * * * *
3: 5 8 * * * * * * *
4: 5 7 * * * * * * *
5: 4 7 * * * * * * *
6: 1 7 * * * * * * *
7: 1 7 9 * * * * * *
8: 1 2 9 * * * * * *
9: 1 2 3 * * * * * *

ind:  1  2  3  4  5  6  7
arr:  5 10  6 12  3 24  7
1:  5  *  *  *  *  *  *  *  *
2:  5 10  *  *  *  *  *  *  *
3:  5  6  *  *  *  *  *  *  *
4:  5  6 12  *  *  *  *  *  *
5:  3  6 12  *  *  *  *  *  *
6:  3  6 12 24  *  *  *  *  *
7:  3  6  7 24  *  *  *  *  *
*/
