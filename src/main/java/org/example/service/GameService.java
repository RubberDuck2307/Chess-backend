package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Game;
import org.example.persistence.game.GameEntity;
import org.example.persistence.repo.GameRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public long createGame(Set<Long> playerId) {
        GameEntity game = gameRepository.save(new GameEntity());
        return game.getId();
    }

    private boolean isPlayerIncluded(long gameId, long playerId) {
        return true;
    }


    public Game getGame(long gameId) {
        var game = gameRepository.findById(gameId).orElse(null);
        if (game == null) {
            return null;
        }
        return new Game();
    }
}
