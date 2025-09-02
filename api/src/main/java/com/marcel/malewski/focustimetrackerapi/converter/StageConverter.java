package com.marcel.malewski.focustimetrackerapi.converter;

import com.marcel.malewski.focustimetrackerapi.enums.Stage;
import com.marcel.malewski.focustimetrackerapi.exception.StageNotFoundException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter
public class StageConverter implements AttributeConverter<Stage, String> {

    @Override
    public String convertToDatabaseColumn(Stage maybeStage) {
        return switch (maybeStage) {
            case Stage stage -> stage.getStageName();
            case null -> null;
        };
    }

    @Override
    public Stage convertToEntityAttribute(String maybeStageName) {
        return switch (maybeStageName) {
            case String stageName -> Stream.of(Stage.values())
                .filter(s -> s.getStageName().equals(stageName))
                .findFirst()
                .orElseThrow(() -> new StageNotFoundException(stageName));
            case null -> null;
        };
    }
}
