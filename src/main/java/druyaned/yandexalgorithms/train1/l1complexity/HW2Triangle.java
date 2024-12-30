package druyaned.yandexalgorithms.train1.l1complexity;

import java.util.Scanner;

public class HW2Triangle {
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        if (b < a) {
            int temp = a;
            a = b;
            b = temp;
        }
        if (c < a) {
            int temp = a;
            a = c;
            c = temp;
        }
        if (c < b) {
            int temp = b;
            b = c;
            c = temp;
        }
        if (a + b <= c) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
        }
    }

}
