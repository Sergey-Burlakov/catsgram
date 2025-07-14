package ru.yandex.practicum.catsgram.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ParameterNotValidException extends IllegalArgumentException {
    String parameter;
    String reason;
    public ParameterNotValidException(String parameter, String reason) {
        super(String.format("Некорректное значение параметра %s : %s", parameter, reason));
        this.parameter = parameter;
        this.reason = reason;
    }
}

