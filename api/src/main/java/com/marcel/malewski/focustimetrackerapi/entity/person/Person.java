package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcel.malewski.focustimetrackerapi.converter.StageConverter;
import com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic.MainTopic;
import com.marcel.malewski.focustimetrackerapi.enums.BreakType;
import com.marcel.malewski.focustimetrackerapi.enums.Stage;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "person")
public class Person implements UserDetails {
    @Id
    @SequenceGenerator(name = "person_sequence", sequenceName = "person_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_sequence")
    private Long id;
    @Version
    private Integer version;

    @Column(unique = true)
    @NotNull
    private String login;
    @NotNull
    private String password;
    @Column(unique = true)
    @NotNull
    private String email;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdAt;

    // Timer settings
    @Convert(converter = StageConverter.class)
    @Builder.Default
    @NotNull
    private Stage timerStage = Stage.HOME;
    @Nullable
    private String timerSelectedTopic;

    @Min(0)
    @Max(99)
    @Builder.Default
    @NotNull
    private Integer timerSetHours = 0;
    @Min(0)
    @Max(59)
    @Builder.Default
    @NotNull
    private Integer timerSetMinutes = 0;
    @Min(0)
    @Max(59)
    @Builder.Default
    @NotNull
    private Integer timerSetSeconds = 0;

    @Min(1)
    @Max(59)
    @Builder.Default
    @NotNull
    private Integer timerShortBreak = 5;
    @Min(1)
    @Max(59)
    @Builder.Default
    @NotNull
    private Integer timerLongBreak = 10;

    @Builder.Default
    private boolean timerAutoBreak = false;
    @Min(1)
    @Max(99)
    @Builder.Default
    @NotNull
    private Integer timerInterval = 1;

    @Nullable
    private Integer timerRemainingFocus;
    @Nullable
    private BreakType previousBreakType;
    @Nullable
    private Integer timerRemainingInterval;

    @OneToMany(mappedBy = "owner")
    @Builder.Default
    @NotNull
    private List<MainTopic> mainTopics = new ArrayList<>();

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", login='" + login + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;

        return getId() != null ? getId().equals(person.getId()) : person.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return this.id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
