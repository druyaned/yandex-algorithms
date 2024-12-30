package druyaned.yandexalgorithms.train1.l6binsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW10UnionMedian {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static int leftMedian(int[] arr1, int[] arr2, final int l) {
        int li = 1, ri = l, mi;
        while (li < ri) {
            mi = (li + ri + 1) / 2;
            int lj = 1, rj = l, mj;
            while (lj < rj) {
                mj = (lj + rj) / 2;
                if (arr1[mi] <= arr2[mj]) {
                    rj = mj;
                } else {
                    lj = mj + 1;
                }
            }
            if (mi + lj <= l) {
                li = mi;
            } else {
                ri = mi - 1;
            }
        }
        int i = ri;
        int j = l - i;
        return Integer.max(arr1[i], arr2[j]);
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = Integer.parseInt(elems[0]);
        int l = Integer.parseInt(elems[1]);
        int[][] arrs = new int[n][l + 1];
        for (int i = 0; i < n; ++i) {
            elems = reader.readLine().split(" ");
            for (int j = 1; j <= l; ++j) {
                arrs[i][j] = Integer.parseInt(elems[j - 1]);
            }
        }
        // solve
        for (int fstInd = 0; fstInd < n; ++fstInd) {
            for (int sndInd = fstInd + 1; sndInd < n; ++sndInd) {
                final int[] arr1 = arrs[fstInd];
                final int[] arr2 = arrs[sndInd];
                if (arr1[l] <= arr2[1]) { // special case
                    writer.write(arr1[l] + "\n");
                    continue;
                }
                if (arr2[l] <= arr1[1]) { // special case
                    writer.write(arr2[l] + "\n");
                    continue;
                }
                int medianI = leftMedian(arr1, arr2, l);
                int medianJ = leftMedian(arr2, arr1, l);
                int median = Integer.min(medianI, medianJ);
                writer.write(median + "\n");
            }
        }
    }
    
}
/*
Использую 4 бин-поиска:
(1) правый внешний по arr1; (2) левый внутренний по arr2,
нахожу i, вычисляю j и беру medianI = max(arr1[i], arr2[j]);
(3) правый внешний по arr1; (4) левый внутренний по arr2,
нахожу j, вычисляю i и беру medianJ = max(arr2[j], arr1[i]).
Остается определить median = min(medinI, medianJ).
Хорошие условия: для (2) arr1[i] <= arr2[j], для (4) arr2[j] <= arr1[i].
Еще надо не забыть про spec-cases, когда
arr1[l] <= arr2[1] || arr2[l] <= arr1[1].

l=8
pos:   1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16
arr1:  0  0  7 14 20 24 29 32
arr2:  9 15 16 18 21 29 30 30
mrg:   0  0  7  9 14 15 16 18 20 21 24 29 29 30 30 32
right-bin-search : good { i + j <= l }.
    left-bin-search : good { arr1[i] <= arr2[j] }.
[1 5 8] [5 5]
[1 3 4] [3 1]
[3 4 4] [4 2] rightI = 4
i = 4 => j = 4; medianI = max(arr1[i], arr2[j]) = 18
right-bin-search : good { i + j <= l }.
    left-bin-search : good { arr2[j] <= arr1[i] }.
[1 5 8] [5 6]
[1 3 4] [3 5]
[3 4 4] [4 5] rightJ = 3
j = 3 => i = 5; medianJ = max(arr2[j], arr1[i]) = 20
median = min(medianI, medianJ) = 18

l=10
pos:   1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20
arr1:  0  0  7 15 15 15 20 24 29 32
arr2:  9 15 15 15 15 18 21 29 30 30
mrg:   0  0  7  9 15 15 15 15 15 15 15 18 20 21 24 29 29 30 30 32
right-bin-search : good { i + j <= l }.
    left-bin-search : good { arr1[i] <= arr2[j] }.
[1 6 10] [6 2]
[6 8 10] [8 8]
[6 7 7] [7 7] rightI = 6
i = 6 => j = 4; medianI = max(arr1[i], arr2[j]) = 15
right-bin-search : good { i + j <= l }.
    left-bin-search : good { arr2[j] <= arr1[i] }.
[1 6 10] [6 7]
[1 3 5] [3 4]
[3 4 5] [4 4]
[4 5 5] [5 4] rightJ = 5
j = 5 => i = 5; medianJ = max(arr2[j], arr1[i]) = 15
median = min(medianI, medianJ) = 15

l=2
pos:  1 2
arr1: 1 1
arr2: 3 3
right-bin-search : good { i + j <= l }.
    left-bin-search : good { arr1[i] <= arr2[j] }.
[1 2 2] [2 1] rightI = 1
i = 1 => j = 1; medianI = max(arr1[i], arr2[j]) = 3
right-bin-search : good { i + j <= l }.
    left-bin-search : good { arr2[j] <= arr1[i] }.
[1 2 2] [2 2] rightJ = 1 -> 0
j = 0 => i = 2; medianI = max(arr2[j], arr1[i]) = 1
medin = min(medianI, medinJ) = 1

l=4
pos:  1 2 3 4
arr1: 1 1 1 4
arr2: 3 3 3 3
right-bin-search : good { i + j <= l }.
    left-bin-search : good { arr1[i] <= arr2[j] }.
[1 3 4] [3 1]
[3 4 4] [3 3] rightI = 3
i = 3 => j = 1; medianI = max(arr1[i], arr2[j]) = 3
right-bin-search : good { i + j <= l }.
    left-bin-search : good { arr2[j] <= arr1[i] }.
[1 3 4] [3 4]
[1 3 4] [3 4]
[1 2 2] [2 4] rightJ = 1
j = 1; i = 3; medianJ = max(arr2[j], arr1[i]) = 3
median = min(medianI, medianJ) = 3
*/
