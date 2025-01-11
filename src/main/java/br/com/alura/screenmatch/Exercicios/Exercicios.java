package br.com.alura.screenmatch.Exercicios;

import static java.lang.Math.*;

@FunctionalInterface
interface Multiplicacao{
    int multiplicacao(int a, int b);
}

@FunctionalInterface
interface Primo {
    boolean verificarPrimo(int n);
}

@FunctionalInterface
interface Transformar {
    String transformar(String s);
}

public class Exercicios {
    public static void main(String[] args) {
        Multiplicacao multi = (a, b) -> a * b;
        System.out.println(multi.multiplicacao(8, 5));

        Primo primo = n -> {
            if (n <= 1) return false;
            for (int i = 2; i <= sqrt(n); i++) if (n % i == 0) return false;
            return true;
        };
        System.out.println(primo.verificarPrimo(11));
        System.out.println(primo.verificarPrimo(12));

        Transformar toUpperCase = s -> s.toUpperCase();
        System.out.println(toUpperCase.transformar("java"));
    }
}

