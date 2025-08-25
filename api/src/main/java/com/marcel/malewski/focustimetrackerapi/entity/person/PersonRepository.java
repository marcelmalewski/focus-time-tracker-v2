package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcel.malewski.focustimetrackerapi.enums.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT person FROM Person person " +
        "JOIN FETCH person.mainTopics mainTopics Where person.id = ?1 ")
    Optional<Person> findByIdWithFetchedMainTopics(Long id);

    Optional<Person> findByLoginOrEmail(String login, String email);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Person person SET person.timerStage = :timerStage WHERE person.id = :id")
    @Transactional
    int updateTimerStage(@Param(value = "id") long id, @Param(value = "timerStage") Stage timerStage);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Person person " +
        "SET person.timerStage = :timerStage," +
        "person.timerRemainingFocus = :timerRemainingFocus " +
        "WHERE person.id = :id")
    @Transactional
    int updateTimerStageAndRemainingFocus(
        @Param(value = "id") long id,
        @Param(value = "timerStage") Stage timerStage,
        @Param(value = "timerRemainingFocus") int timerRemainingTime
    );

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Person person " +
        "SET person.timerStage = :timerStage," +
        "person.timerRemainingFocus = :timerRemainingFocus " +
        "WHERE person.id = :id")
    @Transactional
    int updateTimerStageAndRemainingInterval(
        @Param(value = "id") long id,
        @Param(value = "timerStage") Stage timerStage,
        @Param(value = "timerRemainingFocus") int timerRemainingTime
    );

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Person person " +
        "SET person.timerStage = :timerStage," +
        "person.timerSelectedTopic = :timerSelectedTopic," +
        "person.timerSetHours = :timerSetHours," +
        "person.timerSetMinutes = :timerSetMinutes," +
        "person.timerSetSeconds = :timerSetSeconds," +
        "person.timerShortBreak = :timerShortBreak," +
        "person.timerLongBreak = :timerLongBreak," +
        "person.timerAutoBreak = :timerAutoBreak," +
        "person.timerInterval = :timerInterval " +
        "WHERE person.id = :id")
    @Transactional
    int updateTimerSettings(
        @Param(value = "id") long id,
        @Param(value = "timerStage") Stage timerStage,
        @Param(value = "timerSelectedTopic") String timerSelectedTopic,
        @Param(value = "timerSetHours") int hours,
        @Param(value = "timerSetMinutes") int minutes,
        @Param(value = "timerSetSeconds") int seconds,
        @Param(value = "timerShortBreak") int shortBreak,
        @Param(value = "timerLongBreak") int longBreak,
        @Param(value = "timerAutoBreak") boolean timerAutoBreak,
        @Param(value = "timerInterval") int interval
    );

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Person person " +
        "SET person.timerStage = :timerStage," +
        "person.timerSelectedTopic = :timerSelectedTopic," +
        "person.timerSetHours = :timerSetHours," +
        "person.timerSetMinutes = :timerSetMinutes," +
        "person.timerSetSeconds = :timerSetSeconds," +
        "person.timerShortBreak = :timerShortBreak," +
        "person.timerLongBreak = :timerLongBreak," +
        "person.timerAutoBreak = :timerAutoBreak," +
        "person.timerInterval = :timerInterval, " +
        "person.timerRemainingFocus = :timerRemainingFocus " +
        "WHERE person.id = :id")
    @Transactional
    int updateTimerSettingsAndRemainingFocus(
        @Param(value = "id") long id,
        @Param(value = "timerStage") Stage timerStage,
        @Param(value = "timerSelectedTopic") String timerSelectedTopic,
        @Param(value = "timerSetHours") int hours,
        @Param(value = "timerSetMinutes") int minutes,
        @Param(value = "timerSetSeconds") int seconds,
        @Param(value = "timerShortBreak") int shortBreak,
        @Param(value = "timerLongBreak") int longBreak,
        @Param(value = "timerAutoBreak") boolean timerAutoBreak,
        @Param(value = "timerInterval") int interval,
        @Param(value = "timerRemainingFocus") int remainingFocus
    );
}
