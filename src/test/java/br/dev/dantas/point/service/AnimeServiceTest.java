package br.dev.dantas.point.service;

import br.dev.dantas.point.commons.AnimeUtils;
import br.dev.dantas.point.domain.entity.Anime;
import br.dev.dantas.point.repository.AnimeRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {

  @InjectMocks
  private AnimeService service;

  @Mock
  private AnimeRepository repository;

  private List<Anime> animes;

  @InjectMocks
  private AnimeUtils animeUtils;


  @BeforeEach
  void init() {
    animes = animeUtils.newAnimeList();
  }

  @Test
  @DisplayName("findAll() returns a list with all Animes")
  @Order(1)
  void findAll_ReturnsAllAnimes_WhenSuccessful() {
    BDDMockito.when(repository.findAll()).thenReturn(this.animes);

    var animes = service.findAll(null);
    Assertions.assertThat(animes).hasSameElementsAs(this.animes);
  }

  @Test
  @DisplayName("findAll() returns a list with all Animes")
  @Order(1)
  void findAll_ReturnsAllPaginatedAnimes_WhenSuccessful() {

    var pageRequest = PageRequest.of(0, this.animes.size());
    PageImpl<Anime> pageAnime = new PageImpl<>(this.animes, pageRequest, 1);

    BDDMockito.when(service.listAnimes(BDDMockito.any(PageRequest.class))).thenReturn(pageAnime);

    var animesFound = service.listAnimes(pageRequest);
    Assertions.assertThat(animesFound).hasSameElementsAs(this.animes);
  }

  @Test
  @DisplayName("findAll() returns a list with found animes when name is not null")
  @Order(2)
  void findAll_ReturnsFoundAnimes_WhenNamePassedAndFound() {
    var name = "Superman";
    List<Anime> animesFound = this.animes.stream().filter(anime -> anime.getName().equals(name))
        .toList();
    BDDMockito.when(repository.findByName(name)).thenReturn(animesFound);

    var listAnimes = service.findAll(name);
    Assertions.assertThat(listAnimes).hasSize(1).contains(animesFound.get(0));
  }

  @Test
  @DisplayName("findBAll() returns an empty list when no anime is found by name")
  @Order(3)
  void findByAll_ReturnsEmptyList_WhenNoNameIsFound() {
    var name = "x";

    BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());
    var listAnimes = service.findAll(name);

    Assertions.assertThat(listAnimes).isNotNull().isEmpty();
  }

  @Test
  @DisplayName("findById() returns a optional anime is id exists")
  @Order(4)
  void findById_ReturnsOptionalAnime_WhenIsIdExists() {
    var id = 1L;
    var animeExpected = this.animes.get(0);
    BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(animeExpected));

    var animeOptional = service.findById(id);
    Assertions.assertThat(animeOptional).isEqualTo(animeExpected);
  }

  @Test
  @DisplayName("findById() returns a throw ResponseStatusException not found")
  @Order(5)
  void findById_ThrowResponseStatusException_WhenNoAnimeIsFound() {
    var id = 1L;
    BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

    Assertions.assertThatException()
        .isThrownBy(() -> service.findById(id))
        .isInstanceOf(ResponseStatusException.class);
  }

  @Test
  @DisplayName("save() creates a anime")
  @Order(6)
  void save_CreateAnime_WhenSuccessful() {
    var animeToBeSaved = animeUtils.newAnimeToSave();

    BDDMockito.when(repository.save(animeToBeSaved)).thenReturn(animeToBeSaved);
    var anime = service.save(animeToBeSaved);

    Assertions.assertThat(anime).isEqualTo(animeToBeSaved).hasNoNullFieldsOrProperties();
  }

  @Test
  @DisplayName("delete() removes a anime")
  @Order(7)
  void delete_RemovesAnime_WhenSuccessFul() {
    var id = 1L;

    var animeToDelete = this.animes.get(0);
    BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(animeToDelete));

    Assertions.assertThatNoException().isThrownBy(() -> service.delete(id));
  }

  @Test
  @DisplayName("delete() removes a throw ResponseStatusException not found")
  @Order(8)
  void delete_ThrowResponseStatusException_WhenNoAnimeIsFound() {
    var id = 1L;
    BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

    Assertions.assertThatException()
        .isThrownBy(() -> service.delete(id))
        .isInstanceOf(ResponseStatusException.class);
  }

  @Test
  @DisplayName("update() updates a anime")
  @Order(9)
  void update_UpdateAnime_WhenSuccessFul() {
    var id = 1L;
    var animeToUpdate = this.animes.get(0);
    animeToUpdate.setName("Image");

    BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(animeToUpdate));
    BDDMockito.when(repository.save(animeToUpdate)).thenReturn(animeToUpdate);

    service.update(animeToUpdate);
    Assertions.assertThatNoException().isThrownBy(() -> service.update(animeToUpdate));
  }

  @Test
  @DisplayName("update() updates a throw ResponseStatusException not found")
  @Order(10)
  void update_ThrowResponseStatusException_WhenNoAnimeIsFound() {
    var id = 1L;

    var animeToUpdate = this.animes.get(0);
    animeToUpdate.setName("Superman");
    BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

    Assertions.assertThatException()
        .isThrownBy(() -> service.update(animeToUpdate))
        .isInstanceOf(ResponseStatusException.class);
  }
}