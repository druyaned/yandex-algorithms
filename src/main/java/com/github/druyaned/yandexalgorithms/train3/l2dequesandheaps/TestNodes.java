package com.github.druyaned.yandexalgorithms.train3.l2dequesandheaps;

import java.util.Scanner;

public class TestNodes {
    
    public static void main(String[] args) {
        Nodes<Integer> nodes = new Nodes();
        Nodes<Integer>.Iterator iter = nodes.iterator();
        System.out.println("  Menu description:");
        System.out.println("    Previous:           \"p\"");
        System.out.println("    Next:               \"n\"");
        System.out.println("    Add Before:         \"ab [INT_VAL]\"");
        System.out.println("    Add After:          \"aa [INT_VAL]\"");
        System.out.println("    Remove Previous:    \"rp\"");
        System.out.println("    Remove Next:        \"rn\"");
        System.out.println("    Move Back:          \"mb\"");
        System.out.println("    Move Forward:       \"mf\"");
        System.out.println("    Move Before First:  \"m1\"");
        System.out.println("    Move After Last:    \"ml\"");
        System.out.println("    Size:               \"s\"");
        System.out.println("    Quit:               \"q\"");
        String inputPrompt = "--------\nInput: ";
        Scanner sin = new Scanner(System.in);
        String inputLine;
        System.out.printf(inputPrompt);
        while (!(inputLine = sin.nextLine()).equals("q")) {
            String[] parts = inputLine.split(" ");
            String command = parts[0];
            switch (command) {
                case "p" -> { System.out.printf("Output: %s\n", iter.previous()); }
                case "n" -> { System.out.printf("Output: %s\n", iter.next()); }
                case "ab" -> { iter.addBefore(Integer.valueOf(parts[1])); }
                case "aa" -> { iter.addAfter(Integer.valueOf(parts[1])); }
                case "rp" -> { System.out.printf("Output: %s\n", iter.removePrevious()); }
                case "rn" -> { System.out.printf("Output: %s\n", iter.removeNext()); }
                case "mb" -> { System.out.printf("Output: %s\n", iter.moveBack()); }
                case "mf" -> { System.out.printf("Output: %s\n", iter.moveForward()); }
                case "m1" -> { iter.moveBeforeFirst(); }
                case "ml" -> { iter.moveAfterLast(); }
                case "s" -> { System.out.printf("Output: %d\n", nodes.size());}
                default -> {}
            }
            System.out.print("nodes:");
            nodes.forEach(i -> System.out.printf(" %s", i));
            System.out.println();
            System.out.print(inputPrompt);
        }
    }
    
}
