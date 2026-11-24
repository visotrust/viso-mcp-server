/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.config;

import java.util.concurrent.TimeUnit;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.cfg.EnumFeature;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class ApiClientConfig {

    @Value("${visotrust.api.timeout:30000}")
    private int timeout;

    @Value("${visotrust.api.connect-timeout:5000}")
    private int connectTimeout;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(messageConverters());
        restTemplate.setRequestFactory(requestFactory());
        return restTemplate;
    }

    private HttpComponentsClientHttpRequestFactory requestFactory() {
        RequestConfig requestConfig =
                RequestConfig.custom()
                        .setConnectTimeout(Timeout.of(connectTimeout, TimeUnit.MILLISECONDS))
                        .setResponseTimeout(Timeout.of(timeout, TimeUnit.MILLISECONDS))
                        .build();

        CloseableHttpClient httpClient =
                HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    // The Client API keeps adding enum values; one unknown value must not fail a whole response.
    private static HttpMessageConverters messageConverters() {
        JsonMapper.Builder lenientEnumMapperBuilder =
                JsonMapper.builder().enable(EnumFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);

        return HttpMessageConverters.forClient()
                .registerDefaults()
                .withJsonConverter(new JacksonJsonHttpMessageConverter(lenientEnumMapperBuilder))
                .build();
    }
}
