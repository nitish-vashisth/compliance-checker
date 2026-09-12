package com.nitish.compliance.engine;

import com.nitish.compliance.model.RuleResult;
import com.nitish.compliance.model.SignalEvidence;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RuleEvaluator {

    public RuleResult evaluate(
            RuleDefinition rule,
            Map<String, SignalEvidence> signals) {

        RuleCondition condition = rule.condition();

        SignalEvidence signalEvidence =
                signals.get(condition.signal());

        if (signalEvidence == null) {
            return evaluateMissingSignal(condition);
        }

        SignalData signalData =
                signalEvidence.signal().value();

        if (signalData.state() != SignalState.PROVIDED) {
            return RuleResult.UNKNOWN;
        }

        Object value = signalData.value();

        if (value == null) {
            return RuleResult.UNKNOWN;
        }

        String actualValue = String.valueOf(value);

        return switch (condition.operator()) {
            case EQUALS ->
                    evaluateEquals(actualValue, condition.value());

            case NOT_EQUALS ->
                    evaluateNotEquals(actualValue, condition.value());

            case EXISTS ->
                    RuleResult.TRUE;

            case NOT_EXISTS ->
                    RuleResult.FALSE;
        };
    }

    private RuleResult evaluateEquals(
            String actualValue,
            String expectedValue) {

        return actualValue.equals(expectedValue)
                ? RuleResult.TRUE
                : RuleResult.FALSE;
    }

    private RuleResult evaluateNotEquals(
            String actualValue,
            String expectedValue) {

        return actualValue.equals(expectedValue)
                ? RuleResult.FALSE
                : RuleResult.TRUE;
    }

    private RuleResult evaluateMissingSignal(
            RuleCondition condition) {

        return switch (condition.operator()) {
            case EXISTS -> RuleResult.FALSE;
            case NOT_EXISTS -> RuleResult.TRUE;
            default -> RuleResult.UNKNOWN;
        };
    }
}