package com.trionesdev.iotdb.autoconfigure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.iotdb.session.pool.SessionPool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(value = {IotDbProperties.class})
public class IotDbAutoConfiguration {
    private final IotDbProperties iotDbProperties;

    @Bean
    @ConditionalOnMissingBean(SessionPool.class)
    public SessionPool sessionPool() {
        return new SessionPool.Builder()
                .host(iotDbProperties.getHost())
                .port(iotDbProperties.getPort())
                .user(iotDbProperties.getUser())
                .password(iotDbProperties.getPassword())
                .maxSize(iotDbProperties.getMaxSize())
                .build();
    }

}
