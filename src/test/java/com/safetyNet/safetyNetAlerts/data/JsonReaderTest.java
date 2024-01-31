package com.safetyNet.safetyNetAlerts.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetyNet.safetyNetAlerts.model.PersonModelForTesting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class JsonReaderTest {

    @Test
    public void testReadListFromFile_Success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonReader jsonReader = new JsonReader(mapper);
        String filePath = "src/test/resources/test.json";
        String node = "data";
        Class<PersonModelForTesting> valueType = PersonModelForTesting.class;

        List<PersonModelForTesting> result = jsonReader.readListFromFile(filePath, node, valueType);

        assertEquals(2, result.size());
        assertEquals("John", result.getFirst().getFirstName());
        assertEquals("Doe", result.getFirst().getLastName());
        assertEquals(32, result.getFirst().getAge());
        assertEquals(38, result.getLast().getAge());
        assertEquals("Doe", result.getLast().getLastName());
        assertEquals(38, result.getLast().getAge());
    }

    @Test
    public void testReadListFromFile_WrongFile() {
        ObjectMapper mapper = new ObjectMapper();
        JsonReader jsonReader = new JsonReader(mapper);
        String filePath = "src/test/resources/wrongFile.json";
        String node = "data";
        Class<PersonModelForTesting> valueType = PersonModelForTesting.class;

        assertThrows(Exception.class, () ->
                jsonReader.readListFromFile(filePath, node, valueType));
    }
}
