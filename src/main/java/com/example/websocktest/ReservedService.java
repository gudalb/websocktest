package com.example.websocktest;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ReservedService {

    private ArrayList<Item> messages = new ArrayList();
    private int expiresMin = 1;

    public ArrayList<Item> handleMessage(Item message) {
        clearOld(expiresMin, messages);

        if(message.getName().equals("deleteall")) {
            messages.removeIf(item -> item.getId().equals(message.getId()));
            return messages;
        }

        boolean itemExists = false;

        for (Item item:messages) {
            if(item.getName().equals(message.getName())
                    && item.getId().equals(message.getId())
            ) {
                itemExists = true;
                item.setQuantity(message.getQuantity());
                item.setTime(LocalDateTime.now());
            }
        }

        if(itemExists)
            return messages;
        else
            messages.add(message);

        return messages;
    }

    public ArrayList<Item> getMessages() {
        clearOld(expiresMin, messages);
        return messages;
    }

    public void clearOld(int minutes, ArrayList<Item> itemList) {
        LocalDateTime compareDate = LocalDateTime.now().minusMinutes(minutes);
        itemList.removeIf(item -> item.getTime().isBefore(compareDate));
    }
}
