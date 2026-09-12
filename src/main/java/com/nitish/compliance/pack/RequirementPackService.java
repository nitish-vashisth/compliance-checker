package com.nitish.compliance.pack;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RequirementPackService {

    private final RequirementPackRepository repository;

    public RequirementPackService(
            RequirementPackRepository repository) {
        this.repository = repository;
    }

    public List<RequirementPack> findAll() {
        return repository.findAll()
                .stream()
                .filter(pack ->
                        pack.status() ==
                                RequirementPackStatus.APPROVED)
                .filter(pack ->
                        pack.sources() != null &&
                                !pack.sources().isEmpty())
                .toList();
    }

    public RequirementPack findById(String id) {
        return repository.findById(id)
                .filter(pack ->
                        pack.status() ==
                                RequirementPackStatus.APPROVED)
                .filter(pack ->
                        pack.sources() != null &&
                                !pack.sources().isEmpty())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Requirement pack not found: " + id
                        )
                );
    }
}