package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JasyptConfigProperties.class)
@RequiredArgsConstructor
public class JasyptConfig {
    private final JasyptConfigProperties jasyptConfigProperties;

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(System.getProperty("jasypt.encryptor.password"));
        config.setAlgorithm(jasyptConfigProperties.getAlgorithm());
        config.setKeyObtentionIterations(jasyptConfigProperties.getKeyObtentionIterations());
        config.setIvGeneratorClassName(jasyptConfigProperties.getIvGeneratorClassName());
        config.setPoolSize(jasyptConfigProperties.getPoolSize());
        config.setSaltGeneratorClassName(jasyptConfigProperties.getSaltGeneratorClassName());
        config.setStringOutputType(jasyptConfigProperties.getStringOutputType());
        encryptor.setConfig(config);
        return encryptor;
    }
}
