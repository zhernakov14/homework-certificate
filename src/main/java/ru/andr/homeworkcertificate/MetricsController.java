package ru.andr.homeworkcertificate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.boot.actuate.metrics.MetricsEndpoint;

@RestController
public class MetricsController {

    private final MetricsEndpoint metricsEndpoint;

    public MetricsController(MetricsEndpoint metricsEndpoint) {
        this.metricsEndpoint = metricsEndpoint;
    }

    @GetMapping("/metrics")
    public Object getMetrics() {
        return metricsEndpoint.metric("certificates.days_until_expiration", null);
    }
}
