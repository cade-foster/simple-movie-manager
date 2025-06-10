package com.cade.movie_manager.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Movie")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty
    private Long id;

    @Column(name = "title")
    @JsonProperty
    @NonNull
    private String title;

    @Column(name = "release_year")
    @JsonProperty
    @NonNull
    private Integer releaseYear;
}
