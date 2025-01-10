package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.EpisodeData;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ApiService;
import br.com.alura.screenmatch.service.DataConverter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner reading = new Scanner(System.in);
    private ApiService service = new ApiService();
    private DataConverter converter = new DataConverter();
    private final String ADDRESS = "https://www.ombapi.com/?t=";
    private final String API_KEY = "&apikey=65855022c";

    public void displayMenu() {
        System.out.println("Enter the name of the series:");
        var seriesName = reading.nextLine();
        var json = service.getData(ADDRESS + seriesName.replace(" ", "+") + API_KEY);
        SeriesData data = converter.getData(json, SeriesData.class);
        System.out.println(data);

        List<SeasonData> seasons = new ArrayList<>();

        for (int i = 1; i < data.totalSeasons(); i++) {
            json = service.getData(ADDRESS + seriesName.replace(" ", "+") + "&season=" + i + API_KEY);
            SeasonData seasonData = converter.getData(json, SeasonData.class);
            seasons.add(seasonData);
        }
        seasons.forEach(System.out::println);

        seasons.forEach(s -> s.episodes().forEach(e -> System.out.println(e.title())));

        List<EpisodeData> episodeDataList = seasons.stream()
                .flatMap(s -> s.episodes().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 5 episodes");
        episodeDataList.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> episodes = seasons.stream()
                .flatMap(t -> t.episodes().stream()
                        .map(e -> new Episode(t.number(), e)))
                .collect(Collectors.toList());
    }
}
