package druyaned.yandexalgorithms.train1.l1complexity;

import static java.lang.Integer.max;
import java.util.Scanner;

public class HW6Arrangement {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        int w1 = scanner.nextInt();
        int h1 = scanner.nextInt();
        int w2 = scanner.nextInt();
        int h2 = scanner.nextInt();
        Dimension tableDimension = tableDimension(w1, h1, w2, h2);
        System.out.println(tableDimension.w + " " + tableDimension.h);
    }
    
    public static class Dimension {
        
        public final int w, h;
        
        public Dimension(int w, int h) {
            this.w = w;
            this.h = h;
        }
        
    }
    
    public static Dimension tableDimension(int w1, int h1, int w2, int h2) {
        final int N = 4;
        int[] w = {w1 + w2, w1 + h2, h1 + w2, h1 + h2};
        int[] h = {max(h1, h2), max(h1, w2), max(w1, h2), max(w1, w2)};
        int sMin = w[0] * h[0];
        int iMin = 0;
        for (int i = 0; i < N; ++i) {
            int s = w[i] * h[i];
            if (s < sMin) {
                sMin = s;
                iMin = i;
            }
        }
        return new Dimension(w[iMin], h[iMin]);
    }

}
