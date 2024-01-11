package br.dev.dantas.point.repository;

import br.dev.dantas.point.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AnimeHardCodeRepositoryTest {
    @InjectMocks
    private AnimeHardCodeRepository repository;

    @Mock
    private AnimeData animeData;

    private List<Anime> animes;

    @BeforeEach
    void init() {
        var laterna = Anime.builder().id(1L).name("Lanterna").createdAt(LocalDateTime.now()).build();
        var superman = Anime.builder().id(2L).name("Superman").createdAt(LocalDateTime.now()).build();
        var flash = Anime.builder().id(3L).name("Flash").createdAt(LocalDateTime.now()).build();

        animes = new ArrayList<>(List.of(laterna, superman, flash));

        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
    }

    @Test
    @DisplayName("findAll() returns a list with all animes")
    void findAll_ReturnsAllAnimes_WhenSuccessful() {
        var animes = repository.findAll();
        Assertions.assertThat(animes).hasSameElementsAs(animes);
    }

    @Test
    @DisplayName("findById() returns an object with given id")
    void findById_ReturnsAllAnimes_WhenSuccessful() {
        var animeOptional = repository.findById(3L);
        Assertions.assertThat(animeOptional).isPresent().contains(animes.get(2));
    }

    @Test
    @DisplayName("findByName() returns all animes when name is null")
    void findByName_ReturnsAllAnimes_WhenNameIsNulll() {
        var animes = repository.findByName(null);
        Assertions.assertThat(animes).hasSameElementsAs(animes);
    }

    @Test
    @DisplayName("findByName() returns list witg filtered animes name is not null")
    void findByName_ReturnsFilteredAnimes_WhenNameIsNotNulll() {
        var animes = repository.findByName("Superman");
        Assertions.assertThat(animes).hasSize(1).contains(this.animes.get(2));
    }

    @Test
    @DisplayName("findByName() returns empty list when no anime is found")
    void findByName_ReturnsEmptyListOfAnimes_WhenNameIsNotNulll() {
        var animes = repository.findByName("Abasd");
        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save() creates a anime")
    void save_CreatesAnime_WhenSuccessFul() {
        var animeToBeSaved = Anime
                .builder()
                .id(4L)
                .name("Tom e Jerry")
                .createdAt(LocalDateTime.now())
                .build();
        var anime = repository.save(animeToBeSaved);
        Assertions
                .assertThat(anime)
                .isEqualTo(animeToBeSaved).hasNoNullFieldsOrProperties();

        var aimes = repository.findAll();
        Assertions.assertThat(aimes).contains(animeToBeSaved);
    }

    @Test
    @DisplayName("delete() removes a anime")
    void delete_RemovesAnime_WhenSuccessFul() {
        var animeToDelete = this.animes.get(0);
        repository.delete(animeToDelete);

        Assertions.assertThat(this.animes).doesNotContain(animeToDelete);
    }

    @Test
    @DisplayName("update() updates a anime")
    void update_UpdateAnime_WhenSuccessFul() {
        var animeToUpdate = this.animes.get(0);

        animeToUpdate.setName("Homem aranha");

        repository.update(animeToUpdate);
        Assertions.assertThat(this.animes).contains(animeToUpdate);
        this.animes.stream()
                .filter(anime -> anime.getId().equals(animeToUpdate.getId()))
                .findFirst()
                .ifPresent(producer -> Assertions.assertThat(producer.getName()).isEqualTo(animeToUpdate.getName()));
    }
}