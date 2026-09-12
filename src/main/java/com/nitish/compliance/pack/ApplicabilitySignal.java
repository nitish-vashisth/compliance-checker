package com.nitish.compliance.pack;

public record ApplicabilitySignal(
        String id,
        String name,
        String description,
        SignalDataType dataType,
        boolean required
) {
    public ApplicabilitySignal {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "Applicability signal id must not be blank"
            );
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Applicability signal name must not be blank"
            );
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException(
                    "Applicability signal description must not be blank"
            );
        }

        if (dataType == null) {
            throw new IllegalArgumentException(
                    "Applicability signal data type must not be null"
            );
        }
    }
}