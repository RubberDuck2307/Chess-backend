package org.example.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.model.PlayerColor;
import org.springframework.web.socket.WebSocketSession;

@AllArgsConstructor
@Getter
@Setter
public class PlayerSession {

    private String id;
    private WebSocketSession session;

}
