package ru.rtksoftlabs.LicenseCommons;

import org.junit.Test;
import ru.rtksoftlabs.LicenseCommons.inno.ProtectedObjectsServiceImpl;
import ru.rtksoftlabs.LicenseCommons.services.ProtectedObjectsService;
import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObjects;
import ru.rtksoftlabs.LicenseCommons.shared.ResponseSignUtil;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseSignUtilTest {
    private ProtectedObjectsService protectedObjectsService;

    public ResponseSignUtilTest() {
        protectedObjectsService = new ProtectedObjectsServiceImpl();
    }

    @Test
    public void responseSignUtilEqualsTest() throws NoSuchAlgorithmException {
        Instant instant = Instant.now();

        ProtectedObjects protectedObjects = protectedObjectsService.getProtectedObjects();

        ResponseSignUtil responseSignUtil = new ResponseSignUtil(protectedObjects.getObjects().get("App1").returnListOfStringsWithPathToAllLeafs().get(0), true, instant);

        ResponseSignUtil expectedResponseSignUtil = new ResponseSignUtil(protectedObjects.getObjects().get("App1").returnListOfStringsWithPathToAllLeafs().get(0), true, instant);

        assertThat(responseSignUtil).isEqualToComparingFieldByField(expectedResponseSignUtil);
    }

    @Test
    public void generateHashTest() throws NoSuchAlgorithmException {
        Instant instant = Instant.now();

        ProtectedObjects protectedObjects = protectedObjectsService.getProtectedObjects();

        byte[] generatedHash = ResponseSignUtil.generateHash(protectedObjects.getObjects().get("App1").returnListOfStringsWithPathToAllLeafs().get(0), true, instant);

        byte[] expectedHash = ResponseSignUtil.generateHash(protectedObjects.getObjects().get("App1").returnListOfStringsWithPathToAllLeafs().get(0), true, instant);

        assertThat(generatedHash).isEqualTo(expectedHash);
    }
}
