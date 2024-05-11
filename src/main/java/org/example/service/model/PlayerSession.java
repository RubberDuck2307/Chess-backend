package org.example.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.model.PlayerColor;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class PlayerSession {

    private String id;
    private WebSocketSession session;
    private PlayerColor color;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerSession that = (PlayerSession) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(session, that.session)) return false;
        return color == that.color;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (session != null ? session.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
