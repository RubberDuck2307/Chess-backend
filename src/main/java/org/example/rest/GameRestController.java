package org.example.rest;

import lombok.RequiredArgsConstructor;
import org.example.rest.dto.game.NewGameResponseDto;
import org.example.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        long id = gameService.createGame(Set.of(1L, 2L));
        logger.info("Game created with id " + id);
        return ResponseEntity.ok(new NewGameResponseDto(id));
    }
}
