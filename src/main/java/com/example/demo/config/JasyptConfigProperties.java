package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "jasypt.encryptor")
@Data
public class JasyptConfigProperties {
    private String algorithm = "PBEWITHHMACSHA512ANDAES_256";
    private String keyObtentionIterations = "1000";
    private String poolSize = "1";
    private String saltGeneratorClassName = "org.jasypt.salt.RandomSaltGenerator";
    private String stringOutputType = "base64";
    private String ivGeneratorClassName = "org.jasypt.iv.RandomIvGenerator";
}
