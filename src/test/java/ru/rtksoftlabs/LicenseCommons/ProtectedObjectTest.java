package ru.rtksoftlabs.LicenseCommons;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import ru.rtksoftlabs.LicenseCommons.inno.ProtectedObjectsServiceImpl;
import ru.rtksoftlabs.LicenseCommons.services.JsonMapperService;
import ru.rtksoftlabs.LicenseCommons.services.impl.JsonMapperServiceImpl;
import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObject;
import ru.rtksoftlabs.LicenseCommons.services.ProtectedObjectsService;

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
        ProtectedObject protectedObject = protectedObjectsService.getProtectedObjects().get(0);

        String content = jsonMapperService.generateJson(protectedObject);

        String expectedString = "{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc1\"},{\"data\":\"sc2\"},{\"data\":\"sc3\"}]},{\"data\":\"Roles\"}]}";

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

        ProtectedObject protectedObjectManyNodes = protectedObjectsService.getProtectedObjects().get(0);

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

        ProtectedObject protectedObjectManyNodes = protectedObjectsService.getProtectedObjects().get(0);

        boolean isFind = protectedObjectManyNodes.find(protectedObject);

        assertThat(isFind).isFalse();

        stringForSearch = "{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\"}]}";

        protectedObject = jsonMapperService.generateProtectedObject(stringForSearch);

        isFind = protectedObjectManyNodes.find(protectedObject);

        assertThat(isFind).isFalse();
    }
}
