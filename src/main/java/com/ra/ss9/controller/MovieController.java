package com.ra.ss9.controller;

import com.ra.ss9.model.dto.request.MovieRequestDTO;
import com.ra.ss9.model.dto.response.DataResponse;
import com.ra.ss9.model.entity.Movie;
import com.ra.ss9.service.MovieService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<DataResponse<Movie>> addMovie(@Valid @ModelAttribute MovieRequestDTO movieRequestDTO) {
        return new ResponseEntity<>(new DataResponse<Movie>(movieService.save(movieRequestDTO), HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<Movie>> updateMovie(@PathVariable Long id,
                                                           @Valid @ModelAttribute MovieRequestDTO movieRequestDTO) {
        Movie updatedMovie = movieService.update(movieRequestDTO, id);
        return new ResponseEntity<>(new DataResponse<>(updatedMovie, HttpStatus.OK), HttpStatus.OK );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<?>> deleteMovie(@PathVariable Long id) {
        movieService.deleteById(id);
        return  new ResponseEntity<>(new DataResponse<>("Xóa thành công", HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<Movie>>> getAllMovies(@RequestParam String searchMovie) {
        List<Movie> movies = movieService.findAll(searchMovie);
        return new ResponseEntity<>(new DataResponse<>(movies, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/search-logs")
    public ResponseEntity<DataResponse<Map<String, Integer>>> getSearchLogs() {
        Map<String, Integer> keywords = movieService.getSearchKeywords();
        return new ResponseEntity<>(new DataResponse<>(keywords, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/suggestions")
    public ResponseEntity<DataResponse<List<Movie>>> getSuggestedMovies() {
        List<Movie> suggestedMovies = movieService.getSuggestedMovies();
        return new ResponseEntity<>(new DataResponse<>(suggestedMovies, HttpStatus.OK), HttpStatus.OK);
    }
}
