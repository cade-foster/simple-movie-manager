package com.cade.movie_manager.movie;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository class for interacting with movies in the database.
 */
@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    Optional<Movie> findFirstByTitleAndReleaseYear(String title, int releaseYear);
}
