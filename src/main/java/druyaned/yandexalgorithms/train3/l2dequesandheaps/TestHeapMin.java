package druyaned.yandexalgorithms.train3.l2dequesandheaps;

import java.util.Scanner;

public class TestHeapMin {
    
    public static void main(String[] args) {
        int capacity = 15;
        HeapMin<Integer> heap = new HeapMin(capacity);
        System.out.println("  Menu description:");
        System.out.println("    Add value: \"av [INT_VAL]\"");
        System.out.println("    Pop root:  \"pr\"");
        System.out.println("    Quit:      \"q\"");
        String inputPrompt = "--------\nInput(av, pr, q): ";
        Scanner sin = new Scanner(System.in);
        System.out.printf(inputPrompt);
        String inputLine;
        while (!(inputLine = sin.nextLine()).equals("q")) {
            String[] parts = inputLine.split(" ");
            String command = parts[0];
            switch (command) {
                case "av" -> {
                    Integer val = Integer.valueOf(parts[1]);
                    heap.add(val);
                }
                case "pr" -> {
                    Integer root = heap.pop();
                    System.out.printf("Popped root: %s\n", root);
                }
                default -> {}
            }
            heap.show();
            System.out.print(inputPrompt);
        }
    }
    
}
