package com.safetyNet.safetyNetAlerts.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class JsonReader {
    private final ObjectMapper mapper;

    public JsonReader() {
        this.mapper = new ObjectMapper();
    }

    public <T> List<T> readListFromFile(String filePath, String node, Class<T> valueType) throws Exception {
        JsonNode rootNode = mapper.readTree(new File(filePath));
        JsonNode targetNode = rootNode.path(node);
        return mapper.readValue(targetNode.traverse(), mapper.getTypeFactory().constructCollectionType(List.class, valueType));
    }

    public <T> void writeListToFile(String filePath, List<T> data) throws Exception {
        mapper.writeValue(new File(filePath), data);
    }
}
