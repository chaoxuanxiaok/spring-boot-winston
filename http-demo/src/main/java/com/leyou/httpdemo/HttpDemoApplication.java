package com.leyou.httpdemo;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class HttpDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HttpDemoApplication.class, args);
	}

	/**
	 * 在项目中注册一个RestTemplate对象，可以在启动类位置注册
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {	//默认的RestTemplate	底层是走JDK的URLConnection
		return new RestTemplate();
	}
}
