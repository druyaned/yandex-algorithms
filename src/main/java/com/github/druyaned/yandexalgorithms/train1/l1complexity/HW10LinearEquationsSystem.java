package com.github.druyaned.yandexalgorithms.train1.l1complexity;

import java.util.Scanner;

public class HW10LinearEquationsSystem {
    
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();
        double d = scanner.nextDouble();
        double e = scanner.nextDouble();
        double f = scanner.nextDouble();
        System.out.println(result2(a, b, c, d, e, f));
    }
    
    public static String result(double a, double b, double c, double d, double e, double f) {
        // { x = (e*d - f*b)/(a*d - c*b); y = (f*a - e*c)/(a*d - c*b) }
        double den = a * d - c * b; // denominator
        double numX = e * d - f * b; // numerator of the X
        double numY = f * a - e * c; // numerator of the Y
        boolean everyX = a == 0.0 && c == 0.0;
        boolean everyY = b == 0.0 && d == 0.0;
        if (den != 0.0) {
            return "2 " + (numX / den) + " " + (numY / den);
        } else {
            if (numX == 0.0 && numY == 0.0) {
                if (everyX && everyY) {
                    if (e != 0.0 || f != 0.0) {
                        return "0";
                    } else {
                        return "5";
                    }
                } else if (everyX) {
                    if (b != 0) {
                        return "4 " + (e / b);
                    } else {
                        return "4 " + (f / d);
                    }
                } else if (everyY) {
                    if (a != 0) {
                        return "3 " + (e / a);
                    } else {
                        return "3 " + (f / c);
                    }
                } else {
                    if (b != 0.0) {
                        return "1 " + (-a / b) + " " + (e / b);
                    } else {
                        return "1 " + (-c / d) + " " + (f / d);
                    }
                }
            } else {
                return "0";
            }
        }
    }
    
    public static String result2(double a, double b, double c, double d, double e, double f) {
        // { x = (e*d - f*b)/(a*d - c*b); y = (f*a - e*c)/(a*d - c*b) }
        double den = a * d - c * b;
        double numX = e * d - f * b;
        double numY = f * a - e * c;
        boolean eachY = b == 0.0 && d == 0.0;
        boolean eachX = a == 0.0 && c == 0.0;
        if (den != 0) {
            return "2 " + (numX / den) + " " + (numY / den);
        } else {
            if (eachY && eachX) { // 0 0 0 0
                if (e == 0 && f == 0) {
                    return "5";
                } else {
                    return "0";
                }
            } else if (eachY) { // ? 0 ? 0
                if (a == 0) {
                    if (e == 0) {
                        return "3 " + (f / c);
                    } else {
                        return "0"; // a == 0 && c != 0 && e != 0
                    }
                } else if (c == 0) {
                    if (f == 0) {
                        return "3 " + (e / a);
                    } else {
                        return "0"; // a != 0 && c == 0 && f != 0
                    }
                } else {
                    double x1 = f / c;
                    double x2 = e / a;
                    if (x1 == x2) {
                        return "3 " + x1;
                    } else {
                        return "0"; // a != 0 && c != 0 && f * a != e * c
                    }
                }
            } else if (eachX) { // 0 ? 0 ?
                if (b == 0) {
                    if (e == 0) {
                        return "4 " + (f / d);
                    } else {
                        return "0"; // b == 0 && d != 0 && e != 0
                    }
                } else if (d == 0) {
                    if (f == 0) {
                        return "4 " + (e / b);
                    } else {
                        return "0"; // b != 0 && d == 0 && f != 0
                    }
                } else {
                    double y1 = f / d;
                    double y2 = e / b;
                    if (y1 == y2) {
                        return "4 " + y1;
                    } else {
                        return "0"; // b != 0 && d != 0 && e * d != f * b
                    }
                }
            } else {
                if (e * d == f * b && f * a == e * c) {
                    double k, h;
                    if (b == 0) {
                        k = -c / d;
                        h = f / d;
                    } else {
                        k = -a / b;
                        h = e / b;
                    }
                    return "1 " + k + " " + h;
                } else {
                    return "0";
                }
            }
        }
    }

}
/*
Решение следует из рисунка.
2-е решение идентично 1-му, но понятнее из рисунка.
{
    a*x + b*y = e;
    c*x + d*y = f;
}
{
    y = (-a/b)*x + e/b;
    y = (-c/d)*x + f/d;
}
{
    x = (e*d - f*b)/(a*d - c*b);
    y = (f*a - e*c)/(a*d - c*b);
}


a b c d e f
1 0 0 1 3 3
2 3 3

a b c d e f
1 1 2 2 1 2
1 -1 1

a b c d e f
0 2 0 4 1 2
4 0.5
*/
