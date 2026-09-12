package com.nitish.compliance.pack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequirementPackSourceTypeTest {

    @Test
    void shouldContainExpectedSourceTypes() {

        assertEquals(
                RequirementPackSourceType.PRIMARY_REGULATION,
                RequirementPackSourceType.valueOf("PRIMARY_REGULATION")
        );

        assertEquals(
                RequirementPackSourceType.REGULATOR_GUIDANCE,
                RequirementPackSourceType.valueOf("REGULATOR_GUIDANCE")
        );

        assertEquals(
                RequirementPackSourceType.GOVERNMENT_DOCUMENTATION,
                RequirementPackSourceType.valueOf("GOVERNMENT_DOCUMENTATION")
        );

        assertEquals(
                RequirementPackSourceType.OFFICIAL_STANDARD,
                RequirementPackSourceType.valueOf("OFFICIAL_STANDARD")
        );

        assertEquals(
                RequirementPackSourceType.OFFICIAL_INTERPRETATION,
                RequirementPackSourceType.valueOf("OFFICIAL_INTERPRETATION")
        );
    }

    @Test
    void shouldContainExactlyFiveSourceTypes() {

        assertEquals(
                5,
                RequirementPackSourceType.values().length
        );
    }
}