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
    @Query("UPDATE Person person SET person.timerAutoBreak = :timerAutoBreak WHERE person.id = :id")
    @Transactional
    int updateTimerAutoBreak(@Param(value = "id") long id, @Param(value = "timerAutoBreak") boolean timerAutoBreak);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Person person " +
        "SET person.timerStage = :timerStage," +
        "person.timerRemainingTime = :timerRemainingTime " +
        "WHERE person.id = :id")
    @Transactional
    int updateTimerStageAndRemainingTime(
        @Param(value = "id") long id,
        @Param(value = "timerStage") Stage timerStage,
        @Param(value = "timerRemainingTime") int timerRemainingTime
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
        "person.timerInterval = :timerInterval," +
        "person.timerRemainingTime = :timerRemainingTime " +
        "WHERE person.id = :id")
    @Transactional
    int updateTimerSettings(
        @Param(value = "id") long id,
        @Param(value = "timerStage") Stage timerStage,
        @Param(value = "timerSelectedTopic") String timerSelectedTopic,
        @Param(value = "timerSetHours") Integer hours,
        @Param(value = "timerSetMinutes") Integer minutes,
        @Param(value = "timerSetSeconds") Integer seconds,
        @Param(value = "timerShortBreak") Integer shortBreak,
        @Param(value = "timerLongBreak") Integer longBreak,
        @Param(value = "timerInterval") Integer interval,
        @Param(value = "timerRemainingTime") Integer remainingTime
    );
}
