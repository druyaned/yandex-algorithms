package com.github.druyaned.yandexalgorithms.train1.lect6;

import java.util.Scanner;

/**
 * Лекция 6 задача 6.
 * Задана процентная ставка по кредиту (X% годовых),
 * срок крдитования (N месяцев) и сумма кредита (M рублей).
 * <br>Необходимо рассчитать размер аннуитетного ежемесячного платежа.
 * 
 * @author druyaned
 */
public class Task6Hypothec {
    
    public static void main(String[] args) {
        double annualRate = 5.2; // in %
        int months = 120;
        double credit = 10000000d; // in Russian rubles
        double annualFactor = annualRate / 100d;
        double monthlyPayment = monthlyPayment(annualFactor, months, credit);
        double overPayment = monthlyPayment * months - credit;
        System.out.println("Аннуитетный платеж: " + monthlyPayment);
        System.out.println("Кредит / месяцы: " + credit / months);
        System.out.println("Общая переплата: " + overPayment);
    }
    
    public static double monthlyPayment(double annualFactor, int months, double credit) {
        double monthlyFactor = monthlyFactor(annualFactor);
        double leftPayment = 0d, rightPayment = credit;
        double midPayment = (leftPayment + rightPayment) / 2d;
        double epsilon = Math.ulp(leftPayment);
        while (leftPayment + epsilon < rightPayment) {
            if (willBeRepaid(monthlyFactor, months, credit, midPayment)) {
                rightPayment = midPayment;
            } else {
                leftPayment = midPayment;
            }
            midPayment = (leftPayment + rightPayment) / 2d;
            epsilon = Math.ulp(leftPayment);
        }
        return leftPayment + epsilon;
    }
    
    private static double monthlyFactor(double annualFactor) {
        double key = 1d + annualFactor;
        double leftFactor = 0d, rightFactor = annualFactor;
        double midFactor = (leftFactor + rightFactor) / 2d;
        double epsilon = Math.ulp(leftFactor);
        while (leftFactor + epsilon < rightFactor) {
            double element = times(1d + midFactor, 12);
            if (key <= element) {
                rightFactor = midFactor;
            } else {
                leftFactor = midFactor;
            }
            midFactor = (leftFactor + rightFactor) / 2d;
            epsilon = Math.ulp(leftFactor);
        }
        return leftFactor + epsilon;
    }
    
    private static double times(double value, int power) {
        double result = value;
        for (int i = 1; i < power; ++i) {
            result *= value;
        }
        return result;
    }
    
    private static boolean willBeRepaid(double monthlyFactor, double months, double credit,
            double monthlyPayment) {
        for (int i = 0; i < months; ++i) {
            double ratePayment = credit * monthlyFactor;
            credit -= monthlyPayment - ratePayment;
        }
        return credit <= 0d;
    }
    
}
