/*
 * Copyright 2015, Yahoo Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */
package bz.tsung.jsonapi4j.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.Map;
import java.util.Objects;

/**
 * Resource wrapper around serialized/deserialized JSON API
 *
 * NOTE: We violate the DRY principle to create a clear separation of concern. That is,
 *       the Resource is a distinct from an internal Record. In fact, they are not
 *       interchangeable even though they represent very similar data
 */
@ToString
public class Resource {
    private String type;
    private String id;
    private Map<String, Object> attributes;
    private Map<String, Relationship> relationships;
    private Map<String, String> links;
    private Map<String, Meta> meta;

    public Resource(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public Resource(@JsonProperty("type") String type,
                    @JsonProperty("id") String id,
                    @JsonProperty("attributes") Map<String, Object> attributes,
                    @JsonProperty("relationships") Map<String, Relationship> relationships,
                    @JsonProperty("links") Map<String, String> links,
                    @JsonProperty("meta") Map<String, Meta> meta) {
        this.type = type;
        this.id = id;
        this.attributes = attributes;
        this.relationships = relationships;
        this.links = links;
        this.meta = meta;
    }

    public String getId() {
        return (id == null) ? null : id;
    }

    public void setRelationships(Map<String, Relationship> relationships) {
        this.relationships = relationships;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, Relationship> getRelationships() {
        return relationships == null || relationships.isEmpty() ? null : relationships;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, Object> getAttributes() {
        return attributes == null || attributes.isEmpty() ? null : attributes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAttributes(Map<String, Object> obj) {
        this.attributes = obj;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, Meta> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Meta> meta) {
        this.meta = meta;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    /**
     * Convert Resource to resource identifier.
     *
     * @return linkage
     */
    public ResourceIdentifier toResourceIdentifier() {
        return new ResourceIdentifier(type, id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        result = prime * result + (attributes == null ? 0 : attributes.hashCode());
        result = prime * result + (type == null ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Resource) {
            Resource that = (Resource) obj;
            return Objects.equals(this.id, that.id)
                   && Objects.equals(this.attributes, that.attributes)
                   && Objects.equals(this.type, that.type)
                   && Objects.equals(this.relationships, that.relationships);
        }
        return false;
    }

}
