package com.bloidonia;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class HelloTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    @Client("/")
    private HttpClient client;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    void endpointThere() {
        assertEquals("Hello World!", client.toBlocking().exchange("/hello", String.class).body());
    }

    @Test
    void pathRead() {
        assertEquals("Hello Tim!", client.toBlocking().exchange("/hello/Tim", String.class).body());
    }
}
