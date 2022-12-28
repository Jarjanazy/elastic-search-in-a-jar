package codeit.elasticsearchjar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class ElasticSearchInAJar {

    public static void main(String[] args) throws IOException {

        String configurationFilePath = args[0];

        var userConfiguration = getUserConfiguration(configurationFilePath);

        new ElasticSearchRunner().runElasticSearch(userConfiguration);

        keepThreadAlive();
    }


    private static UserConfiguration getUserConfiguration(String configurationFilePath) throws IOException {
        var mapper = new ObjectMapper(new YAMLFactory());

        return mapper.readValue(new File(configurationFilePath), UserConfiguration.class);
    }

    private static void keepThreadAlive() {
        Object forever = new Object();
        synchronized (forever) {
            try { forever.wait(); } catch (InterruptedException ignore) {}
        }
    }
}
