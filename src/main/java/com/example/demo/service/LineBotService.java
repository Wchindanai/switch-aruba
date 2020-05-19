package com.example.demo.service;

import com.example.demo.entity.InterfaceResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
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
        var message = e.getMessage().getText();
        if (message.equalsIgnoreCase("interface status") || message.equalsIgnoreCase("interface")) {
            return handleGetInterface();
        }
        return new TextMessage(e.getMessage().getText());
    }

    public Message handleGetInterface() throws JsonProcessingException {
        var response = restTemplate.getForEntity(
                "http://100.98.63.125/html/json.html?method:getIntList=&start=0&limit=24",
                String.class);
        var interfaceResponse = mapper.readValue(response.getBody(), InterfaceResponse.class);
        var textResponse = "";
        interfaceResponse.getResults().forEach(interfaceDetail -> textResponse.concat("port: ")
                .concat(interfaceDetail.getPort().concat(" status: ")
                        .concat(interfaceDetail.getStatus().concat(" mode: ")
                                .concat(interfaceDetail.getMode()))));
        return new TextMessage(textResponse);
    }


}
