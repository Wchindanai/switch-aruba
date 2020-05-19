package com.example.demo;

import com.example.demo.entity.InterfaceResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LineBotApplication  {
	public static void main(String[] args) {
		SpringApplication.run(LineBotApplication.class, args);
	}
}
