package com.tinqin.storage.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import com.tinqin.zoostore.restexport.ZooStoreRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZooStoreRestClientFactory {

    @Bean
    public ZooStoreRestClient getZooStoreRestClient(){
        ObjectMapper objectMapper = new ObjectMapper();
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(ZooStoreRestClient.class, "http://localhost:8080");
    }
}
