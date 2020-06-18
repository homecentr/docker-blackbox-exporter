package helpers;

import io.homecentr.testcontainers.images.EnvironmentImageTagResolver;

public class BlackboxExporterImageTagResolver extends EnvironmentImageTagResolver {
    public BlackboxExporterImageTagResolver() {
        super("homecentr/prometheus-blackbox-exporter:local");
    }
}
