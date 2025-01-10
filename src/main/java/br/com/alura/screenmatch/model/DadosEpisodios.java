package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodios(@JsonAlias("title") String titulo,
                             @JsonAlias("episode") Integer numero,
                             @JsonAlias("imdbRating") String avalicao,
                             @JsonAlias("Released") String dataLancamento) {
}
