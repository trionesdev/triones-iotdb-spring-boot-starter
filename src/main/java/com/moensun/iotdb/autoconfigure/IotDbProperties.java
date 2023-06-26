package com.moensun.iotdb.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.iotdb")
public class IotDbProperties {
    private int maxSize = 0;
    private String host;
    private int port;
    private String user;
    private String password;

}