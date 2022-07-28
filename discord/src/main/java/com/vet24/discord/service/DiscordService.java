package com.vet24.discord.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vet24.discord.models.dto.discord.MessageDto;
import com.vet24.discord.models.dto.discord.MessageEmbedDto;
import org.springframework.web.bind.annotation.*;

public interface DiscordService {
    @RequestMapping(value = "/api/webhooks/1002137746204278864/fFu57DcvOGhFfdI20eEzI6XuWaRdQaFKvo4NPi3P0Ey2T8b_rk3E1p7pTRMZ5vJc_ZDt",
            produces = "application/json",
            method = RequestMethod.GET)
    String executeWebhook() throws JsonProcessingException;

    @RequestMapping(value = "/api/webhooks/1002137746204278864/fFu57DcvOGhFfdI20eEzI6XuWaRdQaFKvo4NPi3P0Ey2T8b_rk3E1p7pTRMZ5vJc_ZDt",
            consumes = "application/json",
            method = RequestMethod.POST)
    MessageDto send(@RequestBody MessageDto message, @RequestParam(required = false) Long thread_id,
                    @RequestParam(defaultValue = "true") boolean wait) throws JsonProcessingException;

//    @RequestMapping(value = "/api/webhooks/1002137746204278864/fFu57DcvOGhFfdI20eEzI6XuWaRdQaFKvo4NPi3P0Ey2T8b_rk3E1p7pTRMZ5vJc_ZDt",
//            consumes = "application/json",
//            method = RequestMethod.POST)
//
//    MessageEmbedDto sendEmbedMessage(@RequestBody MessageEmbedDto message, @RequestParam(required = false) Long thread_id, @RequestParam(defaultValue = "true") boolean wait) throws JsonProcessingException;
}
