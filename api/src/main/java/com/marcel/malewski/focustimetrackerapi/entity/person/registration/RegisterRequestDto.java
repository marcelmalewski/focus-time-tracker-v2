package com.marcel.malewski.focustimetrackerapi.entity.person.registration;

import com.marcel.malewski.focustimetrackerapi.validation.space.WithoutSpacesOnTheSides;
import com.marcel.malewski.focustimetrackerapi.validation.space.WithoutSpacesWithinAWord;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.marcel.malewski.focustimetrackerapi.entity.person.PersonValidationConstants.*;

// TODO branch test, rejestracja
// TODO add validation for strong password
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    @Size(min = LOGIN_MIN_SIZE, max = LOGIN_MAX_SIZE)
    @NotBlank
    @WithoutSpacesOnTheSides
    private String login;
    @Size(min = PASSWORD_MIN_SIZE, max = PASSWORD_MAX_SIZE)
    @NotBlank
    @WithoutSpacesWithinAWord
    @WithoutSpacesOnTheSides
    private String password;
    @Email
    @NotBlank
    private String email;
}
