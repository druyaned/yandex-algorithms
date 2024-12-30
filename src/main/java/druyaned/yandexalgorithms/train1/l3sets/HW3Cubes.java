package druyaned.yandexalgorithms.train1.l3sets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Arrays;

public class HW3Cubes {
    
    public static void main(String[] args) {
        int n, m, anya[], borya[];
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] words = reader.readLine().split(" ");
            n = Integer.parseInt(words[0]);
            m = Integer.parseInt(words[1]);
            anya = new int[n];
            borya = new int[m];
            for (int i = 0; i < n; ++i) {
                anya[i] = Integer.parseInt(reader.readLine());
            }
            for (int i = 0; i < m; ++i) {
                borya[i] = Integer.parseInt(reader.readLine());
            }
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
        Arrays.sort(anya);
        Arrays.sort(borya);
        int maxN = n > m ? n : m;
        int[] common = new int[maxN];
        int nc = 0, key, left, right, mid;
        for (int i = 0; i < m; ++i) {
            key = borya[i];
            left = 0;
            right = n - 1;
            while (left <= right) {
                mid = (left + right) / 2;
                if (key == anya[mid]) {
                    common[nc++] = key;
                    break;
                } else if (key < anya[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        System.out.printf("%d\n", nc);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nc; ++i) {
            builder.append(common[i]).append(' ');
        }
        System.out.printf("%s\n%d\n", builder.toString(), n - nc);
        builder.setLength(0);
        for (int ia = 0, ic = 0; ia < n; ++ia) {
            if (ic < nc && anya[ia] == common[ic]) {
                ++ic;
            } else {
                builder.append(anya[ia]).append(' ');
            }
        }
        System.out.printf("%s\n%d\n", builder.toString(), m - nc);
        builder.setLength(0);
        for (int ib = 0, ic = 0; ib < m; ++ib) {
            if (ic < nc && borya[ib] == common[ic]) {
                ++ic;
            } else {
                builder.append(borya[ib]).append(' ');
            }
        }
        System.out.printf("%s\n", builder.toString());
    }
    
}
