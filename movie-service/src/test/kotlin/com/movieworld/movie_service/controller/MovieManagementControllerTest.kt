package com.movieworld.movie_service.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.movieworld.movie_service.model.MovieDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MovieManagementControllerTest(
    @Autowired
    private val mockMvc: MockMvc,
) {
    val objectMapper: ObjectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())

    @Test
    fun `should return movie by id`() {
        // given
        val movieDto = MovieDto(
            title = "The Matrix",
            genre = "Action",
            releaseDate = LocalDate.of(1999, 3, 31),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."
        )
        val savedMovie = saveMovie(movieDto)
        println(savedMovie)

        // when
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/movies/id/${savedMovie?.id}")
                .accept(MediaType.APPLICATION_JSON))

        result.andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("The Matrix"))
            .andExpect(jsonPath("$.genre").value("Action"))
            .andExpect(jsonPath("$.releaseDate").value("1999-03-31"))
            .andExpect(jsonPath("$.director").value("Lana Wachowski, Lilly Wachowski"))
            .andExpect(jsonPath("$.description").value("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."))
    }

    @Test
    fun `should return movie by title`() {
        // given
        val movieDto = MovieDto(
            title = "The Matrix",
            genre = "Action",
            releaseDate = LocalDate.of(1999, 3, 31),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."
        )
        saveMovie(movieDto)

        // when
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/movies/{title}", "The Matrix")
                .accept(MediaType.APPLICATION_JSON))

        result.andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("The Matrix"))
            .andExpect(jsonPath("$.genre").value("Action"))
            .andExpect(jsonPath("$.releaseDate").value("1999-03-31"))
            .andExpect(jsonPath("$.director").value("Lana Wachowski, Lilly Wachowski"))
            .andExpect(jsonPath("$.description").value("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."))
    }

    @Test
    fun `should return all movies`() {
        // given
        val movieDto1 = MovieDto(
            title = "The Matrix",
            genre = "Action",
            releaseDate = LocalDate.of(1999, 3, 31),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war " +
                    "against its controllers."
        )
        val movieDto2 = MovieDto(
            title = "The Matrix Reloaded",
            genre = "Action",
            releaseDate = LocalDate.of(2003, 5, 15),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "Freedom fighters Neo, Trinity and Morpheus continue to lead the revolt against the Machine Army, unleashing " +
                    "their arsenal of extraordinary skills and weaponry against the systematic forces of repression and exploitation."
        )
        saveMovies(movieDto1, movieDto2)

        // when
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/movies/all")
            .accept(MediaType.APPLICATION_JSON))

        result.andExpect(status().isOk)
            .andExpect(jsonPath("$[0].title").value("The Matrix"))
            .andExpect(jsonPath("$[0].genre").value("Action"))
            .andExpect(jsonPath("$[0].releaseDate").value("1999-03-31"))
            .andExpect(jsonPath("$[0].director").value("Lana Wachowski, Lilly Wachowski"))
            .andExpect(jsonPath("$[0].description").value("A computer hacker learns from mysterious rebels about " +
                    "the true nature of his reality and his role in the war against its controllers."))
            .andExpect(jsonPath("$[1].title").value("The Matrix Reloaded"))
            .andExpect(jsonPath("$[1].genre").value("Action"))
            .andExpect(jsonPath("$[1].releaseDate").value("2003-05-15"))
            .andExpect(jsonPath("$[1].director").value("Lana Wachowski, Lilly Wachowski"))
            .andExpect(jsonPath("$[1].description").value("Freedom fighters Neo, Trinity and Morpheus continue " +
                    "to lead the revolt against the Machine Army, unleashing their arsenal of extraordinary skills and weaponry against " +
                    "the systematic forces of repression and exploitation."))
    }

    @Test
    fun `should add movie`() {
        // given
        val movieDto = MovieDto(
            title = "The Matrix",
            genre = "Action",
            releaseDate = LocalDate.of(1999, 3, 31),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in " +
                    "the war against its controllers."
        )

        // when
        val result = saveMovie(movieDto)

        // then
        result?.let {
            assert(it.title == "The Matrix")
            assert(it.genre == "Action")
            assert(it.releaseDate == LocalDate.of(1999, 3, 31))
            assert(it.director == "Lana Wachowski, Lilly Wachowski")
            assert(it.description == "A computer hacker learns from mysterious rebels about the true nature of his reality and his role " +
                    "in the war against its controllers.")
        }
    }

    @Test
    fun `should update movie`() {

        // given
        val movieDto = MovieDto(
            title = "The Matrix",
            genre = "Action",
            releaseDate = LocalDate.of(1999, 3, 31),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war " +
                    "against its controllers."
        )
        saveMovie(movieDto)

        val updatedMovieDto = MovieDto(
            title = "The Matrix Reloaded",
            genre = "Action",
            releaseDate = LocalDate.of(2003, 5, 15),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "Freedom fighters Neo, Trinity and Morpheus continue to lead the revolt against the Machine Army, unleashing " +
                    "their arsenal of extraordinary skills and weaponry against the systematic forces of repression and exploitation."
        )

        // when
        val result = mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/movies/update/{title}", "The Matrix")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedMovieDto))
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        // then
        println("Result = ${result.response.contentAsString}")
        val updatedMovie = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/movies/{title}", "The Matrix Reloaded")
                .accept(MediaType.APPLICATION_JSON))
        updatedMovie.andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("The Matrix Reloaded"))
            .andExpect(jsonPath("$.genre").value("Action"))
            .andExpect(jsonPath("$.releaseDate").value("2003-05-15"))
            .andExpect(jsonPath("$.director").value("Lana Wachowski, Lilly Wachowski"))
            .andExpect(jsonPath("$.description").value("Freedom fighters Neo, Trinity and Morpheus continue to " +
                    "lead the revolt against the Machine Army, unleashing their arsenal of extraordinary skills and weaponry against " +
                    "the systematic forces of repression and exploitation."))
    }

    @Test
    fun `should delete movie`() {
        // given
        val movieDto = MovieDto(
            title = "The Matrix",
            genre = "Action",
            releaseDate = LocalDate.of(1999, 3, 31),
            director = "Lana Wachowski, Lilly Wachowski",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war " +
                    "against its controllers."
        )
        saveMovie(movieDto)

        // when
        val result = mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/movies/{title}", "The Matrix")
            .accept(MediaType.APPLICATION_JSON))

        // then
        result.andExpect(status().isOk)
        val deletedMovie = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/movies/{title}", "The Matrix")
            .accept(MediaType.APPLICATION_JSON))
        deletedMovie.andExpect(status().isNotFound)
    }

    @Test
    fun `should return not found when movie not found by id`() {
        // when
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/movies/id/1")
            .accept(MediaType.APPLICATION_JSON))

        // then
        result.andExpect(status().isNotFound)
    }

    @Test
    fun `should return not found when movie not found by title`() {
        // when
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/movies/{title}", "The Matrix")
            .accept(MediaType.APPLICATION_JSON))

        // then
        result.andExpect(status().isNotFound)
    }

    private fun saveMovie(movieDto: MovieDto): MovieDto? {
        val json = objectMapper.writeValueAsString(movieDto)
        return mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/movies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .accept(MediaType.APPLICATION_JSON)).andReturn().returnBody()
    }

    private fun saveMovies(vararg movieDtos: MovieDto): List<MovieDto> {
        return movieDtos.map { movieDto ->
            val json = objectMapper.writeValueAsString(movieDto)
            val result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/movies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON)
            ).andReturn()
            objectMapper.readValue(result.response.contentAsString, MovieDto::class.java)
        }
    }

    private fun MvcResult.returnBody(): MovieDto? {
        return objectMapper.readValue(this.response.contentAsString, MovieDto::class.java)
    }

//    fun MvcResult.returnBodyList(): List<MovieDto> {
//        return objectMapper.readValue(this.response.contentAsString, objectMapper.typeFactory.constructCollectionType(List::class.java, MovieDto::class.java))
//    }
}