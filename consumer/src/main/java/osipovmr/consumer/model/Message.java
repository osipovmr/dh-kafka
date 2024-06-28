package osipovmr.consumer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "message")
public class Message {

    @Id
    private UUID uuid;
    private long partition;
    @Column(name = "offset_number")
    private long offset;
    private String message;
}
