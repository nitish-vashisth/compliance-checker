package com.nitish.compliance.pack;

import java.net.URI;

public record RequirementPackSource(
        String name,
        String url,
        RequirementPackSourceType type
) {

    public RequirementPackSource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Source name must not be blank"
            );
        }

        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException(
                    "Source URL must not be blank"
            );
        }

        try {
            URI uri = URI.create(url);

            if (!"http".equalsIgnoreCase(uri.getScheme())
                    && !"https".equalsIgnoreCase(uri.getScheme())) {
                throw new IllegalArgumentException(
                        "Source URL must use HTTP or HTTPS"
                );
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Source URL must be a valid HTTP or HTTPS URL",
                    e
            );
        }

        if (type == null) {
            throw new IllegalArgumentException(
                    "Source type must not be null"
            );
        }
    }

}