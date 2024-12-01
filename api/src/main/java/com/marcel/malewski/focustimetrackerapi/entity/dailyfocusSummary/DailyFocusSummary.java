package com.marcel.malewski.focustimetrackerapi.entity.dailyfocusSummary;

import com.marcel.malewski.focustimetrackerapi.entity.person.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "dailyfocussummary")
public class DailyFocusSummary {
  @Id
  @SequenceGenerator(name = "dailyfocussummary_sequence", sequenceName = "dailyfocussummary_sequence")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dailyfocussummary_sequence")
  private Long id;
  @Version
  private Integer version;

  @NotNull
  private Integer summedUpFocusTime;
  @NotNull
  private Integer numberOfFinishedFocusSessions;

  @ManyToOne
  @JoinColumn(name = "person_id")
  @NotNull
  private Person owner;
  @OneToMany(mappedBy = "dailyFocusSummary")
  @ToString.Exclude
  @NotNull
  private List<FocusSession> focusSessions;

  @Override
  public String toString() {
    return "DailyFocusSummary{" +
      "id=" + id +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DailyFocusSummary that)) return false;

    return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
  }

  @Override
  public int hashCode() {
    return getId() != null ? getId().hashCode() : 0;
  }
}
