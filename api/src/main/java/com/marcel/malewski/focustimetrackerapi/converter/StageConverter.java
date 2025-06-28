package com.marcel.malewski.focustimetrackerapi.converter;

import com.marcel.malewski.focustimetrackerapi.enums.Stage;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter
public class StageConverter implements AttributeConverter<Stage, String> {

    @Override
    public String convertToDatabaseColumn(Stage maybeStage) {
        return switch (maybeStage) {
            case null -> null;
            case Stage stage -> stage.getStageName();
        };
    }

    // TODO Konkretniejszy exception
    @Override
    public Stage convertToEntityAttribute(String maybeStageName) {
        return switch (maybeStageName) {
            case null -> null;
            case String stageName -> Stream.of(Stage.values())
                .filter(s -> s.getStageName().equals(stageName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        };
    }
}
