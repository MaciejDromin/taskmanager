package com.soitio.taskmanager.scoring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("scoring")
public record ScoringConfig(int limit) {
}
