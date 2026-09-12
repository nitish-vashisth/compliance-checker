package com.nitish.compliance.engine;

import java.util.List;
import java.util.Optional;

public interface RuleRepository {

    List<RuleDefinition> findAll();

    Optional<RuleDefinition> findById(String id);

    List<RuleDefinition> findByRequirementId(String requirementId);
}