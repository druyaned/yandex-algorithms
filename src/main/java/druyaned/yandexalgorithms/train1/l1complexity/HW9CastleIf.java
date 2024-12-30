package druyaned.yandexalgorithms.train1.l1complexity;

import java.util.Scanner;

public class HW9CastleIf {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        int d = scanner.nextInt();
        int e = scanner.nextInt();
        if (prisonerAbility(a, b, c, d, e)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
    
    public static boolean prisonerAbility(int a, int b, int c, int d, int e) {
        if (c < a) {
            int temp = a;
            a = c;
            c = temp;
        }
        if (c < b) {
            b = c;
        }
        if (b < a) {
            int temp = a;
            a = b;
            b = temp;
        }
        if (e < d) {
            int temp = d;
            d = e;
            e = temp;
        }
        return a <= d && b <= e;
    }

}
