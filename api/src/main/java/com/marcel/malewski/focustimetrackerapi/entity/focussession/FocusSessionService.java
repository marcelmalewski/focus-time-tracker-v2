package com.marcel.malewski.focustimetrackerapi.entity.focussession;

import com.marcel.malewski.focustimetrackerapi.security.util.SecurityHelper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class FocusSessionService {
    private final FocusSessionRepository focusSessionRepository;
    private final FocusSessionMapper focusSessionMapper;

    public FocusSessionService(FocusSessionRepository focusSessionRepository, FocusSessionMapper focusSessionMapper) {
        this.focusSessionRepository = focusSessionRepository;
        this.focusSessionMapper = focusSessionMapper;
    }

    public FocusSession create(FocusSession focusSession) {
        return focusSessionRepository.save(focusSession);
    }

    public Page<FocusSessionBasicDataDto> findAllWithPersonAndMainTopicByPersonId(Pageable pageable, Principal principal) {
        long principalId = SecurityHelper.extractIdFromPrincipal(principal);
        Pageable pageableWithSorting = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC,
            FocusSession.FocusSessionFields.CREATED_AT));

        Page<Long> resultIds = focusSessionRepository.findAllFocusSessionIdsByPersonId(principalId,
            pageableWithSorting);
        List<FocusSession> resultFocusSessions =
            focusSessionRepository.findAllWithPersonAndMainTopicByIdsOrderByCreatedAt(resultIds.getContent());

        return new PageImpl<>(
            focusSessionMapper.toFocusSessionBasicDataDtos(resultFocusSessions),
            pageableWithSorting,
            resultIds.getTotalElements());
    }
}
