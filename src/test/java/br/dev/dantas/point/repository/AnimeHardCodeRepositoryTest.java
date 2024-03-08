package br.dev.dantas.point.repository;

import br.dev.dantas.point.commons.AnimeUtils;
import br.dev.dantas.point.domain.entity.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class AnimeHardCodeRepositoryTest {
    @InjectMocks
    private AnimeHardCodeRepository repository;

    @Mock
    private AnimeData animeData;

    private List<Anime> animes;

    @InjectMocks
    private AnimeUtils animeUtils;

    @BeforeEach
    void init() {

        animes = animeUtils.newAnimeList();

        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
    }

    @Test
    @DisplayName("findAll() returns a list with all animes")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenSuccessful() {
        var listAnimes = repository.findAll();
        Assertions.assertThat(animes).hasSameElementsAs(listAnimes);
    }

    @Test
    @DisplayName("findById() returns an object with given id")
    @Order(2)
    void findById_ReturnsAllAnimes_WhenSuccessful() {
        var animeOptional = repository.findById(3L);
        Assertions.assertThat(animeOptional).isPresent().contains(animes.get(2));
    }

    @Test
    @DisplayName("findByName() returns all animes when name is null")
    @Order(3)
    void findByName_ReturnsAllAnimes_WhenNameIsNulll() {
        var anime = repository.findByName(null);
        Assertions.assertThat(animes).hasSameElementsAs(anime);
    }

    @Test
    @DisplayName("findByName() returns list with filtered animes name is not null")
    @Order(4)
    void findByName_ReturnsFilteredAnimes_WhenNameIsNotNulll() {
        var anime = repository.findByName("Superman");
        Assertions.assertThat(anime).hasSize(1).contains(this.animes.get(0));
    }

    @Test
    @DisplayName("findByName() returns empty list when no anime is found")
    @Order(5)
    void findByName_ReturnsEmptyListOfAnimes_WhenNameIsNotNulll() {
        var animes = repository.findByName("Abasd");
        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save() creates a anime")
    @Order(6)
    void save_CreatesAnime_WhenSuccessFul() {
        var animeToBeSaved = animeUtils.newAnimeToSave();

        var anime = repository.save(animeToBeSaved);

        Assertions.assertThat(anime).isEqualTo(animeToBeSaved).hasNoNullFieldsOrProperties();

        var aimes = repository.findAll();
        Assertions.assertThat(aimes).contains(animeToBeSaved);
    }

    @Test
    @DisplayName("delete() removes a anime")
    @Order(7)
    void delete_RemovesAnime_WhenSuccessFul() {
        var animeToDelete = this.animes.get(0);

        repository.delete(animeToDelete);

        Assertions.assertThat(this.animes).doesNotContain(animeToDelete);
    }

    @Test
    @DisplayName("update() updates a throw ResponseStatusException not found")
    @Order(8)
    void update_ThrowResponseStatusException_WhenNoAnimeIsFound() {
        var animeToUpdate = this.animes.get(0);

        animeToUpdate.setName("Homem aranha");

        repository.update(animeToUpdate);
        Assertions.assertThat(this.animes).contains(animeToUpdate);
        this.animes.stream().filter(anime -> anime.getId().equals(animeToUpdate.getId())).findFirst().ifPresent(anime -> Assertions.assertThat(anime.getName()).isEqualTo(animeToUpdate.getName()));
    }
}