package com.github.druyaned.yandexalgorithms.train1.l4maps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Лекция 4 задача 4.
 * Сгруппировать слова по общим буквам.
 * 
 * <p><b>Input</b>:
 * <pre>
 * eat tea tan ate nat bat
 * </pre>
 * <b>Output</b>:
 * <pre>
 * ate eat tea
 * nat tan
 * bat
 * </pre>
 * 
 * @author druyaned
 */
public class Task4GroupWords {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] words = line.split(" ");
        String[] groups = groups(words);
        for (String group : groups) {
            System.out.println(group);
        }
    }
    
    public static String[] groups(String[] words) {
        Map<String, Set<String>> dictionary = new HashMap<>();
        for (String word : words) {
            String sortedWord = sortedWord(word);
            Set<String> groupOfWords = dictionary.get(sortedWord);
            if (groupOfWords == null) {
                groupOfWords = new HashSet<>();
                groupOfWords.add(word);
                dictionary.put(sortedWord, groupOfWords);
            } else {
                groupOfWords.add(word);
            }
        }
        String[] groups = new String[dictionary.size()];
        int i = 0;
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Set<String>> entry : dictionary.entrySet()) {
            Set<String> groupOfWords = entry.getValue();
            builder.setLength(0);
            Iterator<String> iter = groupOfWords.iterator();
            if (iter.hasNext()) {
                builder.append(iter.next());
            }
            while (iter.hasNext()) {
                builder.append(' ').append(iter.next());
            }
            groups[i++] = builder.toString();
        }
        return groups;
    }
    
    private static String sortedWord(String word) {
        char[] letters = word.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }

}
