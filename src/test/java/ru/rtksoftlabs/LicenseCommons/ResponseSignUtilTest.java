package ru.rtksoftlabs.LicenseCommons;

import org.junit.Test;
import ru.rtksoftlabs.LicenseCommons.inno.ProtectedObjectsServiceImpl;
import ru.rtksoftlabs.LicenseCommons.services.ProtectedObjectsService;
import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObject;
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

        ProtectedObject protectedObject = protectedObjectsService.getProtectedObjects().get(0);

        ResponseSignUtil responseSignUtil = new ResponseSignUtil(protectedObject.returnListOfStringsWithPathToAllLeafs().get(0), true, instant);

        ResponseSignUtil expectedResponseSignUtil = new ResponseSignUtil(protectedObject.returnListOfStringsWithPathToAllLeafs().get(0), true, instant);

        assertThat(responseSignUtil).isEqualToComparingFieldByField(expectedResponseSignUtil);
    }

    @Test
    public void generateHashTest() throws NoSuchAlgorithmException {
        Instant instant = Instant.now();

        ProtectedObject protectedObject = protectedObjectsService.getProtectedObjects().get(0);

        byte[] generatedHash = ResponseSignUtil.generateHash(protectedObject.returnListOfStringsWithPathToAllLeafs().get(0), true, instant);

        byte[] expectedHash = ResponseSignUtil.generateHash(protectedObject.returnListOfStringsWithPathToAllLeafs().get(0), true, instant);

        assertThat(generatedHash).isEqualTo(expectedHash);
    }
}
