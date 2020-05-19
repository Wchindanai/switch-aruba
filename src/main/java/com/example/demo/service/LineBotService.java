package com.example.demo.service;

import com.example.demo.entity.InterfaceResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
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
        }
        return new TextMessage(e.getMessage().getText());
    }

    public Message handleGetInterface() throws JsonProcessingException {
        log.info("Call API");
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://100.98.63.125/html/json.html?method:getIntList=&start=0&limit=24",
                String.class);
        log.info("API response:{}", response.getBody());
        InterfaceResponse interfaceResponse = mapper
                .readValue(response.getBody(), InterfaceResponse.class);
        String textResponse = "";
        interfaceResponse.getResults().forEach(interfaceDetail -> textResponse.concat("port: ")
                .concat(interfaceDetail.getPort().concat(" status: ")
                        .concat(interfaceDetail.getStatus().concat(" mode: ")
                                .concat(interfaceDetail.getMode()))));
        log.info("response :{}", textResponse);
        return new TextMessage(textResponse);
    }


}
