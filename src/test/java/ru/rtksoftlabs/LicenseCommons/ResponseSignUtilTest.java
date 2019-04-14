package ru.rtksoftlabs.LicenseCommons;

import org.junit.Test;
import ru.rtksoftlabs.LicenseCommons.inno.ProtectedObjectsServiceImpl;
import ru.rtksoftlabs.LicenseCommons.services.ProtectedObjectsService;
import ru.rtksoftlabs.LicenseCommons.shared.CheckAccessResult;
import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObjects;
import ru.rtksoftlabs.LicenseCommons.shared.ResponseSignUtil;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseSignUtilTest {
    private ProtectedObjectsService protectedObjectsService;

    public ResponseSignUtilTest() {
        protectedObjectsService = new ProtectedObjectsServiceImpl();
    }

    @Test
    public void responseSignUtilEqualsTest() throws NoSuchAlgorithmException {
        Date timestamp = new Date();

        ProtectedObjects protectedObjects = protectedObjectsService.getProtectedObjects();

        CheckAccessResult checkAccessResult = new CheckAccessResult(protectedObjects.getObjects().get("App1").returnListOfStringsWithPathToAllLeafs().get(0), true, timestamp);

        CheckAccessResult expectedCheckAccessResult = new CheckAccessResult(protectedObjects.getObjects().get("App1").returnListOfStringsWithPathToAllLeafs().get(0), true, timestamp);

        assertThat(checkAccessResult).isEqualToComparingFieldByField(expectedCheckAccessResult);
    }

    @Test
    public void generateHashTest() throws NoSuchAlgorithmException {
        Date timestamp = new Date();

        ProtectedObjects protectedObjects = protectedObjectsService.getProtectedObjects();

        byte[] generatedHash = ResponseSignUtil.generateHash(protectedObjects.getObjects().get("App1").returnListOfStringsWithPathToAllLeafs().get(0), true, timestamp);

        byte[] expectedHash = ResponseSignUtil.generateHash(protectedObjects.getObjects().get("App1").returnListOfStringsWithPathToAllLeafs().get(0), true, timestamp);

        assertThat(generatedHash).isEqualTo(expectedHash);
    }
}
