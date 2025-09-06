package com.marcel.malewski.focustimetrackerapi.entity.focussession;

import com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic.MainTopic;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "focussession")
public class FocusSession {
    @Id
    @SequenceGenerator(name = "focussession_sequence", sequenceName = "focussession_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "focussession_sequence")
    private Long id;
    @Version
    private Integer version;

    @NotNull
    private Boolean finished;

    @OneToMany(mappedBy = "focusSession", fetch = FetchType.LAZY)
    @NotNull
    private List<MainTopic> mainTopic;

    @Override
    public String toString() {
        return "FocusSession{" + "id=" + id + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FocusSession that)) return false;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
