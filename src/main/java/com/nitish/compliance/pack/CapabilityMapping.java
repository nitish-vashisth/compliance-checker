package com.nitish.compliance.pack;

public record CapabilityMapping(
        String requirementId,
        String capability,
        String supportLevel,
        String description
) {
}
