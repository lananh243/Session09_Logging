package com.ra.ss9.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Data
@Table(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column(name = "title",length = 100, unique = true, nullable = false)
    private String title;

    @Column(name = "description", length = 150)
    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private String poster;
}
