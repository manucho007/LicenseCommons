package ru.rtksoftlabs.LicenseCommons;

import org.junit.Test;
import ru.rtksoftlabs.LicenseCommons.inno.ProtectedObjectsServiceImpl;
import ru.rtksoftlabs.LicenseCommons.services.ProtectedObjectsService;
import ru.rtksoftlabs.LicenseCommons.shared.ProtectedObject;
import ru.rtksoftlabs.LicenseCommons.shared.Response;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTest {
    private ProtectedObjectsService protectedObjectsService;

    public ResponseTest() {
        protectedObjectsService = new ProtectedObjectsServiceImpl();
    }

    @Test
    public void generateHashTest() throws NoSuchAlgorithmException {
        Response response = new Response();

        Instant instant = Instant.now();

        response.setTimestamp(instant);

        response.setAccess(true);

        ProtectedObject protectedObject = protectedObjectsService.getProtectedObjects().get(0);

        byte[] generatedHash = response.generateHash(protectedObject.returnListOfStringsWithPathToAllLeafs().get(0));

        Response expectedResponse = new Response();

        expectedResponse.setTimestamp(instant);

        expectedResponse.setAccess(true);

        byte[] expectedHash = expectedResponse.generateHash(protectedObject.returnListOfStringsWithPathToAllLeafs().get(0));

        assertThat(generatedHash).isEqualTo(expectedHash);
    }
}
