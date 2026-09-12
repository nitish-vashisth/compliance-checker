package com.nitish.compliance.pack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequirementPackSourceTest {

    /*
    @Test
    void shouldCreateRequirementPackSource() {

        RequirementPackSource source =
                new RequirementPackSource(
                        "U.S. Department of Health and Human Services",
                        "https://www.hhs.gov/hipaa/index.html",
                        RequirementPackSourceType.GOVERNMENT_DOCUMENTATION
                );

        assertEquals(
                "U.S. Department of Health and Human Services",
                source.name()
        );

        assertEquals(
                "https://www.hhs.gov/hipaa/index.html",
                source.url()
        );

        assertEquals(
                RequirementPackSourceType.GOVERNMENT_DOCUMENTATION,
                source.type()
        );
    }

    @Test
    void shouldRejectBlankSourceName() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new RequirementPackSource(
                        "",
                        "https://www.hhs.gov/hipaa/index.html",
                        RequirementPackSourceType.GOVERNMENT_DOCUMENTATION
                )
        );
    }

    @Test
    void shouldRejectBlankSourceUrl() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new RequirementPackSource(
                        "U.S. Department of Health and Human Services",
                        "",
                        RequirementPackSourceType.GOVERNMENT_DOCUMENTATION
                )
        );
    }

    @Test
    void shouldRejectNullSourceType() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new RequirementPackSource(
                        "U.S. Department of Health and Human Services",
                        "https://www.hhs.gov/hipaa/index.html",
                        null
                )
        );
    }

    @Test
    void shouldSupportValueBasedEquality() {

        RequirementPackSource first =
                new RequirementPackSource(
                        "U.S. Department of Health and Human Services",
                        "https://www.hhs.gov/hipaa/index.html",
                        RequirementPackSourceType.GOVERNMENT_DOCUMENTATION
                );

        RequirementPackSource second =
                new RequirementPackSource(
                        "U.S. Department of Health and Human Services",
                        "https://www.hhs.gov/hipaa/index.html",
                        RequirementPackSourceType.GOVERNMENT_DOCUMENTATION
                );

        assertEquals(first, second);
    }

    @Test
    void shouldHaveSameHashCodeForEqualSources() {

        RequirementPackSource first =
                new RequirementPackSource(
                        "U.S. Department of Health and Human Services",
                        "https://www.hhs.gov/hipaa/index.html",
                        RequirementPackSourceType.GOVERNMENT_DOCUMENTATION
                );

        RequirementPackSource second =
                new RequirementPackSource(
                        "U.S. Department of Health and Human Services",
                        "https://www.hhs.gov/hipaa/index.html",
                        RequirementPackSourceType.GOVERNMENT_DOCUMENTATION
                );

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldRejectNonHttpSourceUrl() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new RequirementPackSource(
                        "Example Source",
                        "ftp://example.com/document",
                        RequirementPackSourceType.GOVERNMENT_DOCUMENTATION
                )
        );
    }

    @Test
    void shouldRejectMalformedSourceUrl() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new RequirementPackSource(
                        "Example Source",
                        "://invalid-url",
                        RequirementPackSourceType.GOVERNMENT_DOCUMENTATION
                )
        );
    }

    @Test
    void shouldAcceptHttpsSourceUrl() {

        RequirementPackSource source =
                new RequirementPackSource(
                        "Example Source",
                        "https://example.com/document",
                        RequirementPackSourceType.GOVERNMENT_DOCUMENTATION
                );

        assertEquals(
                "https://example.com/document",
                source.url()
        );
    }

    @Test
    void shouldAcceptHttpSourceUrl() {

        RequirementPackSource source =
                new RequirementPackSource(
                        "Example Source",
                        "http://example.com/document",
                        RequirementPackSourceType.GOVERNMENT_DOCUMENTATION
                );

        assertEquals(
                "http://example.com/document",
                source.url()
        );
    }*/

}