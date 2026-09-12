package com.nitish.compliance.pack;

import com.nitish.compliance.engine.SignalData;
import com.nitish.compliance.engine.SignalState;
import com.nitish.compliance.model.RuleResult;
import com.nitish.compliance.model.SignalEvidence;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RuleExpressionEvaluator {

    public RuleResult evaluate(
            RuleExpression expression,
            Map<String, SignalEvidence> signals
    ) {
        return switch (expression.operator()) {
            case EQUALS -> evaluateEquals(expression, signals);
            case AND -> evaluateAnd(expression, signals);
            case OR -> evaluateOr(expression, signals);
            case EXISTS -> evaluateExists(expression, signals);
            case NOT_EXISTS -> evaluateNotExists(expression, signals);
            default -> throw new IllegalArgumentException(
                    "Unsupported rule expression operator: "
                            + expression.operator()
            );
        };
    }

    private RuleResult evaluateEquals(
            RuleExpression expression,
            Map<String, SignalEvidence> signals
    ) {
        SignalEvidence signalEvidence =
                signals.get(expression.signal());

        if (signalEvidence == null) {
            return RuleResult.UNKNOWN;
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

        return actualValue.equals(expression.value())
                ? RuleResult.TRUE
                : RuleResult.FALSE;
    }

    private RuleResult evaluateAnd(
            RuleExpression expression,
            Map<String, SignalEvidence> signals
    ) {
        boolean hasUnknown = false;

        for (RuleExpression child : expression.children()) {
            RuleResult result = evaluate(child, signals);

            if (result == RuleResult.FALSE) {
                return RuleResult.FALSE;
            }

            if (result == RuleResult.UNKNOWN) {
                hasUnknown = true;
            }
        }

        return hasUnknown
                ? RuleResult.UNKNOWN
                : RuleResult.TRUE;
    }

    private RuleResult evaluateOr(
            RuleExpression expression,
            Map<String, SignalEvidence> signals
    ) {
        boolean hasUnknown = false;

        for (RuleExpression child : expression.children()) {
            RuleResult result = evaluate(child, signals);

            if (result == RuleResult.TRUE) {
                return RuleResult.TRUE;
            }

            if (result == RuleResult.UNKNOWN) {
                hasUnknown = true;
            }
        }

        return hasUnknown
                ? RuleResult.UNKNOWN
                : RuleResult.FALSE;
    }

    private RuleResult evaluateExists(
            RuleExpression expression,
            Map<String, SignalEvidence> signals
    ) {
        return signals.containsKey(expression.signal())
                ? RuleResult.TRUE
                : RuleResult.FALSE;
    }

    private RuleResult evaluateNotExists(
            RuleExpression expression,
            Map<String, SignalEvidence> signals
    ) {
        return signals.containsKey(expression.signal())
                ? RuleResult.FALSE
                : RuleResult.TRUE;
    }

    public void collectMissingSignals(
            RuleExpression expression,
            Map<String, SignalEvidence> signals,
            java.util.Set<String> missingSignals
    ) {
        switch (expression.operator()) {

            case EQUALS:
            case NOT_EQUALS:
                SignalEvidence evidence =
                        signals.get(expression.signal());

                if (evidence == null
                        || evidence.signal().value().state()
                        != SignalState.PROVIDED) {

                    missingSignals.add(expression.signal());
                }
                break;

            case EXISTS:
            case NOT_EXISTS:
                break;

            case AND:
            case OR:
                for (RuleExpression child : expression.children()) {
                    collectMissingSignals(
                            child,
                            signals,
                            missingSignals
                    );
                }
                break;
        }
    }
}