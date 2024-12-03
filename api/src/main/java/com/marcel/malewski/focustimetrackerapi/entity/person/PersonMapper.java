package com.marcel.malewski.focustimetrackerapi.entity.person;

import com.marcel.malewski.focustimetrackerapi.entity.person.dto.PrincipalBasicDataDto;
import com.marcel.malewski.focustimetrackerapi.entity.person.dto.PrincipalBasicDataWithMainTopicsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
  PrincipalBasicDataDto toPrincipalBasicDataDto(Person person);

  PrincipalBasicDataWithMainTopicsDto toPrincipalBasicDataWithMainTopicsDto(Person person);
}
