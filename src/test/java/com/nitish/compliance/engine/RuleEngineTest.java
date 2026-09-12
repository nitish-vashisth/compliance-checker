package com.nitish.compliance.engine;

import com.nitish.compliance.model.Evidence;
import com.nitish.compliance.model.RuleResult;
import com.nitish.compliance.model.Signal;
import com.nitish.compliance.model.SignalEvidence;
import com.nitish.compliance.model.SignalValue;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuleEngineTest {

    @Test
    void shouldReturnTrueWhenHealthDataIsPresent() {

        Signal signal = new Signal(
                "data.health_data",
                SignalValue.TRUE
        );

        Evidence evidence = new Evidence(
                "CUSTOMER_PROVIDED",
                "questionnaire-001",
                "Customer processes patient health information",
                Instant.now()
        );

        SignalEvidence signalEvidence =
                new SignalEvidence(
                        signal,
                        List.of(evidence)
                );

        Map<String, SignalEvidence> signals =
                Map.of(
                        signal.id(),
                        signalEvidence
                );

        RuleRepository repository =
                new JsonRuleRepository();

        RuleEvaluator evaluator =
                new RuleEvaluator();

        RuleEngine engine =
                new RuleEngine(repository, evaluator);

        RuleResult result =
                engine.evaluate(
                        "HIPAA-001-R01",
                        signals
                );

        assertEquals(
                RuleResult.TRUE,
                result
        );
    }

    @Test
    void shouldThrowExceptionWhenRuleDoesNotExist() {

        RuleRepository repository =
                new JsonRuleRepository();

        RuleEvaluator evaluator =
                new RuleEvaluator();

        RuleEngine engine =
                new RuleEngine(repository, evaluator);

        IllegalArgumentException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> engine.evaluate(
                                "DOES-NOT-EXIST",
                                Map.of()
                        )
                );

        assertEquals(
                "Rule not found: DOES-NOT-EXIST",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnUnknownWhenSignalIsUnknown() {

        Signal signal = new Signal(
                "data.health_data",
                SignalValue.UNKNOWN
        );

        SignalEvidence signalEvidence =
                new SignalEvidence(
                        signal,
                        List.of()
                );

        Map<String, SignalEvidence> signals =
                Map.of(
                        signal.id(),
                        signalEvidence
                );

        RuleRepository repository =
                new JsonRuleRepository();

        RuleEvaluator evaluator =
                new RuleEvaluator();

        RuleEngine engine =
                new RuleEngine(repository, evaluator);

        RuleResult result =
                engine.evaluate(
                        "HIPAA-001-R01",
                        signals
                );

        assertEquals(
                RuleResult.UNKNOWN,
                result
        );
    }
}