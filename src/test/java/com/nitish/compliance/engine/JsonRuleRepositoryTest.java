package com.nitish.compliance.engine;


/*
Loads rules from rules.json
Finds a rule by ID
Finds rules by requirement ID
* */

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonRuleRepositoryTest {

    private final RuleRepository repository =
            new JsonRuleRepository();

    @Test
    void shouldLoadRulesFromJson() {

        List<RuleDefinition> rules =
                repository.findAll();

        assertFalse(rules.isEmpty());
    }

    @Test
    void shouldFindRuleById() {

        var rule =
                repository.findById("HIPAA-001-R01");

        assertTrue(rule.isPresent());
        assertEquals(
                "HIPAA-001-R01",
                rule.get().id()
        );
        assertEquals(
                "HIPAA-001",
                rule.get().requirementId()
        );
    }

    @Test
    void shouldFindRulesByRequirementId() {

        List<RuleDefinition> rules =
                repository.findByRequirementId("HIPAA-001");

        assertFalse(rules.isEmpty());

        assertTrue(
                rules.stream()
                        .anyMatch(rule ->
                                rule.id().equals("HIPAA-001-R01"))
        );
    }

    @Test
    void shouldReturnEmptyWhenRuleDoesNotExist() {

        var rule =
                repository.findById("DOES-NOT-EXIST");

        assertTrue(rule.isEmpty());
    }

    @Test
    void shouldLoadRuleCondition() {

        RuleDefinition rule =
                repository.findById("HIPAA-001-R01")
                        .orElseThrow();

        assertEquals(
                "data.health_data",
                rule.condition().signal()
        );

        assertEquals(
                RuleOperator.EQUALS,
                rule.condition().operator()
        );

        assertEquals(
                "TRUE",
                rule.condition().value()
        );
    }

    @Test
    void shouldReturnAllRulesForRequirement() {

        List<RuleDefinition> rules =
                repository.findByRequirementId("HIPAA-001");

        assertFalse(rules.isEmpty());

        assertTrue(
                rules.stream()
                        .allMatch(rule ->
                                rule.requirementId()
                                        .equals("HIPAA-001"))
        );
    }

    @Test
    void shouldReturnEmptyListWhenRequirementDoesNotExist() {

        List<RuleDefinition> rules =
                repository.findByRequirementId("DOES-NOT-EXIST");

        assertTrue(rules.isEmpty());
    }
}