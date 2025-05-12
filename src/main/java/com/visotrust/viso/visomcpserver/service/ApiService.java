/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiToken;

    public ApiService(
            RestTemplate restTemplate,
            @Value("${visotrust.api.base-url}") String baseUrl,
            @Value("${visotrust.api.token}") String apiToken) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.apiToken = apiToken;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        return headers;
    }

    public <T> T get(String path, Class<T> responseType) {
        HttpEntity<?> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<T> response =
                restTemplate.exchange(baseUrl + path, HttpMethod.GET, entity, responseType);
        return response.getBody();
    }

    public <T> List<T> getList(String path, Class<T> elementType) {
        HttpEntity<?> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<List<T>> response =
                restTemplate.exchange(
                        baseUrl + path,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public <T, R> R post(String path, T request, Class<R> responseType) {
        HttpHeaders headers = createHeaders();
        HttpEntity<T> entity = new HttpEntity<>(request, headers);
        ResponseEntity<R> response =
                restTemplate.exchange(baseUrl + path, HttpMethod.POST, entity, responseType);
        return response.getBody();
    }

    public <T, R> R post(String path, T request, ParameterizedTypeReference<R> responseType) {
        HttpHeaders headers = createHeaders();
        HttpEntity<T> entity = new HttpEntity<>(request, headers);
        ResponseEntity<R> response =
                restTemplate.exchange(baseUrl + path, HttpMethod.POST, entity, responseType);
        return response.getBody();
    }

    public <T, R> R put(String path, T request, Class<R> responseType) {
        HttpHeaders headers = createHeaders();
        HttpEntity<T> entity = new HttpEntity<>(request, headers);
        ResponseEntity<R> response =
                restTemplate.exchange(baseUrl + path, HttpMethod.PUT, entity, responseType);
        return response.getBody();
    }

    public <T, R> R put(String path, T request, ParameterizedTypeReference<R> responseType) {
        HttpHeaders headers = createHeaders();
        HttpEntity<T> entity = new HttpEntity<>(request, headers);
        ResponseEntity<R> response =
                restTemplate.exchange(baseUrl + path, HttpMethod.PUT, entity, responseType);
        return response.getBody();
    }

    public <T, R> R patch(String path, T request, Class<R> responseType) {
        HttpHeaders headers = createHeaders();
        HttpEntity<T> entity = new HttpEntity<>(request, headers);
        ResponseEntity<R> response =
                restTemplate.exchange(baseUrl + path, HttpMethod.PATCH, entity, responseType);
        return response.getBody();
    }

    public <T, R> R patch(String path, T request, ParameterizedTypeReference<R> responseType) {
        HttpHeaders headers = createHeaders();
        HttpEntity<T> entity = new HttpEntity<>(request, headers);
        ResponseEntity<R> response =
                restTemplate.exchange(baseUrl + path, HttpMethod.PATCH, entity, responseType);
        return response.getBody();
    }

    public void delete(String path) {
        HttpHeaders headers = createHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        restTemplate.exchange(baseUrl + path, HttpMethod.DELETE, entity, Void.class);
    }
}
