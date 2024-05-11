package org.example.persistence.game;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Entity
@NoArgsConstructor
public class MoveEntity {
    @EmbeddedId
    private MoveId id;
    @Column(name = "fromTile")
    private Short from;
    @Column(name = "toTile")
    private Short to;

    @ManyToOne
    @JoinColumn(name = "game_id")
    @MapsId("gameId")
    private GameEntity game;

    @Embeddable
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MoveId implements Serializable {
        private Long gameId;
        private Short moveNumber;

    }
}
