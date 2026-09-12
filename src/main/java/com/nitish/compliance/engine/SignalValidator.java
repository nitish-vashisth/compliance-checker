package com.nitish.compliance.engine;

import com.nitish.compliance.pack.SignalDataType;
import org.springframework.stereotype.Component;

@Component
public class SignalValidator {

    public boolean isValid(
            Object value,
            SignalDataType dataType) {

        if (value == null || dataType == null) {
            return false;
        }

        return switch (dataType) {
            case BOOLEAN ->
                    value instanceof Boolean
                            || value instanceof String;

            case STRING ->
                    value instanceof String;

            case INTEGER ->
                    value instanceof Integer
                            || value instanceof Long;

            case DECIMAL ->
                    value instanceof Number;

            case DATE ->
                    value instanceof String;

            case STRING_LIST ->
                    value instanceof java.util.List<?>;
        };
    }
}