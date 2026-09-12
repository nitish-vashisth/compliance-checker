package com.nitish.compliance.pack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequirementPackStatusTest {

    @Test
    void shouldContainExpectedStatuses() {

        assertEquals(
                RequirementPackStatus.DRAFT,
                RequirementPackStatus.valueOf("DRAFT")
        );

        assertEquals(
                RequirementPackStatus.APPROVED,
                RequirementPackStatus.valueOf("APPROVED")
        );

        assertEquals(
                RequirementPackStatus.DEPRECATED,
                RequirementPackStatus.valueOf("DEPRECATED")
        );
    }
}