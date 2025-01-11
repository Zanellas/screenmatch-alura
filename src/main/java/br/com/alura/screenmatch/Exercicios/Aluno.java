package br.com.alura.screenmatch.Exercicios;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

public class Aluno {
    private String nome;
    private LocalDate nascimento;

    public Aluno(String nome, LocalDate nascimento) { this.nome = nome; this.nascimento = nascimento; }

    public int getIdade() {
        Period periodo = Period.between(nascimento, LocalDate.now());
        return periodo.getYears();
    }


}

class Principal {
    public static void main(String[] args) {
        List<Aluno> alunos = Arrays.asList(
                new Aluno("Alice", LocalDate.of(2002, 10, 20)),
                new Aluno("Bob", LocalDate.of(1980, 8, 9)),
                new Aluno("Carlos", LocalDate.of(2001, 01, 28)),
                new Aluno("David", LocalDate.of(2003, 05,12)),
                new Aluno("Eva", LocalDate.of(2005, 12, 03))
        );

        IntSummaryStatistics stats = alunos.stream()
                .mapToInt(Aluno::getIdade)
                .summaryStatistics();

        System.out.println("Idade Media: " + stats.getAverage());
        System.out.println("Minima Idade: " + stats.getMin());
        System.out.println("Maxima Idade: " + stats.getMax());
        System.out.println("Total de alunos: " + stats.getCount());

    }
}