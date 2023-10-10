package com.github.druyaned.yandexalgorithms.train1.lect3;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Лекция 3 задача 2.
 * 
 * <p>Дан словарь из N слов, длина каждого не превосходит K.
 * В записи каждого из M слов текста может быть пропущена одна буква.
 * Для каждого слова сказать, входит ли оно (возможно, с одной пропущенной
 * буквой), в словарь.
 * 
 * @author druyaned
 */
public class Task2Dictionary {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] dictionary = new String[n];
        for (int i = 0; i < n; ++i) {
            dictionary[i] = scanner.next();
        }
        int m = scanner.nextInt();
        scanner.nextLine(); // '\n' in an input queue
        String[] text = new String[m];
        for (int i = 0; i < m; ++i) {
            text[i] = scanner.next();
        }
        for (String answer : answers(n, dictionary, m, text)) {
            System.out.println(answer);
        }
    }
    
    public static String[] answers(final int N, String[] dictionary, final int M, String[] text) {
        String[] answer = new String[M];
        Set<String> dictionarySet = new HashSet<>();
        for (int i = 0; i < N; ++i) {
            String word = dictionary[i];
            dictionarySet.add(word);
            final int L = word.length();
            if (1 < L) {
                for (int j = 0; j < L; ++j) {
                    String withoutChar = word.substring(0, j) + word.substring(j + 1, L);
                    dictionarySet.add(withoutChar);
                }
            }
        }
        for (int i = 0; i < M; ++i) {
            String word = text[i];
            answer[i] = dictionarySet.contains(word) ? "YES" : "NO";
        }
        return answer;
    }

}
/*
Input:
3
abc bcd xyz
7
hi abuc bd xyz zy yz xy
Output:
NO
NO
YES
YES
NO
YES
YES
*/