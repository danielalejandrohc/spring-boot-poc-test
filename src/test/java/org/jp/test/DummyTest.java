package org.jp.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.logging.Logger;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestTemplate;

public class DummyTest {

    private static final Logger log = Logger.getLogger(DummyTest.class.getName());

    @Autowired
    private TestRestTemplate restTemplate;

    public DummyTest() {

    }

    @Test
    public void test() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        URL resource = this.getClass().getClassLoader().getResource("application.yml");
        JsonNode ymlFile = mapper.readTree(resource);
        log.info("ymlFile: " + ymlFile);
        String port = ymlFile.get("server").get("port").asText();
        log.info("ymlFile.port: " + port);
        String url = "http://localhost:" + port + "/rest/v1/accounts/account";
        String payLoad = "{\n"
                + "    \"amount\": 213.15,\n"
                + "    \"currency\": \"USD\",\n"
                + "    \"customerName\": \"daniel\"\n"
                + "}";
        restTemplate.postForEntity(url, payLoad, String.class);

        String response = restTemplate.getForObject(url, String.class);
        log.info("Response: " + response);
    }
}
