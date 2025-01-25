package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcel.malewski.focustimetrackerapi.entity.person.dto.PrincipalBasicDataDto;
import com.marcel.malewski.focustimetrackerapi.entity.person.dto.PrincipalWithMainTopicsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PrincipalBasicDataDto toPrincipalBasicDataDto(Person person);

    PrincipalWithMainTopicsDto toPrincipalBasicDataWithMainTopicsDto(Person person);
}
