package com.nitish.compliance.pack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(RequirementPackController.class)
class RequirementPackControllerTest {
    /*
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RequirementPackService service;

    @Test
    void shouldReturnAllRequirementPacks() throws Exception {

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

        when(service.findAll())
                .thenReturn(List.of(pack));

        mockMvc.perform(
                        get("/api/requirement-packs")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id")
                        .value("HIPAA"))
                .andExpect(jsonPath("$[0].name")
                        .value(
                                "Health Insurance Portability and Accountability Act"
                        ))
                .andExpect(jsonPath("$[0].requirementType")
                        .value("LAW"))
                .andExpect(jsonPath("$[0].jurisdiction")
                        .value("US"))
                .andExpect(jsonPath("$[0].effectiveDate")
                        .value("1996-08-21"))
                .andExpect(jsonPath("$[0].sources").isArray())
                .andExpect(jsonPath("$[0].sources[0].name")
                        .value("U.S. Department of Health and Human Services"))
                .andExpect(jsonPath("$[0].sources[0].url")
                        .value("https://www.hhs.gov/hipaa/index.html"))
                .andExpect(jsonPath("$[0].sources[0].type")
                        .value("GOVERNMENT_DOCUMENTATION"));
    }

    @Test
    void shouldReturnRequirementPackById() throws Exception {

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

        when(service.findById("HIPAA"))
                .thenReturn(pack);

        mockMvc.perform(
                        get("/api/requirement-packs/HIPAA")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value("HIPAA"))
                .andExpect(jsonPath("$.requirementType")
                        .value("LAW"))
                .andExpect(jsonPath("$.status")
                        .value("APPROVED"))
                .andExpect(jsonPath("$.effectiveDate")
                        .value("1996-08-21"))
                .andExpect(jsonPath("$.sources[0].name")
                        .value(
                                "U.S. Department of Health and Human Services"
                        ))
                .andExpect(jsonPath("$.sources[0].url")
                        .value(
                                "https://www.hhs.gov/hipaa/index.html"
                        ))
                .andExpect(jsonPath("$.sources").isArray())
                .andExpect(jsonPath("$.sources[0].name")
                        .value("U.S. Department of Health and Human Services"))
                .andExpect(jsonPath("$.sources[0].url")
                        .value("https://www.hhs.gov/hipaa/index.html"))
                .andExpect(jsonPath("$.sources[0].type")
                        .value("GOVERNMENT_DOCUMENTATION"));
    }*/
}