package com.javedrpi.webfluxlogtracing.controller;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

/**
 * @author Javed Ameen Shaikh
 * @website <a href="https://www.javedrpi.com/me">Portfolio</a>
 */

//@Component
@Slf4j
class MyHandler implements ObservationHandler<Observation.Context> {

    @Override
    public void onStart(Observation.Context context) {
        log.info("Before running the observation for context [{}], userType [{}]", context.getName(), getUserTypeFromContext(context));
    }

    @Override
    public void onStop(Observation.Context context) {
        log.info("After running the observation for context [{}], userType [{}]", context.getName(), getUserTypeFromContext(context));
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }

    private String getUserTypeFromContext(Observation.Context context) {
        return StreamSupport.stream(context.getLowCardinalityKeyValues().spliterator(), false)
                .filter(keyValue -> "userType".equals(keyValue.getKey()))
                .map(KeyValue::getValue)
                .findFirst()
                .orElse("UNKNOWN");
    }
}
