package org.example.persistence.game;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
public class GameEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    @OneToMany(mappedBy = "game")
    private Set<MoveEntity> moves;
}
