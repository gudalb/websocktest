package com.example.websocktest;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class ItemController {

    ArrayList<Item> messages = new ArrayList<Item>();
    ReservedService reservedService;

    public ItemController(ReservedService reservedService) {
        this.reservedService = reservedService;
    }

    @MessageMapping("/item")
    @SendTo("/topic/messages")
    public ArrayList<Item> reserved(Item message) throws Exception {
        return reservedService.handleMessage(message);
    }

    @MessageMapping("/connect")
    @SendTo("/topic/messages")
    public ArrayList<Item> connect() throws Exception {
        return reservedService.getMessages();
    }

}