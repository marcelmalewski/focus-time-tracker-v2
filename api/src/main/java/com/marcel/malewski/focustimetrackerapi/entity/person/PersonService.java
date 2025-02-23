package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcel.malewski.focustimetrackerapi.entity.person.dto.PrincipalWithMainTopicsDto;
import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.PrincipalBasicData;
import com.marcel.malewski.focustimetrackerapi.entity.person.interfaces.PrincipalWithMainTopics;
import com.marcel.malewski.focustimetrackerapi.entity.person.dto.TimerSettingsDto;
import com.marcel.malewski.focustimetrackerapi.entity.topic.interfaces.TopicBasicData;
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
    private final SecurityHelper securityHelper;
    private final PersonMapper personMapper;
    private final MainTopicMapper mainTopicMapper;

    public PersonService(PersonRepository personRepository, SecurityHelper securityHelper,
                         PersonMapper personMapper, MainTopicMapper mainTopicMapper) {
        this.personRepository = personRepository;
        this.securityHelper = securityHelper;
        this.personMapper = personMapper;
        this.mainTopicMapper = mainTopicMapper;
    }

    public PrincipalBasicData getPrincipalBasicData(Principal principal,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {
        long principalId = securityHelper.extractIdFromPrincipal(principal);
        Optional<Person> optionalPerson = personRepository.findById(principalId);

        return switch (optionalPerson.orElse(null)) {
            case null -> {
                securityHelper.logoutManually(request, response);
                throw new AuthenticatedPersonNotFoundException();
            }
            case Person person -> personMapper.toPrincipalBasicDataDto(person);
        };
    }

    public PrincipalWithMainTopics getPrincipalWithMainTopics(Principal principal,
                                                              HttpServletRequest request,
                                                              HttpServletResponse response) {
        long principalId = securityHelper.extractIdFromPrincipal(principal);
        Optional<Person> optionalPerson = personRepository.findByIdWithFetchedMainTopics(principalId);

        return switch (optionalPerson.orElse(null)) {
            case null -> {
                securityHelper.logoutManually(request, response);
                throw new AuthenticatedPersonNotFoundException();
            }
            case Person person -> {
                PrincipalBasicData principalBasicData = personMapper.toPrincipalBasicDataDto(person);
                List<TopicBasicData> topicBasicDataList = person.getMainTopics().stream()
                    .map(mainTopic -> (TopicBasicData) mainTopicMapper.toMainTopicsBasicDataDto(mainTopic))
                    .toList();

                yield new PrincipalWithMainTopicsDto(principalBasicData, topicBasicDataList);
            }
        };
    }

    public boolean existsByLogin(String login) {
        return personRepository.existsByLogin(login);
    }

    public boolean existsByEmail(String email) {
        return personRepository.existsByEmail(email);
    }

    public Person create(@NotNull Person person) {
        return personRepository.save(person);
    }

    public void updatePrincipalTimerSettings(
        Principal principal,
        TimerSettingsDto dto,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticatedPersonNotFoundException {
        long principalId = securityHelper.extractIdFromPrincipal(principal);
        int remainingTime = (dto.timerSetHours() * 60 * 60) + (dto.timerSetMinutes() * 60) + dto.timerSetSeconds();
        int numberOfAffectedRows;

        numberOfAffectedRows = personRepository.updateTimerSettings(
            principalId,
            Stage.FOCUS,
            dto.timerSelectedTopic(),
            dto.timerSetHours(),
            dto.timerSetMinutes(),
            dto.timerSetSeconds(),
            dto.timerShortBreak(),
            dto.timerLongBreak(),
            dto.timerInterval(),
            remainingTime
        );

        if (numberOfAffectedRows == 0) {
            securityHelper.logoutManually(request, response);
            throw new AuthenticatedPersonNotFoundException();
        }
    }

    public void updatePrincipalTimerStage(
        long principalId,
        Stage timerStage,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticatedPersonNotFoundException {
        int numberOfAffectedRows = personRepository.updateTimerStage(principalId, timerStage);

        if (numberOfAffectedRows == 0) {
            securityHelper.logoutManually(request, response);
            throw new AuthenticatedPersonNotFoundException();
        }
    }

    public void updatePrincipalTimerStageAndRemainingTime(
        long principalId,
        Stage timerStage,
        int timerRemainingTime,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticatedPersonNotFoundException {
        int numberOfAffectedRows = personRepository.updateTimerStageAndRemainingTime(
            principalId,
            timerStage,
            timerRemainingTime
        );

        if (numberOfAffectedRows == 0) {
            securityHelper.logoutManually(request, response);
            throw new AuthenticatedPersonNotFoundException();
        }
    }

    public void updatePrincipalTimerAutoBreak(
        long principalId,
        boolean timerAutoBreak,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticatedPersonNotFoundException {
        int numberOfAffectedRows = personRepository.updateTimerAutoBreak(principalId, timerAutoBreak);

        if (numberOfAffectedRows == 0) {
            securityHelper.logoutManually(request, response);
            throw new AuthenticatedPersonNotFoundException();
        }
    }

    //TODO when user is deleted, session is also deleted
}
