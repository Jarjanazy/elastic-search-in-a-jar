package codeit.elasticsearchjar;

import java.io.IOException;

public class ElasticSearchInAJar {

    public static void main(String[] args) throws IOException {

        new ElasticSearchRunner().runElasticSearch();

        keepThreadAlive();
    }

    private static void keepThreadAlive() {
        Object forever = new Object();
        synchronized (forever) {
            try { forever.wait(); } catch (InterruptedException ignore) {}
        }
    }
}
