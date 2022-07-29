package com.vet24.web.controllers.discord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vet24.discord.feign.DiscordClient;
import com.vet24.discord.models.dto.discord.MessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
//@RequestMapping("")
@Tag(name = "discord-controller", description = "operations with discord")
@EnableFeignClients(basePackageClasses = DiscordClient.class)
public class DiscordController {

    private final DiscordClient discordClient;

    public DiscordController(DiscordClient discordClient) {
        this.discordClient = discordClient;
    }

    @RequestMapping(
            produces = "application/json",
            method = RequestMethod.GET)
    public String getWebhook() throws JsonProcessingException {
        return discordClient.getWebhook();
    }

    @RequestMapping(value = "/messages/{message_id}",
            produces = "application/json",
            method = RequestMethod.GET)
    public ResponseEntity<MessageDto> getMessageToId(@PathVariable Long message_id,
                                                     @RequestParam(required = false) Long thread_id) throws JsonProcessingException {
        return discordClient.getMessageToId(message_id, thread_id);
    }

    @Operation(summary = "send discord message")

    @RequestMapping(
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity<MessageDto> send(@RequestBody MessageDto message,
                                           @RequestParam(required = false) Long thread_id) throws JsonProcessingException {

        return discordClient.send(message, thread_id);
    }

    @PatchMapping( value = "/messages/{message_id}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<MessageDto> updateMessage(@PathVariable Long message_id,
                                                    @RequestParam(required = false) Long thread_id,
                                                    @RequestBody MessageDto message) throws JsonProcessingException {
        return discordClient.updateMessage(message_id,thread_id,message);
    }

    @RequestMapping(value = "/messages/{message_id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<MessageDto> deleteMessageToId(@PathVariable Long message_id,
                                                        @RequestParam(required = false) Long thread_id) throws JsonProcessingException {
        return discordClient.deleteMessageToId(message_id, thread_id);
    }

}
