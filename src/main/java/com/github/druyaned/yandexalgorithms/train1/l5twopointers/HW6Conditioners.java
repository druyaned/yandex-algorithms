package com.github.druyaned.yandexalgorithms.train1.l5twopointers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HW6Conditioners {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Condey implements Comparable<Condey> {
        
        private final int power, price;
        private int minPriceFromNotLower;

        public Condey(int power, int price) {
            this.power = power;
            this.price = price;
        }
        
        @Override
        public int compareTo(Condey other) {
            return power == other.power ? price - other.price : power - other.power;
        }
        
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = Integer.parseInt(reader.readLine());
        String[] elems = reader.readLine().split(" ");
        int[] classPowers = new int[n];
        for (int i = 0; i < n; ++i) {
            classPowers[i] = Integer.parseInt(elems[i]);
        }
        int m = Integer.parseInt(reader.readLine());
        Condey[] condeys = new Condey[m];
        for (int i = 0; i < m; ++i) {
            elems = reader.readLine().split(" ");
            condeys[i] = new Condey(Integer.parseInt(elems[0]), Integer.parseInt(elems[1]));
        }
        // solve
        Arrays.sort(classPowers);
        Arrays.sort(condeys);
        int minPriceFromNotLower = condeys[m - 1].price;
        condeys[m - 1].minPriceFromNotLower = minPriceFromNotLower;
        for (int i = m - 2; i >= 0; --i) {
            if (minPriceFromNotLower > condeys[i].price) {
                minPriceFromNotLower = condeys[i].price;
            }
            condeys[i].minPriceFromNotLower = minPriceFromNotLower;
        }
        int minPrice = 0;
        for (int i = 0, j = 0; i < n; ++i) {
            while (classPowers[i] > condeys[j].power) { // j < m always
                ++j;
            }
            minPrice += condeys[j].minPriceFromNotLower;
        }
        writer.write(minPrice + "\n");
    }
    
}
/*
мощности классов
2 1 3 3 2 4
мощность цена | минимальная цена из всех, что не ниже
1 3 | 2
2 3 | 2
2 2 | 2
4 3 | 3
5 4 | 3
6 3 | 3

Сортирую мощности классов и кондеи по мощностям.
Проходом справа-налево заполню 3-е поле кондея.
Прохожу по классам, находя нужный кондей с помощью 2-го указателя.

Input:
6
2 1 3 3 2 4
6
2 2
6 3
1 3
5 4
2 3
4 3
Output:
15
*/
