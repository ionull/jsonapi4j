/*
 * Copyright 2015, Yahoo Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */
package bz.tsung.jsonapi4j.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.ToString;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * JSON API Document.
 */
@ToString
public class JsonApiDocument {
    private Data<Resource> data;
    private Meta meta;
    private final Map<String, String> links;
    private final LinkedHashSet<Resource> includedRecs;
    private final List<Resource> included;
    private JsonApi jsonapi;

    public JsonApiDocument() {
        links = new LinkedHashMap<String, String>();
        included = new ArrayList<Resource>();
        includedRecs = new LinkedHashSet<Resource>();
        data = null;
    }

    public void setData(Data<Resource> data) {
        this.data = data;
        this.meta = null;
    }

    public Data<Resource> getData() {
        if (data == null) {
            return null;
        }
        return data;
    }

    public void setJsonapi(JsonApi jsonapi) {
        this.jsonapi = jsonapi;
    }

    public JsonApi getJsonapi() {
        return jsonapi;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @JsonInclude(Include.NON_NULL)
    public Meta getMeta() {
        return meta;
    }

    @JsonInclude(Include.NON_NULL)
    public Map<String, String> getLinks() {
        return links.isEmpty() ? null : links;
    }

    public void addLink(String key, String val) {
        this.links.put(key, val);
    }

    @JsonInclude(Include.NON_NULL)
    public List<Resource> getIncluded() {
        return included.isEmpty() ? null : included;
    }

    public void addIncluded(Resource resource) {
        if (!includedRecs.contains(resource)) {
            included.add(resource);
            includedRecs.add(resource);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(37, 79)
            .append(data)
            .append(meta)
            .append(includedRecs)
            .append(links)
            .append(jsonapi)
            .append(included)
            .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JsonApiDocument)) {
            return false;
        }
        JsonApiDocument other = (JsonApiDocument) obj;
        Collection<Resource> resources = data.get();
        if ((resources == null || other.getData().get() == null) && resources != other.getData().get()) {
            return false;
        } else if (resources != null) {
            if (resources.size() != other.getData().get().size()) {
                return false;
            } else {
                Collection<Resource> others = other.getData().get();
                for (Resource resource : resources) {
                    if (!others.contains(resource)) {
                        return false;
                    }
                }
            }
        }
        // TODO: Verify links and meta?
        if (other.getIncluded() == null) {
            return included.isEmpty();
        }

        Collection<Resource> otherIncluded = other.getIncluded();
        for (Resource resource : included) {
            if (!otherIncluded.contains(resource)) {
                return false;
            }
        }
        return true;
    }
}
