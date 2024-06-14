package socket.socketcontroller;

import org.example.Main;
import org.example.model.Game;
import org.example.model.PlayerColor;
import org.example.service.GameKeeper;
import org.example.service.GameService;
import org.example.socket.socketcontroller.GameSocketController;
import org.example.socket.socketcontroller.message.out.BoardMessage;
import org.example.socket.socketcontroller.message.out.JsonMessageFactory;
import org.example.utils.UriUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = Main.class)
public class GameSocketControllerTest {

    @Autowired
    private JsonMessageFactory jsonMessageFactory;

    @Autowired
    private GameSocketController gameSocketController;

    @SpyBean
    private GameKeeper gameKeeper;

    @MockBean
    private GameService gameService;
    private static MockedStatic<UriUtils> mockedUriUtils;


    @BeforeAll
    static public void setUp() {
        mockedUriUtils = Mockito.mockStatic(UriUtils.class);
    }


    @Test
    public void socketSessionIsClosedWhenGameIdIsNull() throws IOException {
        WebSocketSession session = mock(WebSocketSession.class);
        mockedUriUtils.when(() -> UriUtils.extractPathAttributes(session.getUri(), GameSocketController.GAME_ID)).thenReturn(null);
        mockedUriUtils.when(() -> UriUtils.extractPathAttributes(session.getUri(), GameSocketController.COLOR)).thenReturn("BLACK");
        gameSocketController.afterConnectionEstablished(session);

        Mockito.verify(session, times(1)).close(CloseStatus.BAD_DATA);
    }

    @Test
    public void socketSessionConnectToGameConnectsToGameAndReturnsMessageWithCorrectInfo() throws IOException {
        WebSocketSession session = mock(WebSocketSession.class);
        mockedUriUtils.when(() -> UriUtils.extractPathAttributes(session.getUri(), GameSocketController.GAME_ID)).thenReturn("1");
        mockedUriUtils.when(() -> UriUtils.extractPathAttributes(session.getUri(), GameSocketController.COLOR)).thenReturn("BLACK");
        Game game = spy(Game.class);
        doReturn(1L).when(game).getId();
        doReturn(PlayerColor.WHITE).when(game).getCurrentPlayerColor();
        doReturn("-------").when(game).getBoardState();

        doReturn(game).when(gameService).getGame(1L);
        BoardMessage boardMessage = new BoardMessage(game.getBoardState(), game.getId(), game.getCurrentPlayerColor());

        gameSocketController.afterConnectionEstablished(session);
        verify(gameKeeper, times(1)).connectToGame(eq(1L), any());
        verify(session, times(1)).sendMessage(jsonMessageFactory.createMessage(boardMessage));
    }

}
