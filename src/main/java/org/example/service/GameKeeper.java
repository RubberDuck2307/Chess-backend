package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Game;
import org.example.model.PlayerColor;
import org.example.service.model.GameSession;
import org.example.service.model.PlayerSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameKeeper {

    private final GameService gameService;

    List<GameSession> games = new ArrayList<>();

    public GameSession addGame(Game game) {
        var gameSession = new GameSession(game);
        games.add(gameSession);
        return gameSession;
    }

    public Game connectToGame(long gameId, PlayerSession session) {
        GameSession gameSession = getGameSession(gameId);
        if (gameSession == null) {
            Game game = gameService.getGame(gameId);
            gameSession = addGame(game);
        }
        gameSession.addPlayer(session);

        return gameSession.getGame();
    }


    private GameSession getGameSession(long gameId) {
        return games.stream().filter(g -> g.getGame().getId() == gameId).findFirst().orElse(null);
    }

    public List<Integer> getPossibleMoves(long gameId, int from) {
        return getGameSession(gameId).getGame().getPossibleMoves(from);
    }

    public Game makeMove(long gameId, int from, int to, PlayerColor color) {
        GameSession gameSession = getGameSession(gameId);
        if (gameSession.getGame().isPlayersTurn(color)) {
            gameSession.getGame().makeMove(from, to);
            gameSession.getGame().getBoard().printOutBoard();
        } else {
            throw new RuntimeException("Not your turn");
        }
        return gameSession.getGame();
    }
}
