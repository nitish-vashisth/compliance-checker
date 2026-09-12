package com.nitish.compliance.pack;

import com.nitish.compliance.model.SignalEvidence;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/requirement-packs")
public class RequirementPackAssessmentController {

    private final RequirementPackAssessmentService service;

    public RequirementPackAssessmentController(
            RequirementPackAssessmentService service
    ) {
        this.service = service;
    }

    @PostMapping("/{id}/evaluate")
    public AssessmentResponse evaluate(
            @PathVariable String id,
            @RequestBody Map<String, SignalEvidence> signals
    ) {
        return service.evaluate(id, signals);
    }
}