package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.data_access.GameDao;
import org.example.model.Game;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameDao gameDao;

    public long createGame(Set<Long> playerId) {
        Game game = gameDao.saveGame(new Game(playerId));
        return game.getId();
    }

    private boolean isPlayerIncluded(long gameId, long playerId) {
        return gameDao.getGame(gameId).getPlayersIds().contains(playerId);
    }


    public Game getGame(long gameId) {
        return gameDao.getGame(gameId);
    }
}
