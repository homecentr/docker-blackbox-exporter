import helpers.BlackboxExporterImageTagResolver;
import io.homecentr.testcontainers.containers.GenericContainerEx;
import io.homecentr.testcontainers.containers.HttpResponse;
import io.homecentr.testcontainers.containers.wait.strategy.WaitEx;
import io.homecentr.testcontainers.images.PullPolicyEx;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class BlackboxExporterContainerShould {
    private static GenericContainerEx _blackboxExporterContainer;
    private static final Logger logger = LoggerFactory.getLogger(BlackboxExporterContainerShould.class);

    private static final String SiteUrl = "https://google.com";

    @BeforeClass
    public static void before() {
        _blackboxExporterContainer = new GenericContainerEx<>(new BlackboxExporterImageTagResolver())
                .withRelativeFileSystemBind(Paths.get("..", "example"), "/config")
                .withImagePullPolicy(PullPolicyEx.never())
                .waitingFor(WaitEx.forS6OverlayStart());

        _blackboxExporterContainer.start();
        _blackboxExporterContainer.followOutput(new Slf4jLogConsumer(logger));
    }

    @AfterClass
    public static void after() {
        _blackboxExporterContainer.close();
    }

    @Test
    public void returnMetrics() throws IOException {
        HttpResponse response = _blackboxExporterContainer.makeHttpRequest(9115, "/probe?target=" + SiteUrl + "&module=http_2xx");
        String responseContent = response.getResponseContent();

        assertTrue(responseContent.contains("probe_success 1"));
    }
}