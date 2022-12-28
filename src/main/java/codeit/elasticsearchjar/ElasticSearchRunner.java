package codeit.elasticsearchjar;

import codeit.elasticsearchjar.docker.DockerCommands;

import java.io.IOException;

import static codeit.elasticsearchjar.docker.DockerCommands.*;
import static codeit.elasticsearchjar.docker.DockerDaemonConnection.nonThrowingRunAgainstDaemon;
import static codeit.elasticsearchjar.docker.DockerDaemonConnection.runAgainstDaemon;

public class ElasticSearchRunner {
    public void runElasticSearch(UserConfiguration userConfiguration) throws IOException {

        runAgainstDaemon(DockerCommands.pullImage("docker.elastic.co/elasticsearch/elasticsearch", userConfiguration.getTag()));

        runAgainstDaemon(removeContainerWithName("elasticsearch-in-a-jar"));

        var container = runAgainstDaemon(startElasticSearchContainerFromImage(userConfiguration));

        Runtime
                .getRuntime()
                .addShutdownHook(new Thread(() -> nonThrowingRunAgainstDaemon(stopElasticSearchContainer(container))));
    }
}
