package ru.rtksoftlabs.LicenseCommons.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObject;
import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObjects;
import ru.rtksoftlabs.LicenseCommons.util.License;

import java.io.IOException;
import java.util.List;

public interface JsonMapperService {
    ObjectMapper getJsonMapper();
    String generateJson(ProtectedObject protectedObject) throws JsonProcessingException;
    String generateJson(List<ProtectedObject> protectedObjectList) throws JsonProcessingException;
    String generateJson(ProtectedObjects protectedObjects) throws JsonProcessingException;
    String generateJson(License license) throws JsonProcessingException;
    ProtectedObject generateProtectedObject(String jsonString) throws IOException;
    List<ProtectedObject> generateListOfProtectedObjects(String jsonString) throws IOException;
    License generateLicense(String jsonString) throws IOException;
}
