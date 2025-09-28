package com.marcel.malewski.focustimetrackerapi.entity.focussession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FocusSessionRepository extends JpaRepository<FocusSession, Long> {
    @Query("SELECT fs.id from FocusSession fs WHERE fs.person.id = :personId")
    Page<Long> findAllFocusSessionIdsByPersonId(@Param("personId") long personId,
                                                Pageable pageable);

    @Query("SELECT fs FROM FocusSession fs JOIN FETCH fs.person JOIN FETCH fs.mainTopic WHERE fs " +
        ".id IN :ids ORDER BY fs.createdAt DESC")
    List<FocusSession> findAllWithPersonAndMainTopicByIdsOrderByCreatedAt(@Param("ids") Collection<Long> ids);
}
