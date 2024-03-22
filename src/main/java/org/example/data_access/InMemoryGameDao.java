package org.example.data_access;

import org.example.model.Game;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InMemoryGameDao implements GameDao {

    public long id = 1;
    private final List<Game> gameList = new ArrayList<>();

    @Override
    public Game getGame(Long id) {
        return gameList.stream().filter(game -> game.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Game saveGame(Game game) {
        game.setId(generateId());
        gameList.add(game);
        return game;
    }

    @Override
    public void updateGame(Game game) {
        //unnecessary since getGame returns the object by reference
    }

    private long generateId() {
        return id++;
    }
}
