package ru.rtksoftlabs.LicenseCommons;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsSchema;
import org.junit.Assert;
import org.junit.Test;
import ru.rtksoftlabs.LicenseCommons.services.FileService;
import ru.rtksoftlabs.LicenseCommons.services.impl.FileServiceImpl;
import ru.rtksoftlabs.LicenseCommons.services.SignatureService;
import ru.rtksoftlabs.LicenseCommons.services.impl.SignatureServiceImpl;
import ru.rtksoftlabs.LicenseCommons.util.CustomCase;

import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class SignatureServiceTest {
    private SignatureService signatureService;

    private FileService fileService;

    public SignatureServiceTest() throws IOException {
        String appConfigPath = "application.properties";

        JavaPropsSchema schema = JavaPropsSchema.emptySchema().withoutPathSeparator();

        JavaPropsMapper mapper = new JavaPropsMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper.setPropertyNamingStrategy(new CustomCase());

        signatureService = mapper.readerFor(SignatureServiceImpl.class).with(schema).readValue(getClass().getClassLoader().getResourceAsStream(appConfigPath));

        fileService = new FileServiceImpl();
    }

    @Test
    public void verifyShouldReturnTrueWhenSignedMessageWasNotModifiedAfterSign() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String message = "Hello";

        KeyPair keyPair = signatureService.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();

        byte[] signatureBytes = signatureService.sign(message.getBytes(), privateKey);

        PublicKey publicKey = keyPair.getPublic();

        Assert.assertTrue(signatureService.verify(message.getBytes(), signatureBytes, publicKey));
    }

    @Test
    public void verifyShouldReturnFalseWhenSignedMessageWasModifiedAfterSign() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String messageBeforeSign = "Hello";

        KeyPair keyPair = signatureService.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();

        byte[] signatureBytes = signatureService.sign(messageBeforeSign.getBytes(), privateKey);

        PublicKey publicKey = keyPair.getPublic();

        String messageAfterSign = "Hello!!!";

        Assert.assertFalse(signatureService.verify(messageAfterSign.getBytes(), signatureBytes, publicKey));
    }

    @Test
    public void verifyShouldReturnFalseWhenSignatureWasModifiedAfterSign() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String message = "Hello";

        KeyPair keyPair = signatureService.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();

        byte[] signatureBytes = signatureService.sign(message.getBytes(), privateKey);

        signatureBytes[signatureBytes.length-1] = 'd';

        PublicKey publicKey = keyPair.getPublic();

        Assert.assertFalse(signatureService.verify(message.getBytes(), signatureBytes, publicKey));
    }

    @Test
    public void verifyShouldReturnTrueWhenPublicKeyWasEncodedAndDecoded() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidKeySpecException {
        String message = "Hello";

        KeyPair keyPair = signatureService.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();

        byte[] signatureBytes = signatureService.sign(message.getBytes(), privateKey);

        PublicKey publicKey = keyPair.getPublic();

        byte[] publicKeyBytes = publicKey.getEncoded();

        publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        Assert.assertTrue(signatureService.verify(message.getBytes(), signatureBytes, publicKey));
    }

    @Test
    public void verifyShouldReturnFalseWhenPublicKeyWasModified() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidKeySpecException {
        String message = "Hello";

        KeyPair keyPair = signatureService.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();

        byte[] signatureBytes = signatureService.sign(message.getBytes(), privateKey);

        PublicKey publicKey = keyPair.getPublic();

        byte[] publicKeyBytes = publicKey.getEncoded();

        publicKeyBytes[publicKeyBytes.length-1] = 'd';

        publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        Assert.assertFalse(signatureService.verify(message.getBytes(), signatureBytes, publicKey));
    }
}
