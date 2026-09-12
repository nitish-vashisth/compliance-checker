package com.nitish.compliance.pack;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requirement-packs")
public class RequirementPackController {

    private final RequirementPackService service;

    public RequirementPackController(
            RequirementPackService service) {

        this.service = service;
    }

    @GetMapping
    public List<RequirementPack> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public RequirementPack findById(
            @PathVariable String id) {

        return service.findById(id);
    }
}