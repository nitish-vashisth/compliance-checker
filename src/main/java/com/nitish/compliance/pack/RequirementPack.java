package com.nitish.compliance.pack;

import java.time.LocalDate;
import java.util.List;

public record RequirementPack(
        String id,
        String name,
        String version,
        RequirementType requirementType,
        String jurisdiction,
        RequirementPackStatus status,
        LocalDate effectiveDate,
        List<RequirementPackSource> sources,
        List<ApplicabilitySignal> applicabilitySignals,
        List<DerivedConcept> derivedConcepts,
        List<ApplicabilityRule> applicabilityRules,
        List<RequirementDefinition> requirements,
        List<VerificationRequirement> verificationRequirements,
        List<DiscoveryQuestion> discoveryQuestions,
        List<EvidenceRequirement> evidenceRequirements,
        List<CapabilityMapping> capabilityMappings,
        List<TestScenario> testScenarios
) {

    public RequirementPack {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "Requirement pack id must not be blank"
            );
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Requirement pack name must not be blank"
            );
        }

        if (version == null || version.isBlank()) {
            throw new IllegalArgumentException(
                    "Requirement pack version must not be blank"
            );
        }

        if (requirementType == null) {
            throw new IllegalArgumentException(
                    "Requirement pack type must not be null"
            );
        }

        if (jurisdiction == null || jurisdiction.isBlank()) {
            throw new IllegalArgumentException(
                    "Requirement pack jurisdiction must not be blank"
            );
        }

        if (status == null) {
            throw new IllegalArgumentException(
                    "Requirement pack status must not be null"
            );
        }

        if (effectiveDate == null) {
            throw new IllegalArgumentException(
                    "Requirement pack effective date must not be null"
            );

        }

        if (sources == null || sources.isEmpty()) {
            throw new IllegalArgumentException(
                    "Requirement pack must have at least one source"
            );
        }

        if (applicabilitySignals == null) {
            throw new IllegalArgumentException(
                    "Requirement pack applicability signals must not be null"
            );
        }

        applicabilitySignals = List.copyOf(applicabilitySignals);

        derivedConcepts = List.copyOf(derivedConcepts);

        applicabilityRules = List.copyOf(applicabilityRules);

        requirements = List.copyOf(requirements);

        verificationRequirements = List.copyOf(verificationRequirements);

        discoveryQuestions = List.copyOf(discoveryQuestions);

        evidenceRequirements = List.copyOf(evidenceRequirements);

        capabilityMappings = List.copyOf(capabilityMappings);

        testScenarios = List.copyOf(testScenarios);

        sources = List.copyOf(sources);
    }
}
