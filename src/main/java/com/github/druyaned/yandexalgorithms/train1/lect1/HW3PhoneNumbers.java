package com.github.druyaned.yandexalgorithms.train1.lect1;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;

public class HW3PhoneNumbers {
    
    public static void main(String[] args) {
        final int N = 3;
        final int[] modeLengths = {2 + 3 + 7, 1 + 3 + 7, 7};
        InputStreamReader reader = new InputStreamReader(System.in);
        String newNumber;
        String[] numbers = new String[N];
        StringBuilder builder = new StringBuilder();
        try {
            // newNumber
            char ch = (char)reader.read();
            while (ch != -1 && ch != '\n') {
                if (ch != '-' && ch != '(' && ch != ')') {
                        builder.append(ch);
                    }
                ch = (char)reader.read();
            }
            if (builder.length() == modeLengths[0]) {
                newNumber = builder.substring(2);
            } else if (builder.length() == modeLengths[1]) {
                newNumber = builder.substring(1);
            } else {
                newNumber = "495" + builder.toString();
            }
            // written numbers
            for (int i = 0; i < N; ++i) {
                builder.setLength(0);
                ch = (char)reader.read();
                while (ch != -1 && ch != '\n') {
                    if (ch != '-' && ch != '(' && ch != ')') {
                        builder.append(ch);
                    }
                    ch = (char)reader.read();
                }
                if (builder.length() == modeLengths[0]) {
                    numbers[i] = builder.substring(2);
                } else if (builder.length() == modeLengths[1]) {
                    numbers[i] = builder.substring(1);
                } else {
                    numbers[i] = "495" + builder.toString();
                }
            }
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
        for (int i = 0; i < N; ++i) {
            if (newNumber.equals(numbers[i])) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

}
