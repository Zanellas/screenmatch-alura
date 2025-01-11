package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.EpisodeData;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ApiService;
import br.com.alura.screenmatch.service.DataConverter;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

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

        System.out.println("\nTop 10 episodes");
        episodeDataList.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("First Filter(N/A) " + e))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .peek(e -> System.out.println("Sorted " + e))
                .limit(10)
                .peek(e -> System.out.println("Limit" + e))
                .map(e -> e.title().toUpperCase())
                .peek(e -> System.out.println("map" + e))
                .forEach(System.out::println);

        List<Episode> episodes = seasons.stream()
                .flatMap(t -> t.episodes().stream()
                        .map(e -> new Episode(t.number(), e)))
                .collect(Collectors.toList());

        episodes.forEach(System.out::println);

        System.out.println("Type the title");
        var titleText = reading.nextLine();
        Optional<Episode> searchEpisode = episodes.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(titleText.toUpperCase()))
                .findFirst();

        if (searchEpisode.isPresent()) {
            System.out.println("Found Episode");
            System.out.println("Season: " + searchEpisode.get().getSeason());
        } else {
            System.out.println("Episode not Found");
        }

        System.out.println("From what year do you want to see the episodes?");
        var year = reading.nextInt();

        LocalDate searchDate = LocalDate.of(year, 1, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodes.stream()
                .filter(e -> e.getLaunchDate() != null && e.getLaunchDate().isAfter(searchDate))
                .forEach(e -> System.out.println(
                        "Season: " + e.getSeason() +
                                "Episode: " + e.getTitle() +
                                "Launch Date: " + e.getLaunchDate().format(formatter)
                ));

        Map<Integer, Double> ratingBySeason = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getRating)));

        System.out.println(ratingBySeason);

        DoubleSummaryStatistics est = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));

        System.out.println("Average: " + est.getAverage());
        System.out.println("Best: " + est.getMax());
        System.out.println("Worst: " + est.getMin());


    }
}
