package com.service.utils;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SecretKeyUtilTest {

    @Test
    void 비밀키_생성_테스트() {
        try {
            String secretKey = SecretKeyUtil.generateSecretKey();
            assertNotNull(secretKey, "Generated secret key should not be null");
            assertEquals(24, secretKey.length(), "AES 128-bit key encoded in Base64 should be 24 characters long");
        } catch (Exception e) {
            fail("Exception should not be thrown during key generation: " + e.getMessage());
        }
    }

    @Test
    void 비밀키_암호화_테스트() {
    }

    @Test
    void testEncryptAndDecrypt() throws Exception {
        // SecretKey 생성
        String secretKey = SecretKeyUtil.generateSecretKey();
        System.out.println("생성된 SecretKey: " + secretKey);

        //사용자 email
        String getterEmail = "getterEmail"; // 16바이트보다 작은 데이터
        String providerEmail = "providerEmail";

        // 암호화
        String encryptedData = SecretKeyUtil.encrypt(secretKey, getterEmail);
        System.out.println("암호화된 데이터: " + encryptedData);

        // 복호화
        String decryptedData = SecretKeyUtil.decrypt(secretKey, encryptedData);
        System.out.println("복호화된 데이터: " + decryptedData);

        // 원본 데이터와 복호화된 데이터 비교
        assertEquals(getterEmail, decryptedData);
    }

    @Test
    void testDecryptWithWrongKey() {
        try {
            String secretKey = SecretKeyUtil.generateSecretKey();
            String wrongSecretKey = SecretKeyUtil.generateSecretKey();
            String originalData = "This is a secret message";

            // 암호화
            String encryptedData = SecretKeyUtil.encrypt(secretKey, originalData);

            // 잘못된 키로 복호화 시도
            assertThrows(Exception.class, () -> {
                SecretKeyUtil.decrypt(wrongSecretKey, encryptedData);
            }, "Decryption with a wrong key should throw an exception");
        } catch (Exception e) {
            fail("Exception should not be thrown during the test setup: " + e.getMessage());
        }
    }

    @Test
    void testEncryptWithInvalidKey() {
        String invalidSecretKey = "invalidKey";
        String data = "This is a secret message";

        // 잘못된 키로 암호화 시도
        assertThrows(Exception.class, () -> {
            SecretKeyUtil.encrypt(invalidSecretKey, data);
        }, "Encryption with an invalid key should throw an exception");
    }
}
