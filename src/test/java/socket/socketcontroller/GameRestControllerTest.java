package socket.socketcontroller;

import org.example.Main;
import org.example.persistence.game.GameEntity;
import org.example.persistence.repo.GameRepository;
import org.example.rest.GameRestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;

@SpringBootTest(classes = Main.class)
public class GameRestControllerTest {

    @Autowired
    public GameRestController gameRestController;
    @MockBean
    static public GameRepository repository;

    public GameEntity mockedGameObject = new GameEntity(1L, Collections.emptySet());

    @BeforeEach
    public void setup() {
        Mockito.doReturn(Optional.of(mockedGameObject)).when(repository).findById(mockedGameObject.getId());
        Mockito.doReturn(mockedGameObject).when(repository).save(Mockito.any());
    }

    @Test
    public void createNewGame() {
        var response = gameRestController.createGame();
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }
    @Test
    public void getGameCorrectId(){
        var response = gameRestController.getGame(String.valueOf(mockedGameObject.getId()));
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(response.getBody().getId(), mockedGameObject.getId());
        Mockito.verify(repository, Mockito.times(1)).findById(mockedGameObject.getId());
    }

    @Test public void getGameUnknownId(){
        var response = gameRestController.getGame("2");
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test public void getGameInvalidFormatId(){
        var response = gameRestController.getGame("Id");
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
    }

}
