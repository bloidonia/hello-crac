package com.bloidonia;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class HelloController {

    private final AppConfig appConfig;

    public HelloController(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Get("/hello{/name}")
    public String hello(@Nullable String name) {
        return "Hello " + (name == null ? appConfig.getSuffix() : name) + "!";
    }
}
