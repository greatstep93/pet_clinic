package com.vet24.web.controllers.discord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vet24.discord.feign.DiscordClient;
import com.vet24.discord.models.dto.discord.MessageDto;
import com.vet24.discord.models.dto.discord.MessageEmbedDto;
import com.vet24.discord.service.DiscordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/webhooks")
@Tag(name = "discord-controller", description = "operations with discord")
@EnableFeignClients(basePackageClasses = DiscordClient.class)
public class DiscordController implements DiscordService {

    private DiscordClient discordClient;

    public DiscordController(DiscordClient discordClient) {
        this.discordClient = discordClient;
    }

    private final String webhook = "/1002137746204278864/fFu57DcvOGhFfdI20eEzI6XuWaRdQaFKvo4NPi3P0Ey2T8b_rk3E1p7pTRMZ5vJc_ZDt";

    @RequestMapping(value = webhook,
            produces = "application/json",
            method = RequestMethod.GET)
    @Override
    public String executeWebhook() throws JsonProcessingException {
        return discordClient.executeWebhook();
    }

    @Operation(summary = "send discord message")

    @RequestMapping(value = webhook,
            consumes = "application/json",
            method = RequestMethod.POST)
    @Override
    public MessageDto send(@RequestBody MessageDto message, @RequestParam(required = false) Long thread_id, @RequestParam(defaultValue = "true") boolean wait) throws JsonProcessingException {
        return discordClient.send(message, thread_id, wait);
    }

//    @Operation(summary = "send discord Embed Message")
//
//    @RequestMapping(value = webhook,
//            consumes = "application/json",
//            method = RequestMethod.POST)
//    @Override
//    public MessageEmbedDto sendEmbedMessage(@RequestBody MessageEmbedDto message, @RequestParam(required = false) Long thread_id, @RequestParam(defaultValue = "true") boolean wait) throws JsonProcessingException {
//        return discordClient.sendEmbedMessage(message, thread_id, wait);
//    }
}
