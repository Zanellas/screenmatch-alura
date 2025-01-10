package br.com.alura.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer number;
    private Double rating;
    private LocalDate launchDate;

    public Episode(Integer seasonNumber, EpisodeData episodeData) {
        this.season = seasonNumber;
        this.title = episodeData.title();
        this.number = episodeData.number();

        try {
            this.rating = Double.valueOf(episodeData.rating());
        } catch (NumberFormatException ex) {
            this.rating = 0.0;
        }

        try {
            this.launchDate = LocalDate.parse(episodeData.launchDate());
        } catch (DateTimeParseException ex) {
            this.launchDate = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "season=" + season +
                ", title='" + title + '\'' +
                ", number=" + number +
                ", rating=" + rating +
                ", launchDate=" + launchDate +
                '}';
    }
}
