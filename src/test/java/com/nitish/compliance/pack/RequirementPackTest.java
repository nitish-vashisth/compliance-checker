package com.nitish.compliance.pack;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequirementPackTest {

    /*
    @Test
    void shouldCreateRequirementPack() {

        RequirementPack pack =
                new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "1.0",
                        RequirementType.LAW,
                        "US",
                        RequirementPackStatus.APPROVED,
                        LocalDate.of(1996, 8, 21),
                        List.of(
                                new RequirementPackSource(
                                        "U.S. Department of Health and Human Services",
                                        "https://www.hhs.gov/hipaa/index.html",
                                        RequirementPackSourceType.GOVERNMENT_DOCUMENTATION
                                )
                        ),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                );

        assertEquals("HIPAA", pack.id());
        assertEquals(
                "Health Insurance Portability and Accountability Act",
                pack.name()
        );
        assertEquals("1.0", pack.version());
        assertEquals(
                RequirementType.LAW,
                pack.requirementType()
        );
        assertEquals("US", pack.jurisdiction());
        assertEquals(
                LocalDate.of(1996, 8, 21),
                pack.effectiveDate()
        );

        assertEquals(
                "U.S. Department of Health and Human Services",
                pack.sources().getFirst().name()
        );

        assertEquals(
                "https://www.hhs.gov/hipaa/index.html",
                pack.sources().getFirst().url()
        );
    }

    @Test
    void shouldCreateRequirementPackWithSources() {

        RequirementPackSource source =
                new RequirementPackSource(
                        "U.S. Department of Health and Human Services",
                        "https://www.hhs.gov/hipaa/index.html",
                        RequirementPackSourceType.GOVERNMENT_DOCUMENTATION
                );

        RequirementPack pack =
                new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "1.0",
                        RequirementType.LAW,
                        "US",
                        RequirementPackStatus.APPROVED,
                        LocalDate.of(1996, 8, 21),
                        List.of(source),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                );

        assertEquals(1, pack.sources().size());
        assertEquals(
                RequirementPackSourceType.GOVERNMENT_DOCUMENTATION,
                pack.sources().get(0).type()
        );
    }

    @Test
    void shouldRejectBlankId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> createValidPack(
                        "",
                        "Health Insurance Portability and Accountability Act"
                )
        );
    }

    @Test
    void shouldRejectBlankName() {

        assertThrows(
                IllegalArgumentException.class,
                () -> createValidPack(
                        "HIPAA",
                        ""
                )
        );
    }

    @Test
    void shouldRejectNullStatus() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "1.0",
                        RequirementType.LAW,
                        "US",
                        null,
                        LocalDate.of(1996, 8, 21),
                        List.of(createSource()),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                )
        );
    }

    @Test
    void shouldRejectNullSources() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "1.0",
                        RequirementType.LAW,
                        "US",
                        RequirementPackStatus.APPROVED,
                        LocalDate.of(1996, 8, 21),
                        null,
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                )
        );
    }

    @Test
    void shouldRejectEmptySources() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "1.0",
                        RequirementType.LAW,
                        "US",
                        RequirementPackStatus.APPROVED,
                        LocalDate.of(1996, 8, 21),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                )
        );
    }

    @Test
    void shouldRejectBlankVersion() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "",
                        RequirementType.LAW,
                        "US",
                        RequirementPackStatus.APPROVED,
                        LocalDate.of(1996, 8, 21),
                        List.of(createSource()),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                )
        );
    }

    @Test
    void shouldRejectBlankJurisdiction() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "1.0",
                        RequirementType.LAW,
                        "",
                        RequirementPackStatus.APPROVED,
                        LocalDate.of(1996, 8, 21),
                        List.of(createSource()),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                )
        );
    }

    @Test
    void shouldRejectNullRequirementType() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "1.0",
                        null,
                        "US",
                        RequirementPackStatus.APPROVED,
                        LocalDate.of(1996, 8, 21),
                        List.of(createSource()),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                )
        );
    }

    @Test
    void shouldRejectNullEffectiveDate() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "1.0",
                        RequirementType.LAW,
                        "US",
                        RequirementPackStatus.APPROVED,
                        null,
                        List.of(createSource()),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                )
        );
    }

    @Test
    void shouldSupportValueBasedEquality() {

        RequirementPackSource source = createSource();

        RequirementPack first =
                new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "1.0",
                        RequirementType.LAW,
                        "US",
                        RequirementPackStatus.APPROVED,
                        LocalDate.of(1996, 8, 21),
                        List.of(source),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                );

        RequirementPack second =
                new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "1.0",
                        RequirementType.LAW,
                        "US",
                        RequirementPackStatus.APPROVED,
                        LocalDate.of(1996, 8, 21),
                        List.of(source),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                );

        assertEquals(first, second);
    }

    @Test
    void shouldHaveSameHashCodeForEqualPacks() {

        RequirementPackSource source = createSource();

        RequirementPack first =
                new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "1.0",
                        RequirementType.LAW,
                        "US",
                        RequirementPackStatus.APPROVED,
                        LocalDate.of(1996, 8, 21),
                        List.of(source),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                );

        RequirementPack second =
                new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "1.0",
                        RequirementType.LAW,
                        "US",
                        RequirementPackStatus.APPROVED,
                        LocalDate.of(1996, 8, 21),
                        List.of(source),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                );

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldProtectSourcesFromExternalMutation() {

        List<RequirementPackSource> sources =
                new java.util.ArrayList<>();

        sources.add(createSource());

        RequirementPack pack =
                new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "1.0",
                        RequirementType.LAW,
                        "US",
                        RequirementPackStatus.APPROVED,
                        LocalDate.of(1996, 8, 21),
                        sources,
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                );

        sources.clear();

        assertEquals(1, pack.sources().size());

        assertEquals(
                "U.S. Department of Health and Human Services",
                pack.sources().get(0).name()
        );
    }

    @Test
    void shouldReturnUnmodifiableSources() {

        RequirementPack pack =
                createValidPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act"
                );

        assertThrows(
                UnsupportedOperationException.class,
                () -> pack.sources().clear()
        );
    }

    @Test
    void shouldNotReflectChangesToOriginalSourcesList() {

        List<RequirementPackSource> sources =
                new java.util.ArrayList<>();

        sources.add(createSource());

        RequirementPack pack =
                new RequirementPack(
                        "HIPAA",
                        "Health Insurance Portability and Accountability Act",
                        "1.0",
                        RequirementType.LAW,
                        "US",
                        RequirementPackStatus.APPROVED,
                        LocalDate.of(1996, 8, 21),
                        sources,
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()
                );

        sources.add(
                new RequirementPackSource(
                        "Additional Source",
                        "https://example.com",
                        RequirementPackSourceType.OFFICIAL_INTERPRETATION
                )
        );

        assertEquals(1, pack.sources().size());
    }

    private RequirementPackSource createSource() {
        return new RequirementPackSource(
                "U.S. Department of Health and Human Services",
                "https://www.hhs.gov/hipaa/index.html",
                RequirementPackSourceType.GOVERNMENT_DOCUMENTATION
        );
    }

    private RequirementPack createValidPack(
            String id,
            String name) {

        return new RequirementPack(
                id,
                name,
                "1.0",
                RequirementType.LAW,
                "US",
                RequirementPackStatus.APPROVED,
                LocalDate.of(1996, 8, 21),
                List.of(createSource()),
                List.of(),
                List.of(),
                List.of(),
                List.of()
        );
    }*/
}