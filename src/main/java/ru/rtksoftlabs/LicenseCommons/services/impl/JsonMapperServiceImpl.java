package ru.rtksoftlabs.LicenseCommons.services.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.rtksoftlabs.LicenseCommons.services.JsonMapperService;
import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObject;
import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObjects;
import ru.rtksoftlabs.LicenseCommons.util.License;

import java.io.IOException;
import java.util.List;

public class JsonMapperServiceImpl implements JsonMapperService {
    @Override
    public ObjectMapper getJsonMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        return mapper;
    }

    @Override
    public String generateJson(ProtectedObject protectedObject) throws JsonProcessingException {
        return getJsonMapper().writeValueAsString(protectedObject);
    }

    @Override
    public String generateJson(List<ProtectedObject> protectedObjectList) throws JsonProcessingException {
        return getJsonMapper().writeValueAsString(protectedObjectList);
    }

    @Override
    public String generateJson(ProtectedObjects protectedObjects) throws JsonProcessingException {
        return getJsonMapper().writeValueAsString(protectedObjects);
    }

    @Override
    public String generateJson(License license) throws JsonProcessingException {
        return getJsonMapper().writeValueAsString(license);
    }

    @Override
    public ProtectedObject generateProtectedObject(String jsonString) throws IOException {
        return getJsonMapper().readValue(jsonString, ProtectedObject.class);
    }

    @Override
    public List<ProtectedObject> generateListOfProtectedObjects(String jsonString) throws IOException {
        return getJsonMapper().readValue(jsonString, new TypeReference<List<ProtectedObject>>(){});
    }
}
