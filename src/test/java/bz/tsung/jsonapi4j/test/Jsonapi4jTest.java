package bz.tsung.jsonapi4j.test;

import bz.tsung.jsonapi4j.JsonApiMapper;
import bz.tsung.jsonapi4j.models.JsonApiDocument;
import bz.tsung.jsonapi4j.models.Resource;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tsung on 1/25/16.
 */
public class Jsonapi4jTest {
    @Test
    public void struct() {
        String body = "{\"jsonapi\":{\"version\":\"1.0\"},\"data\":{\"type\":\"errormessage\",\"id\":\"\",\"attributes\":{\"message\":\"Validation failed\",\"errors\":[{\"resource\":\"users\",\"field\":\"email\",\"code\":\"can't be blank\"}]}}}";
        JsonApiMapper mapper = new JsonApiMapper();
        try {
            JsonApiDocument document = mapper.readJsonApiDocument(body);
            assert  document.getJsonapi().version == 1.0f;
            Iterator<Resource> iterator = document.getData().get().iterator();
            assert iterator.hasNext();
            Resource resource = iterator.next();
            assert resource.getType().equals("errormessage");
            Map<String, Object> attributes = resource.getAttributes();
            assert attributes.containsKey("message");
            assert attributes.get("message").equals("Validation failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert 1 == 1;
    }
}
