package com.example.demo.controller;

import com.example.demo.service.LineBotService;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.SneakyThrows;

@LineMessageHandler
public class LineBotController {

    private LineBotService lineBotService;

    public LineBotController(LineBotService lineBotService) {
        this.lineBotService = lineBotService;
    }

    @SneakyThrows
    @EventMapping
    public Message handleTextMessage(MessageEvent<TextMessageContent> e) {
        return lineBotService.handleMessage(e);
    }
}