package com.github.druyaned.yandexalgorithms.train1.l4maps;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Лекция 4 задача 3.
 * Дана строка S. Выведите гистрограмму как в примере (коды символов отсортированы).
 * <pre>
 * S = Hello, world!
 *       #   
 *       ##  
 * ##########
 *  !,Hdelorw
 * </pre>
 * 
 * @author druyaned
 */
public class Task3Histogram {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        System.out.println(histogram(s));
    }
    
    public static String histogram(String s) {
        StringBuilder histogramBuilder = new StringBuilder();
        Map<Character, Integer> lettersCount = new TreeMap<>();
        Integer maxCount = 1;
        for (char letter : s.toCharArray()) {
            Integer count = lettersCount.get(letter);
            if (count == null) {
                lettersCount.put(letter, 1);
            } else {
                Integer newCount = count + 1;
                if (maxCount.compareTo(newCount) < 0) {
                    maxCount = newCount;
                }
                lettersCount.put(letter, newCount);
            }
        }
        for (int currentCount = maxCount; 0 <= currentCount; --currentCount) {
            for (int count : lettersCount.values()) {
                if (currentCount <= count) {
                    histogramBuilder.append('#');
                } else {
                    histogramBuilder.append(' ');
                }
            }
            histogramBuilder.append('\n');
        }
        for (Character letter : lettersCount.keySet()) {
            histogramBuilder.append(letter);
        }
        return histogramBuilder.toString();
    }

}
