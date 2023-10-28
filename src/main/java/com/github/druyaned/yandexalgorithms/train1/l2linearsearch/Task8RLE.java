package com.github.druyaned.yandexalgorithms.train1.l2linearsearch;

import java.util.Scanner;

/**
 * Лекция 2, задача №8.
 * <p><b>Дано</b>
 * <p>Строка (возможно, пустая), состоящая из букв A-Z:
 * <code>AAAABBBCCXYZDDDD</code>. Нужно написать функцию RLE,
 * которая на выход даст строку вида: <code>A4B3C2XYZD4</code>.
 * И сгенерирует ошибку, если на вход пришла невалидная строка.
 * 
 * @author druyaned
 */
public class Task8RLE {
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String line = scanner.nextLine();
        Task8RLE task8 = new Task8RLE();
        System.out.println(task8.RLE(line));
    }
    
    public String RLE(String line) throws IllegalArgumentException {
        StringBuilder encoded = new StringBuilder();
        int i = 0;
        while (i < line.length()) {
            char ch = line.charAt(i);
            if ('Z' < ch || ch < 'A') {
                throw new IllegalArgumentException("invalid line");
            }
            int startIndex = i++;
            while (i < line.length() && line.charAt(i) == ch) {
                ++i;
            }
            int count = i - startIndex;
            encoded.append(ch);
            if (1 < count) {
                encoded.append(count);
            }
        }
        return encoded.toString();
    }
    
}
