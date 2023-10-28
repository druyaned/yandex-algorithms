package com.github.druyaned.yandexalgorithms.train1.l6binsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HW8Wires {
    
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
        int[] lengths = new int[n];
        for (int i = 0; i < n; ++i) {
            lengths[i] = Integer.parseInt(reader.readLine());
        }
        // solve
        Arrays.sort(lengths);
        int leftCut = 0, rightCut = lengths[n - 1], midCut;
        while (leftCut < rightCut) {
            midCut = (leftCut + rightCut + 1) / 2;
            int cnt = 0;
            for (int i = n; i > 0; --i) {
                cnt += lengths[i - 1] / midCut;
            }
            if (cnt >= k) {
                leftCut = midCut;
            } else {
                rightCut = midCut - 1;
            }
        }
        writer.write(rightCut + "\n");
    }
    
}
/*
6 8 9 10 12 13 26
  6|1   8|1   9|1  10|1  12|1  13|1  26|1 
  3|2   4|2   4|2   5|2   6|2   6|2  13|2 
  2|3   2|3   3|3   3|3   4|3   4|3   8|3 
        2|4   2|4   2|4   3|4   3|4   6|4 
                    2|5   2|5   2|5   5|5 
                          2|6   2|6   4|6 
                                      3|7 
                                      3|8 
                                      2|9 
                                      2|10
                                      2|11
                                      2|12
                                      2|13
Сортирую массив длин.
Правый бин-поиск по длине отрезка в пределах [0, lengths[n-1]].
Все хорошо тогда, когда количество отрезков не превышает k.
А считать количество придется в цикле по всем элементам.
Для этого буду делить текущую длину на длину конкретного
элемента и получать количество отрезков текущей длины.
*/
