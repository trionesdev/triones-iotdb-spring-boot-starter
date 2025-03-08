package com.trionesdev.iotdb.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "spring.iotdb")
public class IotDbProperties {
    private Dialect dialect;
    private int maxSize = 10;
    private String host;
    private int port;
    private String[] nodeUrls;
    private String user;
    private String password;
    private String database;

    public enum Dialect {
        TREE,
        TABLE
    }
}
