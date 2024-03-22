package org.example.service.model;

import lombok.Data;
import org.example.model.Game;
import org.example.model.PlayerColor;

import java.util.ArrayList;

@Data
public class GameSession {
    private Game game;
    private ArrayList<PlayerSession> players;

    public GameSession(Game game) {
        this.game = game;
        this.players = new ArrayList<>();
    }

    public void addPlayer(PlayerSession player) {
        players.add(player);
    }
}
