package com.github.druyaned.yandexalgorithms.train2.diva.lesson1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;

public class FuturamaDraft {
    
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            solve(reader);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void showArr(int[] arr, int n) {
        System.out.printf("%d", arr[1]);
        for (int i = 2; i <= n; ++i) {
            System.out.printf(" %d", arr[i]);
        }
        System.out.println();
    }
    
    private static void swap(int[] arr, int i1, int i2) {
        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }
    
    private static int pairHash(int n, int i1, int i2) {
        return i1 * n + i2;
    }
    
    public static void solve(BufferedReader reader) throws IOException {
        // initialize
        int n = 8;
        int[] arr = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            arr[i] = i;
        }
        showArr(arr, n);
        // prepare
        int hashTableSize = (n - 1) * n + n + 1;
        boolean[] usedPairTable = new boolean[hashTableSize];
        int pairsAmount = (n - 1) * n / 2;
        int[] stack1 = new int[pairsAmount];
        int[] stack2 = new int[pairsAmount];
        int stackSize = 0;
        // interactive
        System.out.println("Pair input format: \"num1 num2\".");
        System.out.println("Revert input format: \"r REVERT_TO\".");
        System.out.println("Stack input format: \"s\".");
        System.out.println("Left input format: \"l\".");
        System.out.println("Quit input format: \"q\".");
        String menu = "1) pair; 2) revert; 3) stack; 4) left; 5) quit: ";
        String line;
        System.out.print(menu);
        while (!(line = reader.readLine()).equals("q")) {
            String[] elems = line.split(" ");
            if (elems.length == 0 || elems[0].length() == 0) {
                showArr(arr, n);
                System.out.print(menu);
                continue;
            }
            char ch = elems[0].charAt(0);
            if ('0' <= ch && ch <= '9') {
                int i1 = Integer.parseInt(elems[0]);
                int i2 = Integer.parseInt(elems[1]);
                int pairHash = pairHash(n, i1, i2);
                if (i1 < 1 || n < i1 || i2 < 1 || n < i2 || i1 >= i2 || usedPairTable[pairHash]) {
                    System.out.println("wrong input");
                } else {
                    swap(arr, i1, i2);
                    usedPairTable[pairHash] = true;
                    stack1[stackSize] = i1;
                    stack2[stackSize++] = i2;
                }
            }
            if (elems[0].equals("r")) {
                int revertTo = Integer.parseInt(elems[1]);
                if (revertTo < 0 || stackSize < revertTo) {
                    System.out.println("wrong input");
                } else {
                    while (stackSize != revertTo) {
                        int i1 = stack1[stackSize - 1];
                        int i2 = stack2[--stackSize];
                        int pairHash = pairHash(n, i1, i2);
                        swap(arr, i1, i2);
                        usedPairTable[pairHash] = false;
                    }
                    for (int i = 0; i < stackSize; ++i) {
                        System.out.printf("%2d: %d %d\n", i, stack1[i], stack2[i]);
                    }
                }
            }
            if (elems[0].equals("s")) {
                for (int i = 0; i < stackSize; ++i) {
                    System.out.printf("%2d: %d %d\n", i, stack1[i], stack2[i]);
                }
            }
            if (elems[0].equals("l")) {
                int i = 0;
                for (int i1 = 1; i1 < n - 1; ++i1) {
                    int pair1Hash = i1 * n + n - 1;
                    int pair2Hash = i1 * n + n;
                    if (!usedPairTable[pair1Hash]) {
                        System.out.printf("%2d: %d %d\n", i++, i1, n - 1);
                    }
                    if (!usedPairTable[pair2Hash]) {
                        System.out.printf("%2d: %d %d\n", i++, i1, n);
                    }
                }
                System.out.printf("%2d: %d %d\n", i++, n - 1, n);
            }
            showArr(arr, n);
            System.out.print(menu);
        }
    }
    
}
