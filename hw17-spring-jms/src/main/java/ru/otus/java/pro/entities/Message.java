package ru.otus.java.pro.entities;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    private UUID uuid;

    @Column(name = "message_text")
    private String text;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(uuid, message.uuid) && Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, text);
    }

    @Override
    public String toString() {
        return "Message{" + "uuid=" + uuid + ", text='" + text + '\'' + '}';
    }
}
