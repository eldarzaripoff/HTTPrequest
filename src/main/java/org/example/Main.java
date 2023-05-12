package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static final String URL_ADRESS = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                        .setUserAgent("Google Chrome")
                                .setDefaultRequestConfig(RequestConfig.custom()
                                        .setConnectTimeout(5000)
                                        .setSocketTimeout(30000)
                                        .setRedirectsEnabled(false)
                                        .build())
                                        .build();
        HttpGet request = new HttpGet(URL_ADRESS);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        CloseableHttpResponse response = httpClient.execute(request);

        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

        List<Cats> cats = mapper.readValue(
                response.getEntity().getContent(),
                new TypeReference<>() {
                }
        );
        cats.stream().filter(value -> value.getUpvotes() != 0 && value.getUpvotes() > 0).forEach(System.out::println);

        response.close();
        httpClient.close();
    }
}