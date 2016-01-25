/*
 * Copyright 2015, Yahoo Inc.
 * Licensed under the Apache License, Version 2.0
 * See LICENSE file in project root for terms.
 */
package bz.tsung.jsonapi4j.serialization;

import bz.tsung.jsonapi4j.models.Data;
import bz.tsung.jsonapi4j.models.Resource;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom deserializer for top-level data.
 */
public class DataDeserializer extends JsonDeserializer<Data<Resource>> {

    @Override
    public Data<Resource> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper mapper = new MappingJsonFactory().getCodec();
        if (node.isArray()) {
            List<Resource> resources = new ArrayList<Resource>();
            for (JsonNode n : node) {
                Resource r = mapper.readValue(n.toString(), Resource.class);
                resources.add(r);
            }
            return new Data<Resource>(resources);
        }
        Resource resource = mapper.readValue(node.toString(), Resource.class);
        return new Data<Resource>(resource);
    }
}
