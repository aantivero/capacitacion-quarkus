package ar.gov.agip;

import java.util.concurrent.TimeUnit;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.Test;

import static org.awaitility.Awaitility.await;

@QuarkusTest
@QuarkusTestResource(FtpTestResource.class)
public class FileToFtpTest {

    @Test
    public void testFileToFtp() throws JSchException {

        Config config = ConfigProvider.getConfig();

        JSch jsch = new JSch();
        jsch.setKnownHosts(config.getValue("user.home", String.class) + "/.ssh/known_hosts");

        Session session = jsch.getSession("ftpuser", config.getValue("ftp.host", String.class));
        session.setPort(Integer.parseInt(config.getValue("ftp.port", String.class)));
        session.setPassword("ftppassword");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(5000);
        Channel sftp = null;
        try {
            sftp = session.openChannel("sftp");
            sftp.connect(5000);

            ChannelSftp channelSftp = (ChannelSftp) sftp;

            await().atMost(10L, TimeUnit.SECONDS).pollDelay(500, TimeUnit.MILLISECONDS).until(() -> {
                try {
                    return channelSftp.ls("uploads/books/*.csv").size() >= 3;
                } catch (Exception e) {
                    return false;
                }
            });
        } finally {
            if (sftp != null) {
                sftp.disconnect();
            }
        }
    }
}
