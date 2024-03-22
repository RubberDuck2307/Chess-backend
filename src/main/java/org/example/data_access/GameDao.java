package org.example.data_access;

import org.example.model.Game;

public interface GameDao {
    Game getGame(Long id);
    Game saveGame(Game game);
    void updateGame(Game game);

}
