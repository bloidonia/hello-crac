package com.bloidonia;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.runtime.context.scope.Refreshable;

@Refreshable
@ConfigurationProperties(AppConfig.PREFIX)
public interface AppConfig {

    static final String PREFIX = "app";

    @NonNull
    String getSuffix();
}
