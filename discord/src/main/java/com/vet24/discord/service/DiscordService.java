package com.vet24.discord.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vet24.discord.models.dto.discord.MessageDto;
import org.springframework.web.bind.annotation.*;

public interface DiscordService {
    @RequestMapping(value = "/api/webhooks/993487572003213342/LV3qfF2IcKhsKIQQrv4TPD6w180ALKTXJh0gmJrlO1pg1JLfM1NRzLb3rl1VaQSOKIRG",
            produces = "application/json",
            method = RequestMethod.GET)
    String executeWebhook() throws JsonProcessingException;

    @RequestMapping(value = "/api/webhooks/993487572003213342/LV3qfF2IcKhsKIQQrv4TPD6w180ALKTXJh0gmJrlO1pg1JLfM1NRzLb3rl1VaQSOKIRG",
            consumes = "application/json",
            method = RequestMethod.POST)
    void send(@RequestBody MessageDto message) throws JsonProcessingException;

    @RequestMapping(value = "/api/webhooks/993487572003213342/LV3qfF2IcKhsKIQQrv4TPD6w180ALKTXJh0gmJrlO1pg1JLfM1NRzLb3rl1VaQSOKIRG" + "/{thread_id}",
            consumes = "application/json",
            method = RequestMethod.POST)
    void sendInThread(@RequestBody MessageDto message, @PathVariable Long thread_id) throws JsonProcessingException;
}
