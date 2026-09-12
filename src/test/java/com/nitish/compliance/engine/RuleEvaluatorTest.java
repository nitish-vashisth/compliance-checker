package com.nitish.compliance.engine;

import com.nitish.compliance.model.RuleResult;
import com.nitish.compliance.model.Signal;
import com.nitish.compliance.model.SignalEvidence;
import com.nitish.compliance.model.SignalValue;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
| Scenario                                      | Expected  |
| --------------------------------------------- | --------- |
| Signal = `TRUE`, condition = `EQUALS TRUE`    | `TRUE`    |
| Signal = `FALSE`, condition = `EQUALS TRUE`   | `FALSE`   |
| Signal = `UNKNOWN`, condition = `EQUALS TRUE` | `UNKNOWN` |
| Signal missing, condition = `EQUALS TRUE`     | `UNKNOWN` |
| Signal = `TRUE`, `NOT_EQUALS TRUE`            | `FALSE`   |
| Signal = `FALSE`, `NOT_EQUALS TRUE`           | `TRUE`    |
| Signal exists, `EXISTS`                       | `TRUE`    |
| Signal missing, `EXISTS`                      | `FALSE`   |
| Signal exists, `NOT_EXISTS`                   | `FALSE`   |
| Signal missing, `NOT_EXISTS`                  | `TRUE`    |

* */

class RuleEvaluatorTest {

    private final RuleEvaluator evaluator = new RuleEvaluator();

    @Test
    void shouldReturnTrueWhenConditionMatches() {

        RuleDefinition rule = new RuleDefinition(
                "TEST-001",
                "TEST",
                new RuleCondition(
                        "data.health_data",
                        RuleOperator.EQUALS,
                        "TRUE"
                )
        );

        Map<String, SignalEvidence> signals =
                Map.of(
                        "data.health_data",
                        new SignalEvidence(
                                new Signal(
                                        "data.health_data",
                                        SignalValue.TRUE
                                ),
                                java.util.List.of()
                        )
                );

        assertEquals(
                RuleResult.TRUE,
                evaluator.evaluate(rule, signals)
        );
    }

    @Test
    void shouldReturnFalseWhenConditionDoesNotMatch() {

        RuleDefinition rule = new RuleDefinition(
                "TEST-001",
                "TEST",
                new RuleCondition(
                        "data.health_data",
                        RuleOperator.EQUALS,
                        "TRUE"
                )
        );

        Map<String, SignalEvidence> signals =
                Map.of(
                        "data.health_data",
                        new SignalEvidence(
                                new Signal(
                                        "data.health_data",
                                        SignalValue.FALSE
                                ),
                                java.util.List.of()
                        )
                );

        assertEquals(
                RuleResult.FALSE,
                evaluator.evaluate(rule, signals)
        );
    }

    @Test
    void shouldReturnUnknownWhenSignalIsUnknown() {

        RuleDefinition rule = new RuleDefinition(
                "TEST-001",
                "TEST",
                new RuleCondition(
                        "data.health_data",
                        RuleOperator.EQUALS,
                        "TRUE"
                )
        );

        Map<String, SignalEvidence> signals =
                Map.of(
                        "data.health_data",
                        new SignalEvidence(
                                new Signal(
                                        "data.health_data",
                                        SignalValue.UNKNOWN
                                ),
                                java.util.List.of()
                        )
                );

        assertEquals(
                RuleResult.UNKNOWN,
                evaluator.evaluate(rule, signals)
        );
    }

    @Test
    void shouldReturnUnknownWhenSignalIsMissingForEquals() {

        RuleDefinition rule = new RuleDefinition(
                "TEST-001",
                "TEST",
                new RuleCondition(
                        "data.health_data",
                        RuleOperator.EQUALS,
                        "TRUE"
                )
        );

        assertEquals(
                RuleResult.UNKNOWN,
                evaluator.evaluate(rule, Map.of())
        );
    }

    @Test
    void shouldReturnFalseWhenSignalIsMissingForExists() {

        RuleDefinition rule = new RuleDefinition(
                "TEST-002",
                "TEST",
                new RuleCondition(
                        "data.health_data",
                        RuleOperator.EXISTS,
                        null
                )
        );

        assertEquals(
                RuleResult.FALSE,
                evaluator.evaluate(rule, Map.of())
        );
    }

    @Test
    void shouldReturnTrueWhenSignalIsMissingForNotExists() {

        RuleDefinition rule = new RuleDefinition(
                "TEST-003",
                "TEST",
                new RuleCondition(
                        "data.health_data",
                        RuleOperator.NOT_EXISTS,
                        null
                )
        );

        assertEquals(
                RuleResult.TRUE,
                evaluator.evaluate(rule, Map.of())
        );
    }

    @Test
    void shouldReturnTrueWhenNotEqualsConditionMatches() {

        RuleDefinition rule = new RuleDefinition(
                "TEST-004",
                "TEST",
                new RuleCondition(
                        "data.health_data",
                        RuleOperator.NOT_EQUALS,
                        "FALSE"
                )
        );

        Map<String, SignalEvidence> signals =
                Map.of(
                        "data.health_data",
                        new SignalEvidence(
                                new Signal(
                                        "data.health_data",
                                        SignalValue.TRUE
                                ),
                                java.util.List.of()
                        )
                );

        assertEquals(
                RuleResult.TRUE,
                evaluator.evaluate(rule, signals)
        );
    }

    @Test
    void shouldReturnTrueWhenExistsConditionMatches() {

        RuleDefinition rule = new RuleDefinition(
                "TEST-005",
                "TEST",
                new RuleCondition(
                        "data.health_data",
                        RuleOperator.EXISTS,
                        null
                )
        );

        Map<String, SignalEvidence> signals =
                Map.of(
                        "data.health_data",
                        new SignalEvidence(
                                new Signal(
                                        "data.health_data",
                                        SignalValue.TRUE
                                ),
                                java.util.List.of()
                        )
                );

        assertEquals(
                RuleResult.TRUE,
                evaluator.evaluate(rule, signals)
        );
    }

    @Test
    void shouldReturnFalseWhenNotExistsConditionDoesNotMatch() {

        RuleDefinition rule = new RuleDefinition(
                "TEST-006",
                "TEST",
                new RuleCondition(
                        "data.health_data",
                        RuleOperator.NOT_EXISTS,
                        null
                )
        );

        Map<String, SignalEvidence> signals =
                Map.of(
                        "data.health_data",
                        new SignalEvidence(
                                new Signal(
                                        "data.health_data",
                                        SignalValue.TRUE
                                ),
                                java.util.List.of()
                        )
                );

        assertEquals(
                RuleResult.FALSE,
                evaluator.evaluate(rule, signals)
        );
    }

    @Test
    void shouldReturnFalseWhenNotEqualsConditionDoesNotMatch() {

        RuleDefinition rule = new RuleDefinition(
                "TEST-007",
                "TEST",
                new RuleCondition(
                        "data.health_data",
                        RuleOperator.NOT_EQUALS,
                        "TRUE"
                )
        );

        Map<String, SignalEvidence> signals =
                Map.of(
                        "data.health_data",
                        new SignalEvidence(
                                new Signal(
                                        "data.health_data",
                                        SignalValue.TRUE
                                ),
                                java.util.List.of()
                        )
                );

        assertEquals(
                RuleResult.FALSE,
                evaluator.evaluate(rule, signals)
        );
    }

    @Test
    void shouldReturnUnknownWhenNotEqualsConditionHasUnknownSignal() {

        RuleDefinition rule = new RuleDefinition(
                "TEST-008",
                "TEST",
                new RuleCondition(
                        "data.health_data",
                        RuleOperator.NOT_EQUALS,
                        "TRUE"
                )
        );

        Map<String, SignalEvidence> signals =
                Map.of(
                        "data.health_data",
                        new SignalEvidence(
                                new Signal(
                                        "data.health_data",
                                        SignalValue.UNKNOWN
                                ),
                                java.util.List.of()
                        )
                );

        assertEquals(
                RuleResult.UNKNOWN,
                evaluator.evaluate(rule, signals)
        );
    }

    @Test
    void shouldReturnUnknownWhenExistsConditionHasUnknownSignal() {

        RuleDefinition rule = new RuleDefinition(
                "TEST-009",
                "TEST",
                new RuleCondition(
                        "data.health_data",
                        RuleOperator.EXISTS,
                        null
                )
        );

        Map<String, SignalEvidence> signals =
                Map.of(
                        "data.health_data",
                        new SignalEvidence(
                                new Signal(
                                        "data.health_data",
                                        SignalValue.UNKNOWN
                                ),
                                java.util.List.of()
                        )
                );

        assertEquals(
                RuleResult.UNKNOWN,
                evaluator.evaluate(rule, signals)
        );
    }

    @Test
    void shouldReturnUnknownWhenNotExistsConditionHasUnknownSignal() {

        RuleDefinition rule = new RuleDefinition(
                "TEST-010",
                "TEST",
                new RuleCondition(
                        "data.health_data",
                        RuleOperator.NOT_EXISTS,
                        null
                )
        );

        Map<String, SignalEvidence> signals =
                Map.of(
                        "data.health_data",
                        new SignalEvidence(
                                new Signal(
                                        "data.health_data",
                                        SignalValue.UNKNOWN
                                ),
                                java.util.List.of()
                        )
                );

        assertEquals(
                RuleResult.UNKNOWN,
                evaluator.evaluate(rule, signals)
        );
    }
}