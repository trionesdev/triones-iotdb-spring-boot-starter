package com.trionesdev.iotdb.autoconfigure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.iotdb.isession.pool.ITableSessionPool;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.session.pool.TableSessionPoolBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(value = {IotDbProperties.class})
public class IotDbAutoConfiguration {
    private final IotDbProperties iotDbProperties;

    private List<String> nodeUrls() {
        List<String> nodeUrls;
        if (ArrayUtils.isNotEmpty(iotDbProperties.getNodeUrls())) {
            nodeUrls = new ArrayList<>(Arrays.asList(iotDbProperties.getNodeUrls()));
        } else {
            nodeUrls = Collections.singletonList(iotDbProperties.getHost() + ":" + iotDbProperties.getPort());
        }
        return nodeUrls;
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.iotdb", name = "dialect", havingValue = "tree")
    @ConditionalOnMissingBean(SessionPool.class)
    public SessionPool sessionPool() {
        return new SessionPool.Builder()
                .nodeUrls(nodeUrls())
                .user(iotDbProperties.getUser())
                .password(iotDbProperties.getPassword())
                .maxSize(iotDbProperties.getMaxSize())
                .build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.iotdb", name = {"dialect"}, havingValue = "table")
    @ConditionalOnMissingBean(ITableSessionPool.class)
    public ITableSessionPool tableSessionPool() {
        return new TableSessionPoolBuilder()
                .nodeUrls(nodeUrls())
                .user(iotDbProperties.getUser())
                .password(iotDbProperties.getPassword())
                .maxSize(iotDbProperties.getMaxSize())
                .database(iotDbProperties.getDatabase())
                .build();
    }

}
