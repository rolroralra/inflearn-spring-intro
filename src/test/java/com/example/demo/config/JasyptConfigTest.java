package com.example.demo.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JasyptConfigTest {

    @Test
    void test() {
        String plainText = "sa";

        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        jasypt.setPassword("password");
//        jasypt.setAlgorithm("PBEWithMD5AndDES");
        jasypt.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        jasypt.setIvGenerator(new RandomIvGenerator());



        String encryptedText = jasypt.encrypt(plainText);
        String decryptedText = jasypt.decrypt(encryptedText);

        System.out.println(encryptedText);
        assertThat(plainText).isEqualTo(decryptedText);
    }
}