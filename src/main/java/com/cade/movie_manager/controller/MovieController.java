package com.cade.movie_manager.controller;

import com.cade.movie_manager.movie.Movie;
import com.cade.movie_manager.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/movie")
public class MovieController {
    private final MovieRepository movieRepo;

    @Autowired
    public MovieController(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestParam String title, @RequestParam Integer releaseYear) {
        movieRepo.save(new Movie(title, releaseYear));
    }

    @GetMapping("/retrieve")
    public @ResponseBody ResponseEntity<Movie> retrieve(@RequestParam String title, @RequestParam Integer releaseYear) {
        return movieRepo.findFirstByTitleAndReleaseYear(title, releaseYear)
                .map(e -> new ResponseEntity<>(new Movie(e.getId(), e.getTitle(), e.getReleaseYear()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestParam Long id, @RequestParam String title, @RequestParam Integer releaseYear) {
        Optional<Movie> entityOp = movieRepo.findById(id);
        if (entityOp.isPresent()) {
            Movie entity = entityOp.get();
            entity.setTitle(title);
            entity.setReleaseYear(releaseYear);
            movieRepo.save(entity);
        }
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Long id) {
        movieRepo.deleteById(id);
    }
}
