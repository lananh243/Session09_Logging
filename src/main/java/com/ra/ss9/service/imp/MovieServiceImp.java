package com.ra.ss9.service.imp;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ra.ss9.model.dto.request.MovieRequestDTO;
import com.ra.ss9.model.entity.Movie;
import com.ra.ss9.model.utils.LogColor;
import com.ra.ss9.repository.MovieRepository;
import com.ra.ss9.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
public class MovieServiceImp implements MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImp.class);
    private static final String LOG_FILE_PATH = "logs/app.log";
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Movie save(MovieRequestDTO movieRequestDTO) {
        MultipartFile poster = movieRequestDTO.getPoster();
        String url = "";
        if (poster != null && !poster.isEmpty()) {
            try {
                Map upload = cloudinary.uploader().upload(poster.getBytes(), ObjectUtils.emptyMap());
                url = upload.get("secure_url").toString();
                if (url == null || url.isEmpty()) {
                    logger.error("Poster URL is null or empty after upload.");
                    throw new RuntimeException("Url of poster file is empty!");
                }
            } catch (IOException e) {
                logger.error("Error uploading file to Cloudinary: {}", e.getMessage(), e);
                throw new RuntimeException("Upload file error!");
            }
        }

        try {
            Movie movie = Movie.builder()
                    .title(movieRequestDTO.getTitle())
                    .description(movieRequestDTO.getDescription())
                    .releaseDate(movieRequestDTO.getReleaseDate())
                    .poster(url)
                    .build();
            return movieRepository.save(movie);
        } catch (Exception e) {
            logger.error("Error saving movie to database: {}", e.getMessage(), e);
            throw new RuntimeException("Database save error!");
        }
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie with id " + id + " not found!"));
    }

    @Override
    public Movie update(MovieRequestDTO movieRequestDTO, Long id) {
        try {
            Movie oldMovie = findById(id);
            logger.info(LogColor.YELLOW + "Thông tin cũ của phim: " + oldMovie + LogColor.RESET);

            oldMovie.setTitle(movieRequestDTO.getTitle());
            oldMovie.setDescription(movieRequestDTO.getDescription());
            oldMovie.setReleaseDate(movieRequestDTO.getReleaseDate());

            Movie updatedMovie = movieRepository.save(oldMovie);
            logger.info(LogColor.GREEN + "Thông tin mới đã được thay đổi: " + updatedMovie + LogColor.RESET);
            return updatedMovie;
        }catch (Exception e) {
            logger.error(LogColor.RED + "Lỗi khi cập nhật phim: " + e.getMessage() + LogColor.RESET, e);
            throw new RuntimeException("Lỗi khi cập nhật phim");
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Movie movie = findById(id);
            movieRepository.delete(movie);

            logger.info(LogColor.RED + "Xóa thành công" + LogColor.RESET);
            logger.info(LogColor.GREEN + "Thông tin phim đã bị xóa: " + movie + LogColor.RESET);
        }catch (Exception e) {
            logger.error(LogColor.RED + "Lỗi khi xóa phim: " + e.getMessage() + LogColor.RESET, e);
            throw new RuntimeException("Lỗi khi xóa phim");
        }
    }

    @Override
    public List<Movie> findAll(String searchMovie) {
        long start = System.currentTimeMillis();
        List<Movie> result;
        if (searchMovie != null && !searchMovie.isEmpty()) {
            result = movieRepository.findByTitleContainingIgnoreCase(searchMovie);
        } else {
            result = movieRepository.findAll();
        }
        long end = System.currentTimeMillis();

        logger.info(LogColor.GREEN +
                "Search keyword: " + (searchMovie != null ? searchMovie : "none") +
                ", Result size: " + result.size() +
                ", Execution time: " + (end - start) + " ms" +
                LogColor.RESET);

        return result;
    }

    @Override
    public Map<String, Integer> getSearchKeywords() {
        Map<String, Integer> result = new HashMap<>();
        Path path = Paths.get(LOG_FILE_PATH);

        if (!Files.exists(path)) {
            logger.warn("Log file not found: {}", LOG_FILE_PATH);
            return result;
        }

        try (Stream<String> lines = Files.lines(path)) {
            lines.filter(line -> line.contains("searchMovie="))
                    .forEach(line -> {
                        String keyword = extractSearchKeyword(line);
                        if (keyword != null && !keyword.isEmpty()) {
                            result.put(keyword, result.getOrDefault(keyword, 0) + 1);
                        }
                    });

            logger.info("{" +
                    "\"event\": \"getSearchKeywords\", " +
                    "\"status\": \"success\", " +
                    "\"keywordsCount\": " + result.size() +
                    "}");
        } catch (IOException e) {
            logger.error("{" +
                    "\"event\": \"getSearchKeywords\", " +
                    "\"status\": \"error\", " +
                    "\"message\": \"" + e.getMessage() + "\"" +
                    "}", e);
        }

        return result;
    }

    @Override
    public List<Movie> getSuggestedMovies() {
        Map<String, Integer> keywordMap = getSearchKeywords();
        Set<String> keywords = keywordMap.keySet();

        List<Movie> suggestions = new ArrayList<>();
        for (String keyword : keywords) {
            suggestions.addAll(movieRepository.findByTitleContainingIgnoreCase(keyword));
        }

        return suggestions.stream().distinct().toList();
    }

    private String extractSearchKeyword(String line) {
        int index = line.indexOf("searchMovie=");
        if (index == -1) return null;

        String sub = line.substring(index + "searchMovie=".length());
        int endIndex = sub.indexOf(","); // lấy trước dấu "," nếu có
        return (endIndex > 0) ? sub.substring(0, endIndex).trim() : sub.trim();
    }
}
