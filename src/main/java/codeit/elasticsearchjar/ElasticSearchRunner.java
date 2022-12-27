package codeit.elasticsearchjar;

import codeit.elasticsearchjar.docker.DockerCommands;

import java.io.IOException;

import static codeit.elasticsearchjar.docker.DockerDaemonConnection.runAgainstDaemon;

public class ElasticSearchRunner {
    public void runElasticSearch() throws IOException {

        runAgainstDaemon(DockerCommands.pullImage("docker.elastic.co/elasticsearch/elasticsearch", "7.5.2"));

    }
}
