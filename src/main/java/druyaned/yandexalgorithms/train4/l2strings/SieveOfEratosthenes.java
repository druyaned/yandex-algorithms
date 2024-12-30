package druyaned.yandexalgorithms.train4.l2strings;

public class SieveOfEratosthenes {
    
    public static final int LIM = (int)1e9 + 9;
    private static final boolean[] isPrime = new boolean[LIM + 1];
    
    static {
        for (int i = 0; i <= LIM; ++i) {
            isPrime[i] = true;
        }
        int cycleLimit = (int)Math.sqrt(LIM);
        for (int num = 2; num <= cycleLimit; ++num) {
            if (isPrime[num]) {
                for (int derivation = 2 * num; derivation <= LIM; derivation += num) {
                    isPrime[derivation] = false;
                }
            }
        }
    }
    
    public static boolean isPrime(int num) {
        return isPrime[num];
    }
    
    public static int[] getPrimes(int from, int to) {
        int n = 0;
        for (int num = from; num <= to; ++num) {
            if (isPrime[num]) {
                n++;
            }
        }
        int[] primes = new int[n];
        for (int num = from, size = 0; num <= to; ++num) {
            if (isPrime[num]) {
                primes[size++] = num;
            }
        }
        return primes;
    }
    
}
