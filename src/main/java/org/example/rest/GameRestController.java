package org.example.rest;

import lombok.RequiredArgsConstructor;
import org.example.rest.dto.game.NewGameResponseDto;
import org.example.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameRestController {

    private final GameService gameService;


    @PostMapping("/")
    public ResponseEntity<NewGameResponseDto> createGame() {
        long id = gameService.createGame(Set.of(1L, 2L));
        return ResponseEntity.ok(new NewGameResponseDto(id));
    }
}
