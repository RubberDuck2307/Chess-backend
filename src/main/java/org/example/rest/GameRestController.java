package org.example.rest;

import lombok.RequiredArgsConstructor;
import org.example.service.GameService;
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
    public void createGame() {
        gameService.createGame(Set.of(1L, 2L));
    }
}
