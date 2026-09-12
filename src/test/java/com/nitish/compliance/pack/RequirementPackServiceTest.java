package com.nitish.compliance.pack;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequirementPackServiceTest {

    /*
    private final RequirementPackService service =
            new RequirementPackService(
                    new JsonRequirementPackRepository()
            );

    @Test
    void shouldFindAllRequirementPacks() {

        List<RequirementPack> packs =
                service.findAll();

        assertFalse(packs.isEmpty());

        assertTrue(
                packs.stream()
                        .anyMatch(pack ->
                                pack.id().equals("HIPAA"))
        );
    }

    @Test
    void shouldFindRequirementPackById() {

        RequirementPack pack =
                service.findById("HIPAA");

        assertEquals("HIPAA", pack.id());

        assertEquals(
                "Health Insurance Portability and Accountability Act",
                pack.name()
        );

        assertEquals(
                RequirementType.LAW,
                pack.requirementType()
        );

        assertEquals(
                LocalDate.of(1996, 8, 21),
                pack.effectiveDate()
        );
    }

    @Test
    void shouldThrowExceptionWhenRequirementPackDoesNotExist() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.findById("DOES-NOT-EXIST")
                );

        assertEquals(
                "Requirement pack not found: DOES-NOT-EXIST",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnApprovedPackWhenSourcesArePresent() {

        var pack = service.findById("HIPAA");

        assertNotNull(pack);
        assertFalse(pack.sources().isEmpty());
    }

    @Test
    void shouldReturnOnlyApprovedPacksWithSources() {

        var packs = service.findAll();

        assertFalse(packs.isEmpty());

        packs.forEach(pack -> {
            assertEquals(
                    RequirementPackStatus.APPROVED,
                    pack.status()
            );

            assertNotNull(pack.sources());
            assertFalse(pack.sources().isEmpty());
        });
    }*/
}