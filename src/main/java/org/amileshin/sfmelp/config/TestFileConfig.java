package org.amileshin.sfmelp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix="sfmelp.files")
public class TestFileConfig {
    private String file;
    private String path;
}
