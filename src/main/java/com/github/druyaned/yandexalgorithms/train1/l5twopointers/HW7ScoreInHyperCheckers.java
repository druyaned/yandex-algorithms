package com.github.druyaned.yandexalgorithms.train1.l5twopointers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class HW7ScoreInHyperCheckers {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = Integer.parseInt(elems[0]);
        int k = Integer.parseInt(elems[1]);
        int[] x = new int[n];
        elems = reader.readLine().split(" ");
        for (int i = 0; i < n; ++i) {
            x[i] = Integer.parseInt(elems[i]);
        }
        // solve
        HashMap<Integer, Integer> keyCounts = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            Integer count = keyCounts.get(x[i]);
            if (count == null) {
                keyCounts.put(x[i], 1);
            } else {
                keyCounts.put(x[i], count + 1);
            }
        }
        Set<Integer> keySet = keyCounts.keySet();
        int[] keys = new int[keySet.size()];
        int v = 0; // volume of the set == keySet.size()
        for (int key : keySet) {
            keys[v++] = key;
        }
        Arrays.sort(keys);
        int[] twoCounts = new int[v + 1];
        for (int i = v; i > 0; --i) {
            int count = keyCounts.get(keys[i - 1]);
            twoCounts[i - 1] = count > 1 ? twoCounts[i] + 1 : twoCounts[i];
        }
        long cnt = 0L;
        int twoCount = 0;
        for (int l = 0, r = 0; l < v; ++l) {
            while (r < v && (keys[r] - 1) / keys[l] + 1 <= k) {
                if (keyCounts.get(keys[r]) >= 2) {
                    ++twoCount;
                }
                ++r;
            }
            int keyCount = keyCounts.get(keys[l]);
            final long curV = r - l; // current volume of the set
            cnt += (curV - 1L) * (curV - 2L) * 3L; // no reputations
            if (keyCount >= 2) {
                cnt += (curV - 1L) * 3L; // two reputations
                --twoCount;
            }
            cnt += twoCount * 3L; // two reputations
            if (keyCount >= 3) {
                cnt += 1L; // three reputations
            }
        }
        writer.write(cnt + "\n");
    }
    
}
/*
Input:
9 3
3 4 5 6 6 7 7 7 8
Output:
151

Input:
11 2
1 2 2 3 4 12 21 21 23 23 23
Output:
34

Создаю keyCount - отображение ключ-количество.
Создаю keys - отсортированный массив уникальных ключей (x-ов).
v - объем множества ключей (размер массива).
Прохожу по keys c двумя указателями : l в [0, v),
находя r пока (keys[r] - 1) / keys[l] + 1 <= k.

Как считать количество перестановок
по 3-м элементам из мультимножества?
Рассмотрю на примере.

x:   3 4 5 6 6 7 7 7 8
key: 3 4 5 6 7 8 | v=6 (volume of the set)
cnt: 1 1 1 2 3 1

No repetitions:
3;[45678]
    {3;4;[5678]}
    {3;5;[678]}
    {3;6;[78]}
    {3;7;8}
4;[5678]
    {4;5;[678]}
    {4;6;[78]}
    {4;7;8}
5;[678]
    {5;6;[78]}
    {5;7;8}
6;[78]
    {6;7;8}
4+3+2+1 + 3+2+1 + 2+1 + 1 = 4*1 + 3*2 + 2*3 + 1*4;
2*(4*1 + 3*2) = 20;
20*6 = 120

With repetitions:
3;[45678]
    {3;4;[5678]}
    {3;5;[678]}
    {3;6;[678]} | +1 (*3)
    {3;7;[78]} | +1 (*3)
4;[5678]
    {4;5;[678]}
    {4;6;[678]} | +1 (*3)
    {4;7;[78]} | +1 (*3)
5;[678]
    {5;6;[678]} | +1 (*3)
    {5;7;[78]} | +1 (*3)
6;[678]
    {6;6;[78]} | +2 (*3)
    {6;7;[78]} | +1 (*3)
7;[78]
    {7;7;7} | +1 (*1)
    {7;7;8} | +1 (*3)

No reputitions:    v-2 = 4; 2*(4*1 + 3*2) * 6 = 120
    int m = 1;
    for (; m <= (v-2)/2; ++m) {permutationCount += 2 * (v-1-m) * m;}
    if (v-1-m == m) {permutationCount += m * m;}
    permutationCount *= 6;
Two reputitions:   v-1 = 5; twoCount=2; 5 * 2 * 3 = 30
    permutationCount += (v-1) * twoCount * 3;
Three reputitions: threeCount=1
    if (keyCount >= 3) permutationCount += 1;
permutationCount = 120+30+1 = 151

Но надо высчитать для одного элемента, который в l.
No reputitions:    v=r-l; m=v-2; cnt += ( (1+m)*m/2 )*6
Two reputitions:   cnt += twoCount * 3; if (keyCount >= 2) {cnt += (v - 1) * 3;}
Three reputitions: if (keyCount >= 3) {cnt += 1;}
*/
