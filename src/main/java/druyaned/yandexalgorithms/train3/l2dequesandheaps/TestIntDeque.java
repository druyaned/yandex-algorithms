package druyaned.yandexalgorithms.train3.l2dequesandheaps;

import java.util.Scanner;

public class TestIntDeque {
    
    public static void main(String[] args) {
        int capacity = 7;
        IntDeque deque = new IntDeque(capacity);
        System.out.println("  Menu description:");
        System.out.println("    Add first:     \"af [INT_VAL]\"");
        System.out.println("    Add last:      \"al [INT_VAL]\"");
        System.out.println("    Remove first:  \"rf\"");
        System.out.println("    Remove last:   \"rl\"");
        System.out.println("    Quit:          \"q\"");
        String inputPrompt = "Input (af, al, rf, rl, q): ";
        String inputLine;
        Scanner sin = new Scanner(System.in);
        deque.show();
        System.out.print(inputPrompt);
        while (!(inputLine = sin.nextLine()).equals("q")) {
            String[] parts = inputLine.split(" ");
            String command = parts[0];
            switch (command) {
                case "af" -> {
                    int val = Integer.parseInt(parts[1]);
                    deque.addFirst(val);
                }
                case "al" -> {
                    int val = Integer.parseInt(parts[1]);
                    deque.addLast(val);
                }
                case "rf" -> {
                    deque.removeFirst();
                }
                case "rl" -> {
                    deque.removeLast();
                }
                default -> {}
            }
            deque.show();
            System.out.print(inputPrompt);
        }
    }
    
}
