package ru.rtksoftlabs.LicenseCommons;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import ru.rtksoftlabs.LicenseCommons.services.JsonMapperService;
import ru.rtksoftlabs.LicenseCommons.services.impl.JsonMapperServiceImpl;
import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObject;
import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObjects;

import static org.assertj.core.api.Assertions.assertThat;

public class ProtectedObjectsTest {
    private JsonMapperService jsonMapperService;

    public ProtectedObjectsTest() {
        jsonMapperService = new JsonMapperServiceImpl();
    }

    @Test
    public void addChilds() throws JsonProcessingException {
        ProtectedObjects protectedObjects = new ProtectedObjects();

        ProtectedObject protectedObject1 = protectedObjects.add("App1");
        protectedObjects.add("App2");

        ProtectedObject child = protectedObject1.addChild("Scripts");

        child.addChild("sc1");
        child.addChild("sc2");
        child.addChild("sc3");

        protectedObject1.addChild("Roles");

        String content = jsonMapperService.generateJson(protectedObjects);

        String expectedString = "{\"objects\":{\"App2\":{\"data\":\"App2\"},\"App1\":{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc1\"},{\"data\":\"sc2\"},{\"data\":\"sc3\"}]},{\"data\":\"Roles\"}]}}}";

        assertThat(content).isEqualTo(expectedString);
    }

    @Test
    public void whenAddSameAppThenAddingOnlyChilds() throws JsonProcessingException {
        ProtectedObjects protectedObjects = new ProtectedObjects();

        ProtectedObject protectedObject1 = protectedObjects.add("App1").addChild("Scripts");

        protectedObject1.addChild("sc1");
        protectedObject1.addChild("sc2");
        protectedObject1.addChild("sc3");

        ProtectedObject protectedObject2 = protectedObjects.add("App1").addChild("Roles");

        protectedObject2.addChild("role1");
        protectedObject2.addChild("role2");
        protectedObject2.addChild("role3");

        String content = jsonMapperService.generateJson(protectedObjects);

        String expectedString = "{\"objects\":{\"App1\":{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc1\"},{\"data\":\"sc2\"},{\"data\":\"sc3\"}]},{\"data\":\"Roles\",\"children\":[{\"data\":\"role1\"},{\"data\":\"role2\"},{\"data\":\"role3\"}]}]}}}";

        assertThat(content).isEqualTo(expectedString);
    }

    @Test
    public void whenAddProtectedObjectThenAddingAsChild() throws JsonProcessingException {
        ProtectedObjects protectedObjects = new ProtectedObjects();

        ProtectedObject protectedObject1 = new ProtectedObject("App1");

        protectedObject1.addChild("Roles").addChild("role1");

        protectedObjects.add(protectedObject1);

        ProtectedObject protectedObject2 = new ProtectedObject("Scripts");

        protectedObject2.addChild("sc1");

        protectedObject1.addChild(protectedObject2);

        String content = jsonMapperService.generateJson(protectedObjects);

        String expectedString = "{\"objects\":{\"App1\":{\"data\":\"App1\",\"children\":[{\"data\":\"Roles\",\"children\":[{\"data\":\"role1\"}]},{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc1\"}]}]}}}";

        assertThat(content).isEqualTo(expectedString);
    }

    @Test
    public void whenAddSameChildInBeginningThenAutoClapping() throws JsonProcessingException {
        ProtectedObjects protectedObjects = new ProtectedObjects();

        ProtectedObject protectedObject1 = new ProtectedObject("App1");

        protectedObject1.addChild("Scripts").addChild("sc1");

        protectedObject1.addChild("Scripts").addChild("sc2");

        protectedObjects.add(protectedObject1);

        String content = jsonMapperService.generateJson(protectedObjects);

        String expectedString = "{\"objects\":{\"App1\":{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc1\"},{\"data\":\"sc2\"}]}]}}}";

        assertThat(content).isEqualTo(expectedString);
    }

    @Test
    public void whenAddSameChildInMiddleThenAutoClapping() throws JsonProcessingException {
        ProtectedObjects protectedObjects = new ProtectedObjects();

        ProtectedObject protectedObject1 = new ProtectedObject("App1");

        protectedObject1.addChild("Scripts").addChild("sc1").addChild("sc2");

        protectedObject1.addChild("Scripts").addChild("sc1").addChild("sc3");

        protectedObjects.add(protectedObject1);

        String content = jsonMapperService.generateJson(protectedObjects);

        String expectedString = "{\"objects\":{\"App1\":{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc1\",\"children\":[{\"data\":\"sc2\"},{\"data\":\"sc3\"}]}]}]}}}";

        assertThat(content).isEqualTo(expectedString);
    }

    @Test
    public void whenAddSameChildObjectInMiddleThenAutoClapping() throws JsonProcessingException {
        ProtectedObjects protectedObjects = new ProtectedObjects();

        ProtectedObject protectedObject1 = new ProtectedObject("App1");
        ProtectedObject protectedObject2 = new ProtectedObject("App1");

        protectedObject1.addChild("Scripts").addChild("sc1").addChild("sc2");

        protectedObject2.addChild("Scripts").addChild("sc1").addChild("sc3");

        protectedObjects.add(protectedObject1);
        protectedObjects.add(protectedObject2);

        String content = jsonMapperService.generateJson(protectedObjects);

        String expectedString = "{\"objects\":{\"App1\":{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc1\",\"children\":[{\"data\":\"sc2\"},{\"data\":\"sc3\"}]}]}]}}}";

        assertThat(content).isEqualTo(expectedString);
    }

    @Test
    public void whenAddSameChildObjectInEndThenAutoClapping() throws JsonProcessingException {
        ProtectedObjects protectedObjects = new ProtectedObjects();

        ProtectedObject protectedObject1 = new ProtectedObject("App1");
        ProtectedObject protectedObject2 = new ProtectedObject("App1");

        protectedObject1.addChild("Scripts").addChild("sc2").addChild("sc5");

        protectedObject2.addChild("Scripts").addChild("sc1").addChild("sc5");

        protectedObjects.add(protectedObject1);
        protectedObjects.add(protectedObject2);

        String content = jsonMapperService.generateJson(protectedObjects);

        String expectedString = "{\"objects\":{\"App1\":{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc2\",\"children\":[{\"data\":\"sc5\"}]},{\"data\":\"sc1\",\"children\":[{\"data\":\"sc5\"}]}]}]}}}";

        assertThat(content).isEqualTo(expectedString);
    }

    @Test
    public void whenAddDifferentObjectsThenAllowRepeatsInChilds() throws JsonProcessingException {
        ProtectedObjects protectedObjects = new ProtectedObjects();

        ProtectedObject protectedObject1 = new ProtectedObject("App1");
        ProtectedObject protectedObject2 = new ProtectedObject("App2");

        protectedObject1.addChild("Scripts").addChild("sc1").addChild("sc2");

        protectedObject2.addChild("Scripts").addChild("sc1").addChild("sc2");

        protectedObjects.add(protectedObject1);
        protectedObjects.add(protectedObject2);

        String content = jsonMapperService.generateJson(protectedObjects);

        String expectedString = "{\"objects\":{\"App2\":{\"data\":\"App2\",\"children\":[{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc1\",\"children\":[{\"data\":\"sc2\"}]}]}]},\"App1\":{\"data\":\"App1\",\"children\":[{\"data\":\"Scripts\",\"children\":[{\"data\":\"sc1\",\"children\":[{\"data\":\"sc2\"}]}]}]}}}";

        assertThat(content).isEqualTo(expectedString);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateNullProtectedObjectThenThrowException() {
        ProtectedObject protectedObject = new ProtectedObject(null);
    }

    @Test(expected = RuntimeException.class)
    public void whenCreateEmptyStringProtectedObjectThenThrowException() {
        ProtectedObject protectedObject = new ProtectedObject("");
    }

    @Test(expected = RuntimeException.class)
    public void whenAddEmptyChildThenThrowException() {
        ProtectedObject protectedObject = new ProtectedObject();

        protectedObject.addChild("");
    }

    @Test(expected = RuntimeException.class)
    public void whenAddNullProtectedObjectThenThrowException() {
        ProtectedObject protectedObject = new ProtectedObject();

        ProtectedObjects protectedObjects = new ProtectedObjects();

        protectedObjects.add(protectedObject);
    }

    @Test(expected = RuntimeException.class)
    public void whenAddChildNullProtectedObjectThenThrowException() {
        ProtectedObject protectedObject = new ProtectedObject();

        ProtectedObjects protectedObjects = new ProtectedObjects();

        protectedObjects.addChild(protectedObject);
    }

    @Test(expected = RuntimeException.class)
    public void whenAddChildToNullParentNullProtectedObjectThenThrowException() {
        ProtectedObject protectedObject = new ProtectedObject();

        ProtectedObjects protectedObjects = new ProtectedObjects();

        protectedObjects.addChild(null, protectedObject);
    }

    @Test(expected = RuntimeException.class)
    public void whenAddChildToEmptyParentNullProtectedObjectThenThrowException() {
        ProtectedObject protectedObject = new ProtectedObject();

        ProtectedObjects protectedObjects = new ProtectedObjects();

        protectedObjects.addChild("", protectedObject);
    }

    @Test(expected = RuntimeException.class)
    public void whenAddChildToParentNullProtectedObjectThenThrowException() {
        ProtectedObject protectedObject = new ProtectedObject();

        ProtectedObjects protectedObjects = new ProtectedObjects();

        protectedObjects.addChild("d", protectedObject);
    }

    @Test(expected = RuntimeException.class)
    public void whenAddChildDifferentChildToParentThenThrowException() {
        ProtectedObject protectedObject = new ProtectedObject("f");

        ProtectedObjects protectedObjects = new ProtectedObjects();

        protectedObjects.addChild("d", protectedObject);
    }

    @Test(expected = RuntimeException.class)
    public void whenAddDifferentChildToParentThenThrowException() {
        ProtectedObject protectedObject = new ProtectedObject("f");

        ProtectedObjects protectedObjects = new ProtectedObjects();

        protectedObjects.add("d", protectedObject);
    }

    @Test
    public void whenAddSameChildObjectWithNameInEndThenAutoClapping() throws JsonProcessingException {
        ProtectedObjects protectedObjects = new ProtectedObjects();

        ProtectedObject protectedObject1 = new ProtectedObject("App1");
        ProtectedObject protectedObject2 = new ProtectedObject("App1", "Приложение 1");

        protectedObject1.addChild("Scripts", "Скрипты").addChild("sc2").addChild("sc5");

        protectedObject2.addChild("Scripts").addChild("sc1").addChild("sc5");

        protectedObjects.add(protectedObject1);
        protectedObjects.add(protectedObject2);

        String content = jsonMapperService.generateJson(protectedObjects);

        String expectedString = "{\"objects\":{\"App1\":{\"data\":\"App1\",\"name\":\"Приложение 1\",\"children\":[{\"data\":\"Scripts\",\"name\":\"Скрипты\",\"children\":[{\"data\":\"sc2\",\"children\":[{\"data\":\"sc5\"}]},{\"data\":\"sc1\",\"children\":[{\"data\":\"sc5\"}]}]}]}}}";

        assertThat(content).isEqualTo(expectedString);
    }
}
