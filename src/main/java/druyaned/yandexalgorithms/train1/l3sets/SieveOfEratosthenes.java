package druyaned.yandexalgorithms.train1.l3sets;

public class SieveOfEratosthenes {
    
    public static void main(String[] args) {
        final int MIN_VALUE = (int)1e4;
        final int GAP = 200;
        final int MAX_VALUE = MIN_VALUE + GAP;
        boolean[] sieveOfEratosthenes = new boolean[MAX_VALUE + 1];
        for (int i = 2; i <= MAX_VALUE / 2; i++) {
            if (!sieveOfEratosthenes[i]) {
                for (int j = i * 2; j <= MAX_VALUE; j += i) {
                    sieveOfEratosthenes[j] = true;
                }
            }
        }
        for (int i = MIN_VALUE; i <= MAX_VALUE; i++) {
            if (!sieveOfEratosthenes[i]) {
                System.out.printf(" %d", i);
            }
        }
        System.out.println();
    }
    
}
