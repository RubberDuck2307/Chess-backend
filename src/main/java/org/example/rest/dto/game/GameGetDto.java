package org.example.rest.dto.game;

public class GameGetDto {

    private Long id;

    public GameGetDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
