package com.vet24.web.controllers.discord;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vet24.discord.feign.DiscordClient;
import com.vet24.discord.models.dto.discord.MessageDto;
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

    private final String webhook = "/993487572003213342/LV3qfF2IcKhsKIQQrv4TPD6w180ALKTXJh0gmJrlO1pg1JLfM1NRzLb3rl1VaQSOKIRG";

    @RequestMapping(value = webhook,
            produces = "application/json",
            method = RequestMethod.GET)
    @Override
    public String executeWebhook() throws JsonProcessingException {
        return discordClient.executeWebhook();
    }
    @Operation(summary = "send discord message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Send message",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "404", description = "Error 404",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Error 400",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class)))
    })

    @RequestMapping(value = webhook,
            consumes = "application/json",
            method = RequestMethod.POST)
    @Override
    public void send(@RequestBody MessageDto message) throws JsonProcessingException {
        discordClient.send(message);
    }

    @RequestMapping(value = webhook + "/{thread_id}",
            consumes = "application/json",
            method = RequestMethod.POST)
    @Override
    public void sendInThread(@RequestBody MessageDto message, @PathVariable Long thread_id) throws JsonProcessingException {
        discordClient.send(message);
    }
}
