package com.nitish.compliance.pack;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonRequirementPackRepositoryTest {

    /*
    private final RequirementPackRepository repository =
            new JsonRequirementPackRepository();

    @Test
    void shouldLoadRequirementPacksFromJson() {

        List<RequirementPack> packs =
                repository.findAll();

        assertFalse(packs.isEmpty());
    }

    @Test
    void shouldFindRequirementPackById() {

        var pack =
                repository.findById("HIPAA");

        assertTrue(pack.isPresent());

        assertEquals(
                "HIPAA",
                pack.get().id()
        );

        assertEquals(
                RequirementType.LAW,
                pack.get().requirementType()
        );

        assertEquals(
                "US",
                pack.get().jurisdiction()
        );

        assertEquals(
                RequirementPackStatus.APPROVED,
                pack.get().status()
        );

        assertEquals(
                LocalDate.of(1996, 8, 21),
                pack.get().effectiveDate()
        );
    }

    @Test
    void shouldReturnEmptyWhenRequirementPackDoesNotExist() {

        var pack =
                repository.findById("DOES-NOT-EXIST");

        assertTrue(pack.isEmpty());
    }

    @Test
    void shouldLoadRequirementPacksWithRequiredMetadata() {

        List<RequirementPack> packs =
                repository.findAll();

        assertFalse(packs.isEmpty());

        for (RequirementPack pack : packs) {
            assertNotNull(pack.id());
            assertFalse(pack.id().isBlank());

            assertNotNull(pack.name());
            assertFalse(pack.name().isBlank());

            assertNotNull(pack.version());
            assertFalse(pack.version().isBlank());

            assertNotNull(pack.requirementType());

            assertNotNull(pack.jurisdiction());
            assertFalse(pack.jurisdiction().isBlank());
        }
    }

    @Test
    void shouldDeserializeEffectiveDate() {

        var pack =
                repository.findById("HIPAA");

        assertTrue(pack.isPresent());

        assertEquals(
                LocalDate.of(1996, 8, 21),
                pack.get().effectiveDate()
        );
    }

    @Test
    void shouldDeserializeSourceMetadata() {

        var pack =
                repository.findById("HIPAA");

        assertTrue(pack.isPresent());

        assertNotNull(pack.get().sources());

        assertEquals(
                "U.S. Department of Health and Human Services",
                pack.get().sources().getFirst().name()
        );

        assertEquals(
                "https://www.hhs.gov/hipaa/index.html",
                pack.get().sources().getFirst().url()
        );
    }

    @Test
    void shouldLoadMultipleSources() {

        var pack =
                repository.findById("HIPAA");

        assertTrue(pack.isPresent());

        assertNotNull(pack.get().sources());

        assertEquals(
                1,
                pack.get().sources().size()
        );

        assertEquals(
                "U.S. Department of Health and Human Services",
                pack.get().sources().get(0).name()
        );
    }

    @Test
    void shouldLoadPackWithAtLeastOneSource() {

        var pack = repository.findById("HIPAA");

        assertTrue(pack.isPresent());
        assertNotNull(pack.get().sources());
        assertFalse(pack.get().sources().isEmpty());
    }*/
}