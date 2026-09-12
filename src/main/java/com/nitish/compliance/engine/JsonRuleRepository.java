package com.nitish.compliance.engine;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 What this does

 At application startup, Spring creates:

 JsonRuleRepository
 ↓
 loads rules.json
 ↓
 Jackson
 ↓
 List<RuleDefinition>

 So this JSON:

 [
 {
 "id": "HIPAA-001-R01",
 "requirementId": "HIPAA-001",
 "condition": {
 "signal": "data.health_data",
 "operator": "EQUALS",
 "value": "TRUE"
 },
 "result": "TRUE"
 }
 ]

 gets converted into:

 RuleDefinition
 ├── id
 ├── requirementId
 ├── condition
 │    ├── signal
 │    ├── operator
 │    └── value
 └── result
 */



@Repository
public class JsonRuleRepository implements RuleRepository {

    private final List<RuleDefinition> rules;

    public JsonRuleRepository() {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream =
                     new ClassPathResource("rules.json").getInputStream()) {

            this.rules = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<RuleDefinition>>() {}
            );

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to load rules.json",
                    e
            );
        }
    }

    @Override
    public List<RuleDefinition> findAll() {
        return rules;
    }

    @Override
    public Optional<RuleDefinition> findById(String id) {
        return rules.stream()
                .filter(rule -> rule.id().equals(id))
                .findFirst();
    }

    @Override
    public List<RuleDefinition> findByRequirementId(
            String requirementId) {

        return rules.stream()
                .filter(rule ->
                        rule.requirementId().equals(requirementId))
                .toList();
    }
}