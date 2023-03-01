package com.nexters.momo.support;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class TestRedisContainer {

    private static final String DOCKER_REDIS_IMAGE = "redis:7.0.5-alpine";

    public static GenericContainer REDIS_CONTAINER =
            new GenericContainer<>(DockerImageName.parse(DOCKER_REDIS_IMAGE))
                    .withExposedPorts(6379)
                    .withReuse(true);

    static {
        REDIS_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
    }
}
