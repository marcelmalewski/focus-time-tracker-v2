package com.marcel.malewski.focustimetrackerapi.entity.topic.subTopic;

import com.marcel.malewski.focustimetrackerapi.entity.topic.Topic;
import com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic.MainTopic;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "subtopic")
public class SubTopic implements Topic {
  @Id
  @SequenceGenerator(name = "subtopic_sequence", sequenceName = "subtopic_sequence")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subtopic_sequence")
  private Long id;
  @Version
  private Integer version;

  @NotNull
  private String name;

  @ManyToOne
  @JoinColumn(name = "maintopic_id")
  @NotNull
  private MainTopic mainTopic;

  @Override
  public String toString() {
    return "SubTopic{" + "id=" + id + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SubTopic subTopic)) return false;

    return getId() != null ? getId().equals(subTopic.getId()) : subTopic.getId() == null;
  }

  @Override
  public int hashCode() {
    return getId() != null ? getId().hashCode() : 0;
  }
}
