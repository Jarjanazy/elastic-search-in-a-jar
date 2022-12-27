package codeit.elasticsearchjar.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class DockerDaemonConnection {
    private DockerDaemonConnection() {}
    public static void runAgainstDaemon(Consumer<DockerClient> consumer) throws IOException {
        try (DockerClient dockerClient = DockerClientBuilder.getInstance().build()) {
            consumer.accept(dockerClient);
        } catch (IOException ioException) {
            log.error("Unable to connect to DockerClient", ioException);
            throw ioException;
        }
    }

    public static void nonThrowingRunAgainstDaemon(Consumer<DockerClient> consumer) {
        try (DockerClient dockerClient = DockerClientBuilder.getInstance().build()) {
            consumer.accept(dockerClient);
        } catch (IOException ioException) {
            log.error("Unable to connect to DockerClient", ioException);
        }
    }

    public static <T> T runAgainstDaemon(Function<DockerClient,T> function) throws IOException {
        try (DockerClient dockerClient = DockerClientBuilder.getInstance().build()) {
            return function.apply(dockerClient);
        } catch (IOException ioException) {
            log.error("Unable to connect to DockerClient", ioException);
            throw ioException;
        }
    }

}
