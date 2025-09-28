package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcel.malewski.focustimetrackerapi.entity.focussession.FocusSession;
import com.marcel.malewski.focustimetrackerapi.entity.focussession.FocusSessionService;
import com.marcel.malewski.focustimetrackerapi.entity.person.dto.*;
import com.marcel.malewski.focustimetrackerapi.entity.topic.dto.TopicBasicDataDto;
import com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic.MainTopic;
import com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic.MainTopicMapper;
import com.marcel.malewski.focustimetrackerapi.entity.topic.mainTopic.MainTopicRepository;
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
    private final FocusSessionService focusSessionService;
    private final MainTopicMapper mainTopicMapper;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper, FocusSessionService focusSessionService, MainTopicMapper mainTopicMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.focusSessionService = focusSessionService;
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
        ifPersonIdNotFoundLogout(numberOfAffectedRows, request, response);
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
        ifPersonIdNotFoundLogout(numberOfAffectedRows, request, response);
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
        ifPersonIdNotFoundLogout(numberOfAffectedRows, request, response);

        return timerRemainingFocus;
    }

    public int principalMoveTimerToStageFocusAgain(
        Principal principal,
        TimerSetTimeDto timerSetTimeDto,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticatedPersonNotFoundException {
        long principalId = SecurityHelper.extractIdFromPrincipal(principal);
        int timerRemainingFocus = this.calculateRemainingTime(
            timerSetTimeDto.timerSetHours(), timerSetTimeDto.timerSetMinutes(), timerSetTimeDto.timerSetSeconds());

        int numberOfAffectedRows = personRepository.updateTimerStageAndRemainingFocus(
            principalId,
            Stage.FOCUS,
            timerRemainingFocus
        );
        ifPersonIdNotFoundLogout(numberOfAffectedRows, request, response);

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
        ifPersonIdNotFoundLogout(numberOfAffectedRows, request, response);
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
        ifPersonIdNotFoundLogout(numberOfAffectedRows, request, response);

        return timerRemainingFocus;
    }

    private int calculateRemainingTime(int hours, int minutes, int seconds) {
        return hours * 60 * 60 + minutes * 60 + seconds;
    }

    public MoveTimerToStageBreakWithAutoBreakResult principalMoveTimerToStageBreakWitAutoBreak(
        Principal principal,
        boolean finished,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticatedPersonNotFoundException {
        long principalId = SecurityHelper.extractIdFromPrincipal(principal);
        Person person = personRepository.findByIdWithFetchedMainTopics(principalId).orElseThrow(() -> {
            SecurityHelper.logoutManually(request, response);
            return new AuthenticatedPersonNotFoundException();
        });

        int numberOfAffectedRows;
        MoveTimerToStageBreakWithAutoBreakResult result;

        if (person.getTimerRemainingInterval() == 0) {
            numberOfAffectedRows = personRepository.afterStageFocus(
                principalId,
                Stage.LONG_BREAK,
                null,
                person.getTimerInterval()

            );
            result = new MoveTimerToStageBreakWithAutoBreakResult(Stage.LONG_BREAK,
                person.getTimerInterval());
        } else {
            int currentTimerRemainingInterval = person.getTimerRemainingInterval() - 1;
            numberOfAffectedRows = personRepository.afterStageFocus(
                principalId,
                Stage.SHORT_BREAK,
                null,
                currentTimerRemainingInterval

            );
            result = new MoveTimerToStageBreakWithAutoBreakResult(Stage.SHORT_BREAK,
                currentTimerRemainingInterval);
        }
        ifPersonIdNotFoundLogout(numberOfAffectedRows, request, response);

        MainTopic currentMainTopic = person.getMainTopics().stream().filter(mainTopic ->
            mainTopic.getName().equals(person.getTimerSelectedTopic())
        ).findAny().get();
        FocusSession newFocusSession = new FocusSession(finished, person, currentMainTopic);
        focusSessionService.create(newFocusSession);

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
        ifPersonIdNotFoundLogout(numberOfAffectedRows, request, response);

        Person person = personRepository.findByIdWithFetchedMainTopics(principalId).get();
        MainTopic currentMainTopic = person.getMainTopics().stream().filter(mainTopic ->
            mainTopic.getName().equals(person.getTimerSelectedTopic())
        ).findAny().get();
        FocusSession newFocusSession = new FocusSession(dto.finished(), person, currentMainTopic);
        focusSessionService.create(newFocusSession);
    }

    private void ifPersonIdNotFoundLogout(int numberOfAffectedRows, HttpServletRequest request,
                                          HttpServletResponse response) {
        if (numberOfAffectedRows == 0) {
            SecurityHelper.logoutManually(request, response);
            throw new AuthenticatedPersonNotFoundException();
        }
    }
}
