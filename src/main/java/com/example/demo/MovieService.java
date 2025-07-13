package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final List<Movie> movies = new ArrayList<>();
    private final GeminiClient geminiClient;

    public MovieService(GeminiClient geminiClient) {
        this.geminiClient = geminiClient;
    }

    public Movie addMovie(String title, String rating) {
        String description = geminiClient.generateDescription(title);
        Movie movie = new Movie(title, rating, description);
        movies.add(movie);
        return movie;
    }

    public List<Movie> getAllMovies() {
        return movies;
    }
}
