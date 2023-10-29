package com.github.druyaned.yandexalgorithms.train3.l2dequesandheaps;

import java.util.Scanner;

public class TestIntDeque {
    
    public static void main(String[] args) {
        interactiveTest();
    }
    
    private static void interactiveTest() {
        System.out.println("\nInteractive test:");
        int capacity = 7;
        IntDeque deque = new IntDeque(capacity);
        System.out.println("  Menu description:");
        System.out.println("    Add first:     \"af [INT_VAL]\"");
        System.out.println("    Add last:      \"al [INT_VAL]\"");
        System.out.println("    Remove first:  \"rf\"");
        System.out.println("    Remove last:   \"rl\"");
        System.out.println("    Quit:          \"q\"");
        String menu = "Input (af, al, rf, rl, q): ";
        String line;
        Scanner sin = new Scanner(System.in);
        deque.show();
        System.out.print(menu);
        while (!(line = sin.nextLine()).equals("q")) {
            String[] elems = line.split(" ");
            if (elems.length == 2) {
                String cmd = elems[0];
                int val = Integer.parseInt(elems[1]);
                if (cmd.equals("af")) {
                    deque.addFirst(val);
                }
                if (cmd.equals("al")) {
                    deque.addLast(val);
                }
            }
            if (elems.length == 1) {
                if (line.equals("rf")) {
                    deque.removeFirst();
                }
                if (line.equals("rl")) {
                    deque.removeLast();
                }
            }
            deque.show();
            System.out.print(menu);
        }
    }
    
}
