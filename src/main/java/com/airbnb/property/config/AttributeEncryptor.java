package com.airbnb.property.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;

@Component
@Converter
public class AttributeEncryptor implements AttributeConverter<String, String> {
    private static final String AES = "AES/CBC/PKCS5PADDING";
    private final Key key;
    private final Cipher cipher;
    private final IvParameterSpec iv;

    public AttributeEncryptor(ApplicationConfig applicationConfig) throws Exception {
        key = new SecretKeySpec(applicationConfig.getAesKey().getBytes(), "AES");
        iv = new IvParameterSpec(applicationConfig.getIv().getBytes(StandardCharsets.UTF_8));
        cipher = Cipher.getInstance(AES);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException |
                 InvalidAlgorithmParameterException e) {
            throw new IllegalStateException(e);
        }
    }
}
