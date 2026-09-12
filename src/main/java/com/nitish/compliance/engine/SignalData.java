package com.nitish.compliance.engine;

public record SignalData(
        Object value,
        SignalState state
) {
}