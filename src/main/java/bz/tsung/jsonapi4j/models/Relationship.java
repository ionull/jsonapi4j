/*
 * Copyright 2015, Yahoo Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */
package bz.tsung.jsonapi4j.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

/**
 * Model representing JSON API Relationship.
 */
public class Relationship {
    private final Map<String, String> links;
    private final Data<Resource> data; // NOTE: Our serializer handles resources so that's what we store
    @JsonIgnore private final Data<ResourceIdentifier> idData;

    // NOTE: We take in a Resource instead of ResourceIdentifier here due to a deserialization conflict
    public Relationship(@JsonProperty("links") Map<String, String> links,
                        @JsonProperty("data") Data<Resource> data) {
        this.links = links;
        this.data = data;
        if (data != null) {
            if (data.isToOne()) {
                Resource resource = data.get().iterator().next();
                this.idData = new Data<ResourceIdentifier>(resource != null ? resource.toResourceIdentifier() : null);
            } else {
                Collection<ResourceIdentifier> resourceIdentifiers = new ArrayList<ResourceIdentifier>();
                for(Resource resource : data.get()) {
                    resourceIdentifiers.add(resource.toResourceIdentifier());
                }
                this.idData = new Data<ResourceIdentifier>(resourceIdentifiers);
            }
        } else {
            this.idData = null;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, String> getLinks() {
        return links == null || links.isEmpty() ? null : links;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Data<Resource> getData() {
        return data;
    }

    @JsonIgnore
    public Data<ResourceIdentifier> getResourceIdentifierData() {
        return idData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Data<Resource> that = ((Relationship) o).getData();

        if (that == null || data == null) {
            return that == data;
        }

        Collection<ResourceIdentifier> resourceIdentifiers = data.toResourceIdentifiers();
        Collection<ResourceIdentifier> theirIdentifiers = that.toResourceIdentifiers();

        for (ResourceIdentifier resourceIdentifier : resourceIdentifiers) {
            if (!theirIdentifiers.contains(resourceIdentifier)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(data)
            .toHashCode();
    }
}
