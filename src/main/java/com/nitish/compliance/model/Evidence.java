package com.nitish.compliance.model;

import java.time.Instant;

/*

{
  "sourceType": "CUSTOMER_PROVIDED",
  "sourceReference": "security-questionnaire",
  "statement": "Customer processes patient health information",
  "collectedAt": "2026-09-12T10:00:00Z"
}

*/

public record Evidence(
        String sourceType,
        String sourceReference,
        String statement,
        Instant collectedAt
) {
}