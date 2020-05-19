package com.example.demo.service;

import com.example.demo.entity.InterfaceResponse;
import com.example.demo.util.StringFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class LineBotService {

    private RestTemplate restTemplate;
    private ObjectMapper mapper = new ObjectMapper()
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

    public LineBotService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Message handleMessage(
            MessageEvent<TextMessageContent> e) throws JsonProcessingException {
        String message = e.getMessage().getText();
        log.info("message: {}", message);
        if (message.equalsIgnoreCase("interface status") || message.equalsIgnoreCase("interface")) {
            return handleGetInterface();
        } else if (message.equalsIgnoreCase("switch ip")) {
        }
        return new TextMessage(e.getMessage().getText());
    }

    public Message handleGetInterface() throws JsonProcessingException {
        log.info("Call API");
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://tataponwft.ddns.net/html/json.html?method:getIntList=&start=0&limit=24",
                String.class);
        log.info("API response:{}", response.getBody());
        InterfaceResponse interfaceResponse = mapper
                .readValue(response.getBody(), InterfaceResponse.class);
        AtomicReference<String> textResponse = new AtomicReference<>("");
        interfaceResponse.getResults().forEach(interfaceDetail -> {
            if (Objects.isNull(interfaceDetail.getNeighbor())) {
                textResponse
                        .set(StringFormat
                                .format("{} port: {} status: {}, mode: {}, neighbor: {} \n",
                                        textResponse.get(),
                                        interfaceDetail.getPort(), interfaceDetail.getStatus(),
                                        interfaceDetail.getMode(), ""));
            } else {
                textResponse.set(StringFormat
                        .format("{} port: {} status: {}, mode: {}, neighbor: {} \n",
                                textResponse.get(),
                                interfaceDetail.getPort(),
                                interfaceDetail.getStatus(),
                                interfaceDetail.getMode(),
                                interfaceDetail.getNeighbor().getName()));
            }

        });
        log.info("response :{}", textResponse);
        return new TextMessage(textResponse.get());
    }


}
