package com.nitish.compliance.pack;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;


@Repository
public class JsonRequirementPackRepository
        implements RequirementPackRepository {

    private static final String PACK_PATTERN =
            "classpath*:packs/*/requirement.json";

    private final List<RequirementPack> packs;

    public JsonRequirementPackRepository() {

        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());

        try {
            this.packs = loadPacks(objectMapper);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to load requirement packs",
                    e
            );
        }
    }

    private List<RequirementPack> loadPacks(
            ObjectMapper objectMapper)
            throws IOException {

        PathMatchingResourcePatternResolver resolver =
                new PathMatchingResourcePatternResolver();

        Resource[] resources =
                resolver.getResources(PACK_PATTERN);

        List<RequirementPack> loadedPacks =
                Arrays.stream(resources)
                        .map(resource -> loadPack(
                                objectMapper,
                                resource
                        ))
                        .toList();

        Set<String> packIds = new HashSet<>();

        for (RequirementPack pack : loadedPacks) {

            if (!packIds.add(pack.id())) {
                throw new IllegalStateException(
                        "Duplicate requirement pack id: "
                                + pack.id()
                );
            }
        }

        return loadedPacks;
    }

    private RequirementPack loadPack(
            ObjectMapper objectMapper,
            Resource resource) {

        try (InputStream inputStream =
                     resource.getInputStream()) {

            return objectMapper.readValue(
                    inputStream,
                    RequirementPack.class
            );

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to load requirement pack: "
                            + resource.getDescription(),
                    e
            );
        }
    }

    @Override
    public List<RequirementPack> findAll() {
        return packs;
    }

    @Override
    public Optional<RequirementPack> findById(String id) {

        return packs.stream()
                .filter(pack -> pack.id().equals(id))
                .findFirst();
    }
}