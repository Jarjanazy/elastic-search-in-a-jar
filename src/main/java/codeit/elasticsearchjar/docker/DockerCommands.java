package codeit.elasticsearchjar.docker;

import codeit.elasticsearchjar.UserConfiguration;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;
import java.util.function.Function;

import static java.lang.String.format;

@Slf4j
public class DockerCommands {

    public static Function<DockerClient, CreateContainerResponse> startElasticSearchContainerFromImage(UserConfiguration userConfiguration) {
        return dockerClient -> {
            log.info("Starting ElasticSearch container");

            var hostConfig = HostConfig.newHostConfig()
                    .withPortBindings(PortBinding.parse(format("%s:9200", userConfiguration.getPort())));

            var container = dockerClient
                    .createContainerCmd(format("docker.elastic.co/elasticsearch/elasticsearch:%s", userConfiguration.getTag()))
                    .withName("elasticsearch-in-a-jar")
                    .withHostConfig(hostConfig)
                    .withEnv("discovery.type=single-node")
                    .exec();

            dockerClient.startContainerCmd(container.getId()).exec();

            log.info("Done Starting ElasticSearch container");

            return container;
        };
    }

    public static Consumer<DockerClient> stopElasticSearchContainer(CreateContainerResponse container) {
        return dockerClient -> {
            log.info("Stopping ElasticSearch container");

            dockerClient.stopContainerCmd(container.getId()).exec();

            log.info("Done stopping ElasticSearch container");
        };

    }


        public static Consumer<DockerClient> pullImage(String image, String tag) {
        return (dockerClient) -> {
            try {
                log.info(format("Started pulling %s:%s", image, tag));

                dockerClient
                        .pullImageCmd(image)
                        .withTag(tag)
                        .exec(new PullImageResultCallback())
                        .awaitCompletion();

                log.info(format("Done pulling %s:%s", image, tag));
            } catch (InterruptedException e) {
                log.error("Exception while fetching the image: " + image);
                throw new RuntimeException(e);
            }
        };
    }

    public static Consumer<DockerClient> removeContainerWithName(String containerName) {
        return dockerClient -> {
            try {
                dockerClient.inspectContainerCmd(containerName).exec();
                dockerClient.removeContainerCmd(containerName).exec();
            } catch (NotFoundException e) {
                log.info("Container doesn't exist: " + containerName);
            }
            log.info("Removed container: " + containerName);
        };
    }
}
