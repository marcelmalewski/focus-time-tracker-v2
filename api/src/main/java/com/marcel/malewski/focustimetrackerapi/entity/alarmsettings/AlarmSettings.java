package com.marcel.malewski.focustimetrackerapi.entity.alarmsettings;

import com.marcel.malewski.focustimetrackerapi.entity.person.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//TODO custom sound?
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "alarmsettings")
public class AlarmSettings {
  @Id
  @SequenceGenerator(name = "alarmsettings_sequence", sequenceName = "alarmsettings_sequence")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alarmsettings_sequence")
  private Long id;
  @Version
  private Integer version;

  @NotNull
  private Integer volume;

  @OneToOne(mappedBy = "alarmSettings")
  @NotNull
  private Person owner;

  @Override
  public String toString() {
    return "AlarmSettings{" +
      "id=" + id +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AlarmSettings that)) return false;

    return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
  }

  @Override
  public int hashCode() {
    return getId() != null ? getId().hashCode() : 0;
  }
}
