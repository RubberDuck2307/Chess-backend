package org.example.rest;

import lombok.RequiredArgsConstructor;
import org.example.model.Game;
import org.example.persistence.game.GameEntity;
import org.example.rest.dto.game.GameGetDto;
import org.example.rest.dto.game.NewGameResponseDto;
import org.example.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.logging.Logger;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GameRestController {

    private final GameService gameService;
    private final Logger logger = Logger.getLogger(GameRestController.class.getName());

    @PostMapping("/")
    public ResponseEntity<NewGameResponseDto> createGame() {
        long id = gameService.createGame();
        logger.info("Game created with id " + id);
        return ResponseEntity.ok(new NewGameResponseDto(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameGetDto> getGame(@PathVariable String id) {
        long idLong;
        try {
            idLong = Long.parseLong(id);
        } catch (NumberFormatException e){
            return ResponseEntity.badRequest().build();
        }
        Game game = gameService.getGame(idLong);
        if (game == null){
            return ResponseEntity.badRequest().build();
        }
        GameGetDto dto = new GameGetDto(game.getId());
        return ResponseEntity.ok(dto);
    }
}
