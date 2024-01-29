package com.safetyNet.safetyNetAlerts.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class JsonReader {
    private ObjectMapper mapper;

    public JsonReader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> List<T> readListFromFile(String filePath, String node, Class<T> valueType) throws Exception {
        JsonNode rootNode = mapper.readTree(new File(filePath));
        JsonNode targetNode = rootNode.path(node);
        return mapper.readValue(targetNode.traverse(), mapper.getTypeFactory().constructCollectionType(List.class, valueType));
    }
}
