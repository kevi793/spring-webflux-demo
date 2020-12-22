package com.example.springwebfluxdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {

    public static final String HTTP_LOCALHOST_8082 = "http://localhost:8082";
    public static final String API_HELLO_1000 = "/api/hello/1000";

    @GetMapping("/text")
    public Mono<String> text() {
        return Mono.just("Hello World");
    }

    /*
     * AB Command:
     * ab -n 2000 -c 200 -r http://localhost:8080/remote-web-client
     * ab -n 4000 -c 250 -r http://localhost:8080/remote-web-client
     * */
    @GetMapping("/remote-web-client")
    public Mono<String> webclient() throws InterruptedException {
//        System.out.println("Thread name before is " + Thread.currentThread().getName());
        return WebClient.builder()
                .baseUrl(HTTP_LOCALHOST_8082)
                .build()
                .get()
                .uri(API_HELLO_1000).retrieve()
                .bodyToMono(String.class)
                .map(clientResponse -> {
//                    System.out.println("Insire map Hello came here" + Thread.currentThread().getName());
                    return clientResponse;
                });
    }

}
