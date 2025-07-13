package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie.getTitle(), movie.getRating());
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }
}
