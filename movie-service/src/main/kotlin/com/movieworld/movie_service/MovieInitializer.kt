package com.movieworld.movie_service

import com.movieworld.movie_service.model.MovieDto
import com.movieworld.movie_service.service.impl.MovieManagementServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

@Configuration
class MovieInitializer(
    private val movieManagementService: MovieManagementServiceImpl
) {
    private val logger = LoggerFactory.getLogger(MovieInitializer::class.java)

    @Bean
    fun initMovies(): CommandLineRunner {
        return CommandLineRunner {
            val movies = listOf(
                MovieDto(title = "The Shawshank Redemption", description = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", genre = "Drama", releaseDate = LocalDate.of(1994, 9, 23), director = "Frank Darabont"),
                MovieDto(title = "The Godfather", description = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", genre = "Crime, Drama", releaseDate = LocalDate.of(1972, 3, 24), director = "Francis Ford Coppola"),
                MovieDto(title = "The Dark Knight", description = "When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.", genre = "Action, Crime, Drama", releaseDate = LocalDate.of(2008, 7, 18), director = "Christopher Nolan"),
                MovieDto(title = "Pulp Fiction", description = "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", genre = "Crime, Drama", releaseDate = LocalDate.of(1994, 10, 14), director = "Quentin Tarantino"),
                MovieDto(title = "Schindler's List", description = "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.", genre = "Biography, Drama, History", releaseDate = LocalDate.of(1993, 12, 15), director = "Steven Spielberg"),
                MovieDto(title = "The Lord of the Rings: The Return of the King", description = "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.", genre = "Action, Adventure, Drama", releaseDate = LocalDate.of(2003, 12, 17), director = "Peter Jackson"),
                MovieDto(title = "Fight Club", description = "An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.", genre = "Drama", releaseDate = LocalDate.of(1999, 10, 15), director = "David Fincher"),
                MovieDto(title = "Forrest Gump", description = "The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75.", genre = "Drama, Romance", releaseDate = LocalDate.of(1994, 7, 6), director = "Robert Zemeckis"),
                MovieDto(title = "Inception", description = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.", genre = "Action, Adventure, Sci-Fi", releaseDate = LocalDate.of(2010, 7, 16), director = "Christopher Nolan"),
                MovieDto(title = "The Matrix", description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.", genre = "Action, Sci-Fi", releaseDate = LocalDate.of(1999, 3, 31), director = "Lana Wachowski, Lilly Wachowski")
            )

            movies.forEach {
                logger.info("Adding ${it.title} to the database")
                movieManagementService.addMovie(it)
            }
        }
    }
}