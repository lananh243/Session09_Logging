package com.ra.ss9.service;

import com.ra.ss9.model.dto.request.MovieRequestDTO;
import com.ra.ss9.model.entity.Movie;

import java.util.List;
import java.util.Map;

public interface MovieService {
    Movie save(MovieRequestDTO movieRequestDTO);
    Movie findById(Long id);
    Movie update(MovieRequestDTO movieRequestDTO, Long id);
    void deleteById(Long id);
    List<Movie> findAll(String searchMovie);
    Map<String, Integer> getSearchKeywords();
    List<Movie> getSuggestedMovies();
}
