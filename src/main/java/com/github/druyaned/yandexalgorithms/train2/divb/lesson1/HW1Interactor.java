package com.github.druyaned.yandexalgorithms.train2.divb.lesson1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW1Interactor {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int exitStatus = Integer.parseInt(reader.readLine());
        int interactor = Integer.parseInt(reader.readLine());
        int checker = Integer.parseInt(reader.readLine());
        int result;
        switch (interactor) {
            case 0 -> {
                if (exitStatus != 0) {
                    result = 3;
                } else {
                    result = checker;
                }
            }
            case 1 -> result = checker;
            case 4 -> {
                if (exitStatus != 0) {
                    result = 3;
                } else {
                    result = 4;
                }
            }
            case 6 -> result = 0;
            case 7 -> result = 1;
            default -> result = interactor;
        }
        writer.write(result + "\n");
    }
    
}
/*
1)
checker in [0, 7]
interactor in [0, 7]
exitStatus in [-128, 127]
2)

*/