package ru.rtksoftlabs.LicenseCommons;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import ru.rtksoftlabs.LicenseCommons.inno.ProtectedObjectsServiceImpl;
import ru.rtksoftlabs.LicenseCommons.services.JsonMapperService;
import ru.rtksoftlabs.LicenseCommons.services.impl.JsonMapperServiceImpl;
import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObject;
import ru.rtksoftlabs.LicenseCommons.services.ProtectedObjectsService;
import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObjects;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ProtectedObjectTest {
    private ProtectedObjectsService protectedObjectsService;
    private JsonMapperService jsonMapperService;

    public ProtectedObjectTest() {
        protectedObjectsService = new ProtectedObjectsServiceImpl();
        jsonMapperService = new JsonMapperServiceImpl();
    }

    @Test
    public void toJsonTest() throws JsonProcessingException {
        ProtectedObjects protectedObjects = protectedObjectsService.getProtectedObjects();

        String content = jsonMapperService.generateJson(protectedObjects);

        String expectedString = "{\"objects\":{\"App1\":{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc1\"},{\"data\":\"sc2\"},{\"data\":\"sc3\"}]},{\"data\":\"Roles\",\"children\":[{\"data\":\"r1\",\"children\":[{\"data\":\"childR1\",\"children\":[{\"data\":\"ccr2\"},{\"data\":\"ccr22\"},{\"data\":\"ccr222\"}]}]},{\"data\":\"r2\",\"children\":[{\"data\":\"childR2\",\"children\":[{\"data\":\"ccr2\",\"children\":[{\"data\":\"cccr2\"}]}]}]},{\"data\":\"r3\",\"children\":[{\"data\":\"childR3\"}]}]}]},\"App2\":{\"data\":\"App2\"}}}";

        assertThat(content).isEqualTo(expectedString);
    }

    @Test
    public void toObjectTest() throws IOException {
        String jsonStringExpected = "{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc1\"},{\"data\":\"sc2\"},{\"data\":\"sc3\"}]},{\"data\":\"Roles\"}]}";

        ProtectedObject protectedObject = jsonMapperService.generateProtectedObject(jsonStringExpected);

        String jsonStringActual = jsonMapperService.generateJson(protectedObject);

        assertThat(jsonStringActual).isEqualTo(jsonStringExpected);
    }

    @Test
    public void returnTrueWhenFindTest() throws IOException {
        String stringForSearch = "{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc3\"}]}]}";

        ProtectedObject protectedObject = jsonMapperService.generateProtectedObject(stringForSearch);

        ProtectedObjects protectedObjectManyNodes = protectedObjectsService.getProtectedObjects();

        boolean isFind = protectedObjectManyNodes.find(protectedObject);

        assertThat(isFind).isTrue();

        stringForSearch = "{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc2\"}]}]}";

        protectedObject = jsonMapperService.generateProtectedObject(stringForSearch);

        isFind = protectedObjectManyNodes.find(protectedObject);

        assertThat(isFind).isTrue();
    }

    @Test
    public void returnFalseWhenNotFindTest() throws IOException {
        String stringForSearch = "{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc4\"}]}]}";

        ProtectedObject protectedObject = jsonMapperService.generateProtectedObject(stringForSearch);

        ProtectedObjects protectedObjectManyNodes = protectedObjectsService.getProtectedObjects();

        boolean isFind = protectedObjectManyNodes.find(protectedObject);

        assertThat(isFind).isFalse();

        stringForSearch = "{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\"}]}";

        protectedObject = jsonMapperService.generateProtectedObject(stringForSearch);

        isFind = protectedObjectManyNodes.find(protectedObject);

        assertThat(isFind).isFalse();
    }
}
