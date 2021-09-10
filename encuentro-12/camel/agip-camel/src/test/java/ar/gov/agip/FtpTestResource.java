package ar.gov.agip;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.apache.camel.util.CollectionHelper;
import org.apache.commons.io.FileUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class FtpTestResource implements QuarkusTestResourceLifecycleManager {

    private static final int FTP_PORT = 2222;
    private static final String SSH_IMAGE = "quay.io/jamesnetherton/sftp-server:0.1.0";

    private GenericContainer container;

    @Override
    public Map<String, String> start() {
        Path tmpDir = Paths.get(System.getProperty("java.io.tmpdir"), "books");
        try {
            FileUtils.deleteDirectory(tmpDir.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            container = new GenericContainer(SSH_IMAGE)
                    .withExposedPorts(FTP_PORT)
                    .withEnv("PASSWORD_ACCESS", "true")
                    .withEnv("FTP_USER", "ftpuser")
                    .withEnv("FTP_PASSWORD", "ftppassword")
                    .waitingFor(Wait.forListeningPort());

            container.start();

            return CollectionHelper.mapOf(
                    "ftp.host", container.getContainerIpAddress(),
                    "ftp.port", container.getMappedPort(FTP_PORT).toString(),
                    "timer.delay", "100");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        if (container != null) {
            container.stop();
        }
    }
}
