package com.cade.movie_manager.controller;

import com.cade.movie_manager.movie.Movie;
import com.cade.movie_manager.movie.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {
    @Mock
    private MovieRepository mockRepo;

    @InjectMocks
    private MovieController controller;

    @Test
    void test_create() {
        controller.create("movie", 2025);
        verify(mockRepo).save(eq(new Movie("movie", 2025)));
    }

    @Test
    void test_retrieve_found() {
        Movie movie = new Movie(1L, "movie", 2025);
        when(mockRepo.findFirstByTitleAndReleaseYear("movie", 2025)).thenReturn(Optional.of(movie));
        ResponseEntity<Movie> result = controller.retrieve("movie", 2025);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(movie, result.getBody());
    }

    @Test
    void test_retrieve_notFound() {
        when(mockRepo.findFirstByTitleAndReleaseYear("movie", 2025)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, controller.retrieve("movie", 2025).getStatusCode());
    }

    @Test
    void test_update_found() {
        Movie movie = new Movie(1L, "movie", 2025);
        when(mockRepo.findById(1L)).thenReturn(Optional.of(movie));
        controller.update(1L, "updated", 2026);
        Movie updated = new Movie(1L, "updated", 2026);
        verify(mockRepo).save(eq(updated));
    }

    @Test
    void test_update_notFound() {
        when(mockRepo.findById(1L)).thenReturn(Optional.empty());
        controller.update(1L, "updated", 2026);
        verify(mockRepo, never()).save(any());
    }

    @Test
    void test_delete() {
        controller.delete(1L);
        verify(mockRepo).deleteById(1L);
    }
}
