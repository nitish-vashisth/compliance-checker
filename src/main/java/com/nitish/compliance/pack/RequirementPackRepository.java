package com.nitish.compliance.pack;

import java.util.List;
import java.util.Optional;

public interface RequirementPackRepository {

    List<RequirementPack> findAll();

    Optional<RequirementPack> findById(String id);
}