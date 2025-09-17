package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcel.malewski.focustimetrackerapi.entity.person.dto.*;
import com.marcel.malewski.focustimetrackerapi.entity.topic.dto.TopicBasicDataDto;
import com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic.MainTopicMapper;
import com.marcel.malewski.focustimetrackerapi.enums.Stage;
import com.marcel.malewski.focustimetrackerapi.security.exception.AuthenticatedPersonNotFoundException;
import com.marcel.malewski.focustimetrackerapi.security.util.SecurityHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final MainTopicMapper mainTopicMapper;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper, MainTopicMapper mainTopicMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.mainTopicMapper = mainTopicMapper;
    }

    public PrincipalBasicDataDto getPrincipalBasicData(Principal principal,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) {
        long principalId = SecurityHelper.extractIdFromPrincipal(principal);
        Optional<Person> optionalPerson = personRepository.findById(principalId);

        return switch (optionalPerson.orElse(null)) {
            case null -> {
                SecurityHelper.logoutManually(request, response);
                throw new AuthenticatedPersonNotFoundException();
            }
            case Person person -> personMapper.toPrincipalBasicDataDto(person);
        };
    }

    public PrincipalWithMainTopicsDto getPrincipalWithMainTopics(Principal principal,
                                                                 HttpServletRequest request,
                                                                 HttpServletResponse response) {
        long principalId = SecurityHelper.extractIdFromPrincipal(principal);
        Optional<Person> optionalPerson = personRepository.findByIdWithFetchedMainTopics(principalId);

        return switch (optionalPerson.orElse(null)) {
            case null -> {
                SecurityHelper.logoutManually(request, response);
                throw new AuthenticatedPersonNotFoundException();
            }
            case Person person -> {
                PrincipalBasicDataDto principalBasicData = personMapper.toPrincipalBasicDataDto(person);
                List<TopicBasicDataDto> topicBasicDataList = person.getMainTopics().stream()
                    .map(mainTopicMapper::toMainTopicsBasicDataDto)
                    .toList();

                yield new PrincipalWithMainTopicsDto(principalBasicData, topicBasicDataList);
            }
        };
    }

    public Person create(@NotNull Person person) {
        return personRepository.save(person);
    }

    public void updatePrincipalTimerSettings(
        Principal principal,
        TimerSettingsDto timerSettings,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticatedPersonNotFoundException {
        long principalId = SecurityHelper.extractIdFromPrincipal(principal);
        int numberOfAffectedRows = personRepository.updateTimerSettings(
            principalId,
            Stage.HOME,
            timerSettings.timerSelectedTopic(),
            timerSettings.timerSetHours(),
            timerSettings.timerSetMinutes(),
            timerSettings.timerSetSeconds(),
            timerSettings.timerShortBreak(),
            timerSettings.timerLongBreak(),
            timerSettings.timerAutoBreak(),
            timerSettings.timerInterval()
        );

        if (numberOfAffectedRows == 0) {
            SecurityHelper.logoutManually(request, response);
            throw new AuthenticatedPersonNotFoundException();
        }
    }

    public void updatePrincipalTimerStage(
        Principal principal,
        TimerStageDto dto,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticatedPersonNotFoundException {
        long principalId = SecurityHelper.extractIdFromPrincipal(principal);
        int numberOfAffectedRows = personRepository.updateTimerStage(
            principalId,
            dto.timerStage()
        );

        if (numberOfAffectedRows == 0) {
            SecurityHelper.logoutManually(request, response);
            throw new AuthenticatedPersonNotFoundException();
        }
    }

    public int principalMoveTimerToStageFocus(
        Principal principal,
        TimerSettingsDto timerSettings,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticatedPersonNotFoundException {
        long principalId = SecurityHelper.extractIdFromPrincipal(principal);
        int timerRemainingFocus = this.calculateRemainingTime(
            timerSettings.timerSetHours(), timerSettings.timerSetMinutes(), timerSettings.timerSetSeconds());

        int numberOfAffectedRows;
        if (timerSettings.timerAutoBreak()) {
            numberOfAffectedRows = personRepository.updateTimerSettingsAndRemainingFocusWhenAutoBreak(
                principalId,
                Stage.FOCUS,
                timerSettings.timerSelectedTopic(),
                timerSettings.timerSetHours(),
                timerSettings.timerSetMinutes(),
                timerSettings.timerSetSeconds(),
                timerSettings.timerShortBreak(),
                timerSettings.timerLongBreak(),
                true,
                timerSettings.timerInterval(),
                timerRemainingFocus,
                timerSettings.timerInterval()
            );
        } else {
            numberOfAffectedRows = personRepository.updateTimerSettingsAndRemainingFocus(
                principalId,
                Stage.FOCUS,
                timerSettings.timerSelectedTopic(),
                timerSettings.timerSetHours(),
                timerSettings.timerSetMinutes(),
                timerSettings.timerSetSeconds(),
                timerSettings.timerShortBreak(),
                timerSettings.timerLongBreak(),
                false,
                timerSettings.timerInterval(),
                timerRemainingFocus
            );
        }

        if (numberOfAffectedRows == 0) {
            SecurityHelper.logoutManually(request, response);
            throw new AuthenticatedPersonNotFoundException();
        }

        return timerRemainingFocus;
    }

    public void principalMoveTimerBackToStageHome(
        Principal principal,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticatedPersonNotFoundException {
        long principalId = SecurityHelper.extractIdFromPrincipal(principal);
        int numberOfAffectedRows = personRepository.afterStageFocus(
            principalId,
            Stage.HOME,
            null,
            null
        );

        if (numberOfAffectedRows == 0) {
            SecurityHelper.logoutManually(request, response);
            throw new AuthenticatedPersonNotFoundException();
        }
    }

    public int principalMoveTimerToStagePause(
        Principal principal,
        TimerCurrentTimeDto dto,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticatedPersonNotFoundException {
        long principalId = SecurityHelper.extractIdFromPrincipal(principal);
        int timerRemainingFocus = this.calculateRemainingTime(
            dto.timerCurrentHour(), dto.timerCurrentMinute(), dto.timerCurrentSecond());
        int numberOfAffectedRows = personRepository.updateTimerStageAndRemainingFocus(
            principalId,
            Stage.PAUSE,
            timerRemainingFocus
        );

        if (numberOfAffectedRows == 0) {
            SecurityHelper.logoutManually(request, response);
            throw new AuthenticatedPersonNotFoundException();
        }

        return timerRemainingFocus;
    }

    private int calculateRemainingTime(int hours, int minutes, int seconds) {
        return hours * 60 * 60 + minutes * 60 + seconds;
    }

    public MoveTimerToStageBreakWithAutoBreakResult principalMoveTimerToStageBreakWitAutoBreak(
        Principal principal,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticatedPersonNotFoundException {
        long principalId = SecurityHelper.extractIdFromPrincipal(principal);
        Person person = personRepository.findById(principalId).orElseThrow(() -> {
            SecurityHelper.logoutManually(request, response);
            return new AuthenticatedPersonNotFoundException();
        });


        int numberOfAffectedRows;
        MoveTimerToStageBreakWithAutoBreakResult result;

        if (person.getTimerRemainingInterval() == 0) {
            numberOfAffectedRows = personRepository.afterStageFocus(
                principalId,
                Stage.LONG_BREAK,
                person.getTimerInterval(),
                null
            );
            result = new MoveTimerToStageBreakWithAutoBreakResult(Stage.LONG_BREAK,
                person.getTimerInterval());
        } else {
            int currentTimerRemainingInterval = person.getTimerRemainingInterval() - 1;
            numberOfAffectedRows = personRepository.afterStageFocus(
                principalId,
                Stage.SHORT_BREAK,
                currentTimerRemainingInterval,
                null
            );
            result = new MoveTimerToStageBreakWithAutoBreakResult(Stage.SHORT_BREAK,
                currentTimerRemainingInterval);
        }

        if (numberOfAffectedRows == 0) {
            SecurityHelper.logoutManually(request, response);
            throw new AuthenticatedPersonNotFoundException();
        }
        return result;
    }

    public void principalMoveTimerToStageBreakWitManualBreak(
        Principal principal,
        TimerManualBreakDto dto,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticatedPersonNotFoundException {
        long principalId = SecurityHelper.extractIdFromPrincipal(principal);

        int numberOfAffectedRows = personRepository.updateTimerStageAndRemainingFocus(
            principalId,
            dto.breakTypeToStart(),
            null
        );

        if (numberOfAffectedRows == 0) {
            SecurityHelper.logoutManually(request, response);
            throw new AuthenticatedPersonNotFoundException();
        }
    }
}
