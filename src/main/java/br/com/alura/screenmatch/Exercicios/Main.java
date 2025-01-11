package br.com.alura.screenmatch.Exercicios;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {
        List<Integer> numeros = Arrays.asList(10, 20, 30, 40, 50);
        Optional<Integer> max = numeros.stream()
                .max(Integer::compare);
        max.ifPresent(System.out::println);

        List<String> palavras = Arrays.asList("java", "stram", "lambda", "code");
        Map<Integer, List<String>> agrupamento = palavras.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println(agrupamento);

    }
}
