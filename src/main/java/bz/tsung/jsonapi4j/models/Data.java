/*
 * Copyright 2015, Yahoo Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */
package bz.tsung.jsonapi4j.models;

import bz.tsung.jsonapi4j.serialization.DataDeserializer;
import bz.tsung.jsonapi4j.serialization.DataSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Container for different representations of top-level data in JSON API.
 *
 * @param <T> type
 */
@JsonSerialize(using = DataSerializer.class)
@JsonDeserialize(using = DataDeserializer.class)
@ToString
public class Data<T> {
    private final Collection<T> values;
    private final RelationshipType relationshipType;

    /**
     * Constructor.
     *
     * @param value singleton resource
     */
    public Data(T value) {
        this.values = new SingleElementSet<T>(value);
        this.relationshipType = RelationshipType.MANY_TO_ONE; // Any "toOne"
    }

    /**
     * Constructor.
     *
     * @param values List of resources
     */
    public Data(Collection<T> values) {
        this.values = values;
        this.relationshipType = RelationshipType.MANY_TO_MANY; // Any "toMany"
    }

    public Collection<T> get() {
        return values;
    }

    /**
     * Determine whether or not the contained type is toOne.
     *
     * @return True if toOne, false if toMany
     */
    public boolean isToOne() {
        return relationshipType.isToOne();
    }

    @SuppressWarnings("unchecked")
    public Collection<ResourceIdentifier> toResourceIdentifiers() {
        Collection<ResourceIdentifier> resourceIdentifiers = new ArrayList<ResourceIdentifier>();
        for (Resource resource : (Collection<Resource>) get()) {
            if (resource != null) {
                resourceIdentifiers.add(resource.toResourceIdentifier());
            }
            return null;
        }
        return resourceIdentifiers;
    }
}
